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
			sort(history);
			return history[0].getPrice_date();
		}
	}
	
	public static void sort(FundPriceHistoryBean[] a ) {
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
}
