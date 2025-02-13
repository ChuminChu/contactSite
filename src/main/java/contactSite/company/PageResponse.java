package contactSite.company;

import java.util.List;

public record PageResponse(
        int totalPage,
        int pageNumber,
        List<CompanyResponse> companies
) {

}
