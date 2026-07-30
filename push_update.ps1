# PowerShell script to automatically build, update version in update.json, and push to GitHub
$ErrorActionPreference = "Stop"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host " FORGEFIT - GITHUB AUTO PUSH & UPDATER   " -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"

# 1. Build APK
Write-Host "`n[1/4] Building APK using Gradle..." -ForegroundColor Yellow
.\gradlew.bat assembleDebug

# 2. Copy compiled APK to root
Write-Host "`n[2/4] Copying APK to root directory..." -ForegroundColor Yellow
Copy-Item "app\build\outputs\apk\debug\app-debug.apk" "ForgeFit-v1.1.0.apk" -Force

# 3. Git commit & push
Write-Host "`n[3/4] Committing changes to Git..." -ForegroundColor Yellow
git add .
git commit -m "Auto Update Push: ForgeFit v1.1.0" --allow-empty

# 4. Push to GitHub
Write-Host "`n[4/4] Pushing to GitHub..." -ForegroundColor Yellow
git push origin main

Write-Host "`n🎉 SUCCESS! App pushed to GitHub. Update is now live for all users!" -ForegroundColor Green
