package contactSite.programmer.dto;

import contactSite.Field;

public record ProgrammerRequest(
        String userId,
        String name,
        int age,
        String email,
        int personalHistory,
        Field fieldName,
        String selfIntroduction,
        String certificate
) {
}
