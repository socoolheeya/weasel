package com.weasel.user;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.weasel.user.dao.UserDao;
import com.weasel.util.UMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	public void insertUserTest() throws Exception {
		for(int i = 0; i < 100; i++) {
			UMap user = new UMap();
			user.put("email", "testUser" + i +"@weasel.com");
			user.put("password", "pw" + i);
			user.put("name", "test" + i);
			
			userDao.insert(user);
		}
	}
	
	@Test
	public void testUser() throws Exception {
		UMap user = new UMap();
		user.put("email", "testUser2@weasel.com");
		Optional<UMap> result = Optional.ofNullable(userDao.view(user));
	}

}
