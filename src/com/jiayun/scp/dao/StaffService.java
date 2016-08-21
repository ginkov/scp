package com.jiayun.scp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.Staff;

@Service
@Configuration
public class StaffService implements DaoService<Staff> {
	
	@Autowired
	private DAO<Staff> dao;
	
	@Autowired
	private SessionFactory sf;

	@Override
	@Transactional
	public List<Staff> getAll() {
		return dao.getAll(Staff.class);
	}

	@Override
	@Transactional
	public Staff getById(Integer id) {
		return dao.getById(Staff.class, id);
	}
	
	@Transactional 
	public Staff getByName(String name) {
		Session session = sf.getCurrentSession();
		Criteria cr = session.createCriteria(Staff.class).add(Restrictions.eq("name", name));
		return (Staff)cr.uniqueResult();
	}

	@Override
	@Transactional
	public Staff save(Staff t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public Staff update(Staff t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public Staff delById(Integer id) {
		return dao.delById(Staff.class, id);
	}

	@Override
	@Transactional
	public Staff getByUniqueString(String fieldName, String value) {
		return dao.getByName(Staff.class, fieldName, value);
	}

	@Override
	@Transactional
	public Staff del(Staff t) {
		return dao.del(t);
	}

	@Bean
	public StaffService ss() {
		return new StaffService();
	}
}