package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.FundDAO;
import model.Model;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.FundBean;
import formbeans.CreateFundForm;

public class CreateFundAction extends Action {
	
	private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory
			.getInstance(CreateFundForm.class);	
	
	FundDAO funDAO;
	
	public CreateFundAction(Model model) {
		funDAO = model.getFundDAO();
	}

	@Override
	public String getName() {
		return "createFund.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
		
		try {
			CreateFundForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			
			if (!form.isPresent()) {
				return "createFund.jsp";
			}

			// Any validation errors?
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "createFund.jsp";
			}
			System.out.println("fund name :" + form.getFundname());
			FundBean isExistedFund = funDAO.getFundByName(form.getFundname());
			if (isExistedFund != null) {
				errors.add("There already exists a fund with the same username,please try another fund name");
				return "createFund.jsp";
			}
			
			FundBean fund = new FundBean();
			fund.setName(form.getFundname());
			fund.setSymbol(form.getSymbol());
			
			funDAO.createAutoIncrement(fund);
			successes.add("New fund has been created!");
			
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "createFund.jsp";
		}
		
		
		return "manage.jsp";
	}

}
