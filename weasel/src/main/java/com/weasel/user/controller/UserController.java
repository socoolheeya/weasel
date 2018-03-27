package com.weasel.user.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.weasel.user.service.UserService;

@Controller
public class UserController {

	@Resource(name="userService")
	private UserService userService;
}
