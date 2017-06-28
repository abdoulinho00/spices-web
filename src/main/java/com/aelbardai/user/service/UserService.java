package com.aelbardai.user.service;

import com.aelbardai.user.domain.User;
import com.aelbardai.user.domain.UserCreateForm;

import java.util.Collection;
import java.util.Optional;


public interface UserService {
    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);
}
