package com.gamification.collectr.service;

import org.springframework.stereotype.Service;
import com.gamification.collectr.entity.MyUser;
import com.gamification.collectr.exception.UserNotFoundException;

import java.util.List;

@Service
public interface UserService {

    MyUser findUserByEmail(String email);

    MyUser findById(Long id) throws UserNotFoundException;

    MyUser findUserByUserName(String username);

    MyUser findUserByRandomToken(String randomToken);

    boolean findUserByUserNameAndPassword(String userName, String password);

    List<MyUser> findAll();

    void deleteById(long id);

    MyUser saveUser(MyUser u);

    void updateUser(MyUser user);

    List<MyUser> searchUser(String keyword);

    boolean verifyUser(MyUser user);

}

