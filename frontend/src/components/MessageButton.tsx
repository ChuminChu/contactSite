"use client";

import { LuMessageCircleHeart } from "react-icons/lu";
import { useState } from "react";

export default function MessageButton({
  devId,
  token,
}: {
  devId: string;
  token: string;
}) {
  const [loading, setLoading] = useState(false);

  async function sendMessage() {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/messages", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`, // 환경 변수 사용
        },
        body: JSON.stringify({ messageTargetId: devId }),
      });

      if (!res.ok) throw new Error("Failed to send message");

      alert("쪽지를 보냈습니다!");
    } catch (error) {
      console.error(error);
      alert("쪽지를 보내지 못했습니다.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <LuMessageCircleHeart
      className="message"
      onClick={sendMessage}
      style={{ cursor: loading ? "not-allowed" : "pointer" }}
    />
  );
}
