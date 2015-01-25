package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.CustomerFundsInfoBean;
import databeans.FundBean;
import databeans.PositionBean;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.PositionDAO;

public class ViewCustomerAccountAction extends Action {

	CustomerDAO customerDAO;
	PositionDAO positionDAO;
	FundDAO fundDAO;

	public ViewCustomerAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO(); 
		fundDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "viewAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		String messages;

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean)session.getAttribute("customer");

		try{
			
			int CustomerID=customer.getCustomer_id();
			PositionBean [] Positions=positionDAO.getPositionsByCustomerId(CustomerID);
			
			customer = customerDAO.read(customer.getCustomer_id());
			session.setAttribute("customer", customer);
			
			if(customer==null){
				return "manage.jsp";
			}
			if(Positions==null)
			{
				messages="The customer does not have any fund yet";
				request.setAttribute("message", messages);
				return "viewAccountCustomer.jsp";
			}
			
			else{
				CustomerFundsInfoBean [] fundInfo=new CustomerFundsInfoBean[Positions.length];
				
				for(int i=0;i<fundInfo.length;i++)
				{
					FundBean theFund=fundDAO.getFundById(Positions[i].getFund_id());
					CustomerFundsInfoBean customerFundInfo = new CustomerFundsInfoBean();
					fundInfo[i] = customerFundInfo;
					fundInfo[i].setFund_id(theFund.getFund_id());
					fundInfo[i].setFund_name(theFund.getName());
					fundInfo[i].setFund_symbol(theFund.getSymbol());
					fundInfo[i].setShares(Positions[i].getShares());
				}
				
				request.setAttribute("fundInfo",fundInfo);
			}

		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
			return "viewAccountCustomer.jsp";
		}

	}
