package contactSite.company;

import contactSite.Field;

public record CompanyMypageRequest(String id,
                                   String userId,
                                   String companyname,
                                   String businesstype,
                                   Field field,
                                   String website,
                                   String address,
                                   int employeeCount,
                                   String introduction,
                                   int established) {
}
