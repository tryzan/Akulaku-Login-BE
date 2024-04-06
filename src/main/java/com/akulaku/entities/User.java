package com.akulaku.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mst_User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_Id")
    private Long id;

    @NotNull
    @Column(name = "Username")
    private String username;

    @NotNull
    @Column(name = "Password")
    private String password;

    @Column(name="FirstName")
    @NotNull
    private String firstName;

    @Column(name="LastName")
    private String lastName;

    @Column(name = "Address")
    private String address;

    @Column(name = "PhoneNo")
    private String phoneNo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}
