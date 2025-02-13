"use client"

import styles from "@/app/companyProfile/[id]/page.module.css";
import {useState} from "react";

//백에서 회원정보 받아옴 - 쿼리파라미터? (컨트롤러에서는 로그인멤버 토큰으로 받음)

interface MyPage {
    id: string;
    name: string;
    password : string;
    businesstype: string;
    field: string;
    website: string;
    address: string;
    employeecount: number;
    introduction: string;
    established: string;
    likeCount: number;

}

export default function CompanyMypage() {

    const MyData = {
        id: 1,
        name: "TechCorp",
        password: "1234546_asdFGH",
        businessType: "IT 기업",
        field: "Software Development",
        website: "https://www.techcorp.com",
        address: "123 Tech Street, Silicon Valley",
        employeeCount: 100,
        introduction: "혁신적인 소프트웨어 솔루션을 제공하는 기업입니다.",
        establishment: "2021-11-11"
    }

    const [mypage, setMypage] = useState<MyPage>({} as MyPage);

    return (
        <div className="container comlist">
            <header>
                <h3>기업용 마이페이지</h3>
            </header>
            <table className={styles.table}>
                <tbody>
                <tr>
                    <td className={styles.title}>아이디</td>
                    <td></td>
                </tr>
                <tr>
                    <td className={styles.title}>비밀번호?</td>
                    <td><button>비밀번호 변경</button></td>
                </tr>
                <tr>
                    <td className={styles.title}>회사명</td>
                </tr>
                <tr>
                    <td className={styles.title}>비즈니스 분야</td>
                </tr>
                <tr>
                    <td className={styles.title}>구인 분야</td>
                </tr>
                <tr>
                    <td className={styles.title}>웹사이트 링크</td>
                </tr>
                <tr>
                    <td className={styles.title}>지역</td>
                </tr>
                <tr>
                    <td className={styles.title}>직원수</td>
                </tr>
                <tr>
                    <td className={styles.title}>회사 소개</td>
                </tr>
                <tr>
                    <td className={styles.title}>설립년도</td>
                </tr>
                </tbody>
            </table>

            <div>
                <button>수정하기</button>
                <button>탈퇴하기</button>
            </div>
        </div>
    )
}