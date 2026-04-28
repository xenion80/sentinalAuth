package com.example.demo.authservice.services;

import com.example.demo.authservice.Dtos.requests.LoginRequest;
import com.example.demo.authservice.Dtos.responses.LoginResponse;
import com.example.demo.authservice.Entities.RefreshToken;
import com.example.demo.authservice.Entities.User;
import com.example.demo.authservice.repositories.RefreshTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),loginRequest.getPassword()));

        User user= (User) authentication.getPrincipal();
        String accesstoken=jwtAuthService.generateAccessToken(user);
        String refreshTokenvalue=jwtAuthService.generateRefreshToken(user);
        RefreshToken refreshToken=RefreshToken.builder()
                .token(refreshTokenvalue)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(90))
                .revoked(false)
                .build();
        refreshTokenRepository.save(refreshToken);


        return new LoginResponse(user.getId(),user.getName(),user.getEmail(),user.getRoles().stream().map(role->role.getName()).collect(Collectors.toSet()),accesstoken,refreshTokenvalue);


    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken=null;
        if(request.getCookies()!=null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if(refreshToken!=null){
            RefreshToken token=refreshTokenRepository.findByToken(refreshToken).orElse(null);
            if (token!=null){
                token.setRevoked(true);
                refreshTokenRepository.save(token);
            }

        }
        Cookie cookie=new Cookie("refreshToken","");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }

    public LoginResponse refreshToken(String refreshToken) {
        RefreshToken token=refreshTokenRepository.findByToken(refreshToken).orElseThrow(()->new RuntimeException("Invalid token"));
        Long userid= jwtAuthService.extractUserId(refreshToken);
        if (token.getRevoked())throw new RuntimeException("the refreshtoken is revoked");
        if (jwtAuthService.validToken(refreshToken))throw new RuntimeException("the token is not valid") ;
        User user=userService.getUserById(userid);
        String accesstoken=jwtAuthService.generateRefreshToken(user);
        return new LoginResponse(user.getId(),user.getName(),user.getEmail(),user.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toSet()), accesstoken,refreshToken);



    }
}
