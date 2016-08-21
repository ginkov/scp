package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.YearOrder;

@Service
@Configuration
public class YearOrderService implements DaoService<YearOrder> {
	
	@Autowired
	private DAO<YearOrder> dao;

	@Override
	public List<YearOrder> getAll() {
		return null;
	}

	@Override
	@Transactional
	public YearOrder getById(Integer id) {
		return dao.getById(YearOrder.class, id);
	}
	
	@Override
	public YearOrder getByName(String name) {
		// year order does not have an unique string identifier.
		return null;
	}
	@Override
	@Transactional
	public YearOrder save(YearOrder t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public YearOrder update(YearOrder t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public YearOrder delById(Integer id) {
		return dao.getById(YearOrder.class, id);
	}

	@Override
	public YearOrder getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public YearOrder del(YearOrder t) {
		return dao.del(t);
	}

	@Bean
	public YearOrderService yos() {
		return new YearOrderService();
	}
}