package com.example.CRM.Authentication.services;

import com.example.CRM.Authentication.dto.responses.UserResponseDTO;

import java.util.List;

public interface UserService {

    public UserResponseDTO getUserById(Long id);
    public List<UserResponseDTO>listUsers();
    public List<UserResponseDTO>listUsersByGroupId(Long groupId);
}
