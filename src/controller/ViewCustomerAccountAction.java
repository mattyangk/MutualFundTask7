package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

public class ViewCustomerAccountAction extends Action {

	CustomerDAO customerDAO;

	public ViewCustomerAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "viewAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);

		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean)session.getAttribute("customer");

		try{

			customer = customerDAO.read(customer.getCustomer_id());
			session.setAttribute("customer", customer);
			
			if(customer!=null){
				return "viewAccountCustomer.jsp";
			}

		}catch (RollbackException e) {
			errors.add(e.getMessage());
			return "manage.jsp";
		}
			return "manage.jsp";
		}

	}
