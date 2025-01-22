package contactSite.programmer;

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

    private Number age;

    private String email;

    private Number personalHistory;

    private String fieldName;

    private String selfIntroduction;

    private String certificate;

    public Programmer() {
    }

    public Programmer(String userId, String password, String name, Number age, String email, Number personalHistory, String fieldName, String selfIntroduction, String certificate) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
        this.personalHistory = personalHistory;
        this.fieldName = fieldName;
        this.selfIntroduction = selfIntroduction;
        this.certificate = certificate;
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

    public Number getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Number getPersonalHistory() {
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
}
