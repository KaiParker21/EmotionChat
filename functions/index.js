const { onDocumentCreated } = require("firebase-functions/v2/firestore");
const { setGlobalOptions } = require("firebase-functions/v2");
const { defineSecret } = require("firebase-functions/params");
const admin = require("firebase-admin");
const axios = require("axios");

admin.initializeApp();

setGlobalOptions({ region: "us-central1" });

const hfKey = defineSecret("HUGGINGFACE_KEY");

exports.analyzeEmotion = onDocumentCreated(
  {
    document: "chats/{chatId}/messages/{messageId}",
    secrets: [hfKey],
  },
  async (event) => {
    const snap = event.data;
    if (!snap) return;

    const text = snap.data().text;
    if (!text) return;

    try {
      const response = await axios.post(
        "https://router.huggingface.co/hf-inference/models/j-hartmann/emotion-english-distilroberta-base",
        { inputs: text },
        {
          headers: {
            Authorization: `Bearer ${hfKey.value()}`,
            "Content-Type": "application/json",
          },
        }
      );

      const emotions = response.data[0];
      const topEmotion = emotions.reduce((prev, current) =>
        prev.score > current.score ? prev : current
      );

      await snap.ref.update({
        emotionLabel: topEmotion.label,
        emotionConfidence: topEmotion.score,
      });

    } catch (error) {
      console.error("Emotion API failed:", error.response?.data || error.message);

      await snap.ref.update({
        emotionLabel: "unknown",
        emotionConfidence: 0.0,
      });
    }
  }
);
