package contactSite.programmer.dto;

public record ProgrammerRequest(
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
