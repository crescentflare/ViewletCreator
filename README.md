# Viewlet creator

A library to create views from json-based data for both Android and iOS.


### iOS integration guide

The library is available through [CocoaPods](http://cocoapods.org). To install it, simply add the following line to your Podfile:

```ruby
pod "ViewletCreator", '~> 0.5.2'
```

The above version is for Swift 5.0. For older Swift versions use the following:
- Swift 4.2: ViewletCreator 0.5.0
- Swift 4.1: ViewletCreator 0.4.2
- Swift 4.0: ViewletCreator 0.4.1
- Swift 3: ViewletCreator 0.4.0


### Android integration guide

When using gradle, the library can easily be imported into the build.gradle file of your project. Add the following dependency:

```
implementation 'com.crescentflare.viewletcreator:ViewletCreatorLib:0.5.0'
```

Make sure that jcenter is added as a repository.


### Status

This library has evolved into [JsonInflator](https://github.com/crescentflare/JsonInflator). The new library is able to create views and other objects from json-based data and will continue development.
