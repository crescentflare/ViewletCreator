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
    // MARK: Lookups
    // ---
    
    public static var colorLookup: ViewletColorLookup?
    public static var dimensionLookup: ViewletDimensionLookup?
    

    // ---
    // MARK: Array conversion
    // ---

    public static func asStringArray(value: Any?) -> [String] {
        var array: [String] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let stringValue = asString(value: valueItem) {
                    array.append(stringValue)
                }
            }
        }
        return array
    }
    
    public static func asDoubleArray(value: Any?) -> [Double] {
        var array: [Double] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let doubleValue = asDouble(value: valueItem) {
                    array.append(doubleValue)
                }
            }
        }
        return array
    }

    public static func asFloatArray(value: Any?) -> [Float] {
        var array: [Float] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let floatValue = asFloat(value: valueItem) {
                    array.append(floatValue)
                }
            }
        }
        return array
    }
    
    public static func asIntArray(value: Any?) -> [Int] {
        var array: [Int] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let intValue = asInt(value: valueItem) {
                    array.append(intValue)
                }
            }
        }
        return array
    }

    public static func asBoolArray(value: Any?) -> [Bool] {
        var array: [Bool] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let boolValue = asBool(value: valueItem) {
                    array.append(boolValue)
                }
            }
        }
        return array
    }

    
    // ---
    // MARK: View related data conversion
    // ---
    
    public static func asDimensionArray(value: Any?) -> [CGFloat] {
        var array: [CGFloat] = []
        if let valueArray = value as? [Any] {
            for valueItem in valueArray {
                if let pointValue = asDimension(value: valueItem) {
                    array.append(pointValue)
                }
            }
        }
        return array
    }

    public static func asColor(value: Any?) -> UIColor? {
        if let colorString = value as? String {
            if colorString.hasPrefix("$") {
                return colorLookup?.getColor(refId: String(colorString[colorString.index(after: colorString.startIndex)...]))
            }
            var rgbValue: UInt32 = 0
            let scanner = Scanner(string: colorString)
            var alpha: CGFloat = 1
            if colorString.hasPrefix("#") {
                scanner.scanLocation = 1
            }
            scanner.scanHexInt32(&rgbValue)
            if colorString.count >= 8 {
                alpha = CGFloat((rgbValue & 0xff000000) >> 24) / 255
            }
            let red = CGFloat((rgbValue & 0xff0000) >> 16) / 255
            let green = CGFloat((rgbValue & 0xff00) >> 8) / 255
            let blue = CGFloat(rgbValue & 0xff) / 255
            return UIColor(red: red, green: green, blue: blue, alpha: alpha)
        }
        return nil
    }
    
    public static func asDimension(value: Any?) -> CGFloat? {
        if var stringValue = value as? String {
            var multiplier: CGFloat = 1
            if stringValue.hasPrefix("$") {
                return dimensionLookup?.getDimension(refId: String(stringValue[stringValue.index(after: stringValue.startIndex)...]))
            }
            if stringValue.hasSuffix("dp") || stringValue.hasSuffix("sp") {
                stringValue = String(stringValue[..<stringValue.index(stringValue.endIndex, offsetBy: -2)])
            } else if stringValue.hasSuffix("wp") {
                stringValue = String(stringValue[..<stringValue.index(stringValue.endIndex, offsetBy: -2)])
                multiplier = UIScreen.main.bounds.width / 100
            } else if stringValue.hasSuffix("hp") {
                stringValue = String(stringValue[..<stringValue.index(stringValue.endIndex, offsetBy: -2)])
                multiplier = UIScreen.main.bounds.height / 100
            } else if stringValue.hasSuffix("px") {
                stringValue = String(stringValue[..<stringValue.index(stringValue.endIndex, offsetBy: -2)])
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
    
    public static func asDate(value: Any?) -> Date? {
        if let stringDate = value as? String {
            let formats = [
                "yyyy-MM-dd'T'HH:mm:ssZZZZ",
                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd"
            ]
            for format in formats {
                let dateFormatter = DateFormatter()
                if format.hasSuffix("Z") {
                    dateFormatter.timeZone = TimeZone(identifier: "UTC")
                }
                dateFormatter.dateFormat = format
                if let date = dateFormatter.date(from: stringDate) {
                    return date
                }
            }
        }
        return nil
    }
    
    public static func asString(value: Any?) -> String? {
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
    
    public static func asDouble(value: Any?) -> Double? {
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
    
    public static func asFloat(value: Any?) -> Float? {
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

    public static func asInt(value: Any?) -> Int? {
        if let stringValue = value as? String {
            if let intValue = Int(stringValue) {
                return intValue
            }
            if let doubleValue = Double(stringValue) {
                return Int(doubleValue)
            }
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

    public static func asBool(value: Any?) -> Bool? {
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
