# EmotionChat

EmotionChat is a modern Android chat application built with Kotlin and Jetpack Compose.  
It features real-time messaging powered by Firebase and AI-driven emotion analysis using HuggingFace.

Designed with Clean Architecture and MVVM for scalability and production readiness.

---

## 🚀 Features

- 🔐 Firebase Email/Password Authentication
- 💬 Real-time one-to-one messaging (Cloud Firestore)
- 🤖 Automatic emotion analysis per message (HuggingFace + Cloud Functions)
- 📊 Emotion summary per conversation
- 🧠 Self-chat ("Saved Messages")
- 🎨 Material 3 expressive UI
- 🏗 Clean Architecture (Presentation, Domain, Data layers)
- 🔄 MVVM with manual dependency injection
- ⚡ BlueStacks compatible

---

## 🧠 Emotion Analysis Flow

1. User sends message → stored in Firestore
2. Firebase Cloud Function triggers
3. HuggingFace emotion model processes text
4. Emotion label + confidence score saved back to message
5. UI updates in real-time

---

## 🛠 Tech Stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Firebase Authentication**
- **Cloud Firestore**
- **Firebase Cloud Functions (Gen 2)**
- **HuggingFace Inference API**
- **MVVM + Clean Architecture**

---

## 📦 Installation

Download the latest release from GitHub:

👉 **Latest Release:**  
https://github.com/KaiParker21/EmotionChat/releases/latest

### Install APK

1. Download the `app-release.apk`
2. Enable **Install from unknown sources**
3. Install and open the app

---

## 🔧 Local Setup (Developers)

1. Clone the repository:

