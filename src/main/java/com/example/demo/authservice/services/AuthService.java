package com.example.demo.authservice.services;

import com.example.demo.authservice.Dtos.requests.LoginRequest;
import com.example.demo.authservice.Dtos.responses.LoginResponse;
import com.example.demo.authservice.Entities.ForgotPasswordResetToken;
import com.example.demo.authservice.Entities.RefreshToken;
import com.example.demo.authservice.Entities.User;
import com.example.demo.authservice.Entities.VerificationToken;
import com.example.demo.authservice.exceptions.ResourceNotfoundException;
import com.example.demo.authservice.repositories.PasswordResetTokenRepository;
import com.example.demo.authservice.repositories.RefreshTokenRepository;
import com.example.demo.authservice.repositories.UserRepository;
import com.example.demo.authservice.repositories.VerificationTokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtAuthService jwtAuthService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    @Value("${app.base-url}")
    private String baseUrl;


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

    @Transactional
    public void verify(String token) {
        VerificationToken vt=verificationTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotfoundException("Invalid verification token"));
        if(vt.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Verification token expired");        }
        User user=vt.getUser();
        user.setEnabled(true);
        user.setEmailVerified(true);
        userRepository.save(user);
        verificationTokenRepository.delete(vt);
    }

    @Transactional
    public void requestPasswordReset(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotfoundException("User not found"));

        String token= UUID.randomUUID().toString();
        ForgotPasswordResetToken passwordResetToken=new ForgotPasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiresAt(LocalDateTime.now().plusMinutes(20));
        passwordResetTokenRepository.save(passwordResetToken);
        String url=buildVerificationUrl(baseUrl,token);
        emailService.sendMail(user.getEmail(), "Click here to reset password: ",url);

    }

    public void resetPassword(String token,String newPassword) {
        ForgotPasswordResetToken passwordResetToken= passwordResetTokenRepository.findByToken(token).orElseThrow(()->new ResourceNotfoundException("Invalid verification token"));
        if (passwordResetToken.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("reset link expired");
        }
        User user=passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.deleteByUser(user);
    }
    private String buildVerificationUrl(final String baseURL,
                                        final String token){

        return UriComponentsBuilder.fromUriString(baseURL)
                .path("/auth/reset-password")
                .queryParam("token", token)
                .toUriString();
    }
}
