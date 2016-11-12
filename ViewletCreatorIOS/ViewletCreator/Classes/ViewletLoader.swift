//
//  ViewletLoader.swift
//  Viewlet creator Pod
//
//  Library: loading viewlet properties
//  Load viewlet property definitions from JSON and parses them to attributes (to be used for creation or inflation)
//

import UIKit

public class ViewletLoader {
    
    // ---
    // MARK: Singleton instance
    // ---

    private static let shared = ViewletLoader()
    
    
    // ---
    // MARK: Members
    // ---

    private var loadedJson: [String: [String: Any]] = [:]
    

    // ---
    // MARK: Initialization
    // ---
    
    private init() {
    }
    
    
    // ---
    // MARK: Loading
    // ---
    
    public static func attributesFrom(jsonFile: String) -> [String: Any]? {
        if let item = shared.loadedJson[jsonFile] {
            return item
        }
        let bundle = Bundle.main
        if let path = bundle.path(forResource: jsonFile, ofType: "json") {
            if let jsonData = try? NSData(contentsOfFile: path, options: .mappedIfSafe) as Data {
                if let json = try? JSONSerialization.jsonObject(with: jsonData, options: .allowFragments) {
                    if let jsonDict = json as? [String: Any] {
                        shared.loadedJson[jsonFile] = jsonDict
                        return jsonDict
                    }
                }
            }
        }
        return nil
    }
    
}
