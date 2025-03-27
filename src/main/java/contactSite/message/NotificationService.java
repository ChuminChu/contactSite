package contactSite.message;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationService {
    
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 1시간
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String userId) {
        SseEmitter emitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitters.put(userId, emitter);

        System.out.println("✅ 사용자 SSE 구독: " + userId);
        System.out.println("현재 등록된 사용자 목록: " + emitters.keySet());

        // 대기 중인 알림 전송
        List<String> messages = pendingNotifications.remove(userId);
        if (messages != null) {
            for (String message : messages) {
                try {
                    emitter.send(SseEmitter.event().name("notification").data(message));
                    System.out.println("📤 대기 알림 전송: " + userId + ", 메시지: " + message);
                } catch (IOException e) {
                    System.out.println("❌ 대기 알림 전송 실패: " + userId + ", 오류 메시지: " + e.getMessage());
                }
            }
        }

        // 초기 연결 확인 이벤트
        try {
            emitter.send(SseEmitter.event().name("connection").data("연결 성공: " + userId));
        } catch (IOException e) {
            emitters.remove(userId);
            emitter.completeWithError(e);
            System.out.println("❌ 초기 연결 실패: " + userId);
        }

        // Emitter 이벤트 핸들러 설정
        emitter.onCompletion(() -> {
            emitters.remove(userId);
            System.out.println("✅ SSE 완료: " + userId);
        });

        emitter.onTimeout(() -> {
            emitters.remove(userId);
            System.out.println("⏳ SSE 타임아웃: " + userId);
            emitter.complete();
        });

        emitter.onError((e) -> {
            emitters.remove(userId);
            System.out.println("❌ SSE 오류 발생: " + userId + ", 오류 메시지: " + e.getMessage());
            emitter.completeWithError(e);
        });

        return emitter;
    }


    // 알림 대기열 (구독자가 없을 때 메시지를 저장)
    private final Map<String, List<String>> pendingNotifications = new ConcurrentHashMap<>();

    public void sendNotification(String userId, String message) {
        SseEmitter emitter = emitters.get(userId); // 저장된 Emitter 조회

        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(message));
                System.out.println("📤 알림 전송 성공: " + userId + ", 메시지: " + message);
            } catch (IOException e) {
                emitters.remove(userId);
                System.out.println("❌ 알림 전송 실패: " + userId + ", 오류 메시지: " + e.getMessage());
            }
        } else {
            // 구독자가 없으면 대기열에 저장
            System.out.println("❌ SSE Emitter 없음: " + userId + ", 메시지 대기열에 저장");
            pendingNotifications.computeIfAbsent(userId, k -> new ArrayList<>()).add(message);
        }
    }

/*
    public void sendNotification(String userId, String message) {
        SseEmitter emitter = emitterRepository.get(userId);
        if (emitter == null) {
            log.warn("SSE Emitter가 존재하지 않습니다: {}", userId);
            return;
        }
        try {
            emitter.send(SseEmitter.event().name("notification").data(message));
        } catch (IOException e) {
            emitterRepository.remove(userId);
            log.error("알림 전송 실패: {}", userId, e);
        }
    }*/



    // 모든 사용자에게 브로드캐스트 알림 (선택 사항)
    public void broadcastNotification(String message) {
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().name("broadcast-notification").data(message));
            } catch (IOException e) {
                emitters.remove(userId); // 실패한 연결 제거
                System.err.println("Failed to broadcast notification to user: " + userId);
            }
        });
    }
}


