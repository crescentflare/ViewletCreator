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
    
    public static func registeredViewletNames() -> [String] {
        return shared.registeredViewlets.keys.map({ $0 })
    }
    

    // ---
    // MARK: Create and update
    // ---

    public static func create(attributes: [String: Any], parent: UIView? = nil, binder: ViewletBinder? = nil) -> UIView? {
        if let viewlet = findViewletInAttributes(attributes) {
            let view = viewlet.create()
            for styler in shared.registeredStylers {
                styler(view, attributes, parent)
            }
            viewlet.update(view: view, attributes: attributes, parent: parent, binder: binder)
            return view
        }
        return nil
    }
    
    public static func inflateOn(view: UIView, attributes: [String: Any]?, parent: UIView? = nil, binder: ViewletBinder? = nil) {
        if attributes == nil {
            return
        }
        if let viewlet = findViewletInAttributes(attributes!) {
            for styler in shared.registeredStylers {
                styler(view, attributes!, parent)
            }
            viewlet.update(view: view, attributes: attributes!, parent: parent, binder: binder)
        }
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
    
}
