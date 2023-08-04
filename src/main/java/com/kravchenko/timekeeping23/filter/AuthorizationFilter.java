package com.kravchenko.timekeeping23.filter;

import com.kravchenko.timekeeping23.dto.ReadUserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.kravchenko.timekeeping23.util.UrlPath.*;


@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, IMAGES, WELCOME, LOCALE, CSS);
    private static final Set<String> COMMON_PATH = Set.of(LOGOUT, ACTIVITIES, USERS, USER_ACCOUNT);
    private static final Set<String> USER_PATH = Set.of(ACTIVITY_INFO, ACTIVITY_UPDATE, USER_ACTIVITY, USER_UPDATE);
//    private static final Set<String> USER_PATH = Set.of(LOGOUT, ACTIVITIES, ACTIVITY_INFO, ACTIVITY_UPDATE, USERS, USER_ACCOUNT, USER_ACTIVITY, USER_UPDATE);
    private static final Set<String> ADMIN_PATH = Set.of(ACTIVITY_CREATE, ACTIVITY_DELETE);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var uri = ((HttpServletRequest) servletRequest).getRequestURI();
        var session = ((HttpServletRequest) servletRequest).getSession();
        var role = (String)session.getAttribute("role");

        if (isPublicPath(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isCommonPath(uri) && isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isUserPath(uri) && isUserLoggedIn(servletRequest) && ("USER".equals(role) || "ADMIN".equals(role))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isAdminPath(uri) && isUserLoggedIn(servletRequest) && "ADMIN".equals(role)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            var prevPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage : LOGIN);
        }
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        var user = (ReadUserDto) ((HttpServletRequest) servletRequest).getSession(false).getAttribute("user");
        return user != null;
    }

    private boolean isPublicPath(String uri) {
        if(uri.startsWith(IMAGES) || uri.startsWith(CSS) || uri.startsWith("/image")){
            return true;
        }
        return PUBLIC_PATH.contains(uri);
    }

    private boolean isCommonPath(String uri) {
        return COMMON_PATH.contains(uri);
    }

    private boolean isUserPath(String uri) {
        return USER_PATH.contains(uri);
    }

    private boolean isAdminPath(String uri) {
        return ADMIN_PATH.contains(uri);
    }
}
