//
//  Data+MD5.swift
//  Viewlet creator example
//
//  Extends data to easily calculcate the MD5 hash of it
//

import Foundation

extension Data {

    func md5() -> String {
        let context = UnsafeMutablePointer<CC_MD5_CTX>.allocate(capacity: 1)
        var digest = Array<UInt8>(repeating: 0, count: Int(CC_MD5_DIGEST_LENGTH))
        CC_MD5_Init(context)
        let _ = self.withUnsafeBytes { (body: UnsafePointer<UInt8>) in
            CC_MD5_Update(context, body, CC_LONG(self.count))
        }
        CC_MD5_Final(&digest, context)
        context.deallocate(capacity: 1)
        var hexString = ""
        for byte in digest {
            hexString += String(format:"%02x", byte)
        }
        return hexString
    }
    
}
