//
//  Data+MD5.swift
//  Viewlet creator example
//
//  Extends data to easily calculcate the MD5 hash of it
//

import Foundation

extension Data {

    func md5() -> String {
        let hash = self.withUnsafeBytes { (bytes: UnsafeRawBufferPointer) -> [UInt8] in
            var hash = [UInt8](repeating: 0, count: Int(CC_MD5_DIGEST_LENGTH))
            CC_MD5(bytes.baseAddress, CC_LONG(self.count), &hash)
            return hash
        }
        var hexString = ""
        for byte in hash {
            hexString += String(format:"%02x", byte)
        }
        return hexString
    }
    
}
