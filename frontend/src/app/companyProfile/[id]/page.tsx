"use client"

import {useEffect, useState} from "react";
import styles from "./page.module.css";
import {useParams} from "next/navigation";

//기업 프로필 상세보기 + 좋아요, 지원하기
interface Profile {
    id: string;
    name: string;
    businesstype: string;
    field: string;
    website: string;
    address: string;
    employeecount: number;
    introduction: string;
    established: string;
    likeCount: number;
}

// const companyData: { [key : number]: Profile } = {
//     1:{
//         id: 1,
//         name: "TechCorp",
//         businessType: "IT 기업",
//         field: "Software Development",
//         website: "https://www.techcorp.com",
//         address: "123 Tech Street, Silicon Valley",
//         employeeCount: 100,
//         introduction: "혁신적인 소프트웨어 솔루션을 제공하는 기업입니다.",
//         establishment: "2021-11-11"
//     },
//     2:{
//         id:3,
//         name : "kakao",
//         businessType : "IT",
//         field : "Software Development",
//         website : "https://www.kakaocorp.com/page/service/service/KakaoTalk",
//         address: "주소명",
//         employeeCount: 1000,
//         introduction : "기업소개",
//         establishment: "2021-11-11"
//     }
// };

export default function ProfilePage() {
    const params = useParams();  //기업 id 가져옴
    const companyId = params.id as string;
    // const companyProfile = companyData[companyId];
    const [profile, setProfile] = useState<Profile>({} as Profile);

    //날짜 date객체로 변환
    const formattedDate = profile.established
        ? new Date(profile.established).toLocaleDateString()
        : ""; // 기본값 추가

    //좋아요
    const [selectedOption, setSelectedOption] = useState(false);
    function 좋아요버튼클릭시(){
        setSelectedOption(!selectedOption);
    }

    //지원하기
    const [selectedOption2, setSelectedOption2] = useState(false);
    function 지원하기버튼클릭시(){
        setSelectedOption2(!selectedOption2);
    }

    useEffect(() => {
        const fetchProfle = async () => {
            const response = await fetch(`http://localhost:8080/companies/${companyId}`);
            const profiledata = await response.json()
            setProfile(profiledata);
        }
            fetchProfle();
    },[companyId]);

    return (
        <>
            <div className="container comlist">
                <header>
                    <h3>기업 프로필</h3>
                </header>
                <table className={styles.table}>
                    <tbody>
                    <tr>
                        <td className={styles.title}>회사명</td>
                        <td className={styles.content}>{profile.name}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>비즈니스 분야</td>
                        <td>{profile.businesstype}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>구인 분야</td>
                        <td>{profile.field}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>웹사이트 링크</td>
                        <td>
                            <a href={profile.website} target="_blank" rel="noopener noreferrer">
                                {profile.website}
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td className={styles.title}>지역</td>
                        <td>{profile.address}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>직원수</td>
                        <td>{profile.employeecount}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>회사 소개</td>
                        <td>{profile.introduction}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>설립년도</td>
                        <td>{formattedDate}</td>
                    </tr>
                    <tr>
                        <td className={styles.title}>좋아요 개수</td>
                        <td>{profile.likeCount}</td>
                    </tr>
                    </tbody>
                </table>

                <div className={styles.option}>
                    <button className={`${styles.button} ${selectedOption ? styles.selected : ''}`}
                            onClick={좋아요버튼클릭시}>
                        {selectedOption ? "좋아요X" : "좋아요"}
                    </button>
                    <button className={`${styles.button} ${selectedOption2 ? styles.selected2 : ''}`}
                            onClick={지원하기버튼클릭시}>
                        {selectedOption2 ? "지원완료" : "지원하기"}</button>
                </div>

            </div>
        </>
    )
}