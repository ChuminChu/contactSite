"use client"
import DevItem from "@/components/DevItem";
import "./developer.page.scss";

export default function DeveloperListPage() {
  const dev_info = [
    {
      id: 1,
      img_url : "@/images/img01.jpg",
      name : "추민영",
      rating : 24,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 2,
      img_url : "@/images/img02.jpg",
      name : "전지예",
      rating : 27,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 3,
      img_url : "@/images/img03.jpg",
      name : "황승혁",
      rating : 22,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 4,
      img_url : "@/images/img04.jpg",
      name : "문성희",
      rating : 47,
      dev_pos : "풀스텍 개발자"
    },
    {
      id: 5,
      img_url : "@/images/img05.jpg",
      name : "문인혁",
      rating : 22,
      dev_pos : "프론트 개발자"
    },
    {
      id: 6,
      img_url : "@/images/img06.jpg",
      name : "김민성",
      rating : 43,
      dev_pos : "백엔드 개발자"
    },{
      id: 7,
      img_url : "@/images/img07.jpg",
      name : "홍길동",
      rating : 18,
      dev_pos : "퍼블리셔"
    },
    {
      id: 8,
      img_url : "@/images/img08.jpg",
      name : "김복만",
      rating : 12,
      dev_pos : "디자이너"
    },
    {
      id: 9,
      img_url : "@/images/img01.jpg",
      name : "추민영",
      rating : 24,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 10,
      img_url : "@/images/img02.jpg",
      name : "전지예",
      rating : 27,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 11,
      img_url : "@/images/img03.jpg",
      name : "황승혁",
      rating : 22,
      dev_pos : "백엔드 개발자"
    },
    {
      id: 12,
      img_url : "@/images/img04.jpg",
      name : "문성희",
      rating : 47,
      dev_pos : "풀스텍 개발자"
    },
    {
      id: 13,
      img_url : "@/images/img05.jpg",
      name : "문인혁",
      rating : 22,
      dev_pos : "프론트 개발자"
    },
    {
      id: 14,
      img_url : "@/images/img06.jpg",
      name : "김민성",
      rating : 43,
      dev_pos : "백엔드 개발자"
    },{
      id: 15,
      img_url : "@/images/img07.jpg",
      name : "홍길동",
      rating : 18,
      dev_pos : "퍼블리셔"
    },
    {
      id: 16,
      img_url : "@/images/img08.jpg",
      name : "김복만",
      rating : 12,
      dev_pos : "디자이너"
    },
  ]
  return (
    <>
      <div className="search search-dev">
        <header>
          검색창
        </header>
        <section>검색옵션</section>
      </div>
      <div className="container devlist">
        <header>
          <h3>개발자 목록</h3>
        </header>
        <section>
          {dev_info.map((dev)=>(
            <DevItem 
              key={dev.id}
              devName={dev.name}
              likesRating={dev.rating}
              position={dev.dev_pos}
              path={dev.img_url}
            />
          ))}
        </section>
      </div>
    </>
    
  );
}
