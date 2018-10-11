# Viewlet creator

[![CI Status](http://img.shields.io/travis/crescentflare/ViewletCreator.svg?style=flat)](https://travis-ci.org/crescentflare/ViewletCreator)
[![License](https://img.shields.io/cocoapods/l/ViewletCreator.svg?style=flat)](http://cocoapods.org/pods/ViewletCreator)
[![Version](https://img.shields.io/cocoapods/v/ViewletCreator.svg?style=flat)](http://cocoapods.org/pods/ViewletCreator)
[![Version](https://img.shields.io/bintray/v/crescentflare/maven/ViewletCreatorLib.svg?style=flat)](https://bintray.com/crescentflare/maven/ViewletCreatorLib)

Viewlet creator is a project to allow creation of layouts and view components from JSON on both iOS and Android. It provides a way to make UI development more modular and dynamic. The example demonstrates how to use viewlet creator to prototype user interfaces real-time on multiple devices simultaneously.

Use the library together with [UniLayout](https://github.com/crescentflare/UniLayout) for simultaneous multi-platform development of UI on both iOS and Android.


### Features

* Provides a structure to create view components from JSON
* Register viewlets in the viewlet creator to allow viewlets within viewlets
* Adds utilities to safely fetch view properties from a JSON structure with data conversion
* Share colors and coordinates/sizes by using color and dimension tables
* Define styles with default attributes to fall back on for each viewlet


### iOS integration guide

The library is available through [CocoaPods](http://cocoapods.org). To install it, simply add the following line to your Podfile:

```ruby
pod "ViewletCreator", '~> 0.4.3'
```

The above version is for Swift 4.2. For older Swift versions use the following:
- Swift 4.1: ViewletCreator 0.4.2
- Swift 4.0: ViewletCreator 0.4.1
- Swift 3: ViewletCreator 0.4.0


### Android integration guide

When using gradle, the library can easily be imported into the build.gradle file of your project. Add the following dependency:

```
compile 'com.crescentflare.viewletcreator:ViewletCreatorLib:0.4.0'
```

Make sure that jcenter is added as a repository.


### Example

The provided example shows how to create viewlets, register them and use them to make and prototype UI. In the example, it will use autolayout on iOS and viewgroup related layouts for Android.


### Status

The library is new but already provides a range of useful functionality. More features may be added in the future.
