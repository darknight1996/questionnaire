package com.example.demo.services.impl;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepo;
import com.example.demo.services.MailService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getCurrentUser() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserDetails userDetails = ((UserDetails) principal);
            User user = userRepo.findByUsername(userDetails.getUsername());
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAuth(User user) {
        Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication result = authenticationManager.authenticate(request);
        SecurityContextHolder.getContext().setAuthentication(result);
    }

    public String changePassword(String currentPassword, String newPassword1, String newPassword2) {
        User user = getCurrentUser();
        if (!currentPassword.equals(user.getPassword())) {
            return "error1";
        } else if (!newPassword1.equals(newPassword2)) {
            return "error2";
        } else {
            user.setPassword(newPassword1);
            userRepo.save(user);
            mailService.changePasswordNotification(user);
            return "";
        }
    }

    public void editUser(User user, String oldUsername) {
        User userFromDb = userRepo.findByUsername(oldUsername);
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setUsername(user.getUsername());
        userFromDb.setPhoneNumber(user.getPhoneNumber());
        userRepo.save(userFromDb);
        // update user in security session
        updateAuth(userFromDb);
    }

    public boolean addUser(User user) {
        // check is user exists
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        // fill user's fields save in db and send mail notification
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        mailService.sendRegistrationNotification(user);
        return true;
    }

}
