package com.jiayun.scp.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiayun.scp.model.ServiceRecord;

@Service
@Configuration
public class ServiceRecordService implements DaoService<ServiceRecord> {
	
	@Autowired
	private DAO<ServiceRecord> dao;

	@Override
	@Transactional
	public List<ServiceRecord> getAll() {
		return dao.getAll(ServiceRecord.class);
	}

	@Override
	@Transactional
	public ServiceRecord getById(Integer id) {
		return dao.getById(ServiceRecord.class, id);
	}


	@Override
	@Transactional
	public ServiceRecord save(ServiceRecord t) {
		return dao.save(t);
	}

	@Override
	@Transactional
	public ServiceRecord update(ServiceRecord t) {
		return dao.update(t);
	}

	@Override
	@Transactional
	public ServiceRecord delById(Integer id) {
		return dao.delById(ServiceRecord.class, id);
	}

	@Bean
	public ServiceRecordService srs() {
		return new ServiceRecordService();
	}

	@Override
	public ServiceRecord getByName(String nameString) {
		// service record does not have a unique string identifer.
		return null;
	}

	@Override
	public ServiceRecord getByUniqueString(String fieldName, String value) {
		return null;
	}

	@Override
	@Transactional
	public ServiceRecord del(ServiceRecord t) {
		return dao.del(t);
	}
}
