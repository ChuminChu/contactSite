package contactSite.programmer.dto.read;

public record ProgrammerReadResponse(
        String id,
        String name,
        int age,
        String fieldName,
        Boolean isLiked
) {
}
