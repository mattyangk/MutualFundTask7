package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import model.CustomerDAO;
import databeans.CustomerBean;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean>{
	private CustomerDAO customerDAO;
	
	public TransactionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(TransactionBean.class, tableName, connectionPool);
	}
	
	public TransactionBean[] readAllPendingTransaction() throws RollbackException {

		TransactionBean[] pendingTransaction = match(MatchArg.equals("is_complete", "faulse"));

		return pendingTransaction;
	}
	
/*	public void updateBalance(TransactionBean transaction) throws RollbackException {
		try {
			Transaction.begin();
			//customerDAO
			//test
			TransactionBean currentTransaction = read(transaction.getTransaction_id());
			CustomerBean customer = read(transaction.getCustomer_id());

			if (currentTransaction == null) {
				throw new RollbackException("This transaction:  " + transaction.getTransaction_id()
						+ "   no longer exists");
			} 
			if (customer == null) {
				throw new RollbackException("This customer:" + transaction.getCustomer_id()
						+ "   no longer exists");
			}else {
				int balance = customer.getBalance();
				int 
				a[0].setClickCount(c);
				update(a[0]);
			}

			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}*/
}
