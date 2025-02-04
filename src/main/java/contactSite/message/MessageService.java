package contactSite.message;

import contactSite.company.Company;
import contactSite.company.CompanyRepository;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
import contactSite.message.dto.MessageSendResponse;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ProgrammerRepository programmerRepository;
    private final CompanyRepository companyRepository;

    public MessageService(MessageRepository messageRepository, ProgrammerRepository programmerRepository, CompanyRepository companyRepository) {
        this.messageRepository = messageRepository;
        this.programmerRepository = programmerRepository;
        this.companyRepository = companyRepository;
    }

    public MessageResponse create(String senderId, MessageRequest request) {
        String senderName = "";
        if(request.receiverId().startsWith("C")){
            Company company = companyRepository.findById(request.receiverId())
                    .orElseThrow(() -> new NoSuchElementException("해당 기업이 존재하지 않습니다!"));
            senderName = company.getCompanyname();
        }
        if (request.receiverId().startsWith("P")) {
            Programmer programmer = programmerRepository.findById(request.receiverId())
                    .orElseThrow(() -> new NoSuchElementException("해당 개발자가 존재하지 않습니다!"));
            senderName = programmer.getName();
        }


        Message message = messageRepository.save(
                new Message(
                        senderId,
                        request.receiverId(),
                        senderName
                        ));

        return new MessageResponse(
                message.getId(),
                message.getReceiverId(),
                message.getSenderId(),
                message.getSenderName());
    }

    //사용자가 보낸거 찾을거야
    public List<MessageSendResponse> findAllSendMessages(String senderId) {
        List<Message> messageList = messageRepository.findAllBySenderId(senderId);
        return messageAdd(messageList, true);
    }

    //내가 받은거
    public List<MessageSendResponse> findAllReceiveMessages(String receiveId) {
        List<Message> messageList = messageRepository.findAllByReceiverId(receiveId);

        return messageAdd(messageList, false);
    }

    private List<MessageSendResponse> messageAdd(List<Message> messageList, boolean isSender) {
        return messageList.stream()
                .filter(message -> {
                    if (isSender) {
                    return !message.isDeletedBySender(); // 받은 사람이 메세지를 삭제하지 않은 걸 보여줘라
                } else {
                    return !message.isDeletedByReceiver(); // 받은 메시지는 받은 사람이 삭제하지 않았을 때만 포함
                }
                })
                .map(message -> new MessageSendResponse(
                        message.getId(),
                        message.getSenderName()
                ))
                .toList();
    }

    @Transactional
    public void deleteMessage(Long messageId, String memberId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("해당 쪽지가 없습니다."));

        // 보낸 사람인지 받은 사람인지 확인
        if (message.getSenderId().equals(memberId)) {
            message.deleteBySender();  // 보낸 사람이 삭제한 경우
        }
        else if (message.getReceiverId().equals(memberId)) {
            message.deleteByReceiver();  // 받은 사람이 삭제한 경우
        }
        else {
            throw new IllegalArgumentException("이 쪽지는 해당 사용자의 권한이 없습니다.");
        }
    }
}
