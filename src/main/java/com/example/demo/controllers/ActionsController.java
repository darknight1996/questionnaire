package com.example.demo.controllers;

import com.example.demo.models.Field;
import com.example.demo.models.FieldType;
import com.example.demo.models.Response;
import com.example.demo.models.User;
import com.example.demo.services.FieldService;
import com.example.demo.services.ResponseService;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.bytebuddy.description.method.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ActionsController {

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ResponseService responseService;

    @PostMapping("/registration")
    public String addUser(User user) {
        if (!userService.addUser(user)) {
            return "redirect:/registration?error";
        }
        return "redirect:/login";
    }

    @PostMapping("/profile")
    public String editUser(User user, String oldUsername) {
        userService.editUser(user, oldUsername);
        return "redirect:/";
    }

    @PostMapping("/password")
    public String changePassword(String currentPassword, String newPassword1, String newPassword2) {
        String message = userService.changePassword(currentPassword, newPassword1, newPassword2);
        if (message.isEmpty()) {
            return "redirect:/";
        } else {
            return "redirect:/password?" + message;
        }
    }

    @PostMapping("/fields/add")
    public String addField(Field field, String options) {
        field.setOptions(new ArrayList<>(Arrays.asList(options.split("\n"))));
        fieldService.addField(field);
        return "redirect:/fields";
    }

    @PostMapping("/fields/edit")
    public String editField(Long id, String label, FieldType type, String options, boolean required, boolean active) {
        fieldService.editField(id, label, type, new ArrayList<>(Arrays.asList(options.split("\n"))), required, active);
        return "redirect:/fields";
    }

    @PostMapping("/fields/delete")
    public String deleteField(Long id) {
        fieldService.deleteField(id);
        return "redirect:/fields";
    }

    @PostMapping(value = "/response")
    public String addResponse(String map) {
        System.out.println(map);
        Type type = new TypeToken<Map<Long, String>>(){}.getType();
        Gson gson = new Gson();
        Map<Long, String> myMap = gson.fromJson(map, type);
        responseService.addResponse(myMap);
        return "redirect:/";
    }

}
