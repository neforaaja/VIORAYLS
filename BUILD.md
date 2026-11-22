# VIORAYLS — Build Instructions

## Prerequisites (x86_64 Linux host recommended)
- JDK 17
- Android SDK (compileSdk 34, build-tools 34.0.0, platform-tools, platform android-34)
- Gradle 8.11.1 (wrapper included)

Set environment variables:
```bash
export ANDROID_HOME=$HOME/Android/Sdk   # or /opt/android-sdk
export PATH=$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH
export PATH=$PATH:/opt/gradle-8.11.1/bin
```

## Build APK
```bash
cd VIORAYLS
./gradlew assembleDebug   # or: gradle assembleDebug
```
Output: `app/build/outputs/apk/debug/app-debug.apk`

Install on device:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

## Notes
- This project was prepared on an ARM64 host. The Android `aapt2` tool shipped in
  SDK build-tools is an x86_64 binary and cannot run on an ARM64 kernel without
  `binfmt_misc` + qemu-user (not available in this environment). Build the APK on
  an x86_64 host (or a x86_64 CI/cloud build) to get an installable APK.
- No API keys are hardcoded. iTunes Search API and LRCLIB are key-free public APIs.

## APIs used
- Music search & metadata: Apple iTunes Search API (https://developer.apple.com/library/archive/documentation/AudioVideo/Conceptual/iTuneSearchAPI/)
- Synced lyrics (LRC): LRCLIB (https://lrclib.net/docs) — free, no key
- Audio playback: Android Media3 ExoPlayer (plays iTunes preview URLs / valid audio URLs)
