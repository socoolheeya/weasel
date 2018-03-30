package com.weasel.user.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.weasel.user.service.UserService;
import com.weasel.util.CommandMap;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping(value="/userList.do")
	public String userList(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		return "user/userList";
	}
	
	@RequestMapping(value="/userView.do")
	public String userView(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		return "user/userView";
	}
	
	@RequestMapping(value="/userEdit.do")
	public String userEdit(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		return "user/userEdit";
	}
	
	@RequestMapping(value="/userInsert.json", method=RequestMethod.POST)
	@ResponseBody
	public void userInsertJson(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		userService.userInsert(cmMap.getMap());
	}
	
	@RequestMapping(value="/userUpdate.json", method=RequestMethod.POST)
	@ResponseBody
	public void userUpdateJson(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		userService.userUpdate(cmMap.getMap());
	}
	
	@RequestMapping(value="/userDelete.json", method=RequestMethod.POST)
	@ResponseBody
	public void userDeleteJson(CommandMap cmMap, Model model) throws Exception {
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		log.debug(">>> " + Thread.currentThread().getStackTrace()[1].getMethodName() + " : " + cmMap.getMap());
		log.debug("==============================================================================================");
		log.debug("==============================================================================================");
		
		userService.userDelete(cmMap.getMap());
	}
}
