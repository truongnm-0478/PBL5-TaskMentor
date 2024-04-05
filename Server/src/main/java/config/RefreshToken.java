package config;

import models.LoginResponse;
import services.AuthService;
import utils.ResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/refresh")
public class RefreshToken extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String refreshToken = request.getParameter("refresh_token");

        if (isValidRefreshToken(refreshToken)) {
            // Generate a new access token
            String newAccessToken = generateAccessToken(refreshToken);

            Cookie cookie = new Cookie("access_token", newAccessToken);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);

            ResponseUtil.sendJsonResponse(response, HttpServletResponse.SC_OK, "Token refresh successfully.", new LoginResponse(newAccessToken, refreshToken));
        } else {
            ResponseUtil.sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token");
        }
    }

    private boolean isValidRefreshToken(String refreshToken) {
        return AuthService.verifyRefreshToken(refreshToken);
    }

    private String generateAccessToken(String refreshToken) {
        String username = AuthService.getUsernameFromFresherToken(refreshToken);
        return AuthService.generateAccessToken(username);
    }
}
