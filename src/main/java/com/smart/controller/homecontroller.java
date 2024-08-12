package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.smart.dao.userrepository;
import com.smart.entities.user;
import com.smart.helper.message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@Controller
public class homecontroller {
	@Autowired
	private BCryptPasswordEncoder BCryptPasswordEncoder;
	@Autowired
	private userrepository userrepository;

	@GetMapping("/")
	public String home() {
		return "home";

	}

	@GetMapping("/about")
	public String about() {
		return "about";

	}

	@GetMapping("/signup")
	public String signup(Model m) {
		m.addAttribute("user", new user());
		return "signup";
	}

	@PostMapping("/do_register")
	public String registeruser(@Valid @ModelAttribute("user") user user,BindingResult result ,
			@RequestParam(value = "agreement", defaultValue = "false") Boolean agreement,Model m,HttpSession session) {
		try {
			
			  if (!agreement) {
			 System.out.print("you have not agreed terms and condition"); throw new
			 Exception("you have not agreed terms and conditions");
			  
			  }
			 
			/*
			 * if (!agreement) {
			 * System.out.print("You have not agreed to the terms and conditions.");
			 * session.setAttribute("message", new
			 * message("You must agree to the terms and conditions.", "alert-danger"));
			 * return "signup"; // Return to the signup page }
			 */
			if(result.hasErrors()) {
				m.addAttribute("user",user);
				System.out.print("error"+result.toString());
				return "signup";
			}
			user.setRole("Role_user");
			user.setEnabled(true);
			user.setImageurl("default.png");
			user.setPassword(BCryptPasswordEncoder.encode(user.getPassword()));
			user resultt = this.userrepository.save(user);
			m.addAttribute("user", new user());
			System.out.print(user);
			session.setAttribute("message", new message("Successfully Registered!! " , "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			m.addAttribute("user", user);
			session.setAttribute("message", new message("something went wrong!! " + e.getMessage(), "alert-danger"));
		   return "signup";
		}
	}

}
