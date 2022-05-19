/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kyleboos.stock.buildSrc

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.1.3"
    const val minSdkVersion = 26
    const val targetSdkVersion = 31
    const val compileSdkVersion = 31
    const val buildToolsVersion = "30.0.2"

    object Accompanist {
        const val version = "0.23.1"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$version"
    }

    object Kotlin {
        private const val version = "1.6.10"
        //search for usage of literal
        const val kspVersion = "1.6.10-1.0.2"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.6.0"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object Network {
        const val retrofitVersion = "2.9.0"
        const val moshiVersion = "1.13.0"
        const val openCSVVersion = "5.6"

        const val openCsv = "com.opencsv:opencsv:$openCSVVersion"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
        const val moshiAdapters = "com.squareup.moshi:moshi-adapters:$moshiVersion"
        const val moshiKaptCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshiVersion"
        const val retrofitMoshiAdapter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"

        const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:$retrofitVersion"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$4.9.0"
    }

    object JUnit {
        private const val version = "4.13"
        const val junit = "junit:junit:$version"
    }

    object AndroidX {
        const val coreKtx = "androidx.core:core-ktx:1.7.0"
        const val navigation = "androidx.navigation:navigation-compose:2.4.0"
        const val datastore = "androidx.datastore:datastore-preferences:1.0.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        object Room {
            const val version = "2.4.2"
            const val runtime = "androidx.room:room-runtime:$version"
            const val annotationProcessor = "androidx.room:room-compiler:$version"
            const val ktx = "androidx.room:room-ktx:$version"
        }

        object Hilt {
            const val version = "2.41"
            const val kaptCompiler = "com.google.dagger:hilt-android-compiler:$version"
            const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val compose = "androidx.hilt:hilt-navigation-compose:1.0.0"
            const val android = "com.google.dagger:hilt-android:$version"
        }

        object Compose {
            const val snapshot = ""
            const val version = "1.1.0"

            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val material = "androidx.compose.material:material:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }

        object ConstraintLayout {
            const val constraintLayoutCompose =
                "androidx.constraintlayout:constraintlayout-compose:1.0.0-rc02"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
        }
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.4.0"
    }

    object ComposeDestinations {
        private const val version = "1.4.4-beta"
        const val core = "io.github.raamcosta.compose-destinations:core:$version"
        const val ksp = "io.github.raamcosta.compose-destinations:ksp:$version"
    }
}
