package com.example.CRM.Authentication.dto.responses;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Long phone;
    private String gender;
    private String city;
    private Long zipcode;
    private String fulladress;
    private Date birthdate;
    private Long cin;
    private Integer accountstatus; //0 = deleted / 1 = active / 2 = suspended
    private Long groupId;
    private Date createdat;
    private Date updatedat;
}
