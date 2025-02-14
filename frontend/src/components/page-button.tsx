"use client"

import {Company, PageResponse} from "@/app/companyList/page";

export function PageButton(props: {
    value: string;
    isDisabled: boolean;
    setCompanies: (companies: Company[]) => void;
    setPage: (page: { totalPages: number, currentPage: number }) => void;
}) {

    const onClick = async () => {
        const response = await fetch(`http://localhost:8080/companies?page=${props.value}`);
        const pageResponse: PageResponse = await response.json()
        props.setCompanies(pageResponse.companies);
        props.setPage({
            totalPages: pageResponse.totalPage,
            currentPage: pageResponse.pageNumber
        });

    }

    return <button disabled={props.isDisabled} onClick={onClick}>{props.value}</button>
}