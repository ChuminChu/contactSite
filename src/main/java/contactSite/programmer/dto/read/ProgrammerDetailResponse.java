package contactSite.programmer.dto.read;

public record ProgrammerDetailResponse(
        String name,
        Number age,
        String email,
        Number personalHistory,
        String fieldName,
        String selfIntroduction,
        String certificate
) {
}
