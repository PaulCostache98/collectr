package com.gamification.collectr.service.impl;

import com.gamification.collectr.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.entity.Role;
import com.gamification.collectr.exception.UserNotFoundException;
import com.gamification.collectr.repository.RoleRepository;
import com.gamification.collectr.repository.UserRepository;
import com.gamification.collectr.service.UserService;
import com.gamification.collectr.service.email.EmailBodyService;
import com.gamification.collectr.service.email.EmailSender;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    EmailBodyService emailBodyService;

    @Autowired
    EmailSender emailSender;
    private final BadgeRepository badgeRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BadgeRepository badgeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.badgeRepository = badgeRepository;
    }

    public MyUser findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public MyUser findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public MyUser findUserByUserName(String userName) {
        return userRepository.findByUsernameIgnoreCase(userName);
    }

    public MyUser findUserByRandomToken(String randomToken) {
        return userRepository.findByRandomToken(randomToken);
    }

    public boolean findUserByUserNameAndPassword(String userName, String password) {
        final Optional<MyUser> myUser = Optional.ofNullable(userRepository.findByUsernameIgnoreCase(userName));
        return myUser.filter(user -> BCrypt.checkpw(password, user.getPassword())).isPresent();
    }

    public List<MyUser> findAll() {
        return userRepository.findAll();
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);

    }

    public MyUser saveUser(MyUser receivedUser) {
        MyUser myUser = new MyUser(receivedUser);
        myUser.setPassword(new BCryptPasswordEncoder().encode(receivedUser.getPassword()));
        if(userRepository.findByUsernameIgnoreCase(myUser.getUsername()) != null) {
            myUser.setPassword(userRepository.findByUsernameIgnoreCase(myUser.getUsername()).getPassword());
        }
        myUser.setRandomToken(UUID.randomUUID().toString());
        emailSender.sendEmail(myUser.getEmail(), "Activate your Account", emailBodyService.emailBody(myUser));
        receivedUser.getRoles().forEach(role -> {
            final Role roleByName = roleRepository.findByName(role.getName());
            if (roleByName == null)
                myUser.getRoles().add(roleRepository.save(role));
            else {
                role.setId(roleByName.getId());
            }
        });
        return userRepository.save(myUser);
    }

    public void updateUser(MyUser user) {
        List<GrantedAuthority> actualAuthorities = getUserAuthority(user.getRoles());

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), actualAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        userRepository.save(user);
    }

    @Override
    public List<MyUser> searchUser(String keyword) {
        return userRepository.searchUser(keyword);
    }

    @Override
    public boolean verifyUser(MyUser user) {

        MyUser userVerify = userRepository.findByUsernameIgnoreCase(user.getUsername());
        return encoder.matches(user.getPassword(), userVerify.getPassword());
    }


    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

}
