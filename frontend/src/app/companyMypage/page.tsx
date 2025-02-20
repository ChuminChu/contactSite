"use client"

import styles from "@/app/companyProfile/[id]/page.module.css";
import {useEffect, useState} from "react";

//백에서 회원정보 토큰

interface MyPage {
    id: string;
    name: string;
    password: string;
    businessType: string;
    field: string;
    website: string;
    address: string;
    employeeCount: number;
    introduction: string;
    establishment: string;
}

export default function CompanyMypage() {

    // const myData: MyPage = {
    //     id: "1",
    //     name: "TechCorp",
    //     password: "1234546_asdFGH",
    //     businessType: "IT 기업",
    //     field: "Software Development",
    //     website: "https://www.techcorp.com",
    //     address: "123 Tech Street, Silicon Valley",
    //     employeeCount: 100,
    //     introduction: "혁신적인 소프트웨어 솔루션을 제공하는 기업입니다.",
    //     establishment: "2021-11-11",
    // }

    const [MyData, setMyData] = useState<MyPage>({} as MyPage);

    useEffect(() => {
        const fetchData = async () => {
            const response = await fetch("http://localhost:8080/companies/my");
            const mypagedata = await response.json();
            setMyData(mypagedata);
        }
        fetchData();
    })


    return (
        <div className="container comlist">
            <header>
                <h3>기업용 마이페이지</h3>
            </header>
            <table className={styles.table}>
                <tbody>
                <tr>
                    <td className={styles.title}>아이디</td>
                    <td>{MyData.id}</td>
                </tr>
                <tr>
                    <td className={styles.title}>비밀번호</td>
                    <td>{MyData.password}</td>
                    <td>
                        <button>비밀번호 변경</button>
                    </td>
                </tr>
                <tr>
                    <td className={styles.title}>회사명</td>
                    <td>{MyData.name}</td>
                </tr>
                <tr>
                    <td className={styles.title}>비즈니스 분야</td>
                    <td>{MyData.businessType}</td>
                </tr>
                <tr>
                    <td className={styles.title}>구인 분야</td>
                    <td>{MyData.field}</td>
                </tr>
                <tr>
                    <td className={styles.title}>웹사이트 링크</td>
                    <td>{MyData.website}</td>
                </tr>
                <tr>
                    <td className={styles.title}>지역</td>
                    <td>{MyData.address}</td>
                </tr>
                <tr>
                    <td className={styles.title}>직원수</td>
                    <td>{MyData.employeeCount}</td>
                </tr>
                <tr>
                    <td className={styles.title}>회사 소개</td>
                    <td>{MyData.introduction}</td>
                </tr>
                <tr>
                    <td className={styles.title}>설립년도</td>
                    <td>{MyData.establishment}</td>
                </tr>
                </tbody>
            </table>

            < div className={styles.option}>
                <button className={styles.button1}>수정하기</button>
                <button className={styles.button2}>탈퇴하기</button>
            </div>
        </div>
    )
}