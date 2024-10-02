package com.example.service.impl;

import com.example.models.Role;
import com.example.models.User;
import com.example.repository.UserRepo;
import com.example.service.MailService;
import com.example.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final MailService mailService;

    private final UserRepo userRepo;

    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(final MailService mailService, final UserRepo userRepo, final AuthenticationManager authenticationManager) {
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    public User getCurrentUser() {
        try {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            final UserDetails userDetails = ((UserDetails) principal);

            return userRepo.findByUsername(userDetails.getUsername());
        } catch (Exception e) {
            return null;
        }
    }

    public void updateAuth(final User user) {
        final Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

        final Authentication result = authenticationManager.authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(result);
    }

    public String changePassword(final String currentPassword, final String newPassword1, final String newPassword2) {
        final User user = getCurrentUser();
        if (!currentPassword.equals(user.getPassword())) {
            return "error1";
        } else if (!newPassword1.equals(newPassword2)) {
            return "error2";
        } else {
            user.setPassword(newPassword1);

            userRepo.save(user);

            //mailService.changePasswordNotification(user);

            return "";
        }
    }

    public void editUser(final User user, final String oldUsername) {
        final User existedUser = userRepo.findByUsername(oldUsername);

        existedUser.setFirstName(user.getFirstName());
        existedUser.setLastName(user.getLastName());
        existedUser.setUsername(user.getUsername());
        existedUser.setPhoneNumber(user.getPhoneNumber());

        userRepo.save(existedUser);

        updateAuth(existedUser);
    }

    public boolean addUser(final User user) {
        final User existedUser = userRepo.findByUsername(user.getUsername());

        if (existedUser != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepo.save(user);

        //mailService.sendRegistrationNotification(user);

        return true;
    }

}
