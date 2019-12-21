# Minecraft Development Library

![Rating](https://img.shields.io/badge/Rating-1%2F5-Red)
![Sauce](https://img.shields.io/badge/100%25-Spaghetti%20Code-orange)
![Build Status](https://img.shields.io/badge/Passing-yellowgreen)


Have you Ever Written Code for Minecraft So Good You use it again? Me Either, but if I did I'd put it here.

  - Mixins
  - Radial Gui

### Gradle

```
buildscript {
    repositories {
        jcenter()
    }


}


allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
...

dependencies {
    compile 'com.github.WalkingLibrary:McDevLib::$version'

}


```
License
----
![AUR license](https://img.shields.io/badge/License-MIT-blue)


