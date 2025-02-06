import Image from "next/image";
// import styles from "./page.module.scss";
import "./app.page.scss";

import MainImage from "./index/MainImage";
import DevCarousel from "./index/DevCarousel";
import CopList from "./index/CopList";

export default function IndexPage() {
  return (
    <>
    <MainImage/>
    <DevCarousel/>
    <CopList/>
    </>
  );
}
