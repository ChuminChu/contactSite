package contactSite.message.dto;

public record MessageSendResponse(
        Long messageId,
        String senderOrReceiverName
) {
}
