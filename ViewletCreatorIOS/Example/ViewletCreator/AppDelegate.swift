//
//  AppDelegate.swift
//  Viewlet creator example
//
//  The application delegate, handling global events while the app is running
//

import UIKit
import ViewletCreator

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    // --
    // MARK: Window member (used to contain the navigation controller)
    // --

    var window: UIWindow?


    // --
    // MARK: Lifecycle callbacks
    // --
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        registerViewlets()
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.backgroundColor = UIColor.white
        window?.rootViewController = UINavigationController(rootViewController: ViewController())
        window?.makeKeyAndVisible()
        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // No implementation
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // No implementation
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // No implementation
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // No implementation
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // No implementation
    }
    
    
    // --
    // MARK: Viewlet registration
    // --

    func registerViewlets() {
        ViewletCreator.register(name: "textView", viewlet: TextViewViewlet())
    }

}
