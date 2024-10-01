package com.example.service;

import com.example.models.User;

public interface UserService {

    User getCurrentUser();

    void updateAuth(User user);

    String changePassword(String currentPassword, String newPassword1, String newPassword2);

    void editUser(User user, String oldUsername);

    boolean addUser(User user);

}
