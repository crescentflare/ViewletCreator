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
    
    public static func asPointValueArray(value: Any) -> [CGFloat] {
        var array: [CGFloat] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let pointValue = asPointValue(value: valueItem) {
                    array.append(pointValue)
                }
            }
        }
        return array
    }

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
    
    public static func asPointValue(value: Any) -> CGFloat? {
        if var stringValue = value as? String {
            var multiplier: CGFloat = 1
            if stringValue.hasSuffix("dp") || stringValue.hasSuffix("sp") {
                stringValue = stringValue.substring(to: stringValue.index(stringValue.endIndex, offsetBy: -2))
            } else if stringValue.hasSuffix("px") {
                stringValue = stringValue.substring(to: stringValue.index(stringValue.endIndex, offsetBy: -2))
                multiplier = 1 / UIScreen.main.scale
            }
            if let floatValue = asFloat(value: stringValue) {
                return CGFloat(floatValue) * multiplier
            }
            return nil
        }
        if let floatValue = asFloat(value: value) {
            return CGFloat(floatValue)
        }
        return nil
    }

    
    // ---
    // MARK: Basic value conversion
    // ---
    
    public static func asString(value: Any) -> String? {
        if value is String {
            return value as? String
        } else if let doubleValue = value as? Double {
            return String(doubleValue)
        } else if let floatValue = value as? Float {
            return String(floatValue)
        } else if let intValue = value as? Int {
            return String(intValue)
        } else if let boolValue = value as? Bool {
            return boolValue ? "true" : "false"
        }
        return nil
    }
    
    public static func asDouble(value: Any) -> Double? {
        if let stringValue = value as? String {
            return Double(stringValue)
        } else if value is Double {
            return value as? Double
        } else if let floatValue = value as? Float {
            return Double(floatValue)
        } else if let intValue = value as? Int {
            return Double(intValue)
        } else if let boolValue = value as? Bool {
            return boolValue ? 1 : 0
        }
        return nil
    }
    
    public static func asFloat(value: Any) -> Float? {
        if let stringValue = value as? String {
            return Float(stringValue)
        } else if let doubleValue = value as? Double {
            return Float(doubleValue)
        } else if value is Float {
            return value as? Float
        } else if let intValue = value as? Int {
            return Float(intValue)
        } else if let boolValue = value as? Bool {
            return boolValue ? 1 : 0
        }
        return nil
    }

    public static func asInt(value: Any) -> Int? {
        if let stringValue = value as? String {
            return Int(stringValue)
        } else if let doubleValue = value as? Double {
            return Int(doubleValue)
        } else if let floatValue = value as? Float {
            return Int(floatValue)
        } else if value is Int {
            return value as? Int
        } else if let boolValue = value as? Bool {
            return boolValue ? 1 : 0
        }
        return nil
    }

    public static func asBool(value: Any) -> Bool? {
        if let stringValue = value as? String {
            return Bool(stringValue)
        } else if let doubleValue = value as? Double {
            return doubleValue > 0
        } else if let floatValue = value as? Float {
            return floatValue > 0
        } else if let intValue = value as? Int {
            return intValue > 0
        } else if value is Bool {
            return value as? Bool
        }
        return nil
    }
    
}
