package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.CustomerDAO;
import model.PositionDAO;
import model.FundDAO;
import formbeans.*;
import databeans.*;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

public class employeeViewCustomerAction  extends Action{
	
	private FormBeanFactory<SearchCustomerForm> formBeanFactory = FormBeanFactory
			.getInstance(SearchCustomerForm.class);
	
	
	CustomerDAO customerDAO;
	PositionDAO positionDAO;
	FundDAO fundDAO;
	
	public employeeViewCustomerAction(Model model){
		
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO(); 
		fundDAO = model.getFundDAO();
		
	}
	
	public String getName() {
		return "employeeViewCustomerAction.do";
	}

	public String perform(HttpServletRequest request){
		
		List<String> errors = new ArrayList<String>();
		String messages;
		request.setAttribute("errors",errors);
		
		try{
			SearchCustomerForm form=formBeanFactory.create(request);
			request.setAttribute("form", form);
			if(!form.isPresent())
			{
				
				System.out.println("First time");
				return "viewAccountEmployee.jsp";
			}
			
			String customerName=form.getCustomerName();
			System.out.println("customerName :"+customerName);
			
		    CustomerBean theCustomer=customerDAO.getCustomerByUsername(customerName);
			System.out.println("customer :"+theCustomer);
			
			int CustomerID=theCustomer.getCustomer_id();
			PositionBean [] Positions=positionDAO.getPositionsByCustomerId(CustomerID);
			
			if(Positions==null)
			{
				messages="The customer does not have any fund yet";
				request.setAttribute("message", messages);
				return "viewAccountEmployee.jsp";
			}
			
			else{
				CustomerFundsInfoBean [] fundInfo=new CustomerFundsInfoBean[Positions.length];
				FundBean theFund;
				for(int i=0;i<fundInfo.length;i++)
				{
					theFund=fundDAO.getFundById(Positions[i].getFund_id());
					fundInfo[i].setFund_id(theFund.getFund_id());
					fundInfo[i].setFund_name(theFund.getName());
					fundInfo[i].setFund_symbol(theFund.getSymbol());
					fundInfo[i].setShares(Positions[i].getShares());
				}
				
				request.setAttribute("fundInfo",fundInfo);
				return "viewAccountEmployee.jsp";	
				
			}
		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "viewAccountEmployee.jsp";
		} catch (FormBeanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewAccountEmployee.jsp";	
	}

}
