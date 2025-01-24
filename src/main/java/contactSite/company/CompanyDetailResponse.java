package contactSite.company;

import contactSite.Field;

import java.time.LocalDate;

public record CompanyDetailResponse(String id,
                                    String name,
                                    String businesstype,
                                    Field field,
                                    String website,
                                    String address,
                                    int employeecount,
                                    String introduction,
                                    LocalDate established,
                                    int likeCount
                                    ) {
}
