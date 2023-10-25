package com.example.CRM.Authentication.dto.mapper;

import com.example.CRM.Authentication.dto.responses.UserResponseDTO;
import com.example.CRM.Authentication.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public List<UserResponseDTO>convertToDTOList(List<User>users){
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private UserResponseDTO convertToDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstname(user.getFirstname());
        userResponseDTO.setLastname(user.getLastname());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setGender(user.getGender());
        userResponseDTO.setCity(user.getCity());
        userResponseDTO.setZipcode(user.getZipcode());
        userResponseDTO.setFulladress(user.getFulladress());
        userResponseDTO.setBirthdate(user.getBirthdate());
        userResponseDTO.setCin(user.getCin());
        userResponseDTO.setAccountstatus(user.getAccountstatus());
        userResponseDTO.setConfirmaccount(user.isConfirmaccount());
        userResponseDTO.setGroupId(user.getGroupId());
        userResponseDTO.setCreatedat(user.getCreatedat());
        userResponseDTO.setUpdatedat(user.getUpdatedat());
        return userResponseDTO;
    }
}
