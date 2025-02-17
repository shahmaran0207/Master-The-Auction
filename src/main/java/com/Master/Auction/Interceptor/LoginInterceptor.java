package com.Master.Auction.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Cookie;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 쿠키에서 firebaseUid 값 확인
        String firebaseUid = getCookieValue(request, "firebaseUid");

        if (firebaseUid == null || firebaseUid.isEmpty()) {
            // firebaseUid가 없으면 로그인 페이지로 리다이렉트
            response.sendRedirect("/Member/login");
            return false;
        }
        return true; // 인증 성공
    }

    // 요청에서 특정 쿠키 값 가져오기
    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // 쿠키가 없을 경우 null 반환
    }
}
