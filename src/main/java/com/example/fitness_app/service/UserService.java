package com.example.fitness_app.service;


import com.example.fitness_app.DTO.LoginDetails;
import com.example.fitness_app.DTO.ResponseLogin;
import com.example.fitness_app.DTO.UserRegisterDTO;
import com.example.fitness_app.DTO.UserResponseDTO;
import com.example.fitness_app.Enum.Roles;
import com.example.fitness_app.globalException.PasswordValidation;
import com.example.fitness_app.model.User;
import com.example.fitness_app.repository.UserRepository;
import com.example.fitness_app.securityConfig.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserResponseDTO createUser(UserRegisterDTO userRegisterDTO) {

        // validation check
        if ( userRegisterDTO.getPassword() == null  || userRegisterDTO.getPassword().length() < 8) {
            throw new PasswordValidation("Password must be at least 8 characters long");
        }

        // 1. DTO se Entity banayi
        User user = mapToEntity(userRegisterDTO);


        //DATA SAVE K LIYE METHDO
        User save = saveUser(user);

        UserResponseDTO userResponseDTO = mapToDTO(save);

        return userResponseDTO;
    }

    // DATA LENE K LIYE EK METHOD KYA KYA HAI


    private User mapToEntity(UserRegisterDTO userRegisterDTO) {
        // USING BUILDER TO SAVE DATA INTO ENTITY
        User user = User.builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .name(userRegisterDTO.getName())
                .email(userRegisterDTO.getEmail())
                .role(Roles.USER)
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .build();



//        User user = new User();
//        user.setFirstName(userRegisterDTO.getFirstName());
//        user.setLastName(userRegisterDTO.getLastName());
//        user.setName(userRegisterDTO.getName());
//        user.setEmail(userRegisterDTO.getEmail());
//        user.setPassword(userRegisterDTO.getPassword());
////        List.Of();
////        List.of();
//        // default role yha s ayga
//        Roles defaultRole = Roles.USER;
//        user.setRole(defaultRole);
        return user;
    }

    // SAVE KRNE K LIYE KIYA JATA HAI
    private User saveUser(User user) {
        return userRepository.save(user);
    }

    // RESPONSE K LIYE Y METHOD
    private UserResponseDTO mapToDTO(User user) {

        UserResponseDTO userResponse = UserResponseDTO.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

//        UserResponseDTO userResponseDTO = new UserResponseDTO();
//        userResponseDTO.setUserId(user.getId());
//        userResponseDTO.setFirstName(user.getFirstName());
//        userResponseDTO.setLastName(user.getLastName());
//        userResponseDTO.setName(user.getName());
//        userResponseDTO.setEmail(user.getEmail());
//        userResponseDTO.setRole(user.getRole());
//        userResponseDTO.setCreatedAt(user.getCreatedAt());
//        userResponseDTO.setUpdatedAt(user.getUpdatedAt());

        return userResponse;
    }


    // YHA P LOGIN S RELATED KAAM HO RHAA HAI
    public ResponseLogin loginUser(LoginDetails loginDetails) {
            UsernamePasswordAuthenticationToken authenToken =
                    new UsernamePasswordAuthenticationToken(loginDetails.getEmail(), loginDetails.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenToken);

        // 2. Authentication success hone ke baad UserDetails nikaali
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // TOKEN GENERATE METHOD
        String tokenGenerated = jwtService.generateToken(userDetails);

        return ResponseLogin.builder()
                .message("Successfully logged in and Token generated")
                .email(userDetails.getUsername())
                .token(tokenGenerated)
                .build();
    }
}
