buildscript {
    dependencies {
        classpath(libs.google.services)
    }
    extra.apply {
        set("compose_ui_version","1.2.0-alpha03")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.hilt).apply(false)
}