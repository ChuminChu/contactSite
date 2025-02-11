//"use client";

//import React, { useState } from "react";
import Link from "next/link";
import { FaSignInAlt, FaUser, FaComment, FaBuilding } from "react-icons/fa";
import { getUserType, setUserType } from "@/app/userTypeUtils";

export default async function Header() {
  await setUserType(); // 서버에서 userType 설정
  const userType = getUserType(); // 전역 userType 가져오기

  //const authState: string = "notLoggedIn";
  return (
    <div className="hdrWrap">
      <header>
        <h1 className="logo">
          <Link href="/">LOGOHERE</Link>
        </h1>
        <ul className="nav">
          <li>
            <Link href="/developerList">개발자 목록</Link>
          </li>
          <li>
            <Link href="/companyList">기업 목록</Link>
          </li>
        </ul>
        <div className="utilBox">
          {userType === "notLoggedIn" && (
            <>
              <Link href={"/login"}>
                <FaSignInAlt />
              </Link>
            </>
          )}
          {(userType === "developer" || userType === "businessmen") && (
            <>
              <Link href={""}>
                <FaComment />
              </Link>
              <Link href={""}>
                {userType === "developer" ? (
                  <FaUser />
                ) : userType === "businessmen" ? (
                  <FaBuilding />
                ) : null}
              </Link>
            </>
          )}
        </div>
      </header>
    </div>
  );
}
