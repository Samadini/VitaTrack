# VitaTrack - Your Health & Fitness Companion

A comprehensive Android health and fitness tracking application built with modern Android development practices.

## 🌟 Features

### ✅ Core Functionality Implemented

1. **User Authentication**
   - Firebase Authentication with email/password
   - Google Sign-In integration
   - Secure user registration and login
   - User profile management

2. **Beautiful Dashboard**
   - Daily progress overview (steps, calories, water, exercise)
   - Progress bars and visual indicators
   - Quick action buttons
   - Recent activity feed
   - Greeting based on time of day

3. **Exercise Tracking**
   - Add, edit, delete exercises (CRUD operations)
   - Multiple exercise types (walking, running, yoga, gym, cycling, swimming)
   - Duration and calories burned tracking
   - Exercise history and statistics
   - Beautiful icons for each exercise type

4. **Water Intake Tracking**
   - Circular progress indicator
   - Quick add buttons (250ml, 500ml, 750ml)
   - Custom amount input
   - Daily goal tracking (2L default)
   - Time-stamped water logs

5. **Meal Tracking System**
   - Meal categorization (breakfast, lunch, dinner, snacks)
   - Nutrition tracking (calories, protein, carbs, fat)
   - Meal history and management
   - Daily nutrition summary

6. **Data Management**
   - Room database for local storage
   - Firebase Firestore for cloud sync
   - Repository pattern for data access
   - LiveData for reactive UI updates

7. **Modern UI/UX**
   - Material Design 3 components
   - Beautiful blue gradient theme matching your design
   - Responsive layouts
   - Smooth animations and transitions
   - Consistent design language

## 🏗️ Architecture

- **MVVM Architecture** with ViewModels and LiveData
- **Repository Pattern** for data abstraction
- **Room Database** for local data persistence
- **Firebase** for authentication and cloud storage
- **Navigation Component** for fragment navigation
- **Data Binding** for efficient UI updates

## 🎨 Design

The app follows your beautiful blue gradient design with:
- Primary blue theme (#4A90E2)
- Gradient backgrounds
- Clean card-based layouts
- Consistent typography and spacing
- Material Design principles

## 📱 Screens Implemented

1. **Splash Screen** - Beautiful animated logo with gradient background
2. **Authentication** - Login/signup with Google Sign-In
3. **Dashboard** - Central hub with progress overview
4. **Exercise Tracking** - Comprehensive workout logging
5. **Water Tracking** - Hydration monitoring with visual progress
6. **Meal Tracking** - Nutrition and calorie management
7. **Profile Management** - User settings and preferences

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI**: Material Design 3, View Binding
- **Architecture**: MVVM, Repository Pattern
- **Database**: Room (SQLite), Firebase Firestore
- **Authentication**: Firebase Auth, Google Sign-In
- **Navigation**: Navigation Component
- **Async**: Coroutines, LiveData
- **Notifications**: WorkManager, AlarmManager

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or newer
- Android SDK 24+
- Firebase project setup

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/VitaTrack.git
   cd VitaTrack
   ```

2. **Firebase Setup**
   - Create a new Firebase project
   - Enable Authentication (Email/Password and Google)
   - Enable Firestore Database
   - Download `google-services.json` and place in `app/` directory
   - Update the web client ID in `strings.xml`

3. **Build and Run**
   ```bash
   ./gradlew build
   ```

## 📋 Upcoming Features

- [ ] Progress history and analytics with charts
- [ ] Push notifications and reminders
- [ ] Profile and settings screens
- [ ] Social features and challenges
- [ ] Wearable device integration
- [ ] Meal photo recognition
- [ ] Workout plans and routines

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Developer

Built with ❤️ by the VitaTrack team

---

**VitaTrack** - Making health tracking simple, beautiful, and effective! 🏃‍♂️💪🥗💧
