package com.example.CRM.Authentication.services;

import com.example.CRM.Authentication.dto.mapper.UserMapper;
import com.example.CRM.Authentication.dto.responses.UserResponseDTO;
import com.example.CRM.Authentication.entities.User;
import com.example.CRM.Authentication.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImp (UserRepository userRepository)
    {
        this.userRepository=userRepository;
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);

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
        userResponseDTO.setGroupId(user.getGroupId());
        userResponseDTO.setCreatedat(user.getCreatedat());
        userResponseDTO.setUpdatedat(user.getUpdatedat());
        return userResponseDTO;
    }

    @Override
    public List<UserResponseDTO> listUsers() {
        List<User>users = userRepository.findAll();
        UserMapper userMapper = new UserMapper();

        return userMapper.convertToDTOList(users);
    }

    @Override
    public List<UserResponseDTO>listUsersByGroupId(Long groupId){
        List<User>users = userRepository.findByGroupId(groupId);
        UserMapper userMapper = new UserMapper();

        return userMapper.convertToDTOList(users);
    }
}
