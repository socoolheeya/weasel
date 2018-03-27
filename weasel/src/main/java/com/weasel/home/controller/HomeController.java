	package com.weasel.home.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weasel.user.service.UserService;
import com.weasel.util.CommandMap;
import com.weasel.util.UMap;

@Controller
public class HomeController {
	
	@Resource(name="userService")
	private UserService userService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass()); 
	
	@RequestMapping(value= {"/", "/home.do"})
	public String home(CommandMap cmMap, Model model) throws Exception {
		log.debug("=======================================================================");
		log.debug("=======================================================================");
		log.debug(">>> " + Thread.currentThread().getName());
		log.debug("=======================================================================");
		log.debug("=======================================================================");
		
		
		List<UMap> list = userService.userList(cmMap.getMap());
		log.info("size : " + list.size());
		model.addAttribute("list", list);
		for(Map<String, Object> info : list) {
			log.info("email : " + ObjectUtils.toString(info.get("EMAIL")));
			log.info("password : " + ObjectUtils.toString(info.get("PASSWORD")));
			log.info("name : " + ObjectUtils.toString(info.get("NAME")));
			log.info("delyn : " + ObjectUtils.toString(info.get("DELYN")));
		}
		
		return "home/home";
	}
	
	@RequestMapping("/login.do")
	public String login(CommandMap cmMap) throws Exception {
		return "home/login";
	}
	
}
