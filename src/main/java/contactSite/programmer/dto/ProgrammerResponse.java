package contactSite.programmer.dto;

import contactSite.Field;

import java.time.LocalDate;
import java.util.List;

public record ProgrammerResponse(
        String id,
        String userId,
        String name,
        LocalDate birthDate,
        int age,
        String email,
        int personalHistory,
        List<Field> fieldName,
        String selfIntroduction,
        String certificate
) {
}
