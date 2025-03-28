package contactSite.message;

import contactSite.LoginUtils.LoginMember;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageRestController {

    private final MessageService messageService;
    private final NotificationService notificationService;


    public MessageRestController(MessageService messageService, NotificationService notificationService) {
        this.messageService = messageService;
        this.notificationService = notificationService;
    }

    @PostMapping("/messages")
    public MessageResponse createMessage(@LoginMember String senderId, @RequestBody MessageRequest request) {

        //return messageService.create(senderId, request);

        MessageResponse response = messageService.create(senderId, request);
        // 알림 전송 (NotificationService에 위임)
        notificationService.sendNotification(
                request.messageTargetId(),
                "새 메시지가 도착했습니다!"
        );
        return response;
    }

  /*
  //웹소켓
  @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/rooms/{roomId}")
    public void sendMessage(@LoginMember String senderId, @RequestBody MessageRequest request, @DestinationVariable Long roomId) {
    }*/

    // 사용자가 보낸 전체 쪽지 조회
    @GetMapping("/messages/senders")
    public List<MessageResponse> findAllSendMessages(@LoginMember String senderId) {

        return messageService.findAllSendMessages(senderId);
    }

    @DeleteMapping("/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId, @LoginMember String memberId) {

        messageService.deleteMessage(messageId, memberId);
    }

    // 사용자가 받은 전체 쪽지 조회
    @GetMapping("/messages/receivers")
    public List<MessageResponse> findAllReceiveMessages(@LoginMember String receiveId) {

        return messageService.findAllReceiveMessages(receiveId);
    }

}
