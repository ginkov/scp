package com.jiayun.scp.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DaoImpl<T> implements DAO<T> {
	
	
	@Autowired
	private SessionFactory sf;

	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> typeClass) {
		return sf.getCurrentSession().createCriteria(typeClass).list();
	}

	@Override
	public T getById(Class<T> typeClass, Integer id) {
		return sf.getCurrentSession().load(typeClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T getByName(Class<T> type, String fieldName, String nameString) {
		Session session = sf.getCurrentSession();
		return (T)session.createCriteria(type).add(Restrictions.eq(fieldName, nameString)).uniqueResult();
	}

	@Override
	public T save(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
		return t;
	}

	@Override
	public T update(T t) {
		sf.getCurrentSession().saveOrUpdate(t);
		return t;
	}

	@Override
	public T delById(Class<T> classType, Integer id) {
		T old = getById(classType, id);
		sf.getCurrentSession().delete(old);
		return old;
	}

	@Override
	public T del(T t) {
		sf.getCurrentSession().delete(t);
		return t;
	}
	
	

}
