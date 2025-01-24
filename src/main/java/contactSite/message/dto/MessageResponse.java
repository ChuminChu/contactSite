package contactSite.message.dto;

public record MessageResponse(
        Long id,
        String receiverId,
        String senderId) {
}
