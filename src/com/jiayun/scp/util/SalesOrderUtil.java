package com.jiayun.scp.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.YearOrder;

@Service
public class SalesOrderUtil {
	
	@Autowired
	private DaoService<YearOrder> yos;
	
	public synchronized String genSN() {
		@SuppressWarnings("deprecation")
		int year = new Date().getYear() + 1900;

		YearOrder yo = yos.getById(year);
		int orderNumber = yo.getOrdersNumber() + 1;

		yo.setOrdersNumber(orderNumber);
		yos.update(yo);

		return year + String.format("%06d", orderNumber);
	}

}
