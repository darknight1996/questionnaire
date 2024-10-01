package com.example.demo.controllers;

import com.example.demo.models.Field;
import com.example.demo.models.Response;
import com.example.demo.models.User;
import com.example.demo.services.FieldService;
import com.example.demo.services.ResponseService;
import com.example.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class PagesController {

    private final UserService userService;

    private final FieldService fieldService;

    private final ResponseService responseService;

    public PagesController(final UserService userService, final FieldService fieldService, final ResponseService responseService) {
        this.userService = userService;
        this.fieldService = fieldService;
        this.responseService = responseService;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile(final Model model) {
        final User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        return "profile";
    }

    @GetMapping("/password")
    public String password(final Model model) {
        final User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        return "password";
    }

    @GetMapping("/fields")
    public String fields(final Model model) {
        final User user = userService.getCurrentUser();

        model.addAttribute("user", user);

        final List<Field> fields = fieldService.getAllFields();

        model.addAttribute("fields", fields);

        return "fields";
    }

    @GetMapping("/responses")
    public String responses(final Model model) {
        final List<Response> responses = responseService.getResponses();
        final List<Field> activeFields = fieldService.getActiveFields();

        final List<List<String>> responsesForRender = new ArrayList<>();

        responses.forEach(response -> {
            final List<String> answersForRender = new ArrayList<>();
            activeFields.forEach(field -> {
                final String answer = response.getMap().get(field.getId());
                answersForRender.add(Objects.requireNonNullElse(answer, "N/A"));
            });
            responsesForRender.add(answersForRender);

        });

        final User user = userService.getCurrentUser();

        model.addAttribute("user", user);
        model.addAttribute("fields", activeFields);
        model.addAttribute("responses", responsesForRender);

        return "responses";
    }

    @GetMapping("/")
    public String response(final Model model) {
        final User user = userService.getCurrentUser();
        final List<Field> activeFields = fieldService.getActiveFields();

        model.addAttribute("user", user);
        model.addAttribute("fields", activeFields);

        return "response";
    }

}
