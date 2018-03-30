package com.weasel.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.weasel.user.dao.UserDao;
import com.weasel.util.UMap;

@Service("userService")
public class UserService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UMap> userList(UMap paramMap) throws Exception {
		return userDao.list(paramMap);
	}

	public UMap userView(UMap paramMap) throws Exception {
		return userDao.view(paramMap);
	}

	public UMap userViewByUserName(String username) throws Exception {
		return userDao.viewByUserName(username);
	}

	public int userInsert(UMap paramMap) throws Exception {
		String rawPassword = paramMap.getStr("password");
		String encodedPassword = passwordEncoder.encode(rawPassword);
		paramMap.put("password", encodedPassword);

		return userDao.insert(paramMap);
	}

	public int userUpdate(UMap paramMap) throws Exception {
		return userDao.update(paramMap);
	}

	public int userDelete(UMap paramMap) throws Exception {
		return userDao.delete(paramMap);
	}
	
}
