package contactSite.company;

import java.util.List;

public record PageResponse(
        Long totalPage,
        int pageNumber,
        List<CompanyResponse> companies
) {

}
