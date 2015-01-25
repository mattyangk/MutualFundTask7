package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.Model;
import model.CustomerDAO;
import model.PositionDAO;
import model.FundDAO;
import databeans.*;

public class EmployeeViewCustomerAction  extends Action{
	CustomerDAO customerDAO;
	PositionDAO positionDAO;
	FundDAO fundDAO;
	
	public EmployeeViewCustomerAction(Model model){
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO(); 
		fundDAO = model.getFundDAO();
	}
	
	public String getName() {
		return "showCustomerInfo.do";
	}

	public String perform(HttpServletRequest request){
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		String messages;
		
		try{
			String customerName=(String)request.getParameter("customername");
		    CustomerBean theCustomer=customerDAO.getCustomerByUsername(customerName);
		
			int CustomerID=theCustomer.getCustomer_id();
			PositionBean [] Positions=positionDAO.getPositionsByCustomerId(CustomerID);
			
			request.setAttribute("customer",theCustomer);
			
			if(Positions==null)
			{
				messages="The customer does not have any fund yet";
				request.setAttribute("message", messages);
				return "showCustomerInfo.jsp";
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
			return "showCustomerInfo.jsp";
		} 
		
		return "showCustomerInfo.jsp";
	}

}
