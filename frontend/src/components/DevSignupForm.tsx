"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import "./DevSignupForm.scss";
import { FaStarOfLife } from "react-icons/fa";

export function DevSignupForm() {
  const [userId, setUserId] = useState("");
  const [isIdAvailable, setIsIdAvailable] = useState<boolean | null>(null); // 아이디 중복 확인 결과 상태 추가
  const [password, setPassword] = useState("");
  const [checkPassword, setCheckPassword] = useState("");
  const [passwordCheckMSG, setPasswordCheckMSG] = useState(""); // 일치하지 않는 비밀번호 메시지
  const [name, setName] = useState("");
  const [year, setYear] = useState("");
  const [month, setMonth] = useState("");
  const [day, setDay] = useState("");
  const [email, setEmail] = useState("");
  const [personalHistory, setPersonalHistory] = useState(0);
  const [fieldName, setFieldName] = useState<string[]>([]);
  const [selfIntroduction, setSelfIntroduction] = useState("");
  const [certificate, setCertificate] = useState("");
  const router = useRouter();

  // 아이디 중복 확인 함수
  const checkIdDuplication = async () => {
    if (!userId) {
      alert("아이디를 입력하세요!");
      return;
    }

    const response = await fetch("http://localhost:8080/programmers/check-id", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId }),
    });

    const data = await response.json();

    if (data.available) {
      setIsIdAvailable(true);
      alert("사용 가능한 아이디입니다.");
    } else {
      setIsIdAvailable(false);
      alert("이미 사용 중인 아이디입니다.");
    }
  };

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

    if (response.ok) {
      router.push(`/`);
    } else {
      alert("Failed to create Developer Account");
    }
  };

  useEffect(() => {
    validatePassword();
  }, [password, checkPassword]);

  // 비밀번호 확인 처리
  const validatePassword = () => {
    if (checkPassword === "") {
      setPasswordCheckMSG(""); // 비밀번호 확인 칸이 비어있을 때 메시지 없음
    } else if (password === checkPassword) {
      setPasswordCheckMSG("✔ 비밀번호가 일치합니다."); // 초록색 메시지
    } else {
      setPasswordCheckMSG("❌ 비밀번호가 일치하지 않습니다."); // 빨간색 경고 메시지
    }
  };

  // 체크박스 선택 처리
  const handleCheckboxChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { value, checked } = e.target;
    if (checked) {
      setFieldName([...fieldName, value]); // 체크되면 리스트에 추가
    } else {
      setFieldName(fieldName.filter((field) => field !== value)); // 해제하면 리스트에서 제거
    }
  };

  const fieldOptions = [
    { label: "풀스택", value: "Full_Stack" },
    { label: "프론트엔드", value: "Front_End" },
    { label: "백엔드", value: "Back_End" },
    { label: "DB 매니저", value: "DBManege" },
    { label: "서버 매니저", value: "ServerMange" },
  ];

  return (
    <form onSubmit={handleSubmit} className="formContainer">
      <div className="formLayout">
        <label htmlFor="userId" className="label">
          아이디
          <FaStarOfLife className={"require"} />
        </label>
        <div className={"userId"}>
          <input
            type="text"
            id="userId"
            value={userId}
            onChange={(e) => setUserId(e.target.value)}
            required
            className={"inputId"}
          />
          <button type="button" onClick={checkIdDuplication}>
            중복 확인
          </button>
        </div>
        <label htmlFor="password" className={"label"}>
          비밀번호
          <FaStarOfLife className={"require"} />
        </label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => {
            setPassword(e.target.value);
          }}
          required
          className={"input"}
        />
        <label htmlFor="checkPassword" className={"label"}>
          비밀번호 확인
          <FaStarOfLife className={"require"} />
        </label>
        <div className={"checkPassword"}>
          <input
            type="password"
            id="checkPassword"
            value={checkPassword}
            onChange={(e) => {
              setCheckPassword(e.target.value);
            }}
            required
            className={"input"}
          />
          {passwordCheckMSG && (
            <p
              className={
                password === checkPassword ? "success-message" : "error-message"
              }
            >
              {passwordCheckMSG}
            </p>
          )}
        </div>
        <label htmlFor="name" className={"label"}>
          이름
          <FaStarOfLife className={"require"} />
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
          <FaStarOfLife className={"require"} />
        </label>
        <div className={"birth"}>
          <div className={"year"}>
            <select
              id="year"
              value={year}
              onChange={(e) => setYear(e.target.value)}
              required
              className={"inputBirth"}
            >
              <option disabled={true} value=""></option>
              {Array.from(
                { length: 101 },
                (_, i) => new Date().getFullYear() - i,
              ).map((y) => (
                <option key={y} value={y}>
                  {y}
                </option>
              ))}
            </select>
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
              <option disabled={true} value=""></option>
              {Array.from({ length: 12 }, (_, i) => i + 1).map((num) => (
                <option key={num} value={num}>
                  {num}
                </option>
              ))}
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
        <div className="field">
          {fieldOptions.map((field) => (
            <div className="checkboxContainer" key={field.value}>
              <input
                type="checkbox"
                value={field.value}
                id={field.value}
                onChange={handleCheckboxChange}
              />
              <label htmlFor={field.value} className="checkboxLabel">
                {field.label}
              </label>
            </div>
          ))}
        </div>
        ;
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
