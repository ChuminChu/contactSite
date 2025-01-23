package contactSite.programmer.dto;

public record ProgrammerResponse(
        String id,
        String userId,
        String name,
        int age,
        String email,
        int personalHistory,
        String fieldName,
        String selfIntroduction,
        String certificate
) {
}
