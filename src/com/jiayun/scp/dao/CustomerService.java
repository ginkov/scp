package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.Customer;
import com.jiayun.scp.model.CustomerAddress;

@Service
@Configuration
public class CustomerService implements DaoService<Customer> {
	
	@Autowired
	private DAO<Customer> dao;

	@Autowired
	private DAO<CustomerAddress> daoAddress;

	@Override
	@Transactional
	public List<Customer> getAll() {
		return dao.getAll(Customer.class);
	}

	@Override
	@Transactional
	public Customer getById(Integer id) {
		return dao.getById(Customer.class, id);
	}

	@Override
	@Transactional
	public Customer getByName(String nameString) {
		return dao.getByName(Customer.class, "name", nameString);
	}

	@Override
	@Transactional
	public Customer save(Customer t) {
		t.removeEmptyAddresses();
		for(CustomerAddress ca: t.getAddresses()) {
			daoAddress.save(ca);
		}
		return dao.save(t);
	}

	@Override
	@Transactional
	public Customer update(Customer t) {
		t.removeEmptyAddresses();
		for(CustomerAddress ca: t.getAddresses()) {
			daoAddress.update(ca);
		}
		return dao.update(t);
	}

	@Override
	@Transactional
	public Customer delById(Integer id) {
		return dao.delById(Customer.class, id);
	}
	
	@Bean
	public CustomerService cs() {
		return new CustomerService();
	}

	@Override
	@Transactional
	public Customer getByUniqueString(String fieldName, String value) {
		return dao.getByName(Customer.class, fieldName, value);
	}

	@Override
	@Transactional
	public Customer del(Customer t) {
		return dao.del(t);
	}

}
