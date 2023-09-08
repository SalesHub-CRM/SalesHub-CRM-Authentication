package com.example.CRM.Authentication.controllers;

import com.example.CRM.Authentication.dto.requests.LoginRequest;
import com.example.CRM.Authentication.dto.requests.UserSignupRequest;
import com.example.CRM.Authentication.dto.responses.LoginResponse;
import com.example.CRM.Authentication.dto.responses.UserResponseDTO;
import com.example.CRM.Authentication.entities.*;
import com.example.CRM.Authentication.repositories.RoleRepository;
import com.example.CRM.Authentication.repositories.UserRepository;
import com.example.CRM.Authentication.security.jwt.JwtUtils;
import com.example.CRM.Authentication.services.UserDetailsImpl;
import com.example.CRM.Authentication.services.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
//@CrossOrigin("*")
//@CrossOrigin(origins = "${baseUrl}",allowCredentials ="true",allowedHeaders = "*")
@RequestMapping("/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserServiceImp userServiceImp;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    System.out.println(userRepository.existsByUsername(loginRequest.getUsername()));
    if (!userRepository.existsByUsername(loginRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Nom d'utilisateur inexistant!");
    }
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.set("Access-Control-Allow-Credentials",
            "true");
      System.out.println(jwtCookie);
      return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).headers(responseHeaders)
              .body(new LoginResponse(
                      userDetails.getId(),
                      userDetails.getFirstname(),
                      userDetails.getLastname(),
                      userDetails.getUsername(),
                      userDetails.getEmail(),
                      userDetails.getPhone(),
                      userDetails.getGender(),
                      userDetails.getCity(),
                      userDetails.getZipcode(),
                      userDetails.getFulladress(),
                      userDetails.getBirthdate(),
                      userDetails.getCin(),
                      userDetails.getAccountstatus(),
                      userDetails.getGroupId(),
                      userDetails.getCreatedat(),
                      userDetails.getUpdatedat(),
                      jwtCookie.toString(),
                      roles));

  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignupRequest signUpRequest) {
    System.out.println("hello");
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Email is already in use!");
    }

    // Create new admin's account
    User utilisateur = new User();
    utilisateur.setFirstname(signUpRequest.getFirstname());
    utilisateur.setLastname(signUpRequest.getLastname());
    utilisateur.setUsername( signUpRequest.getUsername());
    utilisateur.setEmail( signUpRequest.getEmail());
    utilisateur.setPassword(encoder.encode(signUpRequest.getPassword()));
    utilisateur.setPhone( signUpRequest.getPhone());
    utilisateur.setGender( signUpRequest.getGender());
    utilisateur.setCity(signUpRequest.getCity());
    utilisateur.setZipcode(signUpRequest.getZipcode());
    utilisateur.setFulladress(signUpRequest.getFulladress());
    utilisateur.setBirthdate(signUpRequest.getBirthdate());
    utilisateur.setCin(signUpRequest.getCin());
    utilisateur.setAccountstatus(1);
    utilisateur.setGroupId(signUpRequest.getGroupId());
    //utilisateur.setConfirmaccount(false);

    System.out.println(utilisateur);


    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role utilisateurRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(utilisateurRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "ROLE_ADMIN" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }
          case "ROLE_EMPLOYEE" -> {
            Role adminRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
          }

        }
      });
    }

    utilisateur.setRoles(roles);
    userRepository.save(utilisateur);

    return ResponseEntity.ok("ADMIN registered successfully!");
  }




  @PostMapping("/signout")
  public ResponseEntity<?> logoutUtilisateur() {
    System.out.println("logout");
    ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
        .body("You've been signed out!");
  }


  @GetMapping("/getUser/{id}")
  public UserResponseDTO getUserById(@PathVariable("id") Long id)
  {
    return userServiceImp.getUserById(id);
  }


  @GetMapping("/listUsers")
  public List<UserResponseDTO>listUsers(){
    return userServiceImp.listUsers();
  }

  @GetMapping("listByGroupId/{id}")
  public List<UserResponseDTO>listUsersByGroupId(@PathVariable("id") Long id){
    return userServiceImp.listUsersByGroupId(id);
  }

}
