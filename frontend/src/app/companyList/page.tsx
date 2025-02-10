"use client"

import "./company.page.scss";
import styles from "./page.module.css";
import {useState} from "react";

const API_URL = "https://localhost:3000/companies"

interface Company {
    id: number;
    name: string;
    field: string;
    employeeCount: number;
    address: string;
    likeCount: number;
}

function CompanyPage(company: Company) {
    return (
        <tr>
            <td>{company.name}</td>
            <td>{company.field}</td>
            <td>{company.employeeCount}</td>
            <td>{company.address}</td>
            <td>{company.likeCount}</td>
        </tr>

    )
}


export default function CompanyListPage() {

    const [selectedOption, setSelectedOption] = useState(false);
    const [selectedOption2, setSelectedOption2] = useState(false);


    function 클릭시실행함수() {
        setSelectedOption(!selectedOption);
    }

    function 클릭시실행함수2() {
        setSelectedOption2(!selectedOption2);
    }

    const companies: Company[] = [
        {
            id: 1,
            name: 'TechCorp',
            field: 'Technology',
            employeeCount: 1200,
            address: '123 Tech Street, Silicon Valley',
            likeCount: 2500
        },
        {
            id: 2,
            name: 'HealthPlus',
            field: 'Healthcare',
            employeeCount: 450,
            address: '456 Wellness Blvd, New York',
            likeCount: 1800
        },
        {
            id: 3,
            name: 'GreenEnergy',
            field: 'Renewable Energy',
            employeeCount: 800,
            address: '789 Greenway, Texas',
            likeCount: 2100
        },
        {
            id: 4,
            name: 'FoodieWorks',
            field: 'Food & Beverage',
            employeeCount: 300,
            address: '101 Taste Ave, California',
            likeCount: 3200
        },
        {
            id: 5,
            name: 'FinSecure',
            field: 'Finance',
            employeeCount: 1500,
            address: '202 Secure St, London',
            likeCount: 2800
        },
        {
            id: 6,
            name: 'AutoMotiveX',
            field: 'Automotive',
            employeeCount: 2200,
            address: '303 Car Factory Rd, Detroit',
            likeCount: 3500
        },
        {
            id: 7,
            name: 'EduFuture',
            field: 'Education',
            employeeCount: 600,
            address: '404 Knowledge Blvd, Boston',
            likeCount: 2600
        },
        {id: 8, name: 'SmartHome', field: 'IoT', employeeCount: 400, address: '505 Smart St, Seattle', likeCount: 1500},
        {
            id: 9,
            name: 'GlobalReach',
            field: 'Logistics',
            employeeCount: 1100,
            address: '606 Cargo Rd, Chicago',
            likeCount: 2200
        },
        {
            id: 10,
            name: 'CloudWave',
            field: 'Cloud Computing',
            employeeCount: 700,
            address: '707 Cloud St, San Francisco',
            likeCount: 2300
        },
        {
            id: 11,
            name: 'MedTechX',
            field: 'Healthcare Technology',
            employeeCount: 850,
            address: '808 MedTech Blvd, Toronto',
            likeCount: 2900
        },
        {
            id: 12,
            name: 'EcoBuild',
            field: 'Construction',
            employeeCount: 1300,
            address: '909 Eco Rd, Denver',
            likeCount: 1800
        },
        {
            id: 13,
            name: 'GameMaster',
            field: 'Entertainment',
            employeeCount: 600,
            address: '1010 Game Ln, Los Angeles',
            likeCount: 4000
        },
        {
            id: 14,
            name: 'RetailPlus',
            field: 'Retail',
            employeeCount: 1500,
            address: '1111 Retail St, New York',
            likeCount: 1700
        },
        {
            id: 15,
            name: 'LegalEagle',
            field: 'Legal Services',
            employeeCount: 500,
            address: '1212 Justice Ave, Washington',
            likeCount: 2100
        },
        {
            id: 16,
            name: 'TravelMate',
            field: 'Travel',
            employeeCount: 300,
            address: '1313 Journey Rd, Paris',
            likeCount: 1500
        },
        {
            id: 17,
            name: 'BioLife',
            field: 'Biotech',
            employeeCount: 900,
            address: '1414 Life St, Boston',
            likeCount: 2700
        },
        {
            id: 18,
            name: 'CyberTech',
            field: 'Cybersecurity',
            employeeCount: 1000,
            address: '1515 Cyber Ave, San Diego',
            likeCount: 3200
        },
        {
            id: 19,
            name: 'FashionFuture',
            field: 'Fashion',
            employeeCount: 400,
            address: '1616 Fashion Blvd, Milan',
            likeCount: 2500
        },
        {
            id: 20,
            name: 'SportsNet',
            field: 'Sports & Fitness',
            employeeCount: 500,
            address: '1717 Sports Rd, Miami',
            likeCount: 2000
        }
    ];


    return (
        <>
            <div className="search search-dev">
                <header className={styles.searchbox}>검색창</header>
                <section className={styles.option}>
                    <span>검색옵션</span>

                    <div className={styles.optionContainer}>
                        <button
                            className={`${styles.button} ${selectedOption ? styles.selected : ''}`}
                            onClick={클릭시실행함수}>
                            {selectedOption ? "구인분야 x" : "구인분야"}
                        </button>
                        <button className={`${styles.button} ${selectedOption2 ? styles.selected : ''}`}
                                onClick={클릭시실행함수2}>
                            {selectedOption2 ? "지역x" : "지역"}
                        </button>
                    </div>
                </section>
            </div>
            <div className="container comlist">
                <header>
                    <h3>기업 목록</h3>
                </header>

                <section>
                    <table className={styles.table}>
                        <thead>
                        <tr className={styles.item}>
                            <th>기업명</th>
                            <th>구인분야</th>
                            <th>직원수</th>
                            <th>기업소재지</th>
                            <th>좋아요</th>
                        </tr>
                        </thead>

                        <tbody>
                        {companies.map(company =>
                            <CompanyPage
                                key={company.id}
                                id={company.id}
                                name={company.name}
                                field={company.field}
                                employeeCount={company.employeeCount}
                                address={company.address}
                                likeCount={company.likeCount}/>)}
                        </tbody>

                    </table>
                    <div className={styles.pagination}>
                        <button className={styles.button}>이전</button>
                        <button className={styles.button}>다음</button>
                    </div>

                </section>
            </div>
        </>

    );
}
