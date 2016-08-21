package com.jiayun.scp.dao;

import java.util.List;

public interface DAO <T> {
	
	public List<T> getAll(Class<T> type);
	public T getById(Class<T> type, Integer id);
	public T getByName(Class<T> type, String fieldName, String nameString);
	
	public T save(T t);
	public T update(T t);
	
	public T delById(Class<T> type, Integer id);
	public T del(T t);
}
