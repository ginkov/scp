package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.UserSaleType;

@Service
@Configuration

public class UserSaleTypeService implements DaoService<UserSaleType> {

	@Autowired
	private DAO<UserSaleType> dao;
	
	@Override
	@Transactional
	public List<UserSaleType> getAll() {
		return dao.getAll(UserSaleType.class);
	}

	@Override
	@Transactional
	public UserSaleType getById(Integer id) {
		return dao.getById(UserSaleType.class, id);
	}

	@Override
	@Transactional
	public UserSaleType getByName(String name) {
		return dao.getByName(UserSaleType.class, "name", name);
	}

	@Override
	@Transactional
	public UserSaleType save(UserSaleType t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public UserSaleType update(UserSaleType t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public UserSaleType delById(Integer id) {
		return dao.delById(UserSaleType.class, id);
	}

	@Override
	@Transactional
	public UserSaleType getByUniqueString(String fieldName, String value) {
		return dao.getByName(UserSaleType.class, fieldName, value);
	}

	@Override
	@Transactional
	public UserSaleType del(UserSaleType t) {
		return dao.del(t);
	}

	@Bean
	public UserSaleTypeService usts() {
		return new UserSaleTypeService();
	}
}
