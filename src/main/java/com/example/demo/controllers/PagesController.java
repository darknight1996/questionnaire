package com.example.demo.controllers;

import com.example.demo.models.Field;
import com.example.demo.models.Response;
import com.example.demo.services.FieldService;
import com.example.demo.services.ResponseService;
import com.example.demo.services.UserService;
import com.example.demo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PagesController {

    @Autowired
    private UserService userService;

    @Autowired
    private FieldService fieldService;

    @Autowired
    private ResponseService responseService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/password")
    public String password(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "password";
    }

    @GetMapping("/fields")
    public String fields(Model model) {

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        List<Field> fields = fieldService.getAllFields();
        model.addAttribute("fields", fields);

        return "fields";
    }

    @GetMapping("/responses")
    public String responses(Model model) {
        User user = userService.getCurrentUser();
        List<Response> responses = responseService.getResponses();
        List<List<String>> responsesForRender = new ArrayList<>();
        List<Field> activeFields = fieldService.getActiveFields();
        responses.forEach(response -> {
            List<String> list = new ArrayList<>();
            activeFields.forEach(field -> {
                String answer = response.getMap().get(field.getId());
                if (answer != null) {
                    list.add(answer);
                } else {
                    list.add("N/A");
                }
            });
            responsesForRender.add(list);

        });
        responsesForRender.forEach(System.out::println);
        model.addAttribute("user", user);
        model.addAttribute("fields", activeFields);
        model.addAttribute("responses", responsesForRender);
        return "responses";
    }

    @GetMapping("/")
    public String response(Model model) {

        List<Field> fields = fieldService.getActiveFields();
        model.addAttribute("fields", fields);

        User user = userService.getCurrentUser();
        model.addAttribute("user", user);

        return "response";
    }
}
