package com.smart.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.smart.dao.userrepository;
import com.smart.entities.user;
import com.smart.helper.message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class homecontroller {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private userrepository userrepository;

    @Autowired
    private UserDetailsService userService;

    @ModelAttribute
    public void commonUser(Principal p, Model m) {
        if (p != null) {
            String email = p.getName();
            user user = userrepository.findByEmail(email); // Correct method call
            m.addAttribute("user", user);
        }
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/user/profile")
    public String profile(Principal p, Model m) {
        String email = p.getName();
        user user = userrepository.findByEmail(email); // Correct method call
        m.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/signup")
    public String signup(Model m) {
        m.addAttribute("user", new user());
        return "signup";
    }

    @PostMapping("/do_register")
    public String registeruser(@Valid @ModelAttribute("user") user user,
                               BindingResult result,
                               @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement,
                               Model m, HttpSession session) {
        try {
            if (!agreement) {
                session.setAttribute("message", new message("You must agree to the terms and conditions.", "alert-danger"));
                return "signup";
            }

            if (result.hasErrors()) {
                m.addAttribute("user", user);
                return "signup";
            }

            user.setRole("Role_user");
            user.setEnabled(true);
            user.setImageurl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword())); // Correct encoder instance
            user savedUser = userrepository.save(user); // Save user to the repository
            System.out.print(savedUser);
            session.setAttribute("message", new message("Successfully Registered!!", "alert-success"));
            return "signup";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("user", user);
            session.setAttribute("message", new message("Something went wrong!! " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
}
