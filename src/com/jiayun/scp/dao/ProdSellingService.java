package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdSelling;

@Service
@Configuration
public class ProdSellingService implements DaoService<ProdSelling> {

	@Autowired
	private DAO<ProdSelling> dao;

	@Override
	@Transactional
	public List<ProdSelling> getAll() {
		return dao.getAll(ProdSelling.class);
	}
	@Override
	@Transactional
	public ProdSelling getById(Integer id) {
		return dao.getById(ProdSelling.class, id);
	}

	@Override
	@Transactional
	public ProdSelling getByName(String nameString) {
		return dao.getByName(ProdSelling.class, "name", nameString);
	}

	@Override
	@Transactional
	public ProdSelling save(ProdSelling t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdSelling update(ProdSelling t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdSelling delById(Integer id) {
		return dao.delById(ProdSelling.class, id);
	}

	@Bean
	public ProdSellingService pss() {
		return new ProdSellingService();
	}
	@Override
	@Transactional
	public ProdSelling getByUniqueString(String fieldName, String value) {
		return dao.getByName(ProdSelling.class, fieldName, value);
	}
	@Override
	@Transactional
	public ProdSelling del(ProdSelling t) {
		return dao.del(t);
	}
}
