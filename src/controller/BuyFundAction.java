package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;
import formbeans.BuyFundForm;

public class BuyFundAction extends Action {
	private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory
			.getInstance(BuyFundForm.class);
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	public BuyFundAction(Model model) {
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "buyFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {
			FundBean[] funds = fundDAO.getAllFunds();
			request.setAttribute("funds", funds);
			
			HttpSession session = request.getSession();

			BuyFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			System.out.println(form.getFundname());
			
			// If no params were passed, return with no errors so that the form
			// will be
			// presented (we assume for the first time).
			if (!form.isPresent()) {
				return "buyFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "buyFund.jsp";
			}

			// Look up the user
			FundBean fund = fundDAO.getFundByName(form.getFundname());

			if (fund == null) {
				errors.add("No such fund.");
				return "buyFund.jsp";
			}
			
			

			CustomerBean customer = (CustomerBean) session
					.getAttribute("customer");
			customerDAO.updateBalance(customer.getCustomer_id(),
					form.getFundAmountAsDouble());

			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setAmount(form.getFundAmountAsDouble());
			transaction.setFund_id(fund.getFund_id());
			transaction.setIs_complete(false);
			transaction.setIs_success(false);
			transaction.setTransaction_date(new Date());
			transaction.setTrasaction_type("buy");

			transactionDAO.createAutoIncrement(transaction);
			
			successes.add("Your transaction is in process !");
			
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		} catch (AmountOutOfBoundException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
		
		return "manage.jsp";
	}
}
