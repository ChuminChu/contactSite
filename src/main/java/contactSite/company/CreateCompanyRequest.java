package contactSite.company;

public record CreateCompanyRequest(String userId,
                                   String password,
                                   String companyname,
                                   String businesstype,
                                   String field,
                                   String website,
                                   String address,
                                   int employeeCount,
                                   String introduction,
                                   int established
                                   ) {
}
