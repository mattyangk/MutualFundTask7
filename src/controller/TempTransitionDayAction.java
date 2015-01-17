package controller;

import java.util.ArrayList;
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
import formbeans.TempTransitionForm;

public class TempTransitionDayAction extends Action {

	private FormBeanFactory<TempTransitionForm> formBeanFactory = FormBeanFactory.getInstance(TempTransitionForm.class);

	TransactionDAO transactionDAO;
	CustomerDAO customerDAO;
	
	public TempTransitionDayAction(Model model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "tempTransitionDay.do";
	}

	@Override
	public synchronized String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {

			TempTransitionForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "tempTransitionDay.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				for (String error : errors) {
					System.out.println(error);
				}
				return "tempTransitionDay.jsp";
			}	

			TransactionBean[] transactions = transactionDAO.readAllPendingTransactions();
			CustomerBean customer;
			
			for(int i=0; i<transactions.length;i++){
				switch(transactions[i].getTrasaction_type()){
					case "deposit":{
						customer = customerDAO.read(transactions[i].getCustomer_id());
						customer.setCash(customer.getCash() + transactions[i].getAmount());
						customerDAO.update(customer);
						transactions[i].setExecute_date(form.getTransitionDateAsDate());
						transactions[i].setIs_complete(true);
						//transactions[i].setIs_success(true);
						transactionDAO.update(transactions[i]);
						break;
					}
					case "request":{
						customer = customerDAO.read(transactions[i].getCustomer_id());
						if(customer.getCash() >= transactions[i].getAmount()){
							customer.setCash(customer.getCash() - transactions[i].getAmount());
							customerDAO.update(customer);
							transactions[i].setExecute_date(form.getTransitionDateAsDate());
							transactions[i].setIs_complete(true);
							transactions[i].setIs_success(true);
							transactionDAO.update(transactions[i]);
						}
						else{
							transactions[i].setIs_success(false);
							transactionDAO.update(transactions[i]);
						}
						break;
					}
					case "buy":{
						// no. of shares bought = position.
						break;
					}
					case "sell":{
						break;
					}
				}
			}
			
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
