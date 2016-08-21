package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.YearExpense;

@Service
@Configuration
public class YearExpenseService implements DaoService<YearExpense> {
	
	@Autowired
	private DAO<YearExpense> dao;

	@Override
	public List<YearExpense> getAll() {
		return null;
	}

	@Override
	@Transactional
	public YearExpense getById(Integer id) {
		return dao.getById(YearExpense.class, id);
	}
	
	@Override
	public YearExpense getByName(String name) {
		// year order does not have an unique string identifier.
		return null;
	}
	@Override
	@Transactional
	public YearExpense save(YearExpense t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public YearExpense update(YearExpense t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public YearExpense delById(Integer id) {
		return dao.getById(YearExpense.class, id);
	}

	@Override
	public YearExpense getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public YearExpense del(YearExpense t) {
		return dao.del(t);
	}

	@Bean
	public YearExpenseService yes() {
		return new YearExpenseService();
	}
}