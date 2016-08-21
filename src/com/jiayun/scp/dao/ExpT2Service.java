package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ExpT2;

@Service
@Configuration
public class ExpT2Service implements DaoService<ExpT2> {

	@Autowired
	private DAO<ExpT2> dao;

	@Override
	@Transactional
	public List<ExpT2> getAll() {
		return dao.getAll(ExpT2.class);
	}

	@Override
	@Transactional
	public ExpT2 getById(Integer id) {
		return dao.getById(ExpT2.class, id);
	}

	@Override
	@Transactional
	public ExpT2 getByName(String nameString) {
		return dao.getByName(ExpT2.class, "name", nameString);
	}

	@Override
	@Transactional
	public ExpT2 getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ExpT2 save(ExpT2 t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ExpT2 update(ExpT2 t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ExpT2 delById(Integer id) {
		return dao.delById(ExpT2.class, id);
	}

	@Override
	@Transactional
	public ExpT2 del(ExpT2 t) {
		return dao.del(t);
	}
	
	@Bean
	public ExpT2Service et2s() {
		return new ExpT2Service();
	}

}
