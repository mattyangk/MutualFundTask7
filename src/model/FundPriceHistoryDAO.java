package model;

import java.util.Date;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.FundBean;
import databeans.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean>{
	public FundPriceHistoryDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(FundPriceHistoryBean.class, tableName, connectionPool);
		}
	public FundPriceHistoryBean getFundPriceHistoryByDate(Date date)
			throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("date", date));
		if (funds.length != 1) {
			System.out.println("not such fund");
		}
		return funds[0];
	}
}
