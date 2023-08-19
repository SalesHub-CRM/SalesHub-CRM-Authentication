package com.example.CRM.Authentication.repositories;


import com.example.CRM.Authentication.entities.ERole;
import com.example.CRM.Authentication.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
