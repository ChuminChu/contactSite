package contactSite.programmer.dto.read;

import contactSite.Field;

public record ProgrammerDetailResponse(
        String name,
        Number age,
        String email,
        Number personalHistory,
        Field fieldName,
        String selfIntroduction,
        String certificate,
        Boolean isLiked,
        int likeCount
) {
}
