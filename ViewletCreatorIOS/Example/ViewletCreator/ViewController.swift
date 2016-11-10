//
//  ViewController.swift
//  Viewlet creator example
//
//  The main example view controller
//

import UIKit
import ViewletCreator

class ViewController: UIViewController {

    // --
    // MARK: Lifecycle
    // --

    override func loadView() {
        view = UIView()
        view.backgroundColor = UIColor(white: 1, alpha: 1)
        edgesForExtendedLayout = UIRectEdge()
    }

    override func viewDidLoad() {
        // Initialize navigation bar
        super.viewDidLoad()
        navigationItem.title = "Viewlet example"
        
        // Basic viewlet test
        if let jsonData = ViewletLoader.attributesFrom(jsonFile: "Layouts/layout_main")?["textView"] as? [String: Any] {
            if let view = ViewletCreator.create(name: "textView", attributes: jsonData) {
                view.sizeToFit()
                self.view.addSubview(view)
            }
        }
    }

}
