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

    func networkEnabled() -> Bool {
        return true // Stub, replace with userdefaults
    }

    func autoRefresh() -> Bool {
        return true // Stub, replace with userdefaults
    }
    
    func serverAddress() -> String {
        return "http://127.0.0.1:2233" // Stub, replace with userdefaults
    }

}
