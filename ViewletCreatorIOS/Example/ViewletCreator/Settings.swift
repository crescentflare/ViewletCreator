//
//  Settings.swift
//  Viewlet creator example
//
//  An easy way to handle settings for the viewlet example
//

import UIKit

class Settings {

    // --
    // MARK: Singleton instance
    // --
    
    static var shared: Settings = Settings()
    
    private init() {
    }

    
    // --
    // MARK: Settings access
    // --
    
    var serverEnabled: Bool {
        get { return UserDefaults.standard.bool(forKey: "serverEnabled") }
        set {
            UserDefaults.standard.set(newValue, forKey: "serverEnabled")
        }
    }

    var autoRefresh: Bool {
        get { return UserDefaults.standard.bool(forKey: "autoRefresh") }
        set {
            UserDefaults.standard.set(newValue, forKey: "autoRefresh")
        }
    }
    
    var serverAddress: String {
        get { return UserDefaults.standard.string(forKey: "serverAddress") ?? "http://127.0.0.1:2233" }
        set {
            UserDefaults.standard.set(newValue, forKey: "serverAddress")
        }
    }

}
