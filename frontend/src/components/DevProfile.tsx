import React from "react";
import "./devProfile.scss";

export default function DevProfile({ dev }) {
  if (!dev) {
    return <p>유저 정보를 불러올 수 없습니다.</p>;
  }

  return (
    <div className="profileContainer">
      <h2>{dev.name}님의 프로필</h2>
      <div className="profileDetails">
        {dev.userId && (
          <p>
            <strong>아이디:</strong> {dev.userId}
          </p>
        )}
        <p>
          <strong>이름:</strong> {dev.name}
        </p>
        <p>
          <strong>나이:</strong> {dev.age}세
        </p>
        <p>
          <strong>이메일:</strong> {dev.email}
        </p>
        <p>
          <strong>경력:</strong> {dev.personalHistory}년
        </p>
        <p>
          <strong>분야:</strong> {dev.fieldName.join(",")}
        </p>
        <p>
          <strong>자기소개:</strong> {dev.selfIntroduction}
        </p>
        <p>
          <strong>자격증:</strong> {dev.certificate}
        </p>
      </div>
    </div>
  );
}
