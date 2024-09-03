package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart.entities.contact;
import com.smart.entities.user;

import java.security.Principal;
import java.util.List;

@RestController
public class searchcontroller {

	@Autowired
	private com.smart.dao.contactrepository contactrepository;
	@Autowired
	private com.smart.dao.userrepository userrepository;

	@GetMapping("/user/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal p) {
	System.out.print(query);
		user user=this.userrepository.findByEmail(p.getName());
		List<contact> contacts=this.contactrepository.findByNameContainingAndUser(query, user);
		return ResponseEntity.ok(contacts);
		
	}
	
//	@GetMapping("/sumit")
//	public String sumit() {
//	//	System.out.print(query);
//		
//		return "home";
		
//	}
}
