package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.OrderItem;

@Service
@Configuration
public class OrderItemService implements DaoService<OrderItem> {
	
	@Autowired
	private DAO<OrderItem> dao;

	@Override
	@Transactional
	public List<OrderItem> getAll() {
		return dao.getAll(OrderItem.class);
	}

	@Override
	@Transactional
	public OrderItem getById(Integer id) {
		return dao.getById(OrderItem.class, id);
	}

	@Override
	public OrderItem getByName(String nameString) {
		// order item doesn't have a name.
		return null;
	}

	@Override
	@Transactional
	public OrderItem save(OrderItem t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public OrderItem update(OrderItem t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public OrderItem delById(Integer id) {
		return dao.delById(OrderItem.class, id);
	}
	
	@Bean
	public OrderItemService ois() {
		return new OrderItemService();
	}

	@Override
	public OrderItem getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public OrderItem del(OrderItem t) {
		return dao.del(t);
	}
}
