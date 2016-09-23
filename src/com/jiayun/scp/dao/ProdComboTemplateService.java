package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ProdComboTemplate;

@Repository("pcts")
public class ProdComboTemplateService implements DaoService<ProdComboTemplate> {

	@Autowired
	DAO<ProdComboTemplate> dao;
	
	@Override
	@Transactional
	public List<ProdComboTemplate> getAll() {
		return null;
	}

	@Override
	@Transactional
	public ProdComboTemplate getById(Integer id) {
		return dao.getById(ProdComboTemplate.class, id);
	}

	@Override
	@Transactional
	public ProdComboTemplate getByName(String nameString) {
		return null;
	}

	@Override
	@Transactional
	public ProdComboTemplate getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ProdComboTemplate save(ProdComboTemplate t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ProdComboTemplate update(ProdComboTemplate t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ProdComboTemplate delById(Integer id) {
		return dao.delById(ProdComboTemplate.class, id);
	}

	@Override
	@Transactional
	public ProdComboTemplate del(ProdComboTemplate t) {
		return dao.del(t);
	}
	
}
