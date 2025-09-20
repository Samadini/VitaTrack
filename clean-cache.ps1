# Clean Gradle Cache Script
# Run this in Command Prompt or PowerShell in the project directory

Write-Host "Cleaning Gradle caches..."

# Delete all Gradle caches
if (Test-Path ".gradle") {
    Remove-Item -Recurse -Force ".gradle"
    Write-Host "Deleted .gradle folder"
}

if (Test-Path "app\.gradle") {
    Remove-Item -Recurse -Force "app\.gradle"
    Write-Host "Deleted app\.gradle folder"
}

# Delete user Gradle cache (be careful with this)
$gradleCachePath = "$env:USERPROFILE\.gradle\caches"
if (Test-Path $gradleCachePath) {
    Remove-Item -Recurse -Force $gradleCachePath
    Write-Host "Deleted user Gradle cache"
}

Write-Host "Cache cleanup complete. Now rebuild the project in Android Studio."
Write-Host "Go to: File > Sync Project with Gradle Files"
Write-Host "Then: Build > Clean Project > Build > Rebuild Project"
