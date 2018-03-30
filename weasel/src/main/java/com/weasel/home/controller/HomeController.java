	package com.weasel.home.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weasel.user.service.UserService;
import com.weasel.util.CommandMap;
import com.weasel.util.UMap;

@Controller
public class HomeController {
	
	@Resource(name="userService")
	private UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	
	@RequestMapping(value="/")
	public String index(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		List<UMap> userList = userService.userList(cmMap.getMap());
		
		model.addAttribute("userList", userList);
		
		return "index";
	}
	
	@RequestMapping(value= {"/home.do"})
	public String home(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		List<UMap> userList = userService.userList(cmMap.getMap());
		
		model.addAttribute("userList", userList);
		
		return "home/home";
	}
	
	@RequestMapping("/login.do")
	public String login(CommandMap cmMap) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		return "home/login";
	}
	
	@RequestMapping(value="/login.json", method=RequestMethod.POST)
	@ResponseBody
	public void loginJson(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		List<UMap> userList = userService.userList(cmMap.getMap());
		
		model.addAttribute("userList", userList);
	}
	
	@RequestMapping("/logout.do")
	public void logout(CommandMap cmMap, HttpSession session) throws Exception {
		log.debug("=======================================================================");
		log.debug("=======================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[0].getMethodName());
		log.debug("=======================================================================");
		log.debug("=======================================================================");
	}
	
}
