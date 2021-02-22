package com.cursor.library.models;

import com.cursor.library.daos.UserPermission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String username;
    private String password;

    @ElementCollection(targetClass = UserPermissions.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<UserPermissions> permissions;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream().map(p-> new SimpleGrantedAuthority(p.name())).collect(Collectors.toList());
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
}
