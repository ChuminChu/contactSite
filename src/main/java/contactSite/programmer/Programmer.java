package contactSite.programmer;

import contactSite.Field;
import contactSite.LoginUtils.SecurityUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDate;

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

    private LocalDate birthDate;

    private int age;

    private String email;

    private int personalHistory;

    private Field fieldName;

    private String selfIntroduction;

    private String certificate;

    private int likeCount;

    public Programmer() {
    }

    public Programmer(String userId, String password, String name, LocalDate birthDate, String email, int personalHistory, Field fieldName, String selfIntroduction, String certificate) {
        this.userId = userId;
        this.password = SecurityUtils.sha256EncryptHex2(password);
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.personalHistory = personalHistory;
        this.fieldName = fieldName;
        this.selfIntroduction = selfIntroduction;
        this.certificate = certificate;
    }

    public Programmer(int age) {
        this.age = age;
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

    public LocalDate getBirthDate() {
        return birthDate;
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

    public Field getFieldName() {
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

    public void update(String userId,
                       String name,
                       String email,
                       LocalDate birthDate,
                       int personalHistory,
                       Field fieldName,
                       String selfIntroduction,
                       String certificate){
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.personalHistory = personalHistory;
        this.fieldName = fieldName;
        this.selfIntroduction = selfIntroduction;
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

    //만나이로 계산
public void countAge() {
        LocalDate currentDate = LocalDate.now();

        int calculatedAge = currentDate.getYear() - birthDate.getYear();

    //월 안지났으면 -1 / 일 안지났으면 -1
    if (currentDate.getMonthValue() < birthDate.getMonthValue()){
        calculatedAge --;
    }
    if (currentDate.getMonthValue() == birthDate.getMonthValue() && currentDate.getDayOfMonth() < birthDate.getDayOfMonth()){
        calculatedAge --;
    }

    this.age = calculatedAge;
 }

}

