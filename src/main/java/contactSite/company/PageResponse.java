package contactSite.company;

import java.util.List;

public record PageResponse(
        Long totalPage,
        Long pageNumber,
        List<CompanyResponse> companies
) {

}
