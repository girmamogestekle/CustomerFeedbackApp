package customer.feedback.com.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import customer.feedback.com.utility.JsonDeserializers;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="User")
public class UserEntity extends BaseEntity implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "Username", nullable = false, unique = true)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "IsEnabled", nullable = false)
    private boolean isEnabled;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "JoinUserRoles", joinColumns = @JoinColumn(name = "Username", referencedColumnName = "Username"))
    @Enumerated(EnumType.STRING)
    @Column(name = "Role", nullable = false)
    private Set<Role> roles = new HashSet<>();

    @Override
    @JsonDeserialize(using = JsonDeserializers.CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }

        return authorities;
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

}
