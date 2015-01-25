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

public class viewOneCustomerTransactionHistoryAction extends Action{
	
	CustomerDAO customerDAO;
	TransactionDAO transactionDAO;
	
	public viewOneCustomerTransactionHistoryAction(Model model)
	{
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}
	
	public String getName() {
		return "viewOneCustomerTransactionHistory.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		String customerName=(String)request.getParameter("customername");
		TransactionBean[] allTransactions = null;
		
		try {
			CustomerBean customer=customerDAO.getCustomerByUsername(customerName);
			allTransactions = transactionDAO.getTransactionsByCustomerId(customer.getCustomer_id());
			
			if(allTransactions==null){
				errors.add("No Transactions !");
				return "employeeViewTransactionsHistory.jsp";
			}		
			request.setAttribute("transactionsHistory", allTransactions);
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			errors.add(e.getMessage());
			return "manage.jsp";
		}
		return "employeeViewTransactionsHistory.jsp";
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
