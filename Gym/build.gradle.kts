buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.4")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")
        //classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.21"
        //classpath("com.google.dagger:hilt-android-gradle-plugin:2.44")

    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    //id("com.google.dagger.hilt.android") version "2.42" apply false
}