//
//  PlaygroundViewController.swift
//  Viewlet creator example
//
//  A viewcontroller with a layout file to play around with
//

import UIKit
import ViewletCreator

class PlaygroundViewController: UIViewController, LiveJsonDelegate {

    // --
    // MARK: Members
    // --
    
    var liveLayout: LiveJson?

    
    // --
    // MARK: Lifecycle
    // --

    override func loadView() {
        view = UIViewContainer()
        view.backgroundColor = UIColor(white: 1, alpha: 1)
        edgesForExtendedLayout = UIRectEdge()
    }

    override func viewDidLoad() {
        // Initialize navigation bar
        super.viewDidLoad()
        navigationItem.title = "Playground"
        
        // Live loading or file loading
        updateState()
    }


    // --
    // MARK: Update state based on settings
    // --
    
    func updateState() {
        if Settings.shared.serverEnabled {
            liveLayout = LiveJson(fileName: "layout_playground.json")
            liveLayout?.polling = Settings.shared.autoRefresh
            liveLayout?.delegate = self
        } else {
            liveLayout = nil
            if let jsonData = ViewletLoader.attributesFrom(jsonFile: "Layouts/layout_playground") {
                layoutLoaded(jsonData: jsonData)
            }
        }
    }

    
    // --
    // MARK: Inflation
    // --
    
    func layoutLoaded(jsonData: [String: Any]) {
        let binder = ViewletDictBinder()
        ViewletCreator.inflateOn(view: view, attributes: jsonData, binder: binder)
        view.backgroundColor = UIColor(white: 1, alpha: 1)
    }

    
    // --
    // MARK: Live viewlet handling
    // --
    
    func jsonUpdated(sender: LiveJson) {
        layoutLoaded(jsonData: sender.data!)
    }
    
    func jsonFailed(sender: LiveJson) {
        let views = view.subviews
        for view in views {
            view.removeFromSuperview()
        }
        let errorLabel = UILabel()
        errorLabel.text = "Can't connect to server or load layout_playground.json"
        errorLabel.numberOfLines = 0
        errorLabel.textColor = UIColor.red
        view.addSubview(errorLabel)
        errorLabel.translatesAutoresizingMaskIntoConstraints = false
        view.addConstraint(NSLayoutConstraint(item: errorLabel, attribute: .left, relatedBy: .equal, toItem: view, attribute: .left, multiplier: 1, constant: 16))
        view.addConstraint(NSLayoutConstraint(item: errorLabel, attribute: .top, relatedBy: .equal, toItem: view, attribute: .top, multiplier: 1, constant: 16))
        view.addConstraint(NSLayoutConstraint(item: errorLabel, attribute: .right, relatedBy: .equal, toItem: view, attribute: .right, multiplier: 1, constant: -16))
    }

}
