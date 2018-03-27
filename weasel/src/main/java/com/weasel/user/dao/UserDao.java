package com.weasel.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.weasel.util.UMap;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSessionTemplate dao;
	
	public List<UMap> list(UMap paramMap) throws Exception {
		return dao.selectList("user.list", paramMap);
	}
	
	public UMap view(UMap paramMap) throws Exception {
		return dao.selectOne("user.view", paramMap);
	}
	
	public int insert(UMap paramMap) throws Exception {
		return dao.insert("user.insert", paramMap);
	}
	
	public int update(UMap paramMap) throws Exception {
		return dao.update("user.update", paramMap);
	}
	
	public int delete(UMap paramMap) throws Exception {
		return dao.update("user.delete", paramMap);
	}
}
