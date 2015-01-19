package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.FundBean;
import formbeans.ResearchFundForm;
import formbeans.SellFundForm;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;

public class ResearchFundAction extends Action {
	
	private FormBeanFactory<ResearchFundForm> formBeanFactory = FormBeanFactory
			.getInstance(ResearchFundForm.class);

	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public ResearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	
	@Override
	public String getName() {
		return "researchFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			ResearchFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "researchFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "researchFund.jsp";
			}
			
			FundBean fund = fundDAO.getFundByName(form.getFundname());
			
			
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
