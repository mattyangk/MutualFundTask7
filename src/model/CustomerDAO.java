package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.CustomerBean;
import exception.AmountOutOfBoundException;

public class CustomerDAO extends GenericDAO<CustomerBean> {

	public CustomerDAO(ConnectionPool connectionPool, String tableName)
			throws DAOException {
		super(CustomerBean.class, tableName, connectionPool);
	}

	public CustomerBean[] getAllCustomers() throws RollbackException {

		CustomerBean[] customers = match();

		return customers;

	}

	public CustomerBean getCustomerByUsername(String username)
			throws RollbackException {
		CustomerBean[] customer = match(MatchArg.equals("username", username));
		if (customer.length != 1) {
			System.out.println("not correct number of customers");
			return null;
		}
		return customer[0];
	}

	public void updateBalance(int id, double amount) throws RollbackException,
	AmountOutOfBoundException {
		try {
			Transaction.begin();
			CustomerBean customer = read(id);
			if (customer == null) {
				throw new RollbackException("This customer:" + id
						+ " does not exist");
			} else {
				double balance = customer.getBalance();
				double newBalance = balance - amount;
				if (newBalance < 0)
					throw new AmountOutOfBoundException(balance, amount);
				else {
					customer.setBalance(newBalance);
					update(customer);
				}
			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

	public void updateCash(int customer_id, double amount) throws RollbackException,
	AmountOutOfBoundException {
		try {
			Transaction.begin();
			CustomerBean customer = read(customer_id);
			if (customer == null) {
				throw new RollbackException("This customer:" + customer_id
						+ " does not exist");
			} else {
				customer.setCash(amount);
				customer.setBalance(amount);
				update(customer);

			}
			Transaction.commit();
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}

}
