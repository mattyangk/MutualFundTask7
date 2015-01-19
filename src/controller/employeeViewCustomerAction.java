package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import model.Model;
import model.CustomerDAO;
import formbeans.*;
import databeans.*;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

public class employeeViewCustomerAction  extends Action{
	
	private FormBeanFactory<SearchCustomerForm> formBeanFactory = FormBeanFactory
			.getInstance(SearchCustomerForm.class);
	
	
	CustomerDAO customerDAO;
	
	public employeeViewCustomerAction(Model model){
		
		customerDAO = model.getCustomerDAO();
		
	}
	
	
	public String getName() {
		return "employeeViewCustomerAction.do";
	}
	
	
	
	public String perform(HttpServletRequest request){
		
		List<String> errors = new ArrayList<String>();
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
			
			if(theCustomer==null)
			{
				errors.add("No such Customer");
				System.out.println("mei qudao");
			
				 return "viewAccountEmployee.jsp";
				
			}
			
			else
			{
			  System.out.println("qudao le");
			  request.setAttribute("customer", theCustomer);
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
