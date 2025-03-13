package com.Master.Auction.Service.Member;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import com.Master.Auction.Config.Email.RedisUtil;
import org.springframework.stereotype.Service;
import java.io.UnsupportedEncodingException;
import jakarta.mail.internet.MimeUtility;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;
import lombok.extern.slf4j.Slf4j;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;
    private final TemplateEngine templateEngine; // 🔹 주입받도록 변경

    private static final String SENDER_EMAIL = "shahmaran0207@gmail.com";
    private static final String EMAIL_SUBJECT = "[MTA - Master The Auction] 회원가입 인증 코드 안내";
    private static final int CODE_LENGTH = 6;
    private static final long CODE_EXPIRE_TIME = 60 * 30L; // 30분

    // 인증 코드 생성
    private String createCode() {
        int leftLimit = 48; // '0'
        int rightLimit = 122; // 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // 알파벳과 숫자만 필터링
                .limit(CODE_LENGTH)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        log.info("Generated code: {}", code);  // 인증 코드 로그 출력

        String content = templateEngine.process("mail", context);

        log.info("Generated email content: {}", content);  // 이메일 본문 로그 출력
        return content;
    }

    // 이메일 폼 생성
    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        String authCode = createCode();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject(MimeUtility.encodeText(EMAIL_SUBJECT, "UTF-8", "B")); // 제목 인코딩 처리
        helper.setFrom(SENDER_EMAIL);

        // 본문 설정 (HTML 형식)
        String content = setContext(authCode);
        helper.setText(content, true); // 🔹 HTML 본문 설정

        // Redis에 인증 코드 저장 (30분 유효)
        redisUtil.setDataExpire(email, authCode, CODE_EXPIRE_TIME);

        return message;
    }

    // 인증코드 이메일 발송
    public void sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);  // 이전 인증 코드 삭제
        }

        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
    }

    public Boolean verifyEmailCode(String email, String code) {
        String codeFoundByEmail = redisUtil.getData(email);
        return code.equals(codeFoundByEmail);
    }
}
