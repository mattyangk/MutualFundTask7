package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.TransactionBean;
import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

public class ViewTransactionHistoryAction extends Action {

	CustomerDAO customerDAO;
	TransactionDAO transactionDAO;

	public ViewTransactionHistoryAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	@Override
	public String getName() {
		return "transactionHistory.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean)session.getAttribute("customer");
		TransactionBean[] allTransactions = null;

		try{
			allTransactions = transactionDAO.getTransactionsByCustomerId(customer.getCustomer_id());

			if(allTransactions==null){
				errors.add("No Transactions !");
				return "viewTransactionsHistory.jsp";
			}
			request.setAttribute("transactionsHistory", allTransactions);

		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
		return "viewTransactionsHistory.jsp";
	}

}
