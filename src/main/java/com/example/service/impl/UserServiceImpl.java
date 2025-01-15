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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final MailService mailService;
    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(MailService mailService, UserRepo userRepo, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder) {
        this.mailService = mailService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User getCurrentUser() {
        try {
            final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails userDetails) {
                return userRepo.findByUsername(userDetails.getUsername());
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    @Override
    public void updateAuth(User user) {
        final Authentication request = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        final Authentication result = authenticationManager.authenticate(request);

        SecurityContextHolder.getContext().setAuthentication(result);
    }

    @Override
    public String changePassword(String currentPassword, String newPassword1, String newPassword2) {
        final User user = getCurrentUser();

        if (user == null) {
            return "error: user not found";
        }
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "error1";
        }
        if (!newPassword1.equals(newPassword2)) {
            return "error2";
        }

        user.setPassword(passwordEncoder.encode(newPassword1));
        userRepo.save(user);

        // mailService.changePasswordNotification(user);

        return "success";
    }

    @Override
    public void editUser(User user, String oldUsername) {
        final User existingUser = userRepo.findByUsername(oldUsername);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUsername(user.getUsername());
            existingUser.setPhoneNumber(user.getPhoneNumber());

            userRepo.save(existingUser);
            updateAuth(existingUser);
        }
    }

    @Override
    public boolean addUser(User user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepo.save(user);

        // mailService.sendRegistrationNotification(user);

        return true;
    }

}