package com.example.CRM.Authentication.services;

import com.example.CRM.Authentication.dto.mapper.UserMapper;
import com.example.CRM.Authentication.dto.responses.UserResponseDTO;
import com.example.CRM.Authentication.entities.User;
import com.example.CRM.Authentication.repositories.UserRepository;
import com.example.CRM.Authentication.token.VerificationToken;
import com.example.CRM.Authentication.token.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public UserServiceImp (UserRepository userRepository,VerificationTokenRepository verificationTokenRepository)
    {
        this.userRepository=userRepository;
        this.verificationTokenRepository=verificationTokenRepository;
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
        userResponseDTO.setConfirmaccount(user.isConfirmaccount());
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

    @Override
    public void saveUserVerificationToken(User user,String token){
        var verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String token){
        VerificationToken newToken = verificationTokenRepository.findByToken(token);

        if(newToken==null){
            return "invalid verification token";
        }

        User user = newToken.getUser();
        Calendar calendar = Calendar.getInstance();

        if((newToken.getExpirationTime().getTime()-calendar.getTime().getTime())<=0){
            verificationTokenRepository.delete(newToken);
            return "this token has already expired";
        }

        user.setConfirmaccount(true);
        userRepository.save(user);
        return "valid";
    }
}
