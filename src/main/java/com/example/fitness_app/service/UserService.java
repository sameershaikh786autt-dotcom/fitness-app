package com.example.fitness_app.service;


import com.example.fitness_app.DTO.UserRegisterDTO;
import com.example.fitness_app.DTO.UserResponseDTO;
import com.example.fitness_app.Enum.Roles;
import com.example.fitness_app.globalException.PasswordValidation;
import com.example.fitness_app.model.User;
import com.example.fitness_app.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.List;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        User user = new User();
        user.setFirstName(userRegisterDTO.getFirstName());
        user.setLastName(userRegisterDTO.getLastName());
        user.setName(userRegisterDTO.getName());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());

//        List.Of();
//        List.of();

        // default role yha s ayga
        Roles defaultRole = Roles.USER;
        user.setRole(defaultRole);



        return user;
    }

    // SAVE KRNE K LIYE KIYA JATA HAI
    private User saveUser(User user) {
        return userRepository.save(user);
    }

    // RESPONSE K LIYE Y METHOD
    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setUserId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setUpdatedAt(user.getUpdatedAt());

        return userResponseDTO;
    }
}
