package com.example.CRM.Authentication.services;

import com.example.CRM.Authentication.dto.responses.UserResponseDTO;
import com.example.CRM.Authentication.entities.User;

import java.util.List;

public interface UserService {

    public UserResponseDTO getUserById(Long id);
    public List<UserResponseDTO>listUsers();
    public List<UserResponseDTO>listUsersByGroupId(Long groupId);
    public void saveUserVerificationToken(User user,String token);
    public String validateToken(String token);
}
