"use client";

import { useEffect, useState } from "react";
import DevItem from "@/components/DevItem";
import "./developer.page.scss";

interface Programmer {
  id: string;
  name: string;
  age: number;
  fieldName: string[];
  isLiked: boolean;
  likeCount: number;
}

export default function DeveloperListPage() {
  const [programmers, setProgrammers] = useState<Programmer[]>([]);
  const [fields, setFields] = useState<string[]>([]);
  const [personalHistory, setPersonalHistory] = useState(0);

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = e.target;
    if (checked) {
      setFields([...fields, value]); // 체크되면 리스트에 추가
    } else {
      setFields(fields.filter((field) => field !== value)); // 해제하면 리스트에서 제거
    }
  };

  const handleRadioChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPersonalHistory(Number(e.target.value));
  };

  const fetchDevs = async () => {
    const token =
      "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQMSIsImlhdCI6MTczOTQwOTc2MywiZXhwIjoxNzM5NDUyOTYzfQ.W1Xiv2o3UNGe4L1IqLVGWHD5PSisEdQR2JuwHjPjnhI";

    const res = await fetch(
      `http://localhost:8080/programmers?field=${fields}&personalHistory=${personalHistory}`,
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        cache: "no-store",
      },
    );

    if (!res.ok) throw new Error("Failed to fetch devs");
    const data = await res.json();
    setProgrammers(data);
  };

  useEffect(() => {
    fetchDevs(); // 최초 1회 실행
  }, []);

  const experiences = [
    { value: 0, label: "신입" },
    { value: 3, label: "3년 이상" },
    { value: 5, label: "5년 이상" },
    { value: 10, label: "10년 이상" },
    { value: 15, label: "15년 이상" },
  ];

  return (
    <div className="container devlist">
      <div className="search">
        <div className={"select-options"}>
          <label className={"label"}>검색 옵션</label>
          <div className={"phcontainer"}>
            <label className={"label"}>경력</label>
            <div className={"personalHistory"}>
              {experiences.map((exp) => (
                <div className="checkboxContainer" key={exp.value}>
                  <input
                    type="radio"
                    value={exp.value}
                    id={`exp-${exp.value}`} // ID 중복 방지
                    name="personalHistory"
                    onChange={handleRadioChange}
                  />
                  <label htmlFor={`exp-${exp.value}`} className="checkboxLabel">
                    {exp.label}
                  </label>
                </div>
              ))}
            </div>
          </div>
          <div className={"fieldContainer"}>
            <label className={"label"}>분야</label>
            <div className={"field"}>
              <div className={"checkboxContainer"}>
                <input
                  type="checkbox"
                  value="Full_Stack"
                  id="full"
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="full" className={"checkboxLabel"}>
                  풀스택
                </label>
              </div>
              <div className={"checkboxContainer"}>
                <input
                  type="checkbox"
                  value="Front_End"
                  id="front"
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="front" className={"checkboxLabel"}>
                  프론트엔드
                </label>
              </div>
              <div className={"checkboxContainer"}>
                <input
                  type="checkbox"
                  value="Back_End"
                  id="back"
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="back" className={"checkboxLabel"}>
                  백엔드
                </label>
              </div>
              <div className={"checkboxContainer"}>
                <input
                  type="checkbox"
                  value="DBManege"
                  id="db"
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="db" className={"checkboxLabel"}>
                  DB 매니저
                </label>
              </div>
              <div className={"checkboxContainer"}>
                <input
                  type="checkbox"
                  value="ServerMange"
                  id="server"
                  onChange={handleCheckboxChange}
                />
                <label htmlFor="server" className={"checkboxLabel"}>
                  서버 매니저
                </label>
              </div>
            </div>
          </div>
        </div>
        <button className="button" onClick={fetchDevs}>
          조회
        </button>
      </div>
      <div className="container devlist">
        <header>
          <h3>개발자 목록</h3>
        </header>
        <section>
          {programmers.map((programmer) => (
            <DevItem
              key={programmer.id}
              id={programmer.id}
              name={programmer.name}
              age={programmer.age}
              field={programmer.fieldName}
              isLiked={programmer.isLiked}
              likeCount={programmer.likeCount}
            />
          ))}
        </section>
      </div>
    </div>
  );
}
