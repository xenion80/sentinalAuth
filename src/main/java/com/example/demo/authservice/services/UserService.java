package com.example.demo.authservice.services;

import com.example.demo.authservice.Dtos.responses.UserResponse;
import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.User;
import com.example.demo.authservice.Entities.VerificationToken;
import com.example.demo.authservice.Entities.enums.Role;
import com.example.demo.authservice.exceptions.IdentityAlreadyExistsException;
import com.example.demo.authservice.Dtos.requests.SignUpInputModel;
import com.example.demo.authservice.repositories.RoleEntityRepository;
import com.example.demo.authservice.repositories.UserRepository;
import com.example.demo.authservice.repositories.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleEntityRepository roleEntityRepository;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Transactional
    public UserResponse signup(SignUpInputModel signUpInputModel) {
        Optional<User> user=userRepository.findByEmail(signUpInputModel.getEmail());
        if(user.isPresent()){
            throw new IdentityAlreadyExistsException("User with this email alreday exists: "+signUpInputModel.getEmail());
        }
        RoleEntity userRole = roleEntityRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("USER role missing"));

        User user1=modelMapper.map(signUpInputModel,User.class);
        user1.setPassword(passwordEncoder.encode(signUpInputModel.getPassword()));
        user1.setEnabled(false);
        user1.setRoles(Set.of(userRole));
        user1.setEmailVerified(false);

        User saved = userRepository.save(user1);
        String token= UUID.randomUUID().toString();
        VerificationToken vt=new VerificationToken();
        vt.setToken(token);
        vt.setUser(saved);
        vt.setExpiresAt(LocalDateTime.now().plusHours(20));
        verificationTokenRepository.save(vt);
        String verifyEmail=buildVerificationUrl(baseUrl,token);

        emailService.sendMail(
                saved.getEmail(),
                "Verify your email",
                "Click here: " + verifyEmail

        );


        return modelMapper.map(saved, UserResponse.class);




    }
    private String buildVerificationUrl(final String baseURL,
                                       final String token){

        return UriComponentsBuilder.fromUriString(baseURL)
                .path("/auth/verify-email")
                .queryParam("token", token)
                .toUriString();
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }

    public User getUserById(Long userid) {
        return userRepository.findById(userid).orElseThrow(()->new BadCredentialsException("user Id not found"));
    }
}
