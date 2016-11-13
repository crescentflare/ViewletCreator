//
//  UIViewContainerViewlet.swift
//  Viewlet creator example
//
//  Create a simple view container, subclass UIView to be able to recognize it
//

import UIKit
import ViewletCreator

class UIViewContainer: UIView {
}

class ViewletConstraint: NSLayoutConstraint {
}

class UIViewContainerViewlet: Viewlet {

    // --
    // MARK: Implementation
    // --

    func create() -> UIView {
        return UIViewContainer()
    }
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) {
        if view is UIViewContainer {
            // Add or recycle subviews
            var createdSubviews = 0
            var views = view.subviews
            let internalBinder = ViewletDictBinder()
            removeViewletConstraints(view: view, constraints: view.constraints)
            if let subviews = attributes["subviews"] as? [[String: Any]] {
                for i in 0..<subviews.count {
                    let subview = subviews[i]
                    let viewletName = ViewletCreator.findViewletNameInAttributes(subview)
                    if viewletName != nil && i < views.count && ViewletCreator.canRecycle(view: views[i], attributes: subview) {
                        removeViewletConstraints(view: views[i], constraints: views[i].constraints)
                        ViewletCreator.inflateOn(view: views[i], name: viewletName!, attributes: subview[viewletName!] as? [String: Any], parent: view, binder: binder)
                        if let refId = subview["refId"] as? String {
                            internalBinder.onBind(refId: refId, view: views[i])
                            if binder != nil {
                                binder?.onBind(refId: refId, view: views[i])
                            }
                        }
                        createdSubviews += 1
                    } else {
                        if i < views.count {
                            views[i].removeFromSuperview()
                        }
                        if let createdSubview = ViewletCreator.create(attributes: subview, parent: view, binder: binder) {
                            view.insertSubview(createdSubview, at: createdSubviews)
                            if let refId = subview["refId"] as? String {
                                internalBinder.onBind(refId: refId, view: createdSubview)
                                if binder != nil {
                                    binder?.onBind(refId: refId, view: createdSubview)
                                }
                            }
                            createdSubviews += 1
                        }
                    }
                }
                createdSubviews = subviews.count
            }
            views = view.subviews
            for i in createdSubviews..<views.count {
                views[i].removeFromSuperview()
            }
            
            // Apply constraints to subviews
            if let constraintsSet = attributes["constraints"] as? [String: Any] {
                for (key, constraintsSetItem) in constraintsSet {
                    if key != "manual" {
                        if let constraintView = internalBinder.findByReference(key) {
                            if let constraints = constraintsSetItem as? [String: Any] {
                                applyConstraints(view: view, constraintView: constraintView, constraints: constraints, boundViews: internalBinder)
                            }
                        }
                    }
                }
                if let manualConstraints = constraintsSet["manual"] as? [[String: Any]] {
                    applyManualConstraints(selfView: view, constraints: manualConstraints, boundViews: internalBinder)
                }
            }
            
            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
        }
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UIViewContainer
    }
    
    
    // --
    // MARK: Add constraints
    // --

    private func applyConstraints(view: UIView, constraintView: UIView, constraints: [String: Any], boundViews: ViewletDictBinder) {
        // Apply width and height
        constraintView.translatesAutoresizingMaskIntoConstraints = false
        if let widthRelatedConstraint = constraints["width"] as? String {
            if let relatedView = boundViews.findByReference(widthRelatedConstraint) {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .width, relatedBy: .equal, toItem: relatedView, attribute: .width, multiplier: 1, constant: 0))
            }
        } else if let widthConstraint = constraints["width"] as? CGFloat {
            constraintView.addConstraint(ViewletConstraint(item: constraintView, attribute: .width, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: widthConstraint))
        }
        if let heightRelatedConstraint = constraints["height"] as? String {
            if let relatedView = boundViews.findByReference(heightRelatedConstraint) {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .height, relatedBy: .equal, toItem: relatedView, attribute: .height, multiplier: 1, constant: 0))
            }
        } else if let heightConstraint = constraints["height"] as? CGFloat {
            constraintView.addConstraint(ViewletConstraint(item: constraintView, attribute: .height, relatedBy: .equal, toItem: nil, attribute: .notAnAttribute, multiplier: 1, constant: heightConstraint))
        }
        
        // Apply superview edge alignment
        if let superviewAlignment = constraints["alignToParent"] as? Bool {
            if superviewAlignment {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: view, attribute: .left, multiplier: 1, constant: 0))
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: view, attribute: .top, multiplier: 1, constant: 0))
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: view, attribute: .right, multiplier: 1, constant: 0))
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: view, attribute: .bottom, multiplier: 1, constant: 0))
            }
        } else if let superviewAlignments = constraints["alignToParent"] as? [String] {
            for alignment in superviewAlignments {
                if alignment == "left" {
                    view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: view, attribute: .left, multiplier: 1, constant: 0))
                } else if alignment == "top" {
                    view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: view, attribute: .top, multiplier: 1, constant: 0))
                } else if alignment == "right" {
                    view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: view, attribute: .right, multiplier: 1, constant: 0))
                } else if alignment == "bottom" {
                    view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: view, attribute: .bottom, multiplier: 1, constant: 0))
                }
            }
        } else if let superviewAlignmentOffsets = constraints["alignToParent"] as? [String: CGFloat] {
            if let leftAlignment = superviewAlignmentOffsets["left"] {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: view, attribute: .left, multiplier: 1, constant: leftAlignment))
            }
            if let topAlignment = superviewAlignmentOffsets["top"] {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: view, attribute: .top, multiplier: 1, constant: topAlignment))
            }
            if let rightAlignment = superviewAlignmentOffsets["right"] {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: view, attribute: .right, multiplier: 1, constant: -rightAlignment))
            }
            if let bottomAlignment = superviewAlignmentOffsets["bottom"] {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: view, attribute: .bottom, multiplier: 1, constant: -bottomAlignment))
            }
        }
        
        // Apply superview center alignment
        if let superviewCenter = constraints["centerInParent"] as? Bool {
            if superviewCenter {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .centerX, relatedBy: .equal, toItem: view, attribute: .centerX, multiplier: 1, constant: 0))
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .centerY, relatedBy: .equal, toItem: view, attribute: .centerY, multiplier: 1, constant: 0))
            }
        } else if let superviewCenterAxis = constraints["centerInParent"] as? String {
            if superviewCenterAxis == "x" {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .centerX, relatedBy: .equal, toItem: view, attribute: .centerX, multiplier: 1, constant: 0))
            } else if superviewCenterAxis == "y" {
                view.addConstraint(ViewletConstraint(item: constraintView, attribute: .centerY, relatedBy: .equal, toItem: view, attribute: .centerX, multiplier: 1, constant: 0))
            }
        }
        
        // Apply sibling alignment
        if let siblingAlignmentOffsets = constraints["alignToSibling"] as? [String: [String: Any]] {
            for (siblingKey, edges) in siblingAlignmentOffsets {
                if let siblingView = boundViews.findByReference(siblingKey) {
                    if let leftOffset = edges["toLeft"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: siblingView, attribute: .left, multiplier: 1, constant: -leftOffset))
                    }
                    if let topOffset = edges["toTop"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: siblingView, attribute: .top, multiplier: 1, constant: -topOffset))
                    }
                    if let rightOffset = edges["toRight"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: siblingView, attribute: .right, multiplier: 1, constant: rightOffset))
                    }
                    if let bottomOffset = edges["toBottom"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: siblingView, attribute: .bottom, multiplier: 1, constant: bottomOffset))
                    }
                    if let leftOffset = edges["atLeft"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: siblingView, attribute: .left, multiplier: 1, constant: leftOffset))
                    }
                    if let topOffset = edges["atTop"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: siblingView, attribute: .top, multiplier: 1, constant: topOffset))
                    }
                    if let rightOffset = edges["atRight"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: siblingView, attribute: .right, multiplier: 1, constant: rightOffset))
                    }
                    if let bottomOffset = edges["atBottom"] as? CGFloat {
                        view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: siblingView, attribute: .bottom, multiplier: 1, constant: bottomOffset))
                    }
                }
            }
        } else if let siblingAlignment = constraints["alignToSibling"] as? [String: Any] {
            for (siblingKey, siblingConstraint) in siblingAlignment {
                if let siblingView = boundViews.findByReference(siblingKey) {
                    if let siblingEdge = siblingConstraint as? String {
                        if siblingEdge == "toLeft" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: siblingView, attribute: .left, multiplier: 1, constant: 0))
                        } else if siblingEdge == "toTop" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: siblingView, attribute: .top, multiplier: 1, constant: 0))
                        } else if siblingEdge == "toRight" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: siblingView, attribute: .right, multiplier: 1, constant: 0))
                        } else if siblingEdge == "toBottom" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: siblingView, attribute: .bottom, multiplier: 1, constant: 0))
                        } else if siblingEdge == "atLeft" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .left, relatedBy: .equal, toItem: siblingView, attribute: .left, multiplier: 1, constant: 0))
                        } else if siblingEdge == "atTop" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .top, relatedBy: .equal, toItem: siblingView, attribute: .top, multiplier: 1, constant: 0))
                        } else if siblingEdge == "atRight" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .right, relatedBy: .equal, toItem: siblingView, attribute: .right, multiplier: 1, constant: 0))
                        } else if siblingEdge == "atBottom" {
                            view.addConstraint(ViewletConstraint(item: constraintView, attribute: .bottom, relatedBy: .equal, toItem: siblingView, attribute: .bottom, multiplier: 1, constant: 0))
                        }
                    }
                }
            }
        }
        
        // Apply aspect ratio
        if let aspectRatioConstraint = constraints["aspectRatio"] as? CGFloat {
            constraintView.addConstraint(ViewletConstraint(item: constraintView, attribute: .width, relatedBy: .equal, toItem: constraintView, attribute: .height, multiplier: aspectRatioConstraint, constant: 0))
        }
    }
    
    private func applyManualConstraints(selfView: UIView, constraints: [[String: Any]], boundViews: ViewletDictBinder) {
        for constraint in constraints {
            var targetView = selfView
            var item: UIView?
            if let checkTarget = constraint["target"] as? String {
                if let newTarget = viewByReference(selfView: selfView, ref: checkTarget, boundViews: boundViews) {
                    targetView = newTarget
                } else {
                    continue
                }
            }
            item = viewByReference(selfView: selfView, ref: constraint["item"] as? String, boundViews: boundViews)
            if item == nil {
                continue
            }
            targetView.addConstraint(ViewletConstraint(item: item as Any,
                                                        attribute: constraintAttributeFrom(string: constraint["attribute"] as? String),
                                                        relatedBy: constraintRelatedByFrom(string: constraint["relatedBy"] as? String),
                                                        toItem: viewByReference(selfView: selfView, ref: constraint["toItem"] as? String, boundViews: boundViews),
                                                        attribute: constraintAttributeFrom(string: constraint["toAttribute"] as? String),
                                                        multiplier: constraint["multiplier"] as? CGFloat ?? 1,
                                                        constant: constraint["constant"] as? CGFloat ?? 0))
        }
    }
    
    
    // --
    // MARK: Constraint helpers
    // --

    private func viewByReference(selfView: UIView, ref: String?, boundViews: ViewletDictBinder) -> UIView? {
        if ref == nil {
            return nil
        }
        if ref! == "self" {
            return selfView
        }
        return boundViews.findByReference(ref!)
    }
    
    private func constraintAttributeFrom(string: String?) -> NSLayoutAttribute {
        if string != nil {
            if string! == "left" {
                return .left
            } else if string! == "top" {
                return .top
            } else if string! == "right" {
                return .right
            } else if string! == "bottom" {
                return .bottom
            } else if string! == "leftMargin" {
                return .leftMargin
            } else if string! == "topMargin" {
                return .topMargin
            } else if string! == "rightMargin" {
                return .rightMargin
            } else if string! == "bottomMargin" {
                return .bottomMargin
            } else if string! == "centerX" {
                return .centerX
            } else if string! == "centerY" {
                return .centerY
            } else if string! == "centerXWithinMargins" {
                return .centerXWithinMargins
            } else if string! == "centerYWithinMargins" {
                return .centerYWithinMargins
            } else if string! == "width" {
                return .width
            } else if string! == "height" {
                return .height
            } else if string! == "firstBaseline" {
                return .firstBaseline
            } else if string! == "lastBaseline" {
                return .lastBaseline
            } else if string! == "leading" {
                return .leading
            } else if string! == "trailing" {
                return .trailing
            } else if string! == "leadingMargin" {
                return .leadingMargin
            } else if string! == "trailingMargin" {
                return .trailingMargin
            }
        }
        return .notAnAttribute
    }
    
    private func constraintRelatedByFrom(string: String?) -> NSLayoutRelation {
        if string != nil {
            if string! == "greaterThanOrEqual" {
                return .greaterThanOrEqual
            } else if string! == "lessThanOrEqual" {
                return .lessThanOrEqual
            }
        }
        return .equal
    }
    
    private func removeViewletConstraints(view: UIView, constraints: [NSLayoutConstraint]) {
        for constraint in constraints {
            if constraint is ViewletConstraint {
                view.removeConstraint(constraint)
            }
        }
    }

}
