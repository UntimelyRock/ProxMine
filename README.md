# Prox mine is a proximity chat for Mindustry
This project was created from the timplate https://github.com/Anuken/MindustryJavaModTemplate, most of the build.gradle is original to the template, however almost all other code is my own unless stated otherwise. For a complete list of changes you can review the Git change logs.

ProxMine is still very much a work in progress, This mod is far from finished.

//everything after this is from the template
## Building for Desktop Testing

1. Install JDK **17**.
2. Run `gradlew jar` [1].
3. Your mod jar will be in the `build/libs` directory. **Only use this version for testing on desktop. It will not work with Android.**
To build an Android-compatible version, you need the Android SDK. You can either let Github Actions handle this, or set it up yourself. See steps below.

*[1]* *On Linux/Mac it's `./gradlew`, but if you're using Linux I assume you know how to run executables properly anyway.*  
*[2]: Yes, I know this is stupid. It's a Github UI limitation - while the jar itself is uploaded unzipped, there is currently no way to download it as a single file.*
