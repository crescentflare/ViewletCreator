//
//  UILabelViewlet.swift
//  Viewlet creator example
//
//  Create a simple label
//

import UIKit
import ViewletCreator

class UILabelViewlet: Viewlet {

    func create() -> UIView {
        return UILabel()
    }
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) -> Bool {
        if let label = view as? UILabel {
            // Text
            label.text = NSLocalizedString(ViewletConvUtil.asString(value: attributes["text"]) ?? "", comment: "")
            
            // Text style
            let fontSize = ViewletConvUtil.asDimension(value: attributes["textSize"]) ?? 17
            if let font = ViewletConvUtil.asString(value: attributes["font"]) {
                if font == "bold" {
                    label.font = UIFont.boldSystemFont(ofSize: fontSize)
                } else if font == "italics" {
                    label.font = UIFont.italicSystemFont(ofSize: fontSize)
                } else {
                    label.font = UIFont(name: font, size: fontSize)
                }
            } else {
                label.font = UIFont.systemFont(ofSize: fontSize)
            }
            label.textColor = ViewletConvUtil.asColor(value: attributes["textColor"]) ?? UIColor.darkText
            
            // Other properties
            label.numberOfLines = ViewletConvUtil.asInt(value: attributes["maxLines"]) ?? 0
            if let textAlignment = ViewletConvUtil.asString(value: attributes["textAlignment"]) {
                if textAlignment == "center" {
                    label.textAlignment = .center
                } else if textAlignment == "right" {
                    label.textAlignment = .right
                }
            }

            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
            return true
        }
        return false
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UILabel
    }

}
