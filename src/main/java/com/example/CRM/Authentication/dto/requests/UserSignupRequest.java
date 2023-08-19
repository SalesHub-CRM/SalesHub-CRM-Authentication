package com.example.CRM.Authentication.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;
    private Long phone;
    private String gender;
    private String city;
    private Long zipcode;
    private String fulladress;
    private Date birthdate;
    private Long cin;
    private Integer accountstatus;
    private Date createdat;
    private Date updatedat;
    private Integer confirmaccount;
    private Set<String> roles = new HashSet<>();
}
