package com.example.CRM.Authentication.repositories;

import com.example.CRM.Authentication.entities.Role;
import com.example.CRM.Authentication.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
    User findByPassword(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    // @Query("select u from Utilisateur u , u.roles r, PersonnelMedical p where 'ROLE_ADMIN' in (r.name)")
    List<User> findUsersByRoles(Optional<Role> u);

   /* @Query("Select a from Administrator a where a.id=?")
    User findAdminById(Long id);*/

}
