//
//  ViewletConvUtil.swift
//  Viewlet creator Pod
//
//  Library utility: conversion utilities
//  Provides conversion from optional Any to the desired type
//

public class ViewletConvUtil {
    
    // ---
    // MARK: Initialization
    // ---
    
    private init() {
    }
    
    
    // ---
    // MARK: Dictionary conversion
    // ---
    
    // TODO...
    
    
    // ---
    // MARK: Array conversion
    // ---

    // TODO...

    
    // ---
    // MARK: View related data conversion
    // ---

    public static func asColor(value: Any) -> UIColor? {
        if let colorString = value as? String {
            var rgbValue: UInt32 = 0
            let scanner = Scanner(string: colorString)
            var alpha: Float = 1
            if colorString.hasPrefix("#") {
                scanner.scanLocation = 1
            }
            scanner.scanHexInt32(&rgbValue)
            if colorString.characters.count >= 8 {
                alpha = Float((rgbValue & 0xff000000) >> 24) / 255
            }
            let red = Float((rgbValue & 0xff0000) >> 16) / 255
            let green = Float((rgbValue & 0xff00) >> 8) / 255
            let blue = Float(rgbValue & 0xff) / 255
            return UIColor(colorLiteralRed: red, green: green, blue: blue, alpha: alpha)
        }
        return nil
    }

    
    // ---
    // MARK: Basic value conversion
    // ---
    
    public static func asString(value: Any) -> String? {
        return value as? String
    }
    
    public static func asBoolean(value: Any) -> Bool? {
        return value as? Bool
    }
    
}
