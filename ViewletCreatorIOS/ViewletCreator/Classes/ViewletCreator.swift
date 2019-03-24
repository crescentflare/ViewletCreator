//
//  ViewletCreator.swift
//  Viewlet creator Pod
//
//  Library: viewlet creator
//  The main interface to register and create new viewlets
//

import UIKit

public protocol Viewlet {
    
    func create() -> UIView
    @discardableResult func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) -> Bool
    func canRecycle(view: UIView, attributes: [String: Any]) -> Bool
    
}

public class ViewletCreator {
    
    // ---
    // MARK: Singleton instance
    // ---

    private static let shared = ViewletCreator()
    
    
    // ---
    // MARK: Members
    // ---

    private var registeredViewlets = [String: Viewlet]()
    private var registeredStyles = [String: [String: [String: Any]]]()
    private var mergeSubAttributes = [String]()
    private var excludeAttributes = [String]()
    

    // ---
    // MARK: Initialization
    // ---
    
    private init() {
    }
    
    
    // ---
    // MARK: Registration
    // ---
    
    public static func register(name: String, viewlet: Viewlet) {
        shared.registeredViewlets[name] = viewlet
    }
    
    public static func registerStyle(viewletName: String, styleName: String, styleAttributes: [String: Any]?) {
        var viewletStyle = shared.registeredStyles[viewletName]
        if viewletStyle == nil {
            viewletStyle = [:]
        }
        if styleAttributes == nil {
            let _ = viewletStyle?.removeValue(forKey: styleName)
        } else {
            viewletStyle?[styleName] = styleAttributes
        }
        shared.registeredStyles[viewletName] = viewletStyle!
    }
    
    public static func registeredViewletNames() -> [String] {
        return shared.registeredViewlets.keys.map({ $0 })
    }
    
    public static func setMergeSubAttribbutes(_ attributeNames: [String]) {
        shared.mergeSubAttributes = attributeNames
    }
    
    public static func setExcludeAttributes(_ attributeNames: [String]) {
        shared.excludeAttributes = attributeNames
    }
    

    // ---
    // MARK: Create and update
    // ---

    public static func create(attributes: [String: Any], parent: UIView? = nil, binder: ViewletBinder? = nil) -> UIView? {
        if let viewletName = findViewletNameInAttributes(attributes) {
            if let viewlet = shared.registeredViewlets[viewletName] {
                let view = viewlet.create()
                if let mergedAttributes = processedAttributes(given: attributes, fallback: attributesForStyle(viewletName: viewletName, styleName: ViewletConvUtil.asString(value: attributes["viewletStyle"])), mergeSubAttributes: shared.mergeSubAttributes, excludeAttributes: shared.excludeAttributes) {
                    viewlet.update(view: view, attributes: mergedAttributes, parent: parent, binder: binder)
                }
                return view
            }
        }
        return nil
    }
    
    @discardableResult
    public static func inflateOn(view: UIView, attributes: [String: Any]?, parent: UIView? = nil, binder: ViewletBinder? = nil) -> Bool {
        if attributes == nil {
            return false
        }
        if let viewletName = findViewletNameInAttributes(attributes!) {
            if let viewlet = shared.registeredViewlets[viewletName] {
                if let mergedAttributes = processedAttributes(given: attributes, fallback: attributesForStyle(viewletName: viewletName, styleName: ViewletConvUtil.asString(value: attributes?["viewletStyle"])), mergeSubAttributes: shared.mergeSubAttributes, excludeAttributes: shared.excludeAttributes) {
                    return viewlet.update(view: view, attributes: mergedAttributes, parent: parent, binder: binder)
                }
            }
        }
        return false
    }
    
    public static func canRecycle(view: UIView?, attributes: [String: Any]?) -> Bool {
        if view != nil && attributes != nil {
            if let viewlet = findViewletInAttributes(attributes!) {
                return viewlet.canRecycle(view: view!, attributes: attributes!)
            }
        }
        return false
    }
    
    public static func findViewletInAttributes(_ attributes: [String: Any]) -> Viewlet? {
        if let viewletName = attributes["viewlet"] as? String {
            return shared.registeredViewlets[viewletName]
        }
        return nil
    }
    
    public static func findViewletNameInAttributes(_ attributes: [String: Any]) -> String? {
        return attributes["viewlet"] as? String
    }
    
    
    // ---
    // MARK: Sub-viewlet utilities
    // ---
    
    public static func attributesForSubViewlet(_ subViewletItem: Any?) -> [String: Any]? {
        if let attributes = subViewletItem as? [String: Any] {
            if let viewletName = findViewletNameInAttributes(attributes) {
                return processedAttributes(given: attributes, fallback: attributesForStyle(viewletName: viewletName, styleName: ViewletConvUtil.asString(value: attributes["viewletStyle"])), mergeSubAttributes: shared.mergeSubAttributes, excludeAttributes: shared.excludeAttributes)
            }
        }
        return nil
    }

    public static func attributesForSubViewletList(_ subViewletItemList: Any?) -> [[String: Any]] {
        var viewletItemList: [[String: Any]] = []
        if let itemList = subViewletItemList as? [[String: Any]] {
            for item in itemList {
                if let viewletName = findViewletNameInAttributes(item) {
                    if let resultAttributes = processedAttributes(given: item, fallback: attributesForStyle(viewletName: viewletName, styleName: ViewletConvUtil.asString(value: item["viewletStyle"])), mergeSubAttributes: shared.mergeSubAttributes, excludeAttributes: shared.excludeAttributes) {
                        viewletItemList.append(resultAttributes)
                    }
                }
            }
        }
        return viewletItemList
    }


    // ---
    // MARK: Attribute processing
    // ---
    
    private static func attributesForStyle(viewletName: String, styleName: String?) -> [String: Any]? {
        if let viewletStyles = shared.registeredStyles[viewletName] {
            if styleName == "default" {
                return viewletStyles[styleName!]
            }
            return mergedAttributes(given: viewletStyles[styleName ?? ""], fallback: viewletStyles["default"])
        }
        return nil
    }
    
    private static func processedAttributes(given: [String: Any]?, fallback: [String: Any]?, mergeSubAttributes: [String], excludeAttributes: [String]) -> [String: Any]? {
        if var result = mergedAttributes(given: given, fallback: fallback) {
            for mergeSubAttribute in mergeSubAttributes {
                if let item = result[mergeSubAttribute] as? [String: Any] {
                    result = mergedAttributes(given: item, fallback: result) ?? result
                }
            }
            for excludeAttribute in excludeAttributes {
                result.removeValue(forKey: excludeAttribute)
            }
            return result
        }
        return nil
    }
    
    private static func mergedAttributes(given: [String: Any]?, fallback: [String: Any]?) -> [String: Any]? {
        // Just return one of the attributes if the other is null
        if fallback == nil {
            return given
        } else if given == nil {
            return fallback
        }
        
        // Merge and return without modifying the originals
        var merged: [String: Any] = [:]
        for (key, value) in given! {
            merged[key] = value
        }
        for (key, value) in fallback! {
            if merged[key] == nil {
                merged[key] = value
            }
        }
        return merged
    }

}
