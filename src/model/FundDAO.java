package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.FundBean;

public class FundDAO extends GenericDAO<FundBean> {

	public FundDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(FundBean.class, tableName, connectionPool);
	}

	public FundBean getFundById(int id) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("fund_id", id));
		if (funds.length != 1) {
			System.out.println("not such fund");
		}
		return funds[0];
	}
	
	public FundBean getFundByName(String fundname) throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("name", fundname));
		System.out.println("length: " + funds.length);
		if (funds.length != 1) {
			System.out.println("not such fund");
			return null;
		}
		return funds[0];
	}

	public FundBean[] getAllFunds() throws RollbackException {
		FundBean[] funds = match();
		if (funds.length == 0) {
			System.out.println("no fund");
		}
		return funds;
	}
}
