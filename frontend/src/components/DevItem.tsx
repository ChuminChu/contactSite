import Image from "next/image";
import "./devItem.scss";
import { FaHeart, FaPlusCircle } from "react-icons/fa";
import { useState } from "react";
import Link from "next/link";

// type MyType = {devName: string; likesRating: number, position: string}
interface MyType {
  id: string;
  name: string;
  age: number;
  field: string[];
  isLiked: boolean;
  likeCount: number;
}

export default function DevItem(props: MyType) {
  return (
    <article className={"devItem"}>
      <div className="imgBox">
        {/* <Image src={props.path} width={200} height={200} alt=""/> */}
      </div>
      <div className="txtBox">
        <ul className="posTap">
          {props.field?.map((item, index) => <li key={index}>{item}</li>) || (
            <li>분야 정보 없음</li>
          )}
        </ul>
        <div className="devNameGroup">
          <h4 className="devName">{props.name}</h4>
          {/*<div className="rating" ><button onClick={props.isLiked}><FaHeart />{count}</button></div>*/}
        </div>
        <div className="moreBtn">
          <Link href={`/developerDetail/${props.id}`}>
            <button>
              <label>더보기</label>
              <span>
                <FaPlusCircle />
              </span>
            </button>
          </Link>
        </div>
      </div>
    </article>
  );
}
