package contactSite.programmer.dto.create;

public record ProgrammerCreateRequest(
        String userId,
        String password,
        String name,
        int age,
        String email,
        int personalHistory,
        String fieldName,
        String selfIntroduction,
        String certificate
) {
}
