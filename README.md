# 🌍 AapdaSetu – Disaster Reporting & Response App

**AapdaSetu** is a Kotlin-based Android app built using **Jetpack Compose** and **MVVM architecture**, designed to enable users to securely report disasters in real-time, enhancing emergency response coordination.

---

## 🚀 Features

### 🔐 User Authentication
- **Register/Login** using **email and password**
- **OTP verification** and **biometric authentication** for secure access
- Firebase Authentication integrated

### 🧑 User Profile
- View and edit user details:
  - Name
  - Email
  - Phone number
  - Profile picture
- Profile image is uploaded to Firebase and referenced via URL

### 🏠 Home Screen
- Central dashboard with access to:
  - **Profile**
  - **Report a Disaster**
  - **Map View** showing reported disaster icons by location

### 🆘 Report a Disaster
- Choose disaster type from dropdown (e.g., Earthquake, Fire, Custom)
- Upload **up to 3 images** (max 100KB each, validated)
- Provide description of the disaster
- **Authenticate with fingerprint/passcode** before final submission
- Automatically captures and submits user **geolocation**
- Uploads images to **Firebase Storage** and stores metadata in **Firebase Firestore**

### 🗺️ Map View
- Visualize all reported disasters as icons on a **Google Map**
- Clickable markers show:
  - Disaster type
  - Uploaded images
  - Description
  - Reporter's profile (name, email, phone)

---

## 🏗️ Architecture

The app follows **MVVM Clean Architecture** with clear separation of concerns:

## ScreenShots
AapdaSetu's register screen ->  (https://github.com/user-attachments/assets/34b08bf2-22d9-4e04-a60c-1b316b33b89c)
AapdaSetu's login screen    ->  (https://github.com/user-attachments/assets/db4c3d7b-6952-41de-b51e-1ef359e3730a)
AapdaSetu's Home screen     ->  (https://github.com/user-attachments/assets/e94cb5d7-4c6a-44d7-9f64-7b6f44959a3d)
AapdaSetu's Profile screen  ->  (https://github.com/user-attachments/assets/0f239003-742e-4510-a44d-f9b1faa85ffb)
AapdaSetu's Report screen   ->  (https://github.com/user-attachments/assets/036dca70-9806-40fc-b04c-e39167558a76)




