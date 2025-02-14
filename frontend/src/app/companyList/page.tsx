"use client"

import "./company.page.scss";
import styles from "./page.module.css";
import {useEffect, useState} from "react";
import {CompanyList} from "@/components/companyList";
import {PageButtons} from "@/components/page-buttons";

export interface PageResponse {
    totalPage: number;
    pageNumber : number;
    companies : Company[];
}

export interface Company {
    id: string;
    name: string;
    field: string;
    address: string;
    employeeCount: number;
    likeCount: number;
}

export default function CompanyListPage() {
    const [companies, setCompanies] = useState<Company[]>([]);
    const [page, setPage] = useState({totalPages: 0, currentPage: 1});

    // const [selectedOption, setSelectedOption] = useState(false);
    // const [selectedOption2, setSelectedOption2] = useState(false);
    //
    // function 클릭시실행함수() {
    //     setSelectedOption(!selectedOption);
    // }
    //
    // function 클릭시실행함수2() {
    //     setSelectedOption2(!selectedOption2);
    // }


    //검색필터링
    //필드 선택
    const [seletedFields, setSeletedFields] = useState<string[]>([]);

    const handleField = (field: string) => {
        setSeletedFields(prev => {
            if (prev.includes(field)) {
                return prev.filter(f => f !== field);
            }
            return [...prev, field];
        })
    }

    useEffect(() => {
        const fetchFilterdField = async () => {

            let queryParams = "";

            if (seletedFields.length > 0) {
                queryParams += `field=${seletedFields.join(",")}`;
            }

            // API 호출
            const response = await fetch(`http://localhost:8080/companies?${queryParams}`);
            const companyListResponse: PageResponse = await response.json();
            setCompanies(companyListResponse.companies);
        };
        fetchFilterdField();
    }, [seletedFields]);


    useEffect(() => {
        const fetchCompanies = async () => {
            const response = await fetch('http://localhost:8080/companies');
            const companyListResponse :PageResponse = await response.json()
            setCompanies(companyListResponse.companies);
            setPage({totalPages: companyListResponse.totalPage, currentPage: companyListResponse.pageNumber});
        }

        fetchCompanies();
    }, [])

    console.log(page)
    return (
        <>
            <div className="search search-dev">
                <header className={styles.searchbox}>검색창</header>
                <section className={styles.option}>
                    <div>
                        <button onClick={() => handleField("Front_End")}
                                className={`${styles.button1} ${styles.selected}`}>Front End
                        </button>
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
            <div className="container comlist">
                <header>
                    <h3>기업 목록</h3>
                </header>

                <section>
                    <CompanyList companies={companies}/>
                    <PageButtons totalPage={page.totalPages}
                                 currentPage={page.currentPage}
                                 setCompanies={setCompanies}
                                 setPage={setPage}
                    />
                </section>
            </div>
        </>

    );
}
