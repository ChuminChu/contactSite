"use client";

import "./copRow.scss";

interface MyType {
  tr_num: number; 
  company: string,
  position: string,
  employee: number,
  cop_location : string,
  like_count : number
}
export default function CopRow(props : MyType) {

  return (
    <ul className="cop-tr">
      <li>{props.tr_num}</li>
      <li>{props.company}</li>
      <li>{props.position}</li>
      <li>{props.employee}</li>
      <li>{props.cop_location}</li>
      <li>{props.like_count}</li>
    </ul>
  );
}
