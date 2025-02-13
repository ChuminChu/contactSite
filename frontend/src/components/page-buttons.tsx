import {PageButton} from "@/components/page-button";
import {useState} from "react";

export function PageButtons(props: { totalPage: number; currentPage: number; setCompanies: any }) {

    const pageButtonObjects = generatePagingData(props.totalPage, props.currentPage);

    function generatePagingData(totalPage: number, currentPage: number) {
        const result = [];

        result.push({
            value: String("<"),
            isDisabled: currentPage === 1,
        })


        for (let i = currentPage -2; i <= currentPage+2; i++) {
            if (i < 1 || i > totalPage) {
                continue;
            }
                result.push({
                    value: String(i),
                    isDisabled: currentPage === i,
                });
        }

        result.push({
            value: String(">"),
            isDisabled: currentPage === totalPage,
        })

        return result;
    }

    // const pageButtonObjects = [
    //     {
    //         value: "<<",
    //         isDisabled: true,
    //     },
    //     {
    //         value: "<",
    //         isDisabled: true,
    //     },
    //     {
    //         value: "1",
    //         isDisabled: false,
    //     },
    //     {
    //         value: "2",
    //         isDisabled: false,
    //     },
    //     {
    //         value: "3",
    //         isDisabled: false,
    //     },
    //     {
    //         value: ">",
    //         isDisabled: true,
    //     },
    //     {
    //         value: ">>",
    //         isDisabled: true,
    //     },
    // ]

    // //페이지 넘기기 기능
    // const [currentPage, setCurrentPage] = useState(1);
    // const totalPages = 100;
    //
    // function 이전버튼(){
    //     if(currentPage > 1){
    //         setCurrentPage(currentPage - 1);
    //     }
    // }
    //
    // function 다음버튼(){
    //     if(currentPage < totalPages){
    //         setCurrentPage(currentPage + 1);
    //     }
    // }
    //


    return <div>
        {
            pageButtonObjects.map((o) =>
                <PageButton value={o.value} isDisabled={o.isDisabled} setCompanies={props.setCompanies} />
            )
        }
    </div>
}