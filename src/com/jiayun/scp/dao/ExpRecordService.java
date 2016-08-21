package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ExpRecord;

@Service
@Configuration
public class ExpRecordService implements DaoService<ExpRecord> {

	@Autowired
	private DAO<ExpRecord> dao;

	@Override
	@Transactional
	public List<ExpRecord> getAll() {
		return dao.getAll(ExpRecord.class);
	}

	@Override
	@Transactional
	public ExpRecord getById(Integer id) {
		return dao.getById(ExpRecord.class, id);
	}

	@Override
	@Transactional
	public ExpRecord getByName(String nameString) {
		return dao.getByName(ExpRecord.class, "name", nameString);
	}

	@Override
	@Transactional
	public ExpRecord getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ExpRecord save(ExpRecord t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ExpRecord update(ExpRecord t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ExpRecord delById(Integer id) {
		return dao.delById(ExpRecord.class, id);
	}

	@Override
	@Transactional
	public ExpRecord del(ExpRecord t) {
		return dao.del(t);
	}
	
	@Bean
	public ExpRecordService ers() {
		return new ExpRecordService();
	}

}
