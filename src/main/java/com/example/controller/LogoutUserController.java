package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/logoutUser")
public class LogoutUserController {
	@Autowired
	private HttpSession session;
	
	@GetMapping("/logout")
	private String logout() {
	session.removeAttribute("user");
	return "redirect:/loginUser/toLogin";
	}
	
}
