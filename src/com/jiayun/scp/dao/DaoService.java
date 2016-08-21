package com.jiayun.scp.dao;

import java.util.List;

public interface DaoService<T> {
	
	public List<T> getAll();
	public T getById(Integer id);
	public T getByName(String nameString);
	public T getByUniqueString(String fieldName, String value);
	
	public T save(T t);
	public T update(T t);
	
	public T delById(Integer id);
	public T del(T t);
}