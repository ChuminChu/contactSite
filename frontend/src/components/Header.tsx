//"use client";

//import React, { useState } from "react";
import Link from "next/link";
import { FaSignInAlt, FaUser, FaBuilding } from "react-icons/fa";
import { getUserType, setUserType } from "@/app/userTypeUtils";
import { cookies } from "next/headers";

import ProposalToggle from "@/components/ProposalToggle";

export default async function Header() {
  const cookieStore = await cookies();
  const token = cookieStore.get("token")?.value; // 쿠키에서 token을 가져옴

  await setUserType(); // 서버에서 userType 설정
  const userType = getUserType(); // 전역 userType 가져오기

  return (
    <>
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
                <ProposalToggle token={token || ""} />

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
    </>
  );
}
