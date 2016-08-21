package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdC1;

@Service
@Configuration
public class ProdC1Service implements DaoService<ProdC1> {

	@Autowired
	private DAO<ProdC1> dao;

	@Override
	@Transactional
	public List<ProdC1> getAll() {
		return dao.getAll(ProdC1.class);
	}

	@Override
	@Transactional
	public ProdC1 getById(Integer id) {
		return dao.getById(ProdC1.class, id);
	}

	@Override
	@Transactional
	public ProdC1 getByName(String nameString) {
		return dao.getByName(ProdC1.class, "name", nameString);
	}

	@Override
	@Transactional
	public ProdC1 save(ProdC1 t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdC1 update(ProdC1 t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdC1 delById(Integer id) {
		return dao.delById(ProdC1.class, id);
	}
	
	@Bean
	public ProdC1Service pc1s() {
		return new ProdC1Service();
	}

	@Override
	@Transactional
	public ProdC1 getByUniqueString(String fieldName, String value) {
		return dao.getByName(ProdC1.class, fieldName, value);
	}

	@Override
	@Transactional
	public ProdC1 del(ProdC1 t) {
		return dao.del(t);
	}
}
