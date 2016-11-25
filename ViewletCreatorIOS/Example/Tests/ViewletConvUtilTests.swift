import UIKit
import XCTest
@testable import ViewletCreator

class Tests: XCTestCase {
    
    // ---
    // MARK: Test array conversion
    // ---

    func testAsStringArray() {
        XCTAssertEqual([ "first", "second" ], ViewletConvUtil.asStringArray(value: [ "first", "second" ]))
        XCTAssertEqual([ "10", "12", "99", "24" ], ViewletConvUtil.asStringArray(value: [ 10, 12, 99, 24 ]))
    }

    func testAsDoubleArray() {
        XCTAssertEqual([ 4.22, 8.9, 19.1, 11 ], ViewletConvUtil.asDoubleArray(value: [ "4.22", "8.9", "19.1", "11" ]))
        XCTAssertEqual([ 3.11, 16 ], ViewletConvUtil.asDoubleArray(value: [ 3.11, 16 ]))
    }

    func testAsFloatArray() {
        XCTAssertEqual([ 1.1, 5, 89.16, 2 ], ViewletConvUtil.asFloatArray(value: [ 1.1, 5, 89.16, 2 ]))
        XCTAssertEqual([ 67, 11 ], ViewletConvUtil.asFloatArray(value: [ 67, 11 ]))
    }

    func testAsIntArray() {
        XCTAssertEqual([ 325, -23 ], ViewletConvUtil.asIntArray(value: [ 325, -23 ]))
        XCTAssertEqual([ 6, 3, 12, 2150 ], ViewletConvUtil.asIntArray(value: [ "6.1", "3", "12", "2150.654" ]))
    }

    func testAsBoolArray() {
        XCTAssertEqual([ true, true, false, true ], ViewletConvUtil.asBoolArray(value: [ "true", "true", "false", "true" ]))
        XCTAssertEqual([ true, false, true ], ViewletConvUtil.asBoolArray(value: [ 12, 0, 1 ]))
        XCTAssertEqual([ false, true ], ViewletConvUtil.asBoolArray(value: [ false, true ]))
    }

    
    // ---
    // MARK: Test view related data conversion
    // ---
    
    func testAsPointValueArray() {
        XCTAssertEqual([ 0.5, 10, 4, 9 ], ViewletConvUtil.asDimensionArray(value: [ "1px", "10dp", "4sp", "9dp" ]))
    }

    func testAsColor() {
        XCTAssertEqual(UIColor.red, ViewletConvUtil.asColor(value: "#ff0000"))
        XCTAssertEqual(UIColor(colorLiteralRed: 0, green: 0, blue: 0, alpha: 0), ViewletConvUtil.asColor(value: "#00000000"))
    }
    
    func testAsPointValue() {
        XCTAssertEqual(0.5, ViewletConvUtil.asDimension(value: "1px"))
        XCTAssertEqual(20, ViewletConvUtil.asDimension(value: "20dp"))
        XCTAssertEqual(12, ViewletConvUtil.asDimension(value: "12sp"))
        XCTAssertEqual(8, ViewletConvUtil.asDimension(value: 8))
    }
    

    // ---
    // MARK: Test basic value conversion
    // ---
    
    func testAsDate() {
        XCTAssertEqual(dateFrom(year: 2016, month: 8, day: 19), ViewletConvUtil.asDate(value: "2016-08-19"))
        XCTAssertEqual(dateFrom(year: 2016, month: 5, day: 16, hour: 1, minute: 10, second: 28), ViewletConvUtil.asDate(value: "2016-05-16T01:10:28"))
        XCTAssertEqual(utcDateFrom(year: 2016, month: 2, day: 27, hour: 12, minute: 24, second: 11), ViewletConvUtil.asDate(value: "2016-02-27T12:24:11Z"))
        XCTAssertEqual(utcDateFrom(year: 2016, month: 2, day: 27, hour: 17, minute: 0, second: 0), ViewletConvUtil.asDate(value: "2016-02-27T19:00:00+02:00"))
    }
    
    func testAsString() {
        XCTAssertEqual("test", ViewletConvUtil.asString(value: "test"))
        XCTAssertEqual("12", ViewletConvUtil.asString(value: 12))
        XCTAssertEqual("14.42", ViewletConvUtil.asString(value: 14.42))
        XCTAssertEqual("true", ViewletConvUtil.asString(value: true))
    }
    
    func testAsDouble() {
        XCTAssertEqual(89.213, ViewletConvUtil.asDouble(value: "89.213"))
        XCTAssertEqual(31, ViewletConvUtil.asDouble(value: 31))
    }
    
    func testAsFloat() {
        XCTAssertEqual(21.3, ViewletConvUtil.asFloat(value: 21.3))
        XCTAssertEqual(1, ViewletConvUtil.asFloat(value: true))
    }

    func testAsInt() {
        XCTAssertEqual(3, ViewletConvUtil.asInt(value: "3"))
        XCTAssertEqual(45, ViewletConvUtil.asInt(value: 45.75))
    }

    func testAsBool() {
        XCTAssertEqual(false, ViewletConvUtil.asBool(value: "false"))
        XCTAssertEqual(true, ViewletConvUtil.asBool(value: 2))
    }


    // ---
    // MARK: Helper
    // ---
    
    func dateFrom(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0) -> Date {
        let calendar = Calendar(identifier: .gregorian)
        var components = DateComponents()
        components.year = year
        components.month = month
        components.day = day
        components.hour = hour
        components.minute = minute
        components.second = second
        return calendar.date(from: components)!
    }

    func utcDateFrom(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0, second: Int = 0) -> Date {
        var calendar = Calendar(identifier: .gregorian)
        var components = DateComponents()
        calendar.timeZone = TimeZone(identifier: "UTC")!
        components.year = year
        components.month = month
        components.day = day
        components.hour = hour
        components.minute = minute
        components.second = second
        return calendar.date(from: components)!
    }

}
