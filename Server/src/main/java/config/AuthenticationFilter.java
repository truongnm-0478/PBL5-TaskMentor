package config;

import io.jsonwebtoken.Claims;
import services.AuthService;
import utils.ResponseUtil;

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
            String refreshToken = extractRefreshToken(httpRequest);

            if (accessToken != null && refreshToken != null) {
                if (authenticateTokens(accessToken, refreshToken, httpResponse)) {
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

    private String extractAccessToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private String extractRefreshToken(HttpServletRequest request) {
        return request.getHeader("Refresh-Token");
    }


    private boolean authenticateTokens(String accessToken, String refreshToken, HttpServletResponse httpResponse) {
        try {
            // Verify access token and refresh token
            Claims claims = AuthService.verifyToken(accessToken, refreshToken);

            // Create access token from claims
            String newAccessToken = AuthService.generateAccessTokenFromClaims(claims);

            if (newAccessToken != null) {
                Cookie cookie = new Cookie("access_token", newAccessToken);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                httpResponse.addCookie(cookie);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    public void destroy() {}
}
