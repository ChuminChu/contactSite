

import Image from "next/image";
import "./devItem.scss";
import { FaHeart, FaPlusCircle } from "react-icons/fa";
import { useState } from "react";

// type MyType = {devName: string; likesRating: number, position: string}
interface MyType {
  devName: string; 
  likesRating: number,
  position: string,
  path: string
}

export default function DevItem(props : MyType) {

  const [count, setCount] = useState(0);
  function likeRating():void{
    setCount(count + 1);
  }
  return (
    <article className={"devItem"}>
      <div className="imgBox" >
        {/* <Image src={props.path} width={200} height={200} alt=""/> */}
      </div>
      <div className="txtBox">
        <ul className="posTap">
          <li>{props.position}</li>
        </ul>
        <div className="devNameGroup">
          <h4 className="devName">{props.devName}</h4>
          <div className="rating" ><button onClick={likeRating}><FaHeart />{count}</button></div>
        </div>
        <div className="moreBtn">
          <button><label>더보기</label><span><FaPlusCircle /></span></button>
        </div>
      </div>
    </article>
  );
}
