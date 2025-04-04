package com.example.jpa_board.config.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component  //여러개의 경우 @Configuration 써야함
public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String requestURI = req.getRequestURI();

        List<String> WHITE_LIST = Arrays.asList(
                "/auth/login"  //로그인
                , "/api/members/signup"   //회원가입
        );

        // WHITE_LIST에 포함된 주소인지 검사( 와일드카드 사용 필요시 startswith사용)
        if (WHITE_LIST.contains(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        // 쿠키 확인
        Optional<String> memberId = getCookieValue(req, "memberId");

        if (memberId.isEmpty()) {
            //로그인 안 되어 있으먼 401 오류처리(Enum 보다 필터가 빠르기 때문에 Enum처리 X)
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Need Login"); //결과가 한글은 깨짐..
            return;
        }

        // 되어 있으면 진행
        chain.doFilter(request, response);
    }

    // 쿠키 값 가져옴
    private Optional<String> getCookieValue(HttpServletRequest req, String name) {
        if (req.getCookies() == null) {
            return Optional.empty();
        }
        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .map(Cookie::getValue)
                .findFirst();
    }
}
