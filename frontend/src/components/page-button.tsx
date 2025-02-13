"use client"

export function PageButton(props: { value: string; isDisabled: boolean; setCompanies: any }) {

    const onClick = async () => {
        const response = await fetch(`http://localhost:8080/companies?page=${props.value}`);
        const companies = await response.json()
        props.setCompanies(companies);
    }

    return <button disabled={props.isDisabled} onClick={onClick}>{props.value}</button>
}