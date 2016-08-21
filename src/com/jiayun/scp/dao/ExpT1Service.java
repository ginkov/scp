package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ExpT1;

@Service
@Configuration
public class ExpT1Service implements DaoService<ExpT1> {

	@Autowired
	private DAO<ExpT1> dao;

	@Override
	@Transactional
	public List<ExpT1> getAll() {
		return dao.getAll(ExpT1.class);
	}

	@Override
	@Transactional
	public ExpT1 getById(Integer id) {
		return dao.getById(ExpT1.class, id);
	}

	@Override
	@Transactional
	public ExpT1 getByName(String nameString) {
		return dao.getByName(ExpT1.class, "name", nameString);
	}

	@Override
	@Transactional
	public ExpT1 getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ExpT1 save(ExpT1 t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ExpT1 update(ExpT1 t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ExpT1 delById(Integer id) {
		return dao.delById(ExpT1.class, id);
	}

	@Override
	@Transactional
	public ExpT1 del(ExpT1 t) {
		return dao.del(t);
	}
	
	@Bean
	public ExpT1Service et1s() {
		return new ExpT1Service();
	}

}
