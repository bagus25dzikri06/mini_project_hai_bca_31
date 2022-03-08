package com.simple_form.simple_form.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.simple_form.simple_form.model.UsersModel;
import com.simple_form.simple_form.service.UsersService;

@Controller
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerRequest", new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
            model.addAttribute("loginRequest", new UsersModel());
            return "login_page";
        } else {
            model.addAttribute("userLogin", messages);
            return "personal_page";
        }

    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel, HttpSession session) {
        System.out.println("register request: " + usersModel);

        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            UsersModel registeredUser = usersService.registerUser(usersModel.getLogin(), usersModel.getPassword(),
                    usersModel.getEmail());
            return registeredUser == null ? "error_page" : "redirect:/login";
        } else {

            return "redirect:/login";
        }
    }

    @PostMapping("/app")
    public String login(@ModelAttribute UsersModel usersModel, Model model, @RequestParam("login") String msg,
            HttpServletRequest request) {
        System.out.println("login request: " + usersModel);
        UsersModel authenticated = usersService.authenticate(usersModel.getLogin(), usersModel.getPassword());
        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");

        if (authenticated != null) {
            if (msgs == null) {
                msgs = new ArrayList<>();
                request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
                msgs.add(msg);
            }

            System.out.println("register request: " + msgs);
            model.addAttribute("userLogin", msgs);

            return "personal_page";
        } else {
            return "error_page";
        }

    }

    @GetMapping("/app")
    public String dashboard(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
            model.addAttribute("userLogin", messages);
            return "index";
        } else {
            model.addAttribute("userLogin", messages);

            return "personal_page";
        }

    }

    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
            model.addAttribute("Login", "LOGIN");

            return "index";
        } else {
            model.addAttribute("sessionMessages", messages);

            return "redirect:/app";
        }

    }

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if (msgs == null) {
            msgs = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
        }
        msgs.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
        return "redirect:/";
    }

    @GetMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }

}
