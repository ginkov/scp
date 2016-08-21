package com.jiayun.scp.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.YearExpense;

@Component
public class ExpRecordUtil {
	
	@Autowired
	private DaoService<YearExpense> yes;
	
	public synchronized String genSN() {
		@SuppressWarnings("deprecation")
		int year = new Date().getYear() + 1900;
		int expNum = 0;

		YearExpense ye = yes.getById(year);
		if(ye == null) {
			expNum = 1;
			ye = new YearExpense();
			ye.setYear(year);
		}
		else {
			expNum = ye.getExpNum() + 1;
		}
		ye.setExpNum(expNum);
		yes.update(ye);
		return year + String.format("%06d", expNum);
	}
}
