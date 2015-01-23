package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import databeans.CustomerBean;
import databeans.EmployeeBean;
import model.CustomerDAO;
import model.EmployeeDAO;
import model.Model;

public class LogoutAction extends Action {
	
	CustomerDAO customerDAO;

	EmployeeDAO employeeDAO;

	public LogoutAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	@Override
	public String getName() {
		return "logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
        
        HttpSession session = request.getSession();
        CustomerBean customer = (CustomerBean)session.getAttribute("customer");
        EmployeeBean employee = (EmployeeBean)session.getAttribute("employee");
        
        
        if (customer != null) {
        	session.setAttribute("customer", null);
        } else if (employee != null){
        	session.setAttribute("employee", null);
        } else {
        	errors.add("Logout without login");
        }
        successes.add("Logged out successfully!");
        return "index.jsp";
	}

}
