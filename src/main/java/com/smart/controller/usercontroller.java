package com.smart.controller;

import java.nio.file.Paths;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.Path;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.userrepository;
import com.smart.entities.contact;
import com.smart.entities.user;
import com.smart.helper.message;

import jakarta.servlet.http.HttpSession;
import com.smart.dao.contactrepository;

@Controller
@RequestMapping("/user")
public class usercontroller {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private userrepository userrepository;
	@Autowired
	private contactrepository contactrepository;

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
		m.addAttribute("contact", new contact());
		return "normal/add_contact";
	}

	@PostMapping("/process-contact")
	public String processcontact(@ModelAttribute contact contact, @RequestParam("profileimage") MultipartFile file,
			Principal p, HttpSession session) {
		try {
			System.out.print("data" + contact);

			String username = p.getName();
			user user = this.userrepository.findByEmail(username);
			if (file.isEmpty()) {
				System.out.print("file is empty");
				contact.setImage("register.png");
			} else {
				contact.setImage(file.getOriginalFilename());
				File directory = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(directory.getAbsolutePath() + File.separator + file.getOriginalFilename());

				/* file.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING); */
				file.transferTo(path.toFile());
			}
			contact.setUser(user);
			user.getContacts().add(contact);
			this.userrepository.save(user);
			session.setAttribute("message", new message("your contact is added !!", "success"));

		} catch (Exception e) {
			System.out.print(e.getMessage());
			e.printStackTrace();
			session.setAttribute("message", new message("Something went wrong..Try Again !!", "danger"));

		}

		return "normal/add_contact";
	}

	@GetMapping("/show-contact/{page}")
	public String showcontacts(@PathVariable("page") Integer page, Model m, Principal p) {
		String username = p.getName();

		user user = this.userrepository.findByEmail(username);
		PageRequest pageable = PageRequest.of(page, 4);
		Page<contact> contacts = this.contactrepository.findcontactbyuser(user.getId(), pageable);
		m.addAttribute("contacts", contacts);
		m.addAttribute("currentpage", page);
		m.addAttribute("totalpages", contacts.getTotalPages());
		return "normal/show_contact";
	}

	@GetMapping("/{id}/show-contact")
	public String showcontactdetail(@PathVariable("id") Integer id, Model m, Principal p) {
		Optional<contact> contactoptional = this.contactrepository.findById(id);
		contact contact = contactoptional.get();
		String username = p.getName();
		user user = this.userrepository.findByEmail(username);
		if (user.getId() == contact.getUser().getId())
			m.addAttribute("contact", contact);
		return "normal/contact_detail";
	}

	@GetMapping("/delete/{id}")
	public String deletecontact(@PathVariable("id") Integer id, Model m, Principal p, HttpSession session) {
		Optional<contact> contactoptional = this.contactrepository.findById(id);
		contact contact = contactoptional.get();
		String username = p.getName();
		user user = this.userrepository.findByEmail(username);
		if (user.getId() == contact.getUser().getId()) {
			/*
			 * String imagePath = contact.getImage();
			 * 
			 * if (imagePath != null && !imagePath.isEmpty()) { File imageFile = new
			 * File(imagePath); if (imageFile.exists()) { boolean deleted =
			 * imageFile.delete(); if (!deleted) { // Handle the case where image deletion
			 * failed m.addAttribute("error", "Failed to delete the image file."); } } }
			 */
			this.contactrepository.delete(contact);
			session.setAttribute("message", new message("contact deleted", "success"));
		}

		return "redirect:/user/show-contact/0";

	}

	@PostMapping("/update-contact/{id}")
	public String updateform(@PathVariable("id") Integer id, Model m) {
		contact contact = this.contactrepository.findById(id).get();
		m.addAttribute("contact", contact);

		return "normal/update_form";
	}

	@PostMapping("/process-update")
	public String updatehandler(@ModelAttribute contact contact, @RequestParam("profileimage") MultipartFile file,
			Model m, HttpSession session, Principal p) {
		try {
			contact oldcontactdetail = this.contactrepository.findById(contact.getId()).get();
			if (!file.isEmpty()) {
//				delete old photo
				File deletefile = new ClassPathResource("static/img").getFile();
				File oldFile = new File(deletefile, oldcontactdetail.getImage());
				oldFile.delete();
//				update new photo
				File directory = new ClassPathResource("static/img").getFile();
				Path path = Paths.get(directory.getAbsolutePath() + File.separator + file.getOriginalFilename());
				file.transferTo(path.toFile());
				contact.setImage(file.getOriginalFilename());
			}

			else {
				contact.setImage(oldcontactdetail.getImage());
			}

			System.out.print("contact" + contact.getName());
			System.out.print("contact" + contact.getId());
			String username = p.getName();
			user user = this.userrepository.findByEmail(username);
			contact.setUser(user);
			this.contactrepository.save(contact);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/user/" + contact.getId() + "/show-contact";
	}

	@GetMapping("/profileuser")
	public String yourprofile() {

		return "normal/user_profile";
	}

	@GetMapping("/settings")
	public String settings() {
		return "normal/settings";
	}

	@PostMapping("/change_password")
	public String changepassword(@RequestParam("old-password") String oldpassword,
			@RequestParam("new-password") String newpassword, Principal p, HttpSession session) {
		String username = p.getName();
		user user = this.userrepository.findByEmail(username);

		String oldpass = user.getPassword();

		if (this.passwordEncoder.matches(oldpassword, oldpass)) {
			user.setPassword(this.passwordEncoder.encode(newpassword));
			this.userrepository.save(user);
			session.setAttribute("message", new message("Your Password Changed Succesfully", "success"));
		} else {
			session.setAttribute("message", new message("Your oldPassword is wrong ", "danger"));
			return "normal/settings";
		}
		/*
		 * System.out.println("oldpassword"+oldpassword);
		 * System.out.println("newpassword"+newpassword);
		 */
		return "redirect:/user/index";
	}
	
}
