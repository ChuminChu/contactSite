import { PageButton } from "@/components/page-button";
import { Company } from "@/app/companyList/page";

export function PageButtons(props: {
  totalPage: number;
  currentPage: number;
  setCompanies: (companies: Company[]) => void;
  setPage: (page: { totalPages: number; currentPage: number }) => void;
}) {
  const pageButtonObjects = generatePagingData(
    props.totalPage,
    props.currentPage,
  );

  function generatePagingData(totalPage: number, currentPage: number) {
    const result = [];

    result.push({
      value: String("<"),
      isDisabled: currentPage === 1,
    });

    for (let i = currentPage - 2; i <= currentPage + 2; i++) {
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
    });

    return result;
  }

  return (
    <div>
      {pageButtonObjects.map((o) => (
        <PageButton
          key={o.value} // Key 추가 (리스트 렌더링 시 필수)
          value={o.value}
          isDisabled={o.isDisabled}
          setCompanies={props.setCompanies}
          setPage={props.setPage}
        />
      ))}
    </div>
  );
}
