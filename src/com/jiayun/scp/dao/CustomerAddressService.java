package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.CustomerAddress;

@Service
@Configuration
public class CustomerAddressService implements DaoService<CustomerAddress> {

	@Autowired
	private DAO<CustomerAddress> dao;

	@Override
	@Transactional
	public List<CustomerAddress> getAll() {
		return dao.getAll(CustomerAddress.class);
	}

	@Override
	@Transactional
	public CustomerAddress getById(Integer id) {
		return dao.getById(CustomerAddress.class, id);
	}

	@Override
	public CustomerAddress getByName(String name) {
		// because customer address does not have a "name".
		return null;
	}

	@Override
	@Transactional
	public CustomerAddress save(CustomerAddress t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public CustomerAddress update(CustomerAddress t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public CustomerAddress delById(Integer id) {
		return dao.delById(CustomerAddress.class, id);
	}
	
	@Bean
	public CustomerAddressService cas() {
		return new CustomerAddressService();
	}

	@Override
	public CustomerAddress getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public CustomerAddress del(CustomerAddress t) {
		return dao.del(t);
	}
	

}
