package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.FundBean;
import databeans.FundPriceDetailBean;
import databeans.FundPriceHistoryBean;
import model.FundDAO;
import model.FundPriceHistoryDAO;
import model.Model;

public class ViewAllFundsAction extends Action {

	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	
	public ViewAllFundsAction(Model model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}
	
	@Override
	public String getName() {
		return "viewAllFunds.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);

		HttpSession session = request.getSession();
		
		try {
			FundBean[] funds = fundDAO.getAllFunds();
			FundPriceDetailBean[] fundPriceDetails = new FundPriceDetailBean[funds.length];
			Date latestDay = fundPriceHistoryDAO.findLatestDate();
			for (int i = 0; i < funds.length; i++) {
				FundPriceDetailBean fundPrice = new FundPriceDetailBean();
				fundPrice.setFund_id(funds[i].getFund_id());
				fundPrice.setName(funds[i].getName());
				fundPrice.setSymbol(funds[i].getSymbol());
				if(latestDay != null) {
					FundPriceHistoryBean priceHistory = fundPriceHistoryDAO.read(funds[i].getFund_id(), latestDay);
					// the fund is just created and no latest price
					if (priceHistory == null) {
						fundPrice.setPrice_date(null);
						fundPrice.setPrice(0);
					} else {
						fundPrice.setPrice_date(latestDay);
						fundPrice.setPrice(priceHistory.getPrice());
					}
				} else {
					fundPrice.setPrice_date(null);
					fundPrice.setPrice(0);
				}
				
				fundPriceDetails[i] = fundPrice;
			}
			
			session.setAttribute("fundPriceDetails", fundPriceDetails);
			
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "viewAllFunds.jsp";
		}

		return "viewAllFunds.jsp";
	}

}
