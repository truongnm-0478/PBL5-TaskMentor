package config;

import io.jsonwebtoken.Claims;
import services.AuthService;
import utils.ResponseUtil;
import utils.UserIdHolder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    private final Set<String> securedEndpoints = new HashSet<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securedEndpoints.add("/TaskMentor/api/user");
        securedEndpoints.add("/TaskMentor/api/logout");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();

        if (isSecuredEndpoint(requestURI)) {
            String accessToken = extractAccessToken(httpRequest);

            if (accessToken != null) {
                if (authenticateTokens(accessToken)) {
                    chain.doFilter(request, response);
                    return;
                }
            }
            ResponseUtil.sendErrorResponse(httpResponse, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean isSecuredEndpoint(String requestURI) {
        return securedEndpoints.contains(requestURI);
    }

    public static String extractAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private boolean authenticateTokens(String accessToken) {
        try {
            // Verify access token and refresh token
            Claims claims = AuthService.verifyToken(accessToken);
            int userId = AuthService.getUserIdFromClaimAccessToken(claims);
            UserIdHolder.setUserId(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void destroy() {}

//    private String extractRefreshToken(HttpServletRequest request) {
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//            return authorizationHeader.substring(7);
//        }
//        return null;
//    }
}
