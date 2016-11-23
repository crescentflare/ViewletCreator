import UIKit
import XCTest
@testable import ViewletCreator

class Tests: XCTestCase {
    
    // ---
    // MARK: Test array conversion
    // ---

    func testAsStringArray() {
        XCTAssertEqual([ "10", "12", "99", "24" ], ViewletConvUtil.asStringArray(value: [ 10, 12, 99, 24 ]))
    }

    func testAsDoubleArray() {
        XCTAssertEqual([ 4.22, 8.9, 19.1, 11 ], ViewletConvUtil.asDoubleArray(value: [ "4.22", "8.9", "19.1", "11" ]))
    }

    func testAsFloatArray() {
        XCTAssertEqual([ 1.1, 5, 89.16, 2 ], ViewletConvUtil.asFloatArray(value: [ 1.1, 5, 89.16, 2 ]))
    }

    func testAsIntArray() {
        XCTAssertEqual([ 6, 3, 12, 2150 ], ViewletConvUtil.asIntArray(value: [ 6.1, 3, 12, 2150.654 ]))
    }

    func testAsBoolArray() {
        XCTAssertEqual([ true, true, false, true ], ViewletConvUtil.asBoolArray(value: [ "true", "true", "false", "true" ]))
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

}
