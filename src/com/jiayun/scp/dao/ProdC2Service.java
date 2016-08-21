package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdC2;

@Service
@Configuration
public class ProdC2Service implements DaoService<ProdC2> {
	@Autowired
	private DAO<ProdC2> dao;

	@Override
	@Transactional
	public List<ProdC2> getAll() {
		return dao.getAll(ProdC2.class);
	}

	@Override
	@Transactional
	public ProdC2 getById(Integer id) {
		return dao.getById(ProdC2.class, id);
	}

	@Override
	@Transactional
	public ProdC2 getByName(String nameString) {
		return dao.getByName(ProdC2.class, "name", nameString);
	}

	@Override
	@Transactional
	public ProdC2 save(ProdC2 t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdC2 update(ProdC2 t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdC2 delById(Integer id) {
		return dao.delById(ProdC2.class, id);
	}

	@Bean
	public ProdC2Service pc2s() {
		return new ProdC2Service();
	}

	@Override
	public ProdC2 getByUniqueString(String fieldName, String value) {
		return dao.getByName(ProdC2.class, fieldName, value);
	}

	@Override
	@Transactional
	public ProdC2 del(ProdC2 t) {
		return dao.del(t);
	}
}
