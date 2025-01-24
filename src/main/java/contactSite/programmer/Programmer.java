package contactSite.programmer;

import contactSite.LoginUtils.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
public class Programmer {

    @Id
    @GeneratedValue(generator = "custom-id-generator")
    @GenericGenerator(
            name = "custom-id-generator",
            strategy = "contactSite.programmer.CustomIdGenerator",
            parameters = @Parameter(name = "prefix", value = "P")
    )
    private String id;


    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private int age;

    private String email;

    private int personalHistory;

    private String fieldName;

    private String selfIntroduction;

    private String certificate;

    private int likeCount;

    public Programmer() {
    }

    public Programmer(String userId, String password, String name, int age, String email, int personalHistory, String fieldName, String selfIntroduction, String certificate) {
        this.userId = userId;
        this.password = SecurityUtils.sha256EncryptHex2(password);
        this.name = name;
        this.age = age;
        this.email = email;
        this.personalHistory = personalHistory;
        this.fieldName = fieldName;
        this.selfIntroduction = selfIntroduction;
        this.certificate = certificate;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public int getPersonalHistory() {
        return personalHistory;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public String getCertificate() {
        return certificate;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPersonalHistory(int personalHistory) {
        this.personalHistory = personalHistory;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public void setPassword(String password) {
        this.password = SecurityUtils.sha256EncryptHex2(password);
    }


    public void likeCount(){
        this.likeCount = likeCount+1;
    }

    public boolean isCorrectPassword(String password) {

        return this.getPassword().equals(SecurityUtils.sha256EncryptHex2(password));
    }
}
