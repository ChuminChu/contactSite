package contactSite.programmer.dto.read;

import contactSite.Field;

import java.time.LocalDate;
import java.util.List;

public record ProgrammerReadResponse(
        String id,
        String name,
        int age,
        List<Field> fieldName,
        Boolean isLiked,
        int likeCount
) {
}
