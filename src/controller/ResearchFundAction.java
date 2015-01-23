package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.FundBean;
import databeans.FundPriceDetailBean;
import databeans.FundPriceHistoryBean;
import formbeans.ResearchFundForm;
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
			if (fund == null) {
				errors.add("No fund: " + form.getFundname());
				return "researchFund.jsp";
			}
			
			FundPriceHistoryBean[] historyList = fundPriceHistoryDAO.findAllPrices(fund.getFund_id());
			FundPriceDetailBean[] priceList = new FundPriceDetailBean[historyList.length];
			for (int i = 0; i < historyList.length; i++) {
				FundPriceDetailBean fundPriceDetail = new FundPriceDetailBean();
				fundPriceDetail.setFund_id(fund.getFund_id());
				fundPriceDetail.setName(fund.getName());
				fundPriceDetail.setPrice(historyList[i].getPrice());
				fundPriceDetail.setPrice_date(historyList[i].getPrice_date());
				fundPriceDetail.setSymbol(fund.getSymbol());
				priceList[i] = fundPriceDetail;
				//System.out.println("Price: " + historyList[i].getPrice() + ", date: " + historyList[i].getPrice_date());
			}
			request.setAttribute("fund", fund);
			request.setAttribute("priceList", priceList);
			
			
					
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "researchFund.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "researchFund.jsp";
		}
		
		return "researchFund.jsp";
	}

}
