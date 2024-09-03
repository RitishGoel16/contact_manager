package com.smart.controller;

import java.security.Principal;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
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
import com.smart.service.EmailService;

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
    
    @Autowired
    private EmailService emailservice;
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
    @GetMapping("/forgot")
	public String openemailform() {
		return"forgot_email_form";
	}

	@PostMapping("/send-otp")
	public String sendotp(@RequestParam("email") String email,HttpSession session) {
		System.out.println(email);
		SecureRandom random = new SecureRandom();
        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
        System.out.println("otp"+otp);
        String subject="Otp from SCM";
        String message="<div style=>"+"<h1>OTP ="+otp+"</h1>";
        String to=email;
        boolean flag=this.emailservice.SendEmail(subject, message, to);
        if(flag) {
        	return "verify_otp";
        }
        else {
        	session.setAttribute("message", "check your email id");
        	
        	return "forgot_email_form";
        }
        
		
	}
//	@PostMapping("/verify-otp")
//	public String verifyotp(@RequestParam("otp") String otp,HttpSession session) {
//		System.out.println(otp);
//		SecureRandom random = new SecureRandom();
//        int otp = 100000 + random.nextInt(900000); // Generates a number between 100000 and 999999
//        System.out.println("otp"+otp);
//        String subject="Otp from SCM";
//        String message="<h1>OTP ="+otp+"</h1>";
//        
//        boolean flag=this.emailservice.SendEmail(subject, message, to);
//       
//		
//	}
}
