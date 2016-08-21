package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.Customer;
import com.jiayun.scp.model.OrderItem;
import com.jiayun.scp.model.SalesOrder;
import com.jiayun.scp.model.ServiceRecord;

@Service
@Configuration
public class SalesOrderService implements DaoService<SalesOrder> {
	
	@Autowired
	private DAO<SalesOrder> dao;
	
	@Autowired
	private DAO<OrderItem> daoItem;
	
	@Autowired
	private DAO<ServiceRecord> daoSR;
	
	@Autowired
	private DAO<Customer> daoCustomer;

	@Override
	@Transactional
	public List<SalesOrder> getAll() {
		return dao.getAll(SalesOrder.class);
	}

	@Override
	@Transactional
	public SalesOrder getById(Integer id) {
		return dao.getById(SalesOrder.class, id);
	}

	@Override
	@Transactional
	// actually it is get by serial number.
	public SalesOrder getByName(String sn) {
		return dao.getByName(SalesOrder.class, "sn", sn);
	}

	@Override
	@Transactional
	public SalesOrder save(SalesOrder t) {
		t.removeEmptyItems();
		for(OrderItem item : t.getItems()) {
			daoItem.save(item);
		}
		for(ServiceRecord sr: t.getServiceRecords()) {
			daoSR.save(sr);
		}
		daoCustomer.save(t.getCustomer());
		return dao.save(t);
	}

	@Override
	@Transactional
	public SalesOrder update(SalesOrder t) {
		t.removeEmptyItems();
		for(OrderItem item : t.getItems()) {
			daoItem.update(item);
		}
		for(ServiceRecord sr: t.getServiceRecords()) {
			daoSR.update(sr);
		}
		daoCustomer.update(t.getCustomer());
		return dao.update(t);
	}

	@Override
	@Transactional
	public SalesOrder delById(Integer id) {
		return dao.delById(SalesOrder.class, id);
	}

	@Bean
	public SalesOrderService sos() {
		return new SalesOrderService();
	}

	@Override
	@Transactional
	public SalesOrder getByUniqueString(String fieldName, String value) {
		return dao.getByName(SalesOrder.class, fieldName, value);
	}

	@Override
	@Transactional
	public SalesOrder del(SalesOrder t) {
		return dao.del(t);
	}

}
