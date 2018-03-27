package com.weasel.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.weasel.user.dao.UserDao;
import com.weasel.util.UMap;

@Service("userService")
public class UserService {
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public List<UMap> userList(UMap paramMap) throws Exception {
		return userDao.list(paramMap);
	}
	
	public int userInsert(UMap paramMap) throws Exception {
		return userDao.insert(paramMap);
	}
}
