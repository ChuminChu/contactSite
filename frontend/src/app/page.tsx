
import Image from "next/image";
// import styles from "./page.module.scss";
import "./app.page.scss";

import MainImage from "./index/MainImage";
import DevCarousel from "./index/DevCarousel";
import CopList from "./index/CopList";

export default async function IndexPage() {
  const response = await fetch('http://localhost:8080/companies');
  const companiesResponse = await response.json()
  return (
      <>
        <MainImage/>
        <DevCarousel/>
        <CopList companyList={companiesResponse}/>
      </>
  );
}