import React from "react";
import "./DevSignupForm.scss";
import "./DevEdit.scss";
import Link from "next/link";
import { router } from "next/client";
import { redirect } from "next/navigation";

async function updateDeveloper(formData: FormData) {
  "use server"; // 서버 액션 사용
  const updatedData = {
    userId: "seung5727",
    name: formData.get("name"),
    birthDate: `${formData.get("year") as string}-${(formData.get("month") as string).padStart(2, "0")}-${(formData.get("day") as string).padStart(2, "0")}`,
    email: formData.get("email"),
    personalHistory: formData.get("personalHistory"),
    fieldName: formData.getAll("fieldName"), // 체크박스 값들 배열로 가져옴
    selfIntroduction: formData.get("selfIntroduction"),
    certificate: formData.get("certificate"),
  };

  const token =
    "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJQMSIsImlhdCI6MTczOTQwOTc2MywiZXhwIjoxNzM5NDUyOTYzfQ.W1Xiv2o3UNGe4L1IqLVGWHD5PSisEdQR2JuwHjPjnhI";

  // ✅ 서버 API 호출
  const res = await fetch("http://localhost:8080/programmers/my", {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`, // 실제 토큰으로 변경
    },
    body: JSON.stringify(updatedData),
  });

  if (!res.ok) {
    throw new Error("수정에 실패했습니다.");
  }
  redirect("/developerMyPage");
}

export default function DevEdit({ dev }) {
  if (!dev) {
    return <p>유저 정보를 불러올 수 없습니다.</p>;
  }

  const dateString = dev.birthDate;
  const [year, month, day] = dateString.split("-");

  const fieldOptions = [
    { label: "풀스택", value: "Full_Stack" },
    { label: "프론트엔드", value: "Front_End" },
    { label: "백엔드", value: "Back_End" },
    { label: "DB 매니저", value: "DBManege" },
    { label: "서버 매니저", value: "ServerMange" },
  ];

  return (
    <div className="profileContainer">
      <h2>프로필 수정</h2>
      <form className="formContainer" action={updateDeveloper}>
        <div className="formLayout">
          <label htmlFor="name" className={"label"}>
            이름
          </label>
          <input
            type="text"
            id="name"
            name="name"
            defaultValue={dev.name}
            required
            className={"input"}
          />

          <label htmlFor="birthDate" className={"label"}>
            생년월일
          </label>
          <div className={"birth"}>
            <div className={"year"}>
              <select
                id="year"
                name="year"
                defaultValue={year}
                required
                className={"inputBirth"}
              >
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
                name="month"
                defaultValue={month}
                required
                className={"inputBirth"}
              >
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
                name="day"
                defaultValue={day}
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
            name="email"
            defaultValue={dev.email}
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
              name="personalHistory"
              defaultValue={dev.personalHistory}
              required
              className={"inputPersonalHistory"}
            />
            <span> 년</span>
          </div>

          <label htmlFor="fieldName" className={"label"}>
            분야
          </label>
          <div className={"field"}>
            {fieldOptions.map((field) => (
              <div className={"checkboxContainer"} key={field.value}>
                <input
                  type="checkbox"
                  name="fieldName"
                  value={field.value}
                  id={field.value}
                  defaultChecked={dev.fieldName.includes(field.value)}
                />
                <label htmlFor={field.value} className={"checkboxLabel"}>
                  {field.label}
                </label>
              </div>
            ))}
          </div>

          <label htmlFor="selfIntroduction" className={"label"}>
            자기 소개
          </label>
          <textarea
            id="selfIntroduction"
            name="selfIntroduction"
            defaultValue={dev.selfIntroduction}
            required
            className={"textareaSelf"}
          ></textarea>

          <label htmlFor="certificate" className={"label"}>
            자격증
          </label>
          <textarea
            id="certificate"
            name="certificate"
            defaultValue={dev.certificate}
            required
            className={"textareaCert"}
          ></textarea>
        </div>

        <div className={"btnContainer"}>
          <button type="submit" className={"submitButton"}>
            수정
          </button>
          <Link href={"/developerMyPage"}>
            <button className={"submitButton cancelButton"} type="button">
              취소
            </button>
          </Link>
        </div>
      </form>
    </div>
  );
}
