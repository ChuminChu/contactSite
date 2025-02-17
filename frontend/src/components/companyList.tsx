import styles from "@/app/companyList/page.module.css";
import {Company} from "@/app/companyList/page";
import Link from "next/link";



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

export function CompanyList(props: { companies: Company[] }) {
    console.log(props.companies);
    return (
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
                {props.companies.map(company =>
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
        </section>


    )
}