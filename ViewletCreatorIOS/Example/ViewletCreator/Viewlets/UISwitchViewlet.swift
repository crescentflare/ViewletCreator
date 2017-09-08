//
//  UISwitchViewlet.swift
//  Viewlet creator example
//
//  Create a simple switch
//

import UIKit
import ViewletCreator

class UISwitchViewlet: Viewlet {

    func create() -> UIView {
        return UISwitch()
    }
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) -> Bool {
        if let switchControl = view as? UISwitch {
            // Default state
            switchControl.setOn(ViewletConvUtil.asBool(value: attributes["on"]) ?? false, animated: false)

            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
            return true
        }
        return false
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UISwitch
    }

}
