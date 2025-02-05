package contactSite.message;

import contactSite.company.Company;
import contactSite.company.CompanyRepository;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String receiverName = "";
        if(request.messageTargetId().startsWith("C")){
            Company company = companyRepository.findById(request.messageTargetId())
                    .orElseThrow(() -> new NoSuchElementException("해당 기업이 존재하지 않습니다!"));
            receiverName = company.getCompanyname();
        }
        if (request.messageTargetId().startsWith("P")) {
            Programmer programmer = programmerRepository.findById(request.messageTargetId())
                    .orElseThrow(() -> new NoSuchElementException("해당 개발자가 존재하지 않습니다!"));
            receiverName = programmer.getName();
        }


        Message message = messageRepository.save(
                new Message(
                        senderId,
                        request.messageTargetId(),
                        receiverName
                        ));

        return new MessageResponse(
                message.getId(),
                message.getReceiverId(),
                message.getSenderId(),
                receiverName);
    }

    //보낸 쪽지함(내가 보낸 메세지)
    public List<MessageResponse> findAllSendMessages(String senderId) {
        List<Message> messageList = messageRepository.findAllBySenderId(senderId)
                .stream()
                .filter(message -> !message.isDeletedBySender()) // 내가 삭제한 메세지 빼고
                .toList();

        return messageList.stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getReceiverId(),
                        message.getSenderId(),
                        getReceiverName(message.getReceiverId()) // 받는 사람 이름 조회
                ))
                .toList();
    }

    // 받은 쪽지함 (내가 받은 메시지)
    public List<MessageResponse> findAllReceiveMessages(String receiverId) {
        List<Message> messageList = messageRepository.findAllByReceiverId(receiverId)
                .stream()
                .filter(message -> !message.isDeletedByReceiver())
                .toList();

        return messageList.stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getReceiverId(),
                        message.getSenderId(),
                        getSenderName(message.getSenderId()) //보낸 사람 이름 조회
                ))
                .toList();
    }

    // 받는 사람의 이름 가져오기 (보낸 쪽지함에서 사용)
    private String getReceiverName(String receiverId) {
        if(receiverId.startsWith("C")){
            Company company = companyRepository.findById(receiverId).orElseThrow();
            return company.getCompanyname();
        } else if (receiverId.startsWith("P")) {
            Programmer programmer = programmerRepository.findById(receiverId).orElseThrow();
            return programmer.getName();
        }
        throw new IllegalArgumentException("에러 ");
    }


    // 보낸 사람의 이름 가져오기 (받은 쪽지함에서 사용)
    private String getSenderName(String senderId) {
        if(senderId.startsWith("C")){
            Company company = companyRepository.findById(senderId).orElseThrow();
            return company.getCompanyname();
        } else if (senderId.startsWith("P")) {
            Programmer programmer = programmerRepository.findById(senderId).orElseThrow();
            return programmer.getName();
        }
        throw new IllegalArgumentException("에러");
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
