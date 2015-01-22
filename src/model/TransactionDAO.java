package model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.CustomerBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;

public class TransactionDAO extends GenericDAO<TransactionBean> {

	public TransactionDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(TransactionBean.class, tableName, connectionPool);
	}

	public TransactionBean[] getAllTransactions() throws RollbackException {

		TransactionBean[] transactions = match();

		return transactions;

	}

	public TransactionBean[] readPendingTransInOrder() throws RollbackException {

		TransactionBean[] pendingTransactions = match(MatchArg.equals(
				"is_complete", false));
		sortInAscending(pendingTransactions);
		return pendingTransactions;
	}

	public TransactionBean[] getTransactionsByCustomerId(int id)
			throws RollbackException {

		TransactionBean[] transactions = match(MatchArg.equals("customer_id",
				id));
		sortInDescending(transactions);
		return transactions;
	}

	/*
	 * public TransactionBean[] getTransactionsByTransitionDay(Date date) throws
	 * RollbackException {
	 * 
	 * TransactionBean[] transactions = match(MatchArg.equals("transition_date",
	 * date)); return transactions; }
	 */

	/*
	 * public void updateDate(int transaction_id, Date date) throws
	 * RollbackException, AmountOutOfBoundException { try { Transaction.begin();
	 * TransactionBean transaction = read(transaction_id); if (transaction ==
	 * null) { throw new RollbackException("This transaction:" + transaction_id
	 * + " does not exist"); } else { double = ; double newCash = cash + amount;
	 * customer.setBalance(newCash); update(customer);
	 * 
	 * } Transaction.commit(); } finally { if (Transaction.isActive())
	 * Transaction.rollback(); }
	 */

	public static void sortInAscending(TransactionBean[] a) {
		Arrays.sort(a, new Comparator<TransactionBean>() {
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o1.getTransaction_date().compareTo(
						o2.getTransaction_date());
			}
		});
	}

	public static void sortInDescending(TransactionBean[] a) {
		Arrays.sort(a, new Comparator<TransactionBean>() {
			@Override
			public int compare(TransactionBean o1, TransactionBean o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				return o2.getTransaction_date().compareTo(
						o1.getTransaction_date());
			}
		});
	}

}
