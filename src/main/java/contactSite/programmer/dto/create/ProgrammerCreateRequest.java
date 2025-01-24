package contactSite.programmer.dto.create;

import contactSite.Field;

public record ProgrammerCreateRequest(
        String userId,
        String password,
        String name,
        int age,
        String email,
        int personalHistory,
        Field fieldName,
        String selfIntroduction,
        String certificate
) {
}
