package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import model.CustomerDAO;
import model.Model;

public class ViewAllCustomerDetailsAction extends Action {

	CustomerDAO customerDAO;

	public ViewAllCustomerDetailsAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "viewAllCustomerDetails.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);

		CustomerBean[] customers;

		try{

			customers = customerDAO.getAllCustomers();
			request.setAttribute("customersList", customers);

			if(customers==null){
				return "manage.jsp";
			}

		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
		return "viewAllCustomerDetails.jsp";
	}

}
