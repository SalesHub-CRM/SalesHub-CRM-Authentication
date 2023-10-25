package com.example.CRM.Authentication.services;


import com.example.CRM.Authentication.entities.User;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;

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
  private boolean confirmaccount;
  private Long groupId;
  private Date createdat;
  private Date updatedat;

  private Collection<? extends GrantedAuthority> authorities;

  public UserDetailsImpl(Long id, String firstname,String lastname, String username, String email, String password, Long phone,
                         String gender, String city, Long zipcode,String fulladress, Date birthdate, Long cin, Integer accountstatus,
                         boolean confirmaccount,Long groupId,Date createdat,Date updatedat,Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.gender = gender;
    this.city = city;
    this.zipcode = zipcode;
    this.fulladress = fulladress;
    this.birthdate = birthdate;
    this.cin = cin;
    this.accountstatus = accountstatus;
    this.confirmaccount=confirmaccount;
    this.groupId=groupId;
    this.createdat=createdat;
    this.updatedat=updatedat;
    this.authorities = authorities;
  }

  public static UserDetailsImpl build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        .collect(Collectors.toList());

    return new UserDetailsImpl(
        user.getId(),
        user.getFirstname(),
        user.getLastname(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        user.getPhone(),
        user.getGender(),
        user.getCity(),
        user.getZipcode(),
        user.getFulladress(),
        user.getBirthdate(),
        user.getCin(),
        user.getAccountstatus(),
        user.isConfirmaccount(),
        user.getGroupId(),
        user.getCreatedat(),
        user.getUpdatedat(),
        authorities);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

  public Long getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getEmail() {
    return email;
  }

  public Long getPhone() {
    return phone;
  }

  public String getGender() {
    return gender;
  }

  public String getCity() {
    return city;
  }

  public Long getZipcode() {
    return zipcode;
  }

  public String getFulladress() {
    return fulladress;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public Long getCin() {
    return cin;
  }

  public Integer getAccountstatus() {
    return accountstatus;
  }

  public Long getGroupId() {return groupId;}
  public boolean getConfirmaccount(){return confirmaccount;}

  public Date getCreatedat() {
    return createdat;
  }

  public Date getUpdatedat() {
    return updatedat;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}
