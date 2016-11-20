//
//  ViewletColorLookup.swift
//  Viewlet creator Pod
//
//  Library utility: protocol for color lookup
//  Integrates with ViewletConvUtil to look up custom color references
//

import UIKit

public protocol ViewletColorLookup {
    
    func getColor(refId: String) -> UIColor?
    
}
