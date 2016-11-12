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
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?)
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

    private var registeredViewlets: [String: Viewlet] = [:]
    private var registeredStylers: [(_ view: UIView, _ attributes: [String: Any], _ parent: UIView?) -> Void] = []
    

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
    
    public static func registerStyler(updater: @escaping (_ view: UIView, _ attributes: [String: Any], _ parent: UIView?) -> Void) {
        shared.registeredStylers.append(updater)
    }
    

    // ---
    // MARK: Create and update
    // ---

    public static func create(attributes: [String: Any], parent: UIView? = nil, binder: ViewletBinder? = nil) -> UIView? {
        if let viewletName = findViewletNameInAttributes(attributes) {
            if let viewlet = shared.registeredViewlets[viewletName] {
                if let viewletAttributes = attributes[viewletName] as? [String: Any] {
                    let view = viewlet.create()
                    for styler in shared.registeredStylers {
                        styler(view, viewletAttributes, parent)
                    }
                    viewlet.update(view: view, attributes: viewletAttributes, parent: parent, binder: binder)
                    return view
                }
            }
        }
        return nil
    }
    
    public static func inflateOn(view: UIView, name: String, attributes: [String: Any]?, parent: UIView? = nil, binder: ViewletBinder? = nil) {
        if attributes == nil {
            return
        }
        if let viewlet = shared.registeredViewlets[name] {
            for styler in shared.registeredStylers {
                styler(view, attributes!, parent)
            }
            viewlet.update(view: view, attributes: attributes!, parent: parent, binder: binder)
        }
    }
    
    public static func canRecycle(view: UIView?, attributes: [String: Any]?) -> Bool {
        if view != nil && attributes != nil {
            var viewletName: String?
            var viewlet: Viewlet?
            for (key, _) in attributes! {
                if shared.registeredViewlets[key] != nil {
                    viewletName = key
                    viewlet = shared.registeredViewlets[key]
                    break
                }
            }
            if viewletName != nil {
                if let viewletAttributes = attributes![viewletName!] as? [String: Any] {
                    return viewlet?.canRecycle(view: view!, attributes: viewletAttributes) ?? false
                }
            }
        }
        return false
    }
    
    public static func findViewletInAttributes(_ attributes: [String: Any]) -> Viewlet? {
        for (key, _) in attributes {
            if let viewlet = shared.registeredViewlets[key] {
                return viewlet
            }
        }
        return nil
    }
    
    public static func findViewletNameInAttributes(_ attributes: [String: Any]) -> String? {
        for (key, _) in attributes {
            if shared.registeredViewlets[key] != nil {
                return key
            }
        }
        return nil
    }
    
}
