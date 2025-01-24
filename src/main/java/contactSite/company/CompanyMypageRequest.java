package contactSite.company;

import contactSite.Field;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CompanyMypageRequest(
        @NotBlank @Size(min = 6, max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어와 숫자만 가능합니다. /6자리 이상")
        String userId,

        @Size(min = 1, max = 50)
        @Pattern(regexp = "^[a-zA-Z가-힣0-9]*$", message = "이름은 알파벳, 한글, 숫자만 가능합니다.")
        String companyname,

        @Size(min = 1, max = 20) String businesstype,
        Field field,

        @NotBlank @Pattern(regexp = "^(https?://)?([\\w-]+\\.)+[\\w-]+(/[^\\s]*)?$", message = "유효한 웹사이트 주소를 입력해주세요.")
        String website,

        @NotBlank @Size(min = 1, max = 20) String address,
        @Min(1) @PositiveOrZero(message = "사원수는 양수여야 합니다")  int employeeCount,
        @Size(max = 10000) String introduction,

        @Past(message = "YYYY-MM-DD")
        LocalDate established) {
}
