package contactSite.company;

import contactSite.Field;
import contactSite.LoginUtils.SecurityUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Company {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(
            name = "custom-id-generator",
            strategy = "contactSite.company.CustomIdGenerator",
            parameters = @Parameter(name = "prefix", value = "C")
    )
    private String id;

    //아이디, 비밀번호, 이름 - 필수 입력
    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String companyname;

    //업종, 분야
    private String businessType;

    @Enumerated(EnumType.STRING)
    private Field field;

    //웹사이트, 주소, 설립년도, 자기소개, 사원수
    private String website;
    private String address;
    private int employeeCount;
    private String introduction;
    private int established;  //설립년도

    //좋아요 개수
    private int likeCount;

    protected Company() {
    }

    public Company(String userId, String password, String companyname, String businessType, Field field, String website, String address, int employeeCount, String introduction, int established) {
        this.userId = userId;
        this.password = password;
        this.companyname = companyname;
        this.businessType = businessType;
        this.field = field;
        this.website = website;
        this.address = address;
        this.employeeCount = employeeCount;
        this.introduction = introduction;
        this.established = established;
    }

    public Company(int likeCount) {
        this.likeCount = likeCount;
    }


    //getter
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getBusinessType() {
        return businessType;
    }

    public Field getField() {
        return field;
    }

    public String getWebsite() {
        return website;
    }

    public String getAddress() {
        return address;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getEstablished() {
        return established;
    }

    public int getLikeCount() {
        return likeCount;
    }

    //setter - 아이디는 수정 안함


    public void setPassword(String password) {
        this.password = password;
    }

    //회원정보 수정 함수 - 비밀번호 제외
    public void update(String companyname,
                       String businessType,
                       Field field,
                       String website,
                       String address,
                       int employeeCount,
                       String introduction,
                       int established) {
        this.companyname = companyname;
        this.businessType = businessType;
        this.field = field;
        this.website = website;
        this.address = address;
        this.employeeCount = employeeCount;
        this.introduction = introduction;
        this.established = established;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public boolean isCorrectPassword(String password) {
        return this.getPassword().equals(SecurityUtils.sha256EncryptHex2(password));
    }

}
