import UIKit
import XCTest
@testable import ViewletCreator

class Tests: XCTestCase {
    
    // ---
    // MARK: Test view related data conversion
    // ---

    func testAsColor() {
        XCTAssertEqual(UIColor.red, ViewletConvUtil.asColor(value: "#ff0000"))
        XCTAssertEqual(UIColor(colorLiteralRed: 0, green: 0, blue: 0, alpha: 0), ViewletConvUtil.asColor(value: "#00000000"))
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
