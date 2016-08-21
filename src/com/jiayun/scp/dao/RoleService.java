package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.Role;

@Service
@Configuration
public class RoleService implements DaoService<Role> {
	
	@Autowired
	private DAO<Role> dao;

	@Override
	@Transactional
	public List<Role> getAll() {
		return dao.getAll(Role.class);
	}

	@Override
	@Transactional
	public Role getById(Integer id) {
		return dao.getById(Role.class, id);
	}

	@Override
	@Transactional
	public Role getByName(String role ) {
		return dao.getByName(Role.class, "role", role);
	}

	@Override
	@Transactional
	public Role save(Role t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public Role update(Role t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public Role delById(Integer id) {
		return dao.delById(Role.class, id);
	}
	
	@Bean
	public RoleService rs() {
		return new RoleService();
	}

	@Override
	@Transactional
	public Role getByUniqueString(String fieldName, String value) {
		return dao.getByName(Role.class, fieldName, value);
	}

	@Override
	@Transactional
	public Role del(Role t) {
		return dao.del(t);
	}
}
