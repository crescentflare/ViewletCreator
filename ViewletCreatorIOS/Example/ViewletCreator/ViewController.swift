//
//  ViewController.swift
//  Viewlet creator example
//
//  The main example view controller
//

import UIKit
import ViewletCreator

class ViewController: UIViewController, LiveJsonDelegate {

    // --
    // MARK: Members
    // --
    
    var liveLayout: LiveJson?

    
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
        
        // Live loading or file loading
        if Settings.shared.networkEnabled() {
            liveLayout = LiveJson(fileName: "layout_main.json")
            liveLayout?.polling = Settings.shared.autoRefresh()
            liveLayout?.delegate = self
        } else {
            liveLayout = nil
            if let jsonData = ViewletLoader.attributesFrom(jsonFile: "Layouts/layout_main") {
                layoutLoaded(jsonData: jsonData)
            }
        }
    }


    // --
    // MARK: Inflation
    // --
    
    func layoutLoaded(jsonData: [String: Any]) {
        let subViews = view.subviews
        for subView in subViews {
            subView.removeFromSuperview()
        }
        if let textViewData = jsonData["textView"] as? [String: Any] {
            if let view = ViewletCreator.create(name: "textView", attributes: textViewData) {
                view.sizeToFit()
                self.view.addSubview(view)
            }
        }
    }

    
    // --
    // MARK: Live viewlet handling
    // --
    
    func jsonUpdated(sender: LiveJson) {
        layoutLoaded(jsonData: sender.data!)
    }
    
    func jsonFailed(sender: LiveJson) {
    }

}
