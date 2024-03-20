package com.example.icecreamfactory.controllers.viewcontroller;

import com.example.icecreamfactory.service.UserService;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
	private UserService userService;

	public LoginController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login(){
		return "login";
	}
}

