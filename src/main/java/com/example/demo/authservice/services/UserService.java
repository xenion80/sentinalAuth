package com.example.demo.authservice.services;

import com.example.demo.authservice.Dtos.UserDTO;
import com.example.demo.authservice.Entities.RoleEntity;
import com.example.demo.authservice.Entities.User;
import com.example.demo.authservice.Entities.enums.Role;
import com.example.demo.authservice.exceptions.IdentityAlreadyExistsException;
import com.example.demo.authservice.inputmodels.SignUpInputModel;
import com.example.demo.authservice.repositories.RoleEntityRepository;
import com.example.demo.authservice.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleEntityRepository roleEntityRepository;

    @Transactional
    public UserDTO signup(SignUpInputModel signUpInputModel) {
        Optional<User> user=userRepository.findByEmail(signUpInputModel.getEmail());
        if(user.isPresent()){
            throw new IdentityAlreadyExistsException("User with this email alreday exists"+signUpInputModel.getEmail());
        }
        RoleEntity userRole = roleEntityRepository.findByName(Role.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("USER role missing"));

        User user1=new User();
        user1.setEmail(signUpInputModel.getEmail());
        user1.setName(signUpInputModel.getName());
        user1.setPassword(passwordEncoder.encode(signUpInputModel.getPassword()));
        user1.setEnabled(true);
        user1.setRoles(Set.of(userRole));

        User saved = userRepository.save(user1);

        return modelMapper.map(saved, UserDTO.class);




    }
}
