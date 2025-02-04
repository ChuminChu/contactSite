package contactSite.programmer.dto.read;

import contactSite.Field;

import java.time.LocalDate;

public record ProgrammerReadResponse(
        String id,
        String name,
        int age,
        Field fieldName,
        Boolean isLiked,
        int likeCount
) {
}
