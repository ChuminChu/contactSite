package contactSite.message;

import contactSite.LoginUtils.LoginMember;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
import contactSite.message.dto.MessageSendResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageRestController {

    private final MessageService messageService;

    public MessageRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public MessageResponse createMessage(@LoginMember String senderId, @RequestBody MessageRequest request) {

        return messageService.create(senderId, request);
    }

    // 사용자가 보낸 전체 쪽지 조회
    @GetMapping("/messages/senders")
    public List<MessageSendResponse> findAllSendMessages(@LoginMember String senderId) {

        return messageService.findAllSendMessages(senderId);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId, @LoginMember String memberId) {

        messageService.deleteMessage(messageId, memberId);
    }

    // 사용자가 받은 전체 쪽지 조회
    @GetMapping("/messages/receivers")
    public List<MessageSendResponse> findAllReceiveMessages(@LoginMember String receiveId) {

        return messageService.findAllReceiveMessages(receiveId);
    }

}
