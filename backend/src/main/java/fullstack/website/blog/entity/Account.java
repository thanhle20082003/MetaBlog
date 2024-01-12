package fullstack.website.blog.entity;

import fullstack.website.blog.utils.enums.AccountType;
import fullstack.website.blog.utils.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "account")
@Builder
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;

    @NotBlank
    @Column(columnDefinition = "varchar(255) not null")
    private String password;

    @Column(columnDefinition = "nvarchar(50) not null")
    private String fullName;

    @NotBlank
    @Column(columnDefinition = "varchar(50) not null")
    private String email;

    @Column(columnDefinition = "varchar(10)")
    private String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date birthday;

    @Column(nullable = false)
    private Boolean gender;

    @Column(columnDefinition = "nvarchar(255)")
    private String image;

    @Column(columnDefinition = "nvarchar(255)")
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Temporal(TemporalType.DATE)
    private Date lastAccessDate;

    @Column(columnDefinition = "nvarchar(255)")
    private String accessToken;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Post> posts;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private Set<Like> likes;

    public Account(Long accountId, String username, String email, Role role) {
        this.id = accountId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Account(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return (username != null && !username.isEmpty()) ? username : email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return type != AccountType.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return type != AccountType.UNVERIFIED;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthday=" + birthday +
                ", gender=" + gender +
                ", image='" + image + '\'' +
                ", address='" + address + '\'' +
                ", role=" + role +
                ", type=" + type +
                ", lastAccessDate=" + lastAccessDate +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }

}
