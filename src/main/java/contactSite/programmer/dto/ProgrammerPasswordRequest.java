package contactSite.programmer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ProgrammerPasswordRequest(
        @NotBlank @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "소문자, 대문자, 숫자, 특수문자 필수 / 8자리 이상")
        String password
) {
}
