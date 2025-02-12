import {CompanyList} from "@/components/companyList";

interface Company {
    id: string;
    name: string;
    field: string;
    employeeCount: number;
    address: string;
    likeCount: number;
}

export default async function CopList() {
    const response = await fetch('http://localhost:8080/companies?');
    const companies = await response.json()
    return (
        <div className="container">
            <header>
                <h3>기업 리스트</h3>
                <p></p>
            </header>
            <CompanyList companies={companies}/>
        </div>
    );
}
