package model;

import java.util.Collections;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean>{
	public FundPriceHistoryDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(FundPriceHistoryBean.class, tableName, connectionPool);
		}
	
	public Date findLatestDate() throws RollbackException {
		FundPriceHistoryBean[] history = match();
		if (history == null || history.length == 0) {
			return null;
		} else {
			sort(history);
			return history[0].getPrice_date();
		}
	}
	
	public static void sort(FundPriceHistoryBean[] history){
		int len=0;
		for(len=0;len<history.length&&history[len]!=null;len++){}
		for (int m=0; m<len; m++) {
			for (int n=m+1; n<len; n++) {
				if (history[m].getPrice_date().compareTo(history[n].getPrice_date())<0) {
				FundPriceHistoryBean temp = history[m];
				history[m] = history[n];
				history[n] = temp;
				}
			}
		 }
	}
}
