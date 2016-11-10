//
//  TextViewViewlet.swift
//  Viewlet creator example
//
//  Create a simple text view
//

import UIKit
import ViewletCreator

class TextViewViewlet: Viewlet {

    func create() -> UIView {
        return UILabel()
    }
    
    func update(view: UIView, attributes: [String : Any?], parent: UIView?, binder: ViewletBinder?) {
        if let textView = view as? UILabel {
            if let text = attributes["text"] as? String {
                textView.text = text
            }
            let fontSize = attributes["textSize"] as? CGFloat ?? 17
            if let font = attributes["font"] as? String {
                textView.font = UIFont(name: font, size: fontSize)
            } else {
                textView.font = UIFont.systemFont(ofSize: fontSize)
            }
            textView.numberOfLines = attributes["maxLines"] as? Int ?? 0
            if let textAlignment = attributes["textAlignment"] as? String {
                if textAlignment == "center" {
                    textView.textAlignment = .center
                } else if textAlignment == "right" {
                    textView.textAlignment = .right
                }
            }
        }
    }
    
    func canRecycle(view: UIView, attributes: [String : Any?]) -> Bool {
        return view is UILabel
    }

}
