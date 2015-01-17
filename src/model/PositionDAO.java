package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.PositionBean;

public class PositionDAO extends GenericDAO<PositionBean>{
	public PositionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(PositionBean.class, tableName, connectionPool);
	}
	
	/*public PositionBean getFundByName(String fundname)
			throws RollbackException {
		FundBean[] funds = match(MatchArg.equals("name", fundname));
		if (funds.length != 1) {
			System.out.println("not such fund");
		}
		return funds[0];
	}*/

}
