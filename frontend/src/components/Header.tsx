"use client";

import React, { useState } from "react";
import Link from "next/link";
import { FaSignInAlt,FaUser,FaComment ,FaBuilding} from "react-icons/fa";

export default function Header() {
  const [authState, setAuthState] = useState<"notLoggedIn" | "developer" | "businessmen">("notLoggedIn");

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
          {authState === "notLoggedIn" && (
              <>
                <Link href={"/login"}>
                  <FaSignInAlt />
                </Link>
              </>
          )}

          {authState === "developer" && (
              <>
                <Link href={""}><FaComment /></Link>
                <Link href={"/editDeveloper"}><FaUser /></Link>
              </>
          )}

          {authState === "businessmen" && (
              <>
                <Link href={""}><FaComment /></Link>
                <Link href={"/editCompany"}><FaBuilding /></Link>
              </>
          )}
        </div>
      </header>
    </div>
  );
}
