## Change log
----------------------

Version 6-SNAPSHOT
-------------

ADDED:

- new gradle-plugin dependency of 'org.ajoberstar.grgit:grgit-gradle' in version 5.0.0 for create
  git release tags
- new gradle-plugin dependency of 'com.diffplug.spotless:spotless-plugin-gradle' in version 6.15.0

CHANGED:

- update to jdk version 11
- update gradle to new version 8.0
- update dependency of com.github.ben-manes.versions.gradle.plugin to new version 0.45.0
- update of dependency lombok to new version 1.18.26
- update of dependency throwable to new version 2.3
- update of test dependency test-object to new version 7.2
- update of test dependency test-ng to new version 7.7.1

Version 5.2
-------------

ADDED:

- new method in class URLExtensions that checks if a URL object is reachable
- gradle-plugin dependency of io.freefair.gradle:lombok-plugin in version 6.1.0

CHANGED:

- update gradle to new version 7.2
- update of dependency lombok to new version 1.18.20
- update of dependency throw-able to new version 1.6
- update of test dependency test-objects to new version 5.5
- update of test dependency test-ng to new version 7.4.0
- update dependency of com.github.ben-manes.versions.gradle.plugin to new version 0.39.0
- update gradle-plugin dependency of gradle.plugin.com.hierynomus.gradle.plugins:license-gradle-plugin to new version 0.16.1

Version 5.1
-------------

ADDED:

- gradle as build system
- new method that resolves all InetAddress objects from a sub net mask

CHANGED:

- changed to new package io.github.astrapi69
- changed project nature from maven to gradle nature
- update of dependency throw-able  to new version 1.4
- update of test dependency test-objects version to 5.3
- removed maven related files
- removed of junit dependency
- removed of mockito-core dependency

Version 5
-------------

CHANGED:

- update of parent version to 4.5
- update of test-objects version to 5
- remove logging dependencies

Version 4.14
-------------

ADDED:

- this changelog file
- new eclipse launch scripts created
- created PULL_REQUEST_TEMPLATE.md file
- created CODE_OF_CONDUCT.md file
- created CONTRIBUTING.md file
- provide new package.html for the javadoc of new and existing packages
- javadoc improved and extended
- existing class URLExtensions and enum Protocol moved from file-worker to this project

CHANGED:

- update of parent version to 3.11
- removed unneeded .0 at the end of version
- update of test-objects dependency version from 4.16.0 to 4.26
