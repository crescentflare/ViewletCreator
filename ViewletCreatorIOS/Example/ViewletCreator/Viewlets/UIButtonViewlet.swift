//
//  UIButtonViewlet.swift
//  Viewlet creator example
//
//  Create a simple button
//

import UIKit
import ViewletCreator

class UIButtonViewlet: Viewlet {

    func create() -> UIView {
        return UIButton()
    }
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) {
        if let button = view as? UIButton {
            // Text
            button.setTitle(NSLocalizedString(ViewletConvUtil.asString(value: attributes["text"] as Any) ?? "", comment: ""), for: .normal)
            
            // Text style
            let fontSize = ViewletConvUtil.asPointValue(value: attributes["textSize"] as Any) ?? 17
            if let font = ViewletConvUtil.asString(value: attributes["font"] as Any) {
                if font == "bold" {
                    button.titleLabel?.font = UIFont.boldSystemFont(ofSize: fontSize)
                } else if font == "italics" {
                    button.titleLabel?.font = UIFont.italicSystemFont(ofSize: fontSize)
                } else {
                    button.titleLabel?.font = UIFont(name: font, size: fontSize)
                }
            } else {
                button.titleLabel?.font = UIFont.systemFont(ofSize: fontSize)
            }
            button.setTitleColor(ViewletConvUtil.asColor(value: attributes["textColor"] as Any) ?? view.tintColor, for: .normal)
            
            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
        }
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UIButton
    }

}
