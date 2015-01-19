package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.FundPriceHistoryBean;
import databeans.TransactionBean;

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
			sortByDateDsc(history);
			return history[0].getPrice_date();
		}
	}
	
	public double findLatestPrice(int fund_id) throws RollbackException {
		FundPriceHistoryBean[] history = match(MatchArg.equals("fund_id", fund_id));
		if (history == null || history.length == 0) {
			return -1;
		} else {
			sortByDateDsc(history);
			return history[0].getPrice();
		}
	}
	
	public FundPriceHistoryBean[] findAllPrices(int fund_id) throws RollbackException {
		FundPriceHistoryBean[] history = match(MatchArg.equals("fund_id", fund_id));
		if (history == null || history.length == 0) {
			return null;
		} else {
			sortByDateAsc(history);
			return history;
		}
	}
	
	public static void sortByDateDsc(FundPriceHistoryBean[] a ) {
		Arrays.sort(a, new Comparator<FundPriceHistoryBean>() {
			@Override
			public int compare(FundPriceHistoryBean o1, FundPriceHistoryBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o2.getPrice_date().compareTo(o1.getPrice_date());
			}
		});
	}
	
	public static void sortByDateAsc(FundPriceHistoryBean[] a ) {
		Arrays.sort(a, new Comparator<FundPriceHistoryBean>() {
			@Override
			public int compare(FundPriceHistoryBean o1, FundPriceHistoryBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o1.getPrice_date().compareTo(o2.getPrice_date());
			}
		});
	}
}
