import styles from "@/app/companyList/page.module.css";
import {CompanyPage} from "@/app/companyList/page";

export function CompanyList(props) {
    return (
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
    )
}