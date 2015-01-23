package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.CustomerBean;
import formbeans.CreateCustomerAccoutForm;

public class CreateCustomerAccountAction extends Action {

	private FormBeanFactory<CreateCustomerAccoutForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateCustomerAccoutForm.class);

	CustomerDAO customDao;
	
	public CreateCustomerAccountAction(Model model) {
		customDao = model.getCustomerDAO();
	}

	@Override
	public String getName() {
		return "createCustomerAccount.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
		
		try {

			CreateCustomerAccoutForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
//				System.out.println("In create employee empty");
				return "createCustomerAccount.jsp";
			}
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
//				for (String error : errors) {
//					System.out.println(error);
//				}
				return "createCustomerAccount.jsp";
			}	
			
			
			CustomerBean customer = new CustomerBean();
			customer.setUsername(form.getUsername());
			customer.setPassword(form.getPassword());
			customer.setFirstname(form.getFirstname());
			customer.setLastname(form.getLastname());
			customer.setAddr_line1(form.getAddr_line1());
			customer.setAddr_line2(form.getAddr_line2());
			customer.setCity(form.getCity());
			customer.setState(form.getState());
			customer.setZip(form.getZip());
			
			System.out.println(form.getUsername());
			
			customDao.createAutoIncrement(customer);
		    successes.add("New customer account has been created!");

		} catch (FormBeanException e) {
			errors.add(e.getMessage());
		} catch (RollbackException e) {
			errors.add(e.getMessage());
		}
        
		return "manage.jsp";
		
	}

}
