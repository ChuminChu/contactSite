
import "../app.page.scss";

import MainImage from "@/app/index/MainImage";
import CopList from "@/app/index/CopList";
import DevCarousel from "@/app/index/DevCarousel";
import { fetchUser } from "@/app/api";
import { redirect } from "next/navigation";

export default async function userPage() {
    const useType = await fetchUser();
    if (useType === "notLoggedIn") {
        redirect("/");
    }
    const response = await fetch('http://localhost:8080/companies');
    const companiesResponse = await response.json()

    return (
        <>
            <MainImage />
            <DevCarousel />
            <CopList companyList={companiesResponse}/>
        </>
    );
}