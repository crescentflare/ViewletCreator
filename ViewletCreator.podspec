#
# Be sure to run `pod lib lint ViewletCreator.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'ViewletCreator'
  s.version          = '0.4.4'
  s.summary          = 'ViewletCreator creates layouts from JSON for both Android and iOS.'

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!

  s.description      = <<-DESC
ViewletCreator is a library to create Viewlet objects. These are able to create entire layouts and view components from JSON for a module oriented development of UI. Works well together with UniLayout.
                       DESC

  s.homepage         = 'https://github.com/crescentflare/ViewletCreator'
  # s.screenshots     = 'www.example.com/screenshots_1', 'www.example.com/screenshots_2'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'Crescent Flare Apps' => 'info@crescentflare.com' }
  s.source           = { :git => 'https://github.com/crescentflare/ViewletCreator.git', :tag => s.version.to_s }
  # s.social_media_url = 'https://twitter.com/<TWITTER_USERNAME>'

  s.ios.deployment_target = '8.0'

  s.source_files = 'ViewletCreatorIOS/ViewletCreator/Classes/**/*'
  
  # s.resource_bundles = {
  #   'ViewletCreator' => ['ViewletCreator/Assets/*.png']
  # }

  # s.public_header_files = 'Pod/Classes/**/*.h'
  # s.frameworks = 'UIKit', 'MapKit'
  # s.dependency 'AFNetworking', '~> 2.3'
end
