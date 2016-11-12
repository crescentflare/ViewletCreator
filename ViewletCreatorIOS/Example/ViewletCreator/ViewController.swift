//
//  ViewController.swift
//  Viewlet creator example
//
//  The main example view controller
//

import UIKit
import ViewletCreator

class ViewController: UIViewController, LiveJsonDelegate, UITextFieldDelegate {

    // --
    // MARK: Members
    // --
    
    var liveLayout: LiveJson?
    var serverAddressField: UITextField?
    var serverEnabledSwitch: UISwitch?
    var pollingEnabledSwitch: UISwitch?
    var playgroundButton: UIButton?
    var loadError: UILabel?

    
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
        navigationItem.title = "Viewlet example"
        
        // Live loading or file loading
        updateState()
    }


    // --
    // MARK: Update state based on settings
    // --
    
    func updateState() {
        if Settings.shared.serverEnabled {
            liveLayout = LiveJson(fileName: "layout_main.json")
            liveLayout?.polling = Settings.shared.autoRefresh
            liveLayout?.delegate = self
        } else {
            liveLayout = nil
            if let jsonData = ViewletLoader.attributesFrom(jsonFile: "Layouts/layout_main") {
                layoutLoaded(jsonData: jsonData)
                loadError?.isHidden = true
            }
        }
    }

    
    // --
    // MARK: Inflation
    // --
    
    func layoutLoaded(jsonData: [String: Any]) {
        // Inflation
        let binder = ViewletDictBinder()
        ViewletCreator.inflateOn(view: view, name: "viewContainer", attributes: jsonData, binder: binder)
        
        // Configure server address field
        serverAddressField = binder.findByReference("serverAddress") as? UITextField
        serverAddressField?.delegate = self
        serverAddressField?.text = Settings.shared.serverAddress
        
        // Configure switches
        serverEnabledSwitch = binder.findByReference("serverSwitch") as? UISwitch
        serverEnabledSwitch?.addTarget(self, action: #selector(serverEnabledChanged(_:)), for: .valueChanged)
        serverEnabledSwitch?.setOn(Settings.shared.serverEnabled, animated: false)
        pollingEnabledSwitch = binder.findByReference("pollingSwitch") as? UISwitch
        pollingEnabledSwitch?.addTarget(self, action: #selector(pollingEnabledChanged(_:)), for: .valueChanged)
        pollingEnabledSwitch?.setOn(Settings.shared.autoRefresh, animated: false)
        
        // Button to open the playground viewcontroller
        playgroundButton = binder.findByReference("playgroundButton") as? UIButton
        playgroundButton?.addTarget(self, action: #selector(openPlayground(_:)), for: .touchUpInside)
        
        // Error message
        loadError = binder.findByReference("loadError") as? UILabel
    }

    
    // --
    // MARK: Event handling
    // --

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        serverAddressField?.resignFirstResponder()
        Settings.shared.serverAddress = textField.text ?? ""
        updateState()
        return true
    }
    
    func serverEnabledChanged(_ switchControl: UISwitch) {
        Settings.shared.serverEnabled = switchControl.isOn
        updateState()
    }

    func pollingEnabledChanged(_ switchControl: UISwitch) {
        Settings.shared.autoRefresh = switchControl.isOn
        updateState()
    }
    
    func openPlayground(_ button: UIButton) {
        //
    }
    
    
    // --
    // MARK: Live viewlet handling
    // --
    
    func jsonUpdated(sender: LiveJson) {
        layoutLoaded(jsonData: sender.data!)
        loadError?.isHidden = true
    }
    
    func jsonFailed(sender: LiveJson) {
        if loadError?.isHidden ?? true {
            if let jsonData = ViewletLoader.attributesFrom(jsonFile: "Layouts/layout_main") {
                layoutLoaded(jsonData: jsonData)
                loadError?.isHidden = false
            }
        }
    }

}
