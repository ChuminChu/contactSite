package contactSite.programmer.dto.read;

import contactSite.Field;

public record ProgrammerReadResponse(
        String id,
        String name,
        Number age,
        Field fieldName
        int age,
        String fieldName,
        Boolean isLiked,
        int likeCount
) {
}
