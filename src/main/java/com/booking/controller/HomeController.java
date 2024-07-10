package com.booking.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.model.User;
import com.booking.helper.Message;
import com.booking.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String homeMytrip(Model model) {
		model.addAttribute("title", "Home-Hotel");
		return "index";
	}

//	@GetMapping("/package")
//	public String Package(Model model) {
//		model.addAttribute("title", "Holidays Packages");
//		return "package";
//	}

	@GetMapping("/login")
	public String LoginPage(Model model) {
		model.addAttribute("title", "Login Page");
		return "login";
	}

	@GetMapping("/signup")
	public String RegisterPage(Model model) {
		model.addAttribute("title", "Register Page");
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String RegisterUser(@Valid @ModelAttribute("user") User user, BindingResult result,
			@RequestParam("cpassword") String cpassword, Model model, HttpSession session) {

		try {
			user.setRole("ROLE_USER");
			user.setEnabled(true);

			if (user.getPassword().equals(cpassword)) {

				user.setPassword(passwordEncoder.encode(user.getPassword()));
			} else {
				session.setAttribute("message", new Message("Password MisMatch !!", "alert-danger"));
			}

			User saveUser2 = userService.SaveUser(user);

			if (saveUser2 == null) {
				session.setAttribute("message", new Message("Somthing went wrong !!", "alert-danger"));
			}

			System.out.println("Save USer-->   " + saveUser2);

			if (result.hasErrors()) {
				model.addAttribute("user", user);
				return "register";
			}

			session.setAttribute("message", new Message("Successfully Registered .. Now you Can Login !!", "alert-success"));

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Somthing went wrong!!", "alert-danger"));
			return "register";
		}

		return "redirect:/login";
	}

	
	@GetMapping("/demo")
	public String Demo() {
		return "demo";
	}
}
