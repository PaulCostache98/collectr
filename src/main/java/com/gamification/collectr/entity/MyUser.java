package com.gamification.collectr.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MyUser implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(nullable = false, length = 30)
    private String fullName;

    @Column(nullable = false, length = 30, unique = true)
    private String email;

    @Column
    private String randomToken;

    @Transient
    private String randomTokenEmail;

    @Column
    private float userTokens;

    @Transient
    private String passwordConfirm;

    @Transient
    private List<GrantedAuthority> authorities = null;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_badges",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id", referencedColumnName = "badge_id"))
    private Set<Badge> badges = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_quests",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id", referencedColumnName = "quest_id"))
    private Set<Quest> quests = new HashSet<>();

    @Column
    private Integer leaderboardScore;

    public MyUser(MyUser myUser) {
        this.enabled = myUser.isEnabled();
        this.roles = myUser.getRoles();
        this.username = myUser.getUsername();
        this.fullName = myUser.getFullName();
        this.id = myUser.getId();
        this.accountNonExpired = myUser.isAccountNonExpired();
        this.accountNonLocked = myUser.isAccountNonLocked();
        this.credentialsNonExpired = myUser.isCredentialsNonExpired();
        this.email = myUser.getEmail();
        this.userTokens = myUser.getUserTokens();
        this.badges = myUser.getBadges();
        this.quests = myUser.getQuests();
        this.leaderboardScore = (int) myUser.getUserTokens() + myUser.getBadges().size()*10;
    }

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public boolean isAdmin() {
        return this.getRoles().stream().map(Role::getName).toList().contains("ROLE_ADMIN");
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return this.authorities;
    }

}
