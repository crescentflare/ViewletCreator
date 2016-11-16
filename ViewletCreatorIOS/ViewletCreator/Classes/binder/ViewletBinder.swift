//
//  ViewletBinder.swift
//  Viewlet creator Pod
//
//  Library binder: default protocol
//  A protocol which can be implemented to do custom viewlet binding
//

import UIKit

public protocol ViewletBinder {
    
    func onBind(refId: String, view: UIView)
    
}
