import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "com.example.mvvm"
    compileSdk = 36
    flavorDimensions += "env"

    defaultConfig {
        applicationId = "com.example.mvvm"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    productFlavors {
        create("prod") {
            dimension = "env"
        }

        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
    }

    val localProperties =
        Properties().apply {
            val file = rootProject.file("local.properties")
            if (file.exists()) {
                load(file.inputStream())
            }
        }

    val token: String = localProperties.getProperty("auth_token") ?: ""

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "POSTER_URL", "\"https://image.tmdb.org/t/p/w200\"")
            buildConfigField("String", "API_TOKEN", "\"$token\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField("String", "POSTER_URL", "\"https://image.tmdb.org/t/p/w200\"")
            buildConfigField("String", "API_TOKEN", "\"$token\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-XXLanguage:+PropertyParamAnnotationDefaultTargetMode")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    ktlint {
        version.set("1.2.1") // ktlint CLI
        android.set(true)

        outputToConsole.set(true)
        coloredOutput.set(true)
        ignoreFailures.set(false)

        filter {
            exclude("**/build/**")
            exclude("**/generated/**")
        }
    }
}

dependencies {
    implementation(libs.moshi)
    implementation(libs.moshiKotlin)
    implementation(libs.retrofit)
    implementation(libs.retrofitMoshi)

    implementation(libs.okhttp)
    implementation(libs.okhttpLogging)
    implementation(libs.coil)
    implementation(libs.coilNetwork)
    implementation(libs.hilt.android)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.roomDB)
    implementation(libs.roomKTX)
    testImplementation(libs.roomTest)

    ksp(libs.roomCompiler)
    annotationProcessor(libs.roomCompiler)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
