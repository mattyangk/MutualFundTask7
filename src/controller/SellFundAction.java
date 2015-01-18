package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.FundDAO;
import model.Model;
import model.PositionDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import databeans.FundBean;
import databeans.PositionAndFundBean;
import databeans.PositionBean;
import formbeans.SellFundForm;

public class SellFundAction extends Action {

	private FormBeanFactory<SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(SellFundForm.class);

	private PositionDAO positionDAO;
	private FundDAO fundDAO;

	public SellFundAction(Model model) {
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "sellFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			SellFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			HttpSession session = request.getSession();
			CustomerBean customer = (CustomerBean) session
					.getAttribute("customer");
			if (customer == null) {
				errors.add("session expired");
				return "sellFund.jsp";
			}
			
			FundBean[] funds = fundDAO.getAllFunds();
			PositionAndFundBean[] positionAndFunds = new PositionAndFundBean[funds.length];
			for (int i = 0; i < funds.length; i++) {
				PositionAndFundBean positionAndFund = new PositionAndFundBean();
				PositionBean position = positionDAO.read(funds[i].getFund_id(), customer.getCustomer_id());
				positionAndFund.setAvailable_shares(position.getAvailable_shares());
				positionAndFund.setCustomer_id(customer.getCustomer_id());
				positionAndFund.setFund_id(funds[i].getFund_id());
				positionAndFund.setName(funds[i].getName());
				positionAndFund.setShares(position.getShares());
				positionAndFund.setSymbol(funds[i].getSymbol());
				positionAndFunds[i] = positionAndFund;
			}
			
			request.setAttribute("positionAndFunds", positionAndFunds);

			if (!form.isPresent()) {
				return "sellFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "sellFund.jsp";
			}


		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "manage.jsp";
	}

}
