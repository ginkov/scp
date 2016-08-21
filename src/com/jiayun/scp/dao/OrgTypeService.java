package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.OrgType;

@Service
@Configuration
public class OrgTypeService implements DaoService<OrgType> {
	
	@Autowired
	private DAO<OrgType> dao;

	@Override
	@Transactional
	public List<OrgType> getAll() {
		return dao.getAll(OrgType.class);
	}

	@Override
	@Transactional
	public OrgType getById(Integer id) {
		return dao.getById(OrgType.class, id);
	}

	@Override
	@Transactional
	public OrgType getByName(String name) {
		return dao.getByName(OrgType.class, "name", name);
	}

	@Override
	@Transactional
	public OrgType save(OrgType t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public OrgType update(OrgType t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public OrgType delById(Integer id) {
		return dao.delById(OrgType.class, id);
	}
	
	@Bean
	public OrgTypeService ots() {
		return new OrgTypeService();
	}

	@Override
	public OrgType getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public OrgType del(OrgType t) {
		return dao.del(t);
	}

}
