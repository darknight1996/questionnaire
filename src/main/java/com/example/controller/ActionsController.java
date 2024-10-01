package com.example.controller;

import com.example.models.Field;
import com.example.models.FieldType;
import com.example.models.User;
import com.example.service.FieldService;
import com.example.service.ResponseService;
import com.example.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Controller
public class ActionsController {

    private final UserService userService;

    private final FieldService fieldService;

    private final ResponseService responseService;

    public ActionsController(final UserService userService, final FieldService fieldService, final ResponseService responseService) {
        this.userService = userService;
        this.fieldService = fieldService;
        this.responseService = responseService;
    }

    @PostMapping("/registration")
    public String addUser(final User user) {
        if (!userService.addUser(user)) {
            return "redirect:/registration?error";
        }

        return "redirect:/login";
    }

    @PostMapping("/profile")
    public String editUser(final User user, final String oldUsername) {
        userService.editUser(user, oldUsername);

        return "redirect:/";
    }

    @PostMapping("/password")
    public String changePassword(final String currentPassword, final String newPassword1, final String newPassword2) {
        final String message = userService.changePassword(currentPassword, newPassword1, newPassword2);

        if (message.isEmpty()) {
            return "redirect:/";
        } else {
            return "redirect:/password?" + message;
        }
    }

    @PostMapping("/fields/add")
    public String addField(final Field field, final String options) {
        field.setOptions(new ArrayList<>(Arrays.asList(options.split("\n"))));

        fieldService.addField(field);

        return "redirect:/fields";
    }

    @PostMapping("/fields/edit")
    public String editField(final Long id, final String label, final FieldType type, final String options, final boolean required, final boolean active) {
        fieldService.editField(id, label, type, new ArrayList<>(Arrays.asList(options.split("\n"))), required, active);

        return "redirect:/fields";
    }

    @PostMapping("/fields/delete")
    public String deleteField(final Long id) {
        fieldService.deleteField(id);

        return "redirect:/fields";
    }

    @PostMapping(value = "/response")
    public String addResponse(final String map) {
        final Type type = new TypeToken<Map<Long, String>>(){}.getType();
        final Gson gson = new Gson();
        final Map<Long, String> myMap = gson.fromJson(map, type);

        responseService.addResponse(myMap);

        return "redirect:/";
    }

}
