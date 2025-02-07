"use client";

import { useState } from "react";
import { useRouter } from "next/navigation";
import "./DevSignupForm.scss";

export function DevSignupForm() {
  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  // const [birthDate, setBirthDate] = useState("");
  const [email, setEmail] = useState("");
  const [personalHistory, setPersonalHistory] = useState(0);
  const [fieldName, setFieldName] = useState<string[]>([]);
  const [selfIntroduction, setSelfIntroduction] = useState("");
  const [certificate, setCertificate] = useState("");
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!year || !month || !day) {
      alert("생년월일을 모두 입력하세요!");
      return;
    }

    const birthDate = `${year}-${month.padStart(2, "0")}-${day.padStart(2, "0")}`;

    const response = await fetch("http://localhost:8080/programmers", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        userId,
        password,
        name,
        birthDate,
        email,
        personalHistory: Number(personalHistory),
        fieldName,
        selfIntroduction,
        certificate,
      }),
    });
    // const response = await fetch('http://localhost:8080/posts', {
    //   method: 'POST',
    //   headers: {
    //     'Content-Type': 'application/json',
    //   },
    //   body: JSON.stringify({ title, content, boardId }),
    // })

    if (response.ok) {
      router.push(`/`);
    } else {
      alert("Failed to create Developer Account");
    }
  };

  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = e.target;
    if (checked) {
      setFieldName([...fieldName, value]); // 체크되면 리스트에 추가
    } else {
      setFieldName(fieldName.filter((field) => field !== value)); // 해제하면 리스트에서 제거
    }
  };

  return (
    <form onSubmit={handleSubmit} className="formContainer">
      <div className="formLayout">
        <label htmlFor="userId" className="label">
          아이디
        </label>
        <input
          type="text"
          id="userId"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
          className={"inputId"}
        />

        <label htmlFor="password" className={"label"}>
          비밀번호
        </label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className={"input"}
        />

        <label htmlFor="password" className={"label"}>
          비밀번호 확인
        </label>
        <input type="password" id="password" required className={"input"} />

        <label htmlFor="name" className={"label"}>
          이름
        </label>
        <input
          type="text"
          id="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          className={"input"}
        />

        <label htmlFor="birthDate" className={"label"}>
          생년월일
        </label>
        <div className={"birth"}>
          <div className={"year"}>
            <input
              type="number"
              placeholder="년(4자)"
              id="year"
              value={year}
              onChange={(e) => setYear(e.target.value)}
              required
              className={"inputBirth"}
            />
            <span> 년</span>
          </div>

          <div className={"month"}>
            <select
              id="month"
              value={month}
              onChange={(e) => setMonth(e.target.value)}
              required
              className={"inputBirth"}
            >
              <option value="1">1</option>
              <option value="2">2</option>
              <option value="3">3</option>
              <option value="4">4</option>
              <option value="5">5</option>
              <option value="6">6</option>
              <option value="7">7</option>
              <option value="8">8</option>
              <option value="9">9</option>
              <option value="10">10</option>
              <option value="11">11</option>
              <option value="12">12</option>
            </select>
            <span> 월</span>
          </div>

          <div className={"day"}>
            <input
              type="number"
              id="day"
              value={day}
              onChange={(e) => setDay(e.target.value)}
              required
              className={"inputBirth"}
            />
            <span> 일</span>
          </div>
        </div>

        <label htmlFor="email" className={"label"}>
          이메일
        </label>
        <input
          type="email"
          id="email"
          value={email}
          placeholder={"example@example.com"}
          onChange={(e) => setEmail(e.target.value)}
          required
          className={"input"}
        />

        <label htmlFor="personalHistory" className={"label"}>
          경력 (년)
        </label>
        <div className={"personalHistory"}>
          <input
            type="number"
            id="personalHistory"
            value={personalHistory}
            onChange={(e) => setPersonalHistory(Number(e.target.value))}
            onFocus={() => setPersonalHistory("")}
            required
            className={"inputPersonalHistory"}
          />
          <span> 년</span>
        </div>

        <label htmlFor="fieldName" className={"label"}>
          분야
        </label>
        <div className={"field"}>
          <div className={"checkboxContainer"}>
            <label htmlFor="fieldName" className={"checkboxLabel"}>
              풀스택
            </label>
            <input
              type="checkbox"
              value="Full_Stack"
              onChange={handleCheckboxChange}
            />
          </div>
          <div className={"checkboxContainer"}>
            <label htmlFor="fieldName" className={"checkboxLabel"}>
              프론트엔드
            </label>
            <input
              type="checkbox"
              value="Front_End"
              onChange={handleCheckboxChange}
            />
          </div>
          <div className={"checkboxContainer"}>
            <label htmlFor="fieldName" className={"checkboxLabel"}>
              백엔드
            </label>
            <input
              type="checkbox"
              value="Back_End"
              onChange={handleCheckboxChange}
            />
          </div>
          <div className={"checkboxContainer"}>
            <label htmlFor="fieldName" className={"checkboxLabel"}>
              DB 매니저
            </label>
            <input
              type="checkbox"
              value="DBManage"
              onChange={handleCheckboxChange}
            />
          </div>
          <div className={"checkboxContainer"}>
            <label htmlFor="fieldName" className={"checkboxLabel"}>
              서버 매니저
            </label>
            <input
              type="checkbox"
              value="ServerManage"
              onChange={handleCheckboxChange}
            />
          </div>
        </div>

        <label htmlFor="selfIntroduction" className={"label"}>
          자기 소개
        </label>
        <textarea
          id="selfIntroduction"
          value={selfIntroduction}
          onChange={(e) => setSelfIntroduction(e.target.value)}
          required
          className={"textareaSelf"}
        ></textarea>

        <label htmlFor="certificate" className={"label"}>
          자격증
        </label>
        <textarea
          id="certificate"
          value={certificate}
          placeholder={
            "보유중인 자격증을 입력해주세요. ex) 정보처리기사, 리눅스마스터, ..."
          }
          onChange={(e) => setCertificate(e.target.value)}
          required
          className={"textareaCert"}
        ></textarea>
      </div>
      <button type="submit" className={"submitButton"}>
        회원 가입
      </button>
    </form>
  );
}
