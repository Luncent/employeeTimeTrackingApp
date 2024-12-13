package org.example.employeetimetrackingservice.controllers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/app/*")
public class CookieCheckFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String role = getCookieValue(httpRequest, "role");
        String requestURI = httpRequest.getRequestURI();

        // Проверяем наличие куки
        if (role == null || role.isEmpty()) {
            // Если куки нет, редиректим на страницу логина
            httpResponse.sendRedirect("/");
            return;
        }

        if (requestURI.startsWith("/app/admin")) {
            if (!"admin".equals(role)) {
                httpResponse.sendRedirect("/");
                return;
            }
        } else if (requestURI.startsWith("/app/worker")) {
            if (!"worker".equals(role)) {
                httpResponse.sendRedirect("/");
                return;
            }
        } else if (requestURI.startsWith("/app/manager")) {
            if (!"manager".equals(role)) {
                httpResponse.sendRedirect("/");
                return;
            }
        }

        // Если кука есть, пропускаем запрос дальше
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    private String getCookieValue(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
