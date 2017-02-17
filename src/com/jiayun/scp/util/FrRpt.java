package com.jiayun.scp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jiayun.scp.dao.DaoService;
import com.jiayun.scp.model.ExpRecord;
import com.jiayun.scp.model.FinanceAction;
import com.jiayun.scp.model.FinanceRecord;
import com.jiayun.scp.model.FrWeek;
import com.jiayun.scp.model.SalesOrder;

@Service
public class FrRpt {

	private static final long WEEK_MILLIS = 7*24*3600*1000L;
	private static final long DAY_MILLIS = 24*3600*1000L;
	
	private long minWeekMillis;
	private long maxWeekMillis;
	private long minDateMillis;
	private long maxDateMillis;
	
	private FrWeek[] frWeeks;
	private List<FrWeek> frWeeksCompressed;
	
	private double balance;
	private double totalIn, totalOut;
	private int numWeeks;
	
	@Autowired
	private DaoService<SalesOrder> sos;
	@Autowired
	private DaoService<ExpRecord> ers;
	
	public FrRpt() {
		minWeekMillis = 0L;
		maxWeekMillis = 0L;
		minDateMillis = Long.MAX_VALUE;
		maxDateMillis = 0L;
		balance = 0.0;
		totalIn = 0.0;
		totalOut= 0.0;
	}
	
	public void go() {
		init();
		genFrWeeks();
		sumFrWeeks();
		compressRpt();
	}
	
	private void init() {
		minWeekMillis = 0L;
		maxWeekMillis = 0L;
		minDateMillis = Long.MAX_VALUE;
		maxDateMillis = 0L;
		balance = 0.0;
		totalIn = 0.0;
		totalOut= 0.0;
	}
	
	private void sumFrWeeks() {
		
		for(int i = 0; i < numWeeks; i++) {
			double inAmt = 0.0;
			double outAmt = 0.0;
			double change = 0.0;
			for(FinanceRecord fr: frWeeks[i].getIn()) {
				if(fr.getAction() == FinanceAction.SALE) {
					inAmt += fr.getAmount();
				}
			}
			for(FinanceRecord fr: frWeeks[i].getOut()) {
				outAmt += fr.getAmount();
			}
			change = inAmt - outAmt;
			totalIn += inAmt;
			totalOut+= outAmt;
			balance += change;
			
			frWeeks[i].setBal(balance);
			frWeeks[i].setChangeAmt(change);
			frWeeks[i].setInAmount(inAmt);
			frWeeks[i].setOutAmount(outAmt);
		}
		
	}
	
	private void genFrWeeks() {
		List<FinanceRecord> frList = genFrList();
		minWeekMillis = roundToWeek(minDateMillis);
		maxWeekMillis = roundToWeek(maxDateMillis);
		numWeeks = (int)((maxWeekMillis - minWeekMillis)/WEEK_MILLIS) + 1;
		frWeeks = new FrWeek[numWeeks];
		for(int i = 0; i < numWeeks; i++) {
			FrWeek f = new FrWeek();
			f.setDate(new Date(minWeekMillis+i*WEEK_MILLIS));
			frWeeks[i] = f;
		}
		for(FinanceRecord fr : frList) {
			int i = genWeekPos(fr.getDate());
			if(i >=0 && i<numWeeks) {
				switch(fr.getAction()) {
				case EXPENSE:
					frWeeks[i].getOut().add(fr);
					break;
				case SALE:
					frWeeks[i].getIn().add(fr);
					break;
				case AR:
					frWeeks[i].getIn().add(fr);
					break;
				case INVEST_GOT:
					frWeeks[i].getIn().add(fr);
					break;
				}
			}
		}
	}
	
	private int genWeekPos(Date date) {
		long t = roundToWeek(date.getTime());
		return (int)((t-minWeekMillis)/WEEK_MILLIS);
	}
	private List<FinanceRecord> genFrList() {
		List<FinanceRecord> frList = new ArrayList<>();
		
		for(ExpRecord er: ers.getAll()) {
			FinanceRecord fr = new FinanceRecord();
			fr.fromExpRecord(er);
			frList.add(fr);
			long t = fr.getDate().getTime();
			if(minDateMillis > t) {
				minDateMillis = t;
			}
			if(maxDateMillis < t) {
				maxDateMillis = t;
			}
		}
		
		for(SalesOrder so: sos.getAll()) {
			FinanceRecord fr = new FinanceRecord();
			fr.fromSalesOrder(so);
			frList.add(fr);
			long t = fr.getDate().getTime();
			if(minDateMillis > t) {
				minDateMillis = t;
			}
			if(maxDateMillis < t) {
				maxDateMillis = t;
			}
		}
		minDateMillis = roundToDay(minDateMillis);
		maxDateMillis = roundToDay(maxDateMillis);
		return frList;
	}
	
	private void compressRpt() {
		frWeeksCompressed = new ArrayList<FrWeek>();
		for(FrWeek frWeek : frWeeks) {
			if(frWeek.getIn().size() > 0 || frWeek.getOut().size() > 0) {
				frWeeksCompressed.add(frWeek);
			}
		}
		Collections.reverse(frWeeksCompressed);
	}
	
	private long roundToDay(long t) {
		long residual = t % DAY_MILLIS;
		return t - residual;
	}
	
	private long roundToWeek(long t) {
		long residual = (t - 1341100800000L) % WEEK_MILLIS;
		return t - residual + WEEK_MILLIS;
	}

	public long getMinWeekMillis() {
		return minWeekMillis;
	}

	public long getMaxWeekMillis() {
		return maxWeekMillis;
	}

	public List<FrWeek> getFrWeeksCompressed(){
		return frWeeksCompressed;
	}

	public double getBalance() {
		return balance;
	}

	public double getTotalIn() {
		return totalIn;
	}

	public double getTotalOut() {
		return totalOut;
	}

	public int getNumWeeks() {
		return numWeeks;
	}
	
	
	
}
