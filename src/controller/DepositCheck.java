package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.TransactionBean;
import formbeans.DepositCheckForm;

public class DepositCheck extends Action {

	private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);

	TransactionDAO transactionDAO;
	CustomerDAO customerDAO;
	
	public DepositCheck(Model model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "depositCheck.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
		
		try {

			DepositCheckForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "depositCheck.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				for (String error : errors) {
					System.out.println(error);
				}
				return "depositCheck.jsp";
			}	

			CustomerBean customer = customerDAO.getCustomerByUsername(form.getUsername());
			if(customer==null){
				errors.add("Not valid Customer Username");
				return "depositCheck.jsp";
			}
				
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setAmount(form.getDepositAmountAsDouble());
			transaction.setTrasaction_type("deposit");
			transaction.setIs_complete(false);
			transaction.setTransaction_date(new Date());
			
			transactionDAO.createAutoIncrement(transaction);
			
			successes.add("The request for the deposit has been accepted !");

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}

		return "manage.jsp";
	}

}
