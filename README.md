# EmotionChat

A modern Android chat application built using **Kotlin**, **Jetpack Compose**, **Material 3 (Expressive)**, and **Clean Architecture**.  
EmotionChat performs real-time emotion analysis on every message using **Firebase Cloud Functions** and the **HuggingFace emotion classification model**.

---

## 🚀 Features

### 🔐 Authentication
- Firebase Email/Password authentication
- User registration with username
- Splash screen auth state routing
- Logout support

### 💬 Chat
- One-to-one real-time messaging (Cloud Firestore)
- Self-chat (Saved Messages)
- Deterministic chatId generation
- Smooth animated message list
- Modern expressive message bubbles

### 🧠 Emotion Analysis
- Triggered on new message
- Firebase Cloud Function (Node.js, 2nd Gen)
- Calls HuggingFace emotion model
- Updates message with:
    - `emotion`
    - `confidence`

### 📊 Emotion Summary
- Live emotion distribution summary per conversation
- Percentage + count calculation
- Displayed at top of chat

### 🎨 UI
- Material 3 Expressive Design
- Animated message items
- Clean modern top app bars
- Structured state-driven error handling
- BlueStacks compatible

---

## 🏗 Architecture

Clean Architecture with manual dependency injection.

