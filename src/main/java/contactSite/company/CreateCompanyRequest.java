package contactSite.company;

import contactSite.Field;

public record CreateCompanyRequest(String userId,
                                   String password,
                                   String companyname,
                                   String businesstype,
                                   Field field,
                                   String website,
                                   String address,
                                   int employeeCount,
                                   String introduction,
                                   int established
                                   ) {
}
