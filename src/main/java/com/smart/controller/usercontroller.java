package com.smart.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.smart.dao.userrepository;
import com.smart.entities.contact;
import com.smart.entities.user;

@Controller
@RequestMapping("/user")
public class usercontroller {
	@Autowired
	private userrepository userrepository;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		String username = p.getName();
		System.out.print("username" + username);
		user user = userrepository.findByEmail(username);
		m.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Principal p, Model m) {
//    	get the user using username(email)
		return "normal/user_dashboard";
	}

	@GetMapping("/add_contact")
	public String upencontactform(Model m) {
		m.addAttribute("title", "Aaadd Contact");
		m.addAttribute("contact",new contact());
		return "normal/add_contact";
	}
}
