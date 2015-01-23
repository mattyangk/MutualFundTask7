package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundDAO;
import model.Model;
import model.PositionDAO;
import model.TransactionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionAndFundBean;
import databeans.PositionBean;
import databeans.TransactionBean;
import exception.AmountOutOfBoundException;
import formbeans.SellFundForm;

public class SellFundAction extends Action {

	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SellFundForm.class);

	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private TransactionDAO transactionDAO;

	public SellFundAction(Model model) {
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
	}

	@Override
	public String getName() {
		return "sellFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);

		try {
			SellFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			HttpSession session = request.getSession();
			CustomerBean customer = (CustomerBean) session
					.getAttribute("customer");
			
			FundBean[] funds = fundDAO.getAllFunds();
			ArrayList<PositionAndFundBean> positionAndFunds = new ArrayList<PositionAndFundBean>();
			for (int i = 0; i < funds.length; i++) {
				PositionBean position = positionDAO.read(funds[i].getFund_id(), customer.getCustomer_id());
				if (position == null) {
					continue;
				}
				PositionAndFundBean positionAndFund = new PositionAndFundBean();
				positionAndFund.setAvailable_shares(position.getAvailable_shares());
				positionAndFund.setCustomer_id(customer.getCustomer_id());
				positionAndFund.setFund_id(funds[i].getFund_id());
				positionAndFund.setName(funds[i].getName());
				positionAndFund.setShares(position.getShares());
				positionAndFund.setSymbol(funds[i].getSymbol());
				positionAndFunds.add(positionAndFund);
				System.out.println("test  " + positionAndFund.getName());
			}
			
			
			request.setAttribute("positionAndFunds", positionAndFunds.toArray(new PositionAndFundBean[positionAndFunds.size()]));
			
			if (customer == null) {
				errors.add("session expired");
				return "sellFund.jsp";
			}
			
			if (!form.isPresent()) {
				System.out.println("No param");
				return "sellFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "sellFund.jsp";
			}
			
			System.out.println("Fund name in form is " + form.getFundname());
			
			
			FundBean fund = fundDAO.getFundByName(form.getFundname());

			positionDAO.updateAvailableShares(fund.getFund_id(), customer.getCustomer_id(), form.getShareAsDouble());
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomer_id(customer.getCustomer_id());
			transaction.setFund_id(fund.getFund_id());
			transaction.setIs_complete(false);
			transaction.setIs_success(false);
			transaction.setShares(form.getShareAsDouble());
			transaction.setTransaction_date(new Date());
			transaction.setTrasaction_type("sell");
			
			transactionDAO.createAutoIncrement(transaction);
			successes.add("Your transaction is in process !");


		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		} catch (AmountOutOfBoundException e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		}

		return "manage.jsp";
	}

}
