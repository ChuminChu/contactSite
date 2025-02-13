import styles from "@/app/companyList/page.module.css";
import {CompanyPage} from "@/app/companyList/page";

export function CompanyList(props) {
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

            {/*<div className={styles.pagination}>*/}
            {/*    <button className={styles.button}*/}
            {/*            onClick={이전버튼클릭시}*/}
            {/*            disabled={currentPage === 1}>*/}
            {/*        이전*/}
            {/*    </button>*/}

            {/*    {getPageNumbers().map((page, index) =>*/}
            {/*        page === "..." ? (*/}
            {/*            <span key={`ellipsis-${index}`} className={styles.ellipsis}>...</span>*/}
            {/*        ) : (*/}
            {/*            <button*/}
            {/*                key={`page-${page}`}*/}
            {/*                onClick={() => setCurrentPage(page)}*/}
            {/*                className={currentPage === page ? styles.activePage : ""}*/}
            {/*            >*/}
            {/*                {page}*/}
            {/*            </button>*/}
            {/*        )*/}
            {/*    )}*/}


            {/*    <button className={styles.button}*/}
            {/*            onClick={다음버튼클릭시}*/}
            {/*            disabled={currentPage === totalPages}>*/}
            {/*        다음*/}
            {/*    </button>*/}
            {/*</div>*/}

        </section>


    )
}