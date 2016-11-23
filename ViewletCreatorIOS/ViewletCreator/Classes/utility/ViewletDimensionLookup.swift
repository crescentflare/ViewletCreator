//
//  ViewletDimensionLookup.swift
//  Viewlet creator Pod
//
//  Library utility: protocol for dimension lookup
//  Integrates with ViewletConvUtil to look up custom dimension sizes or coordinates
//

import UIKit

public protocol ViewletDimensionLookup {
    
    func getDimension(refId: String) -> CGFloat?
    
}
