package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdPart;

@Service
@Configuration
public class ProdPartService implements DaoService<ProdPart> {

	@Autowired
	private DAO<ProdPart> dao;

	@Override
	@Transactional
	public List<ProdPart> getAll() {
		return dao.getAll(ProdPart.class);
	}

	@Override
	@Transactional
	public ProdPart getById(Integer id) {
		return dao.getById(ProdPart.class, id);
	}

	@Override
	@Transactional
	public ProdPart getByName(String nameString) {
		return dao.getByName(ProdPart.class, "name", nameString);
	}

	@Override
	@Transactional
	public ProdPart save(ProdPart t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdPart update(ProdPart t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdPart delById(Integer id) {
		return dao.delById(ProdPart.class, id);
	}

	@Override
	@Transactional
	public ProdPart del(ProdPart t) {
		return dao.del(t);
	}

	@Override
	@Transactional
	public ProdPart getByUniqueString(String fieldName, String value) {
		return dao.getByName(ProdPart.class, fieldName, value);
	}

	@Bean
	public ProdPartService pps() {
		return new ProdPartService();
	}
}
