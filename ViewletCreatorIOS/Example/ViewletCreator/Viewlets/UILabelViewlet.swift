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
    
    func update(view: UIView, attributes: [String: Any], parent: UIView?, binder: ViewletBinder?) {
        if let label = view as? UILabel {
            // Text
            label.text = NSLocalizedString(attributes["text"] as? String ?? "", comment: "")
            
            // Text style
            let fontSize = attributes["textSize"] as? CGFloat ?? 17
            if let font = attributes["font"] as? String {
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
            label.textColor = ViewletConvUtil.asColor(value: attributes["textColor"] as Any) ?? UIColor.darkText
            
            // Other properties
            label.numberOfLines = attributes["maxLines"] as? Int ?? 0
            if let textAlignment = attributes["textAlignment"] as? String {
                if textAlignment == "center" {
                    label.textAlignment = .center
                } else if textAlignment == "right" {
                    label.textAlignment = .right
                }
            }

            // Standard view attributes
            UIViewViewlet.applyDefaultAttributes(view: view, attributes: attributes)
        }
    }
    
    func canRecycle(view: UIView, attributes: [String : Any]) -> Bool {
        return view is UILabel
    }

}
