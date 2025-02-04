package contactSite.programmer.dto.read;

import contactSite.Field;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record ProgrammerDetailResponse(
        String name,
        int age,
        String email,
        Number personalHistory,
        Field fieldName,
        String selfIntroduction,
        String certificate,
        Boolean isLiked,
        int likeCount
) {
}
