"use client"

import Link from "next/link";
import "./company.page.scss";
import styles from "./page.module.css";
import {useEffect, useState} from "react";
import CopRow from "@/components/CopRow";
import {useSearchParams} from "next/navigation";
import {CompanyList} from "@/components/companyList";


interface Company {
    id: string;
    name: string;
    field: string;
    employeeCount: number;
    address: string;
    likeCount: number;
}

export function CompanyPage(company: Company) {
    return (
        <tr>
            <td>
                <Link className="dev" href={`/companyProfile/${company.id}`}>
                    {company.name}
                </Link>
            </td>
            <td>{company.field}</td>
            <td>{company.employeeCount}</td>
            <td>{company.address}</td>
            <td>{company.likeCount}</td>
        </tr>

    )
}


export default function CompanyListPage() {
    const [companies, setCompanies] = useState<Company[]>([]);

    const [selectedOption, setSelectedOption] = useState(false);
    const [selectedOption2, setSelectedOption2] = useState(false);

    function 클릭시실행함수() {
        setSelectedOption(!selectedOption);
    }

    function 클릭시실행함수2() {
        setSelectedOption2(!selectedOption2);
    }

    //첫페이지일때 이전버튼 비활성화
    const [currentPage, setCurrentPage] = useState(1);
    const totalPages = 100;

    //현재페이지가 1보다 크면 -1씩
    function 이전버튼클릭시() {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    }

    //현재페이지가 총페이지보다 작으면 +1
    function 다음버튼클릭시() {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    }

    //페이지 보여주는거 1234....
    const getPageNumbers = () => {
        let pages = [];
        const maxPagesToShow = 4; // 한 번에 보여줄 최대 페이지 개수
        const half = Math.floor(maxPagesToShow / 2);

        let startPage = Math.max(1, currentPage - half);
        let endPage = Math.min(totalPages, currentPage + half);

        // 범위를 벗어나지 않도록 startPage, endPage 조정
        if (startPage === 1) {
            endPage = Math.min(totalPages, maxPagesToShow);
        }
        if (endPage === totalPages) {
            startPage = Math.max(1, totalPages - maxPagesToShow + 1);
        }

        // 중복 방지: pages 배열에 존재하지 않는 숫자만 추가!
        const addPage = (page) => {
            if (!pages.includes(page)) pages.push(page);
        };

        addPage(1); // 첫 번째 페이지는 항상 표시
        if (startPage > 2) addPage("..."); // 첫 페이지와의 간격이 크면 '...' 표시

        for (let i = startPage; i <= endPage; i++) {
            addPage(i);
        }

        if (endPage < totalPages - 1) addPage("...");
        addPage(totalPages); // 마지막 페이지는 항상 표시

        return pages;
    };

    //검색필터링
    //필드 선택
    const [seletedFields, setSeletedFields] = useState<string[]>([]);

    const handleField = (field: string) => {
        setSeletedFields(prev => {
            if (prev.includes(field)){
                return prev.filter(f => f !== field);
            }
            return[...prev, field];
        })
    }

    useEffect(()=> {
        const fetchFilterdField = async () =>{
            // const fieldParam = seletedFields.join(",");
            // const addressParam ="서울";

            // 쿼리 파라미터 생성
            //필드, 지역, 필드+지역
            // const queryParams1 = `field=${fieldParam}`;
            // const queryParams2 = `address=${addressParam}`;
            // const queryParams3 = `field=${fieldParam}&address=${addressParam}`;

            let queryParams = "";

            if(seletedFields.length>0){
                queryParams += `field=${seletedFields.join(",")}`;
            }
            // if(addressParam){
            //     if(queryParams) queryParams += "&";
            //     queryParams += `address=${addressParam}`;
            // }
            // API 호출
            const response = await fetch(`http://localhost:8080/companies?${queryParams}`);
            const companies = await response.json();
            setCompanies(companies);
        };
        fetchFilterdField();
    },[seletedFields]);


    // ?field=Front_End
    // ?field=Front_End&address=서울
    // ?address=서울

    // function getField(){
    //     const field = {
    //         method:
    //     }
    // }

    useEffect(() => {
        const fetchCompanies = async () => {
            const response = await fetch('http://localhost:8080/companies?');
            const companies = await response.json()
            setCompanies(companies);
        }

        fetchCompanies();
    }, [])

    // const companies: Company[] = [
    //     {
    //         id: 1,
    //         name: 'TechCorp',
    //         field: 'Technology',
    //         employeeCount: 1200,
    //         address: '123 Tech Street, Silicon Valley',
    //         likeCount: 2500
    //     },
    //     {
    //         id: 2,
    //         name: 'HealthPlus',
    //         field: 'Healthcare',
    //         employeeCount: 450,
    //         address: '456 Wellness Blvd, New York',
    //         likeCount: 1800
    //     },
    //     {
    //         id: 3,
    //         name: 'GreenEnergy',
    //         field: 'Renewable Energy',
    //         employeeCount: 800,
    //         address: '789 Greenway, Texas',
    //         likeCount: 2100
    //     },
    //     {
    //         id: 4,
    //         name: 'FoodieWorks',
    //         field: 'Food & Beverage',
    //         employeeCount: 300,
    //         address: '101 Taste Ave, California',
    //         likeCount: 3200
    //     },
    //     {
    //         id: 5,
    //         name: 'FinSecure',
    //         field: 'Finance',
    //         employeeCount: 1500,
    //         address: '202 Secure St, London',
    //         likeCount: 2800
    //     },
    //     {
    //         id: 6,
    //         name: 'AutoMotiveX',
    //         field: 'Automotive',
    //         employeeCount: 2200,
    //         address: '303 Car Factory Rd, Detroit',
    //         likeCount: 3500
    //     },
    //     {
    //         id: 7,
    //         name: 'EduFuture',
    //         field: 'Education',
    //         employeeCount: 600,
    //         address: '404 Knowledge Blvd, Boston',
    //         likeCount: 2600
    //     },
    //     {id: 8, name: 'SmartHome', field: 'IoT', employeeCount: 400, address: '505 Smart St, Seattle', likeCount: 1500},
    //     {
    //         id: 9,
    //         name: 'GlobalReach',
    //         field: 'Logistics',
    //         employeeCount: 1100,
    //         address: '606 Cargo Rd, Chicago',
    //         likeCount: 2200
    //     },
    //     {
    //         id: 10,
    //         name: 'CloudWave',
    //         field: 'Cloud Computing',
    //         employeeCount: 700,
    //         address: '707 Cloud St, San Francisco',
    //         likeCount: 2300
    //     },
    //     {
    //         id: 11,
    //         name: 'MedTechX',
    //         field: 'Healthcare Technology',
    //         employeeCount: 850,
    //         address: '808 MedTech Blvd, Toronto',
    //         likeCount: 2900
    //     },
    //     {
    //         id: 12,
    //         name: 'EcoBuild',
    //         field: 'Construction',
    //         employeeCount: 1300,
    //         address: '909 Eco Rd, Denver',
    //         likeCount: 1800
    //     },
    //     {
    //         id: 13,
    //         name: 'GameMaster',
    //         field: 'Entertainment',
    //         employeeCount: 600,
    //         address: '1010 Game Ln, Los Angeles',
    //         likeCount: 4000
    //     },
    //     {
    //         id: 14,
    //         name: 'RetailPlus',
    //         field: 'Retail',
    //         employeeCount: 1500,
    //         address: '1111 Retail St, New York',
    //         likeCount: 1700
    //     },
    //     {
    //         id: 15,
    //         name: 'LegalEagle',
    //         field: 'Legal Services',
    //         employeeCount: 500,
    //         address: '1212 Justice Ave, Washington',
    //         likeCount: 2100
    //     },
    //     {
    //         id: 16,
    //         name: 'TravelMate',
    //         field: 'Travel',
    //         employeeCount: 300,
    //         address: '1313 Journey Rd, Paris',
    //         likeCount: 1500
    //     },
    //     {
    //         id: 17,
    //         name: 'BioLife',
    //         field: 'Biotech',
    //         employeeCount: 900,
    //         address: '1414 Life St, Boston',
    //         likeCount: 2700
    //     },
    //     {
    //         id: 18,
    //         name: 'CyberTech',
    //         field: 'Cybersecurity',
    //         employeeCount: 1000,
    //         address: '1515 Cyber Ave, San Diego',
    //         likeCount: 3200
    //     },
    //     {
    //         id: 19,
    //         name: 'FashionFuture',
    //         field: 'Fashion',
    //         employeeCount: 400,
    //         address: '1616 Fashion Blvd, Milan',
    //         likeCount: 2500
    //     },
    //     {
    //         id: 20,
    //         name: 'SportsNet',
    //         field: 'Sports & Fitness',
    //         employeeCount: 500,
    //         address: '1717 Sports Rd, Miami',
    //         likeCount: 2000
    //     }
    // ];


    return (
        <>
            <div className="search search-dev">
                <header className={styles.searchbox}>검색창</header>
                <section className={styles.option}>
                    <div>
                        <button onClick={() => handleField("Front_End")}
                                className={`${styles.button1} ${styles.selected}`} >Front End</button>
                        <button onClick={() => handleField("Back_End")}>Back End</button>
                        <button onClick={() => handleField("Full_Stack")}>Full Stack</button>
                        <button onClick={() => handleField("DBManege")}>DB Manager</button>
                        <button onClick={() => handleField("ServerMange")}>Server Manage</button>
                    </div>
                    {/*<div className={styles.optionContainer}>*/}
                    {/*    <button*/}
                    {/*        className={`${styles.button} ${selectedOption ? styles.selected : ''}`}*/}
                    {/*        onClick={클릭시실행함수}>*/}
                    {/*        {selectedOption ? "구인분야 X" : "구인분야"}*/}

                    {/*    </button>*/}
                    {/*    <button className={`${styles.button} ${selectedOption2 ? styles.selected : ''}`}*/}
                    {/*            onClick={클릭시실행함수2}>*/}
                    {/*        {selectedOption2 ? "지역 X" : "지역"}*/}
                    {/*    </button>*/}
                    {/*</div>*/}
                </section>
            </div>

                {/*<div className={styles.dropdown}>*/}
                {/*    <button className={styles.dropbtn}>구인 분야*/}
                {/*        <i className="fa fa-caret-down"></i>*/}
                {/*    </button>*/}
                {/*    <div className={styles.dropdownContent}>*/}
                {/*        <a href="#">Front_End</a>*/}
                {/*        <a href="#">Back_End</a>*/}
                {/*        <a href="#">Full_Stack</a>*/}
                {/*        <a href="#">DBManege</a>*/}
                {/*        <a href="#">ServerMange</a>*/}
                {/*    </div>*/}
                {/*</div>*/}
            <div className="container comlist">
                <header>
                    <h3>기업 목록</h3>
                </header>

                <section>
                    {/*<table className={styles.table}>*/}
                    {/*    <thead>*/}
                    {/*    <tr className={styles.item}>*/}
                    {/*        <th>기업명</th>*/}
                    {/*        <th>구인분야</th>*/}
                    {/*        <th>직원수</th>*/}
                    {/*        <th>기업소재지</th>*/}
                    {/*        <th>좋아요</th>*/}
                    {/*    </tr>*/}
                    {/*    </thead>*/}

                    {/*    <tbody>*/}
                    {/*    {companies.map(company =>*/}
                    {/*        <CompanyPage*/}
                    {/*            key={company.id}*/}
                    {/*            id={company.id}*/}
                    {/*            name={company.name}*/}
                    {/*            field={company.field}*/}
                    {/*            employeeCount={company.employeeCount}*/}
                    {/*            address={company.address}*/}
                    {/*            likeCount={company.likeCount}/>)}*/}
                    {/*    </tbody>*/}

                    {/*</table>*/}
                    <CompanyList companies={companies} />
                    <div className={styles.pagination}>
                        <button className={styles.button}
                                onClick={이전버튼클릭시}
                                disabled={currentPage === 1}>
                            이전
                        </button>

                        {getPageNumbers().map((page, index) =>
                            page === "..." ? (
                                <span key={`ellipsis-${index}`} className={styles.ellipsis}>...</span>
                            ) : (
                                <button
                                    key={`page-${page}`}
                                    onClick={() => setCurrentPage(page)}
                                    className={currentPage === page ? styles.activePage : ""}
                                >
                                    {page}
                                </button>
                            )
                        )}


                        <button className={styles.button}
                                onClick={다음버튼클릭시}
                                disabled={currentPage === totalPages}>
                            다음
                        </button>
                    </div>

                </section>
            </div>
        </>

    );
}
