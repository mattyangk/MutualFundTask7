package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;
import formbeans.RequestCheckForm;

public class RequestCheckAction extends Action{

	private FormBeanFactory<RequestCheckForm> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckForm.class);
	
	TransactionDAO transactionDAO;
	
	CustomerDAO customerDAO;
	
	public RequestCheckAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}
	
	@Override
	public String getName() {
		return "requestCheck.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
		
		try {
			RequestCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "requestCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				return "requestCheck.jsp";
			}
			
			
			HttpSession session = request.getSession();
			CustomerBean customer = (CustomerBean) session.getAttribute("customer");
			if (customer == null) {
				errors.add("session expired");
				return "index.do";
			}
//			System.out.println(customer.getUsername());
//			System.out.println(customer.getCash());
			
			customerDAO.updateBalance(customer.getCustomer_id(), form.getRequestAmountAsDouble());
			
			TransactionBean transaction = new TransactionBean();
			transaction.setAmount(form.getRequestAmountAsDouble());
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setTrasaction_type("request");
			transaction.setTransaction_date(new Date());
			transaction.setIs_complete(false);
			transaction.setIs_success(false);

			transactionDAO.createAutoIncrement(transaction);		
			
			successes.add("The request for the check has been accepted !");
			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		} catch (AmountOutOfBoundException e) {
			System.out.println(e.getMessage());
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
		
		return "manage.jsp";
	}

}
