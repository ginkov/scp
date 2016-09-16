package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.Invoice;

@Repository("invs")
public class InvoiceService implements DaoService<Invoice> {

	@Autowired
	private DAO<Invoice> dao;

	@Override
	@Transactional
	public List<Invoice> getAll() {
		return dao.getAll(Invoice.class);
	}

	@Override
	@Transactional
	public Invoice getById(Integer id) {
		return dao.getById(Invoice.class, id);
	}

	@Override
	@Transactional
	public Invoice getByName(String nameString) {
		return dao.getByName(Invoice.class, "sn", nameString);
	}

	@Override
	@Transactional
	public Invoice getByUniqueString(String fieldName, String value) {
		return dao.getByName(Invoice.class, fieldName, value);
	}

	@Override
	@Transactional
	public Invoice save(Invoice t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public Invoice update(Invoice t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public Invoice delById(Integer id) {
		return dao.delById(Invoice.class, id);
	}

	@Override
	@Transactional
	public Invoice del(Invoice t) {
		return dao.del(t);
	}

}
