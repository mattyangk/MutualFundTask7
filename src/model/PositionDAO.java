package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.PositionBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;

public class PositionDAO extends GenericDAO<PositionBean> {
	public PositionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(PositionBean.class, tableName, connectionPool);
	}
	
	public PositionBean[] getPositionsByCustomerId(int id)
			throws RollbackException {

		PositionBean[] positions = match(MatchArg.equals("customer_id",
				id));
		return positions;
	}

	public void updateAvailableShares(int fund_id, int customer_id, double sellingShares)
			throws RollbackException, AmountOutOfBoundException {
		try {
			Transaction.begin();
			PositionBean position = read(fund_id, customer_id);
			if (position == null) {
				throw new RollbackException("This position:" + "fund_id:"
						+ fund_id + "customer_id" + customer_id
						+ " does not exist");
			} else {
				double availableShares = position.getAvailable_shares();
				double newAvailableShares = availableShares - sellingShares;
				if (newAvailableShares < 0)
					throw new AmountOutOfBoundException(availableShares,
							sellingShares);
				else {
					position.setAvailable_shares(newAvailableShares);
					update(position);
				}
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void updateShares(int fund_id, int customer_id, double shares)
			throws RollbackException, AmountOutOfBoundException {
		try {
			Transaction.begin();
			PositionBean position = read(fund_id, customer_id);
			if (position == null) {
				throw new RollbackException("This position:" + "fund_id:"
						+ fund_id + "customer_id" + customer_id
						+ " does not exist");
			} else {
				
					position.setShares(shares);
					position.setAvailable_shares(shares);
					update(position);
				
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}
