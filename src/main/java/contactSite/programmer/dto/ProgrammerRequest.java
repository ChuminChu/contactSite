package contactSite.programmer.dto;

import contactSite.Field;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record ProgrammerRequest(
        @NotBlank @Size(min = 6, max = 20)
        @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "아이디는 영어와 숫자만 가능합니다. /6자리 이상")
        String userId,

        @Size(min = 1, max = 20) @Pattern(regexp = "^[a-zA-Z가-힣]*$", message = "이름은 알파벳과 한글만 가능합니다.")String name,
        @Past(message = "YYYY-MM-DD") LocalDate birthDate,  //생년월일 넣으면 나이로 변환
        @Email String email,
        @Max(100) int personalHistory,
        List<Field> fieldName,
        @Size(max = 10000) String selfIntroduction,
        @Size(max = 10000) String certificate
) {
}
