package contactSite.company;

public record CompanyDetailResponse(String id,
                                    String userId,
                                    String password,
                                    String companyname,
                                    String businesstype,
                                    String field,
                                    String website,
                                    String address,
                                    int employeeCount,
                                    String introduction,
                                    int established,
                                    int likeconut
) {
}
