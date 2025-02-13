"use client"

import {useState} from "react";
import {CompanyList} from "@/components/companyList";
import {PageButtons} from "@/components/page-buttons";

interface Company {
    id: string;
    name: string;
    field: string;
    employeeCount: number;
    address: string;
    likeCount: number;
}

export default function CopList(props) {
    const [companies, setCompanies] = useState<Company[]>(props.companies);

    return (
        <div className="container">
            <header>
                <h3>기업 리스트</h3>
                <p></p>
            </header>
            <CompanyList companies={companies}/>
            <PageButtons totalPage={100} currentPage={2} setCompanies={setCompanies}/>
        </div>
    );
}
