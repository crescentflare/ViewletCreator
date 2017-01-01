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
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) {
        if let switchControl = view as? UISwitch {
            // Default state
            switchControl.setOn(ViewletConvUtil.asBool(value: attributes["on"]) ?? false, animated: false)

            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
        }
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UISwitch
    }

}
