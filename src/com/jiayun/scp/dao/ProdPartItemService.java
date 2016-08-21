package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdPartItem;

@Service
@Configuration
public class ProdPartItemService implements DaoService<ProdPartItem> {
	@Autowired
	private DAO<ProdPartItem> dao;

	@Override
	@Transactional
	public List<ProdPartItem> getAll() {
		return dao.getAll(ProdPartItem.class);
	}

	@Override
	@Transactional
	public ProdPartItem getById(Integer id) {
		return dao.getById(ProdPartItem.class, id);
	}

	@Override
	@Transactional
	public ProdPartItem getByName(String nameString) {
		// we don't have getByName for part items.
		return null;
	}

	@Override
	@Transactional
	public ProdPartItem save(ProdPartItem t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdPartItem update(ProdPartItem t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdPartItem delById(Integer id) {
		return dao.delById(ProdPartItem.class, id);
	}

	@Bean
	public ProdPartItemService ppis() {
		return new ProdPartItemService();
	}

	@Override
	public ProdPartItem getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ProdPartItem del(ProdPartItem t) {
		return dao.del(t);
	}

}
