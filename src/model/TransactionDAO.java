package model;

import java.util.Arrays;
import java.util.Comparator;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import model.CustomerDAO;
import databeans.TransactionBean;

public class TransactionDAO extends GenericDAO<TransactionBean>{
	
	public TransactionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(TransactionBean.class, tableName, connectionPool);
	}
	
	public TransactionBean[] readPendingTransInOrder() throws RollbackException {

		TransactionBean[] pendingTransaction = match(MatchArg.equals("is_complete", false));
		sort(pendingTransaction);
		return pendingTransaction;
	}
	
	public static void sort(TransactionBean[] a ) {
		Arrays.sort(a, new Comparator<TransactionBean>() {
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o1.getTransaction_date().compareTo(o2.getTransaction_date());
			}
		});
	}
	

}
