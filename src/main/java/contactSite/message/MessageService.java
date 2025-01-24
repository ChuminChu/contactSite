package contactSite.message;

import contactSite.company.Company;
import contactSite.company.CompanyRepository;
import contactSite.message.dto.MessageRequest;
import contactSite.message.dto.MessageResponse;
import contactSite.message.dto.MessageSendResponse;
import contactSite.programmer.Programmer;
import contactSite.programmer.ProgrammerRepository;
import org.springframework.stereotype.Service;

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
        if(request.receiverId().startsWith("C")){
            companyRepository.findById(request.receiverId())
                    .orElseThrow(() -> new NoSuchElementException("해당 기업이 존재하지 않습니다!"));
        }
        if (request.receiverId().startsWith("P")) {
            programmerRepository.findById(request.receiverId())
                    .orElseThrow(() -> new NoSuchElementException("해당 개발자가 존재하지 않습니다!"));
        }

        Message message = messageRepository.save(
                new Message(
                        senderId,
                        request.receiverId()));
        return new MessageResponse(
                message.getId(),
                message.getReceiverId(),
                message.getSenderId());
    }

    //사용자가 보낸거 찾을거야
    public List<MessageSendResponse> findAllSendMessages(String senderId) {
        List<Message> messageList = messageRepository.findAllBySenderId(senderId);
        List<MessageSendResponse> messageSendResponses = new ArrayList<>();

        for (Message message : messageList) {
            if(senderId.startsWith("C")){
                Company company = companyRepository.findById(message.getSenderId())
                        .orElseThrow(() -> new NoSuchElementException(message.getSenderId() + "님이 보낸 쪽지가 없습니다."));
                messageSendResponses.add(new MessageSendResponse(message.getId(), company.getCompanyname()));
            }
            if(senderId.startsWith("P")){
                Programmer programmer = programmerRepository.findById(message.getSenderId())
                        .orElseThrow(() -> new NoSuchElementException(message.getSenderId() + "님이 보낸 쪽지가 없습니다."));
                messageSendResponses.add(new MessageSendResponse(message.getId(), programmer.getName()));
            }
        }
        return messageSendResponses;
    }


    //내가 받은거
    public List<MessageSendResponse> findAllReceiveMessages(String receiveId) {
        List<Message> messageList = messageRepository.findAllByReceiverId(receiveId);
        List<MessageSendResponse> messageSendResponses = new ArrayList<>();

        for (Message message : messageList) {
            if(receiveId.startsWith("C")){
                Programmer programmer = programmerRepository.findById(message.getSenderId())
                        .orElseThrow(() -> new NoSuchElementException(message.getSenderId() + "님이 보낸 쪽지가 없습니다."));
                messageSendResponses.add(new MessageSendResponse(message.getId(), programmer.getName()));
            }
            if(receiveId.startsWith("P")){
                Company company = companyRepository.findById(message.getSenderId())
                        .orElseThrow(() -> new NoSuchElementException(message.getSenderId() + "님이 보낸 쪽지가 없습니다."));
                messageSendResponses.add(new MessageSendResponse(message.getId(), company.getCompanyname()));
            }
        }
        return messageSendResponses;
    }


    //내가 보낸 메세지 삭제
    public void deleteSendMessage(Long messageId, String senderId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("해당 메세지가 없습니다."));

        if (!message.getSenderId().equals(senderId)) throw new IllegalArgumentException("송신 ID가 일치하지 않습니다.");
        else {
            messageRepository.deleteById(messageId);
        }
    }

    public void deleteReceiveMessage(Long messageId, String receiverId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("해당 메세지가 없습니다."));

        if (!message.getReceiverId().equals(receiverId)) throw new IllegalArgumentException("송신 ID가 일치하지 않습니다.");
        else {
            messageRepository.deleteById(messageId);
        }
    }
}
