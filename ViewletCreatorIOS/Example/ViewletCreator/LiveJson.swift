//
//  LiveJson.swift
//  Viewlet creator example
//
//  Synchronize (and optionally polls) a JSON file from the mock server and notify for updates
//

import UIKit
import Alamofire

protocol LiveJsonDelegate: class {
    
    func jsonUpdated(sender: LiveJson)
    func jsonFailed(sender: LiveJson)
    
}

class LiveJson {

    // --
    // MARK: Members
    // --

    var data: [String: Any]?
    private weak var currentDelegate: LiveJsonDelegate?
    private let fileName: String
    private var currentHash = "_none_"
    private var obtainingData = false
    private var enablePolling = false
    
    
    // --
    // MARK: Setting values
    // --
    
    var polling: Bool {
        get { return enablePolling }
        set {
            enablePolling = newValue
            if enablePolling && delegate != nil {
                loadData()
            }
        }
    }
    
    var delegate: LiveJsonDelegate? {
        get { return currentDelegate }
        set {
            currentDelegate = newValue
            if enablePolling && delegate != nil {
                loadData()
            }
        }
    }

    
    // --
    // MARK: Initialization
    // --

    init(fileName: String) {
        self.fileName = fileName
        loadData()
    }

    
    // --
    // MARK: Handle data
    // --

    private func loadData() {
        if !obtainingData {
            obtainingData = true
            Alamofire.request(Settings.shared.serverAddress() + "/layouts_ios/" + fileName).responseJSON { response in
                var success = false
                self.obtainingData = false
                if let newHash = response.data?.md5() {
                    if newHash != self.currentHash {
                        self.currentHash = newHash
                        self.data = response.result.value as? [String: Any]
                        if self.data != nil {
                            self.delegate?.jsonUpdated(sender: self)
                        }
                    }
                    success = self.data != nil
                }
                if !success {
                    self.delegate?.jsonFailed(sender: self)
                }
                if self.enablePolling && self.delegate != nil {
                    let delayTime = DispatchTime.now() + Double(Int64(1 * Double(NSEC_PER_SEC))) / Double(NSEC_PER_SEC)
                    DispatchQueue.main.asyncAfter(deadline: delayTime) {
                        self.loadData()
                    }
                }
            }
        }
    }
    
}
