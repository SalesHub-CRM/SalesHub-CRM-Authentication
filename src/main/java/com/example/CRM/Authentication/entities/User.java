package com.example.CRM.Authentication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    //@Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer accountstatus; //0 = deleted / 1 = active / 2 = suspended
    //@Column(columnDefinition = "INTEGER DEFAULT 0")

    @CreationTimestamp
    private Date createdat;
    @UpdateTimestamp
    private Date updatedat;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    // getters and setters


    public User(Long id, String firstname, String lastname, String email, String password, Long phone, String city, Long zipcode, String fulladress, Date birthdate, Long cin, int accountstatus, Date createdat, Date updatedat) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.city = city;
        this.zipcode = zipcode;
        this.fulladress = fulladress;
        this.birthdate = birthdate;
        this.cin = cin;
        this.accountstatus = accountstatus;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public User() {

    }
}
