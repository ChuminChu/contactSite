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


export default function CopList(props: { companyList: { companies: Company[]; totalPage: number; currentPage: number } }) {
    const [companies, setCompanies] = useState<Company[]>(props.companyList.companies);
    const [page, setPage] = useState({totalPages: 0, currentPage: 1});

    return (
        <div className="container">
            <header>
                <h3>기업 리스트</h3>
                <p></p>
            </header>
            <CompanyList companies={companies}/>
            <PageButtons totalPage={page.totalPages}
                         currentPage={page.currentPage}
                         setCompanies={setCompanies}
                         setPage={setPage}
            />
        </div>
    );
}
