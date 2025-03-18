"use client";
import { useState, useEffect } from "react";
import "./Proposal.scss";

interface Message {
  id: number;
  receiverId: string;
  senderId: string;
  senderOrReceiverName: string;
}

interface ProposalProps {
  type: "sent" | "received";
  token: string;
}

export default function Proposal({ type, token }: ProposalProps) {
  const [messages, setMessages] = useState<Message[]>([]);
  const [selectedIds, setSelectedIds] = useState<number[]>([]);

  // 메일 데이터 서버에서 가져오기
  useEffect(() => {
    const fetchMessages = async () => {
      try {
        const response = await fetch(
          type === "sent"
            ? "http://localhost:8080/messages/senders" // 보낸 메일 조회
            : "http://localhost:8080/messages/receivers", // 받은 메일 조회
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`, // 토큰을 가져오는 방식
            },
          },
        );

        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(`메일 조회 실패: ${errorData.message}`);
        }

        const data = await response.json();
        setMessages(data); // 받은 데이터로 메일 목록 설정
      } catch (error) {
        console.error("메일 데이터를 가져오는 중 에러가 발생했습니다.", error);
      }
    };

    if (token && type) {
      fetchMessages();
    }
  }, [token, type]);

  // 체크박스를 통해 선택된 항목들 관리
  const toggleSelection = (id: number) => {
    setSelectedIds((prev) =>
      prev.includes(id) ? prev.filter((item) => item !== id) : [...prev, id],
    );
  };

  // 선택된 항목 삭제
  const handleDelete = async () => {
    try {
      // 선택된 메일 삭제 요청
      for (const id of selectedIds) {
        const response = await fetch(
          `http://localhost:8080/messages/${id}`, // 메일 삭제 API
          {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${token}`, // 토큰을 가져오는 방식
            },
          },
        );

        if (!response.ok) {
          const errorData = await response.json();
          throw new Error(`메일 삭제 실패: ${errorData.message}`);
        }
      }

      // 삭제 후 상태 갱신
      setMessages((prev) =>
        prev.filter((message) => !selectedIds.includes(message.id)),
      );
      setSelectedIds([]); // 삭제 후 선택된 항목 초기화
    } catch (error) {
      console.error("메일 삭제에 실패했습니다.", error);
    }
  };

  return (
    <div className="proposal">
      <div className="menu">
        <div className="header">
          <h2>{type === "sent" ? "보낸 쪽지" : "받은 쪽지"}</h2>
        </div>
        <div className="content">
          <div className="recipient">
            <label>{type === "sent" ? "받는 사람" : "보낸 사람"}</label>
            <div className="message-list">
              {messages.map((message) => (
                <div key={message.id} className="message-item">
                  <input
                    type="checkbox"
                    checked={selectedIds.includes(message.id)}
                    onChange={() => toggleSelection(message.id)}
                  />
                  <span>{message.senderOrReceiverName}</span>
                </div>
              ))}
            </div>
          </div>
        </div>
        <div className="actions">
          <button
            className="delete-btn"
            onClick={handleDelete}
            disabled={selectedIds.length === 0}
          >
            <span className="icon">🗑️</span> 선택삭제
          </button>
          <div className="pagination">
            <button className="page-btn">◀</button>
            <button className="page-btn">▶</button>
          </div>
        </div>
      </div>
    </div>
  );
}
