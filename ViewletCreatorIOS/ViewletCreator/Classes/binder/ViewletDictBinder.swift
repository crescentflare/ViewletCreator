//
//  ViewletDictBinder.swift
//  Viewlet creator Pod
//
//  Library binder: view dictionary
//  A viewlet binder implementation which contains a dictionary of all referenced views
//

import UIKit

open class ViewletDictBinder : ViewletBinder {
    
    private var boundViews: [String: UIView] = [:]
    
    public init() {
    }
    
    open func onBind(refId: String, view: UIView) {
        boundViews[refId] = view
    }
    
    open func findByReference(_ refId: String) -> UIView? {
        return boundViews[refId]
    }
    
}
