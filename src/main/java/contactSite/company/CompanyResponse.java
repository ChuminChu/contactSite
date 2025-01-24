package contactSite.company;

import contactSite.Field;

public record CompanyResponse(String id,
                              String name,
                              Field field,
                              String address,
                              int likeCount

) {
}
