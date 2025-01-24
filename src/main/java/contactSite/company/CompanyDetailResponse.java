package contactSite.company;

import contactSite.Field;

public record CompanyDetailResponse(String id,
                                    String name,
                                    String businesstype,
                                    Field field,
                                    String website,
                                    String address,
                                    int employeecount,
                                    String introduction,
                                    int established,
                                    int likeCount
                                    ) {
}
