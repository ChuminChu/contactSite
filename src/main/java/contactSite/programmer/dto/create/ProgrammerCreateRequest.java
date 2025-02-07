package contactSite.programmer.dto.create;

import contactSite.Field;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ProgrammerCreateRequest(
        @NotBlank @Size(min = 6, max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어와 숫자만 가능합니다. /6자리 이상")
        String userId,

        @NotBlank @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "소문자, 대문자, 숫자, 특수문자 필수 / 8자리 이상")
        String password,

        @NotBlank @Size(min = 1, max = 20) @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 알파벳과 한글만 가능합니다.") String name,
        @Past(message = "YYYY-MM-DD") LocalDate birthDate,
        @Email String email,
        @Max(100) int personalHistory,
        List<Field> fieldName,
        @Size(max = 10000) String selfIntroduction,
        @Size(max = 10000) String certificate




) {
}
