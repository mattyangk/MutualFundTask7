package controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.*;
import databeans.*;
import exception.AmountOutOfBoundException;
import formbeans.*;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

public class TransitionDayAction extends Action {

	private FormBeanFactory<TransitionForm> formBeanFactory = FormBeanFactory
			.getInstance(TransitionForm.class);

	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundpriceHistoryDAO;
	private CustomerDAO customerDAO;

	public TransitionDayAction(Model model) {
		fundDAO = model.getFundDAO();
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundpriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	public String getName(){
		return "transitionDayAction.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try{
			TransitionForm form=formBeanFactory.create(request);
			request.setAttribute("form", form);

			FundBean[] allFunds=fundDAO.getAllFunds();//distinct different funds;

			if(allFunds==null||allFunds.length==0)
			{
				System.out.println("No fund");
				errors.add("No fund has been created yet");
				return "transitionDay.jsp";//?
			}

			else {
				System.out.println("YES,has funds");
			}

			LastPriceForFundsBean[] funds=new LastPriceForFundsBean[allFunds.length];

			int existedCount=fundpriceHistoryDAO.getCount();//any record existed in fundpriceHistory table?
			if(existedCount==0){
				request.setAttribute("theLastDate", "Current");
				System.out.println("No latestday");
			}
			else{
				request.setAttribute("theLastDate", "yes"); //should show the latest day,some function missed.
				System.out.println("has date");
			}

			double lastP=1.1;
			for(int i=0;i<funds.length;i++){
				LastPriceForFundsBean onefund=new LastPriceForFundsBean();
				onefund.setFund_id(allFunds[i].getFund_id());
				onefund.setFund_name(allFunds[i].getName());
				onefund.setFund_symbol(allFunds[i].getSymbol());
				System.out.println(i);

				// lastPrice=fundpriceHistoryDAO.findLatestPrice(allFunds[i].getFund_id());//need to do,based on fundID return that fund's lastPrice;
				//if no existed, return -1
				onefund.setLast_price(lastP);
				funds[i]=onefund;
			}

			request.setAttribute("allFunds", funds);

			if(!form.isPresent())
			{
				return "transitionDay.jsp";
			}
			//form error check need to be done;

			String[]price=form.getPrice();    //start to get the value from the form
			String[]fund_id=form.getFund_id();

			//TODO
			//typed date check should be done,should be valid, and after the last end day.

			java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			Date newLateDate=dateFormat.parse(form.getTransitionDate());

			//update the historyPirce table first;
			for(int i=0;i<price.length;i++)
			{
				if(price[i]==null||price[i].length()==0){ //TODO CHECK NULL
					errors.add("Should type all price for all funds");
				}
				FundPriceHistoryBean one=new FundPriceHistoryBean();
				one.setFund_id(Integer.parseInt(fund_id[i]));
				one.setPrice(Double.parseDouble(price[i]));
				one.setPrice_date(newLateDate);
				fundpriceHistoryDAO.create(one);
				System.out.println("updating");
			}

			//Then update pending transaction based on the queue.
			TransactionBean [] transactions=transactionDAO.readPendingTransInOrder();//Successfully check all the pending transaction?
			
			System.out.println("got all pending transactions !");
			
			for(int i=0;i<transactions.length;i++)
			{
				CustomerBean customer=customerDAO.read(transactions[i].getCustomer_id());
				double cash=customer.getCash(); //need to be done;
				
				switch(transactions[i].getTrasaction_type()){
				case "deposit":{
					System.out.println("action : deposit");
					double addMoney=transactions[i].getAmount();
					System.out.println("new cash - deposit : "+(cash+addMoney));
					customerDAO.updateCash(transactions[i].getCustomer_id(),(cash+addMoney));
					transactions[i].setExecute_date(form.getTransitionDateAsDate());
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);
					break;
				}
				case "request":{
					System.out.println("action : request");
					double awayMoney=transactions[i].getAmount();
					if(customer.getCash() >= awayMoney){
						System.out.println("new cash - request  : "+(cash-awayMoney));
						customerDAO.updateCash(transactions[i].getCustomer_id(),(cash-awayMoney));
						transactions[i].setExecute_date(form.getTransitionDateAsDate());
						transactions[i].setIs_complete(true);
						transactions[i].setIs_success(true);
						transactionDAO.update(transactions[i]);
					}
					else{
						transactions[i].setIs_success(false);
						transactionDAO.update(transactions[i]);
					}
					break;
				}
				case "buy":{
					// no. of shares bought = position.
					System.out.println("action : buy");
					int customerID=transactions[i].getCustomer_id();
					int fundID=transactions[i].getFund_id();
					double newFundPrice=fundpriceHistoryDAO.findLatestPrice(transactions[i].getFund_id());//need to be done
					double newShares = transactions[i].getAmount()/newFundPrice;//be careful to the number;
					
					transactions[i].setExecute_date(form.getTransitionDateAsDate());
					transactions[i].setShares(newShares);
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);

					if(positionDAO.read(customerID,fundID)!=null)  //need to be done
					{
						PositionBean onePosition=positionDAO.read(customerID,fundID);
						double currentShares=onePosition.getShares();
						System.out.println("newShares :"+currentShares+newShares);
						onePosition.setShares(currentShares+newShares);
						onePosition.setAvailable_shares(onePosition.getShares());
						positionDAO.update(onePosition);

					}else
					{
						PositionBean newPosition=new PositionBean();
						newPosition.setCustomer_id(customerID);
						newPosition.setFund_id(fundID);
						newPosition.setShares(newShares);
						System.out.println("newShares :"+newShares);
						newPosition.setAvailable_shares(newShares);
						// insert new data into position table.
						positionDAO.create(newPosition);
					}

					// UPDATE CASH
					customerDAO.updateCash(transactions[i].getCustomer_id(),cash-transactions[i].getAmount());
					break;
				}
				case "sell":{
					System.out.println("action : request");
					int customerID=transactions[i].getCustomer_id();
					int fundID=transactions[i].getFund_id();
					double newFundPrice=fundpriceHistoryDAO.findLatestPrice(transactions[i].getFund_id());//need to be done
					double amount=transactions[i].getShares() * newFundPrice;
					double newShares=transactions[i].getShares();

					transactions[i].setAmount(amount);
					transactions[i].setExecute_date(form.getTransitionDateAsDate());
					transactions[i].setShares(newShares);
					System.out.println("newShares :"+newShares);
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);

					if(positionDAO.read(customerID,fundID)!=null){  //need to be done
						//TODO POSITIONDAO.READ ORDER
						PositionBean onePosition=positionDAO.read(customerID,fundID);
						double currentShares=onePosition.getShares();
						onePosition.setShares(currentShares-newShares);
						onePosition.setAvailable_shares(onePosition.getShares());  //needed new attribute.
						positionDAO.update(onePosition);
					}
					else{
						//TODO EXCEPTION DETAIL
						throw new RollbackException("Some errors with funds");
					}
					customerDAO.updateCash(transactions[i].getCustomer_id(),cash+transactions[i].getAmount());
					break;
				} // end last case
				} // end switch

			} // end for loop;

			return "transitionDay.jsp" ; 

		}catch(RollbackException e){
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (ParseException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (AmountOutOfBoundException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		}catch( FormBeanException e){
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		}
		finally{
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
