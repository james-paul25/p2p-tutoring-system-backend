package com.example.p2pTutoringSystem.entities;

import com.example.p2pTutoringSystem.enumarate.UserRole;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private long userId;

    @Column(nullable = false, name="username")
    private String username;

    @Column(nullable = false, unique = true, name="email")
    private String email;

    @Column(nullable = false, name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name="role")
    private UserRole role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, name="created_at")
    private Date createdAt;


    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    public User(String username, String email, String password, UserRole role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdAt = new Date();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(this.role.name());

        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

}
