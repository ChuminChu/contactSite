"use client";

import {handleSubmit} from "@/app/login/actions";
import { IoIosSquareOutline, IoMdCheckboxOutline } from "react-icons/io";
import "./login.page.scss"
import Link from "next/link";

export default function LoginPage() {
  return (
      <div className="container login">
        <header>
          <h3>로그인/회원가입</h3>
        </header>
        <section>
          <form action={handleSubmit} className="loginForm">
            <div className={"loginType"}>
              <label>
                  <input type="radio" name={"loginType"} value={"dev"} required/>
                  <IoMdCheckboxOutline />
                  <IoIosSquareOutline />
                <span>개발자</span>
              </label>
              <label>
                  <input type="radio" name={"loginType"} value={"com"} required/>
                  <IoMdCheckboxOutline />
                  <IoIosSquareOutline />
                <span>기업</span>
              </label>
            </div>
            <div className={"loginId"}>
                {/*<label htmlFor="email">이메일</label>*/}
                <input type="text" name="userid" placeholder="아이디입력" required/>
            </div>
            <div className={"loginPw"}>
                {/*<label htmlFor="password">비밀번호</label>*/}
                <input type="password" name="userpw" placeholder="비밀번호입력" required/>
            </div>
            <div className={"loginBtn"}>
              <button>로그인</button>
            </div>
          </form>
          <div className={"signup"}>
              <p>개발자 또는 기업 회원가입을 하시면 다양한 서비스를 이용하실수 있습니다.</p>
              <div className="signupBtns">
                  <Link className="dev" href={"/developerSignup"}>개발자회원 가입하기</Link>
                  <span>|</span>
                  <Link className="com" href={"/companySignup"}>기업회원 가입하기</Link>
              </div>
          </div>
        </section>
      </div>
  );
}