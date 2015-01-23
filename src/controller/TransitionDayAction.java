package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

	public String getName() {
		return "transitionDayAction.do";
	}

	public synchronized String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		List<String> successes = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("successes", successes);
		
		try {
			TransitionForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			FundBean[] allFunds = fundDAO.getAllFunds();// distinct different
														// funds;

			if (allFunds == null || allFunds.length == 0) {
				System.out.println("No fund");
				errors.add("No fund has been created yet");
				return "transitionDay.jsp";
			}

			LastPriceForFundsBean[] funds = new LastPriceForFundsBean[allFunds.length];

			// can be null if it's the first run
			
			SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			
			Date latestDate = fundpriceHistoryDAO.findLatestDate();
			String theDate = null;
		    if(latestDate != null){
		    	theDate = dateFormat.format(latestDate);
				
		    }
			request.setAttribute("theLastDate", theDate);

			for (int i = 0; i < funds.length; i++) {
				LastPriceForFundsBean onefund = new LastPriceForFundsBean();
				onefund.setFund_id(allFunds[i].getFund_id());
				onefund.setFund_name(allFunds[i].getName());
				onefund.setLast_date(theDate);
				onefund.setLast_price(fundpriceHistoryDAO.findLatestPrice(onefund.getFund_id()));
				onefund.setFund_symbol(allFunds[i].getSymbol());
				System.out.println(i);

				double lastPrice = fundpriceHistoryDAO
						.findLatestPrice(allFunds[i].getFund_id());
				onefund.setLast_price(lastPrice);
				funds[i] = onefund;
			}

			request.setAttribute("allFunds", funds);

			if (!form.isPresent()) {
				return "transitionDay.jsp";
			}
			// form error check need to be done;
			errors.addAll(form.getValidationErrors());
			if (!errors.isEmpty()) {
				return "transitionDay.jsp";
			}
			
			
		
			Date newLateDate = dateFormat.parse(form.getTransitionDate());
						
			if (latestDate != null && newLateDate.compareTo(latestDate) <= 0) {
				errors.add("The input date is not after the latest transition date: " + latestDate);
				System.out.println("The input date is not after the latest transition date: " + latestDate);
				return "transitionDay.jsp";
			}

			String[] price = form.getPrice(); // start to get the value from the
												// form
			String[] fund_id = form.getFund_id();

			// update the historyPirce table first;
			for (int i = 0; i < price.length; i++) {
				if (price[i] == null || price[i].length() == 0) {
					errors.add("Should type all price for all funds");
					return "transitionDay.jsp";
				}
				
				try {
					Double.parseDouble(price[i]);
				} catch (NumberFormatException e) {
					// call getValidationErrors() to detect this
					errors.add("Provide valid Prices for funds");
					return "transitionDay.jsp";
				}
				
				FundPriceHistoryBean one = new FundPriceHistoryBean();
				double newPrice=Double.parseDouble(price[i]);
				if(newPrice<0)
				{	errors.add("Should put price above zero");
					return "transitionDay.jsp";
				}
				
				System.out.println("original : "+newPrice);
				BigDecimal bd = new BigDecimal(newPrice);
				bd = bd.setScale(2, RoundingMode.HALF_UP);
				double newPriceRounded = bd.doubleValue();    
				System.out.println("rounded : "+newPriceRounded);
				
				if(newPrice < 0.01){
					errors.add("Invalid Transaction ! Fund Price cannot be less than $0.01");
					return "transitionDay.jsp";
				}
				else if((newPrice!=newPriceRounded) && (newPrice-newPriceRounded) < 0.01){
					errors.add("Fund Price can only have upto 2 places of decimal !");
					return "transitionDay.jsp";
				}
				
				one.setFund_id(Integer.parseInt(fund_id[i]));
				one.setPrice(Double.parseDouble(price[i]));
				one.setPrice_date(newLateDate);
				fundpriceHistoryDAO.create(one);
				System.out.println("updating");
			}

			// Then update pending transaction based on the queue.
			TransactionBean[] transactions = transactionDAO
					.readPendingTransInOrder();// Successfully check all the
												// pending transaction?

			System.out.println("got all pending transactions !");

			for (int i = 0; i < transactions.length; i++) {
				CustomerBean customer = customerDAO.read(transactions[i]
						.getCustomer_id());
				double cash = customer.getCash();

				switch (transactions[i].getTrasaction_type()) {
				case "deposit": {
					System.out.println("action : deposit");
					double addMoney = transactions[i].getAmount();
					System.out.println("new cash - deposit : "
							+ (cash + addMoney));
					customerDAO.updateCash(transactions[i].getCustomer_id(),
							(cash + addMoney));
					transactions[i].setExecute_date(form
							.getTransitionDateAsDate());
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);
					break;
				}
				case "request": {
					System.out.println("action : request");
					double awayMoney = transactions[i].getAmount();
					if (customer.getCash() >= awayMoney) {
						System.out.println("new cash - request  : "
								+ (cash - awayMoney));
						customerDAO.updateCash(
								transactions[i].getCustomer_id(),
								(cash - awayMoney));
						transactions[i].setExecute_date(form
								.getTransitionDateAsDate());
						transactions[i].setIs_complete(true);
						transactions[i].setIs_success(true);
						transactionDAO.update(transactions[i]);
					} else {
						transactions[i].setIs_success(false);
						transactions[i].setIs_complete(true);
						transactions[i].setExecute_date(form
								.getTransitionDateAsDate());
						transactionDAO.update(transactions[i]);
						errors.add("Transaction id: "
								+ transactions[i].getTransaction_id()
								+ " failed");
					}
					break;
				}
				case "buy": {
					// no. of shares bought = position.
					System.out.println("action : buy");
					int customerID = transactions[i].getCustomer_id();
					int fundID = transactions[i].getFund_id();

					// TEST CASH
					if (cash < transactions[i].getAmount()) {
						transactions[i].setIs_success(false);
						transactions[i].setIs_complete(true);
						transactionDAO.update(transactions[i]);
						errors.add("Transaction id: "
								+ transactions[i].getTransaction_id()
								+ " failed");
						continue;
					}
					// UPDATE CASH
					double newFundPrice = fundpriceHistoryDAO
							.findLatestPrice(transactions[i].getFund_id());
					double newShares = transactions[i].getAmount()
							/ newFundPrice;// be careful to the number;
					
					System.out.println("original newShares : "+newShares);
					BigDecimal bdShares = new BigDecimal(newShares);
					bdShares = bdShares.setScale(3, RoundingMode.HALF_UP);
					double roundedShares = bdShares.doubleValue();    
					System.out.println("rounded roundedShares : "+roundedShares);
					
					if(newShares < 0.001){
						errors.add("Transaction id: "+ transactions[i].getTransaction_id()+ " failed ! Shares cannot be less than 0.001 shares.");
						transactions[i].setIs_success(false);
						transactions[i].setIs_complete(true);
						transactions[i].setExecute_date(form
								.getTransitionDateAsDate());
						transactionDAO.update(transactions[i]);
						continue;
					}
					
					double amountDeducted = transactions[i].getAmount();
					double newCash = cash - amountDeducted;

					System.out.println("original newCash : "+newCash);
					BigDecimal bdCash = new BigDecimal(newCash);
					bdCash = bdCash.setScale(2, RoundingMode.HALF_UP);
					newCash = bdCash.doubleValue();    
					System.out.println("rounded newCash : "+newCash);
					
					transactions[i].setExecute_date(form
							.getTransitionDateAsDate());
					transactions[i].setShares(roundedShares);
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);
					System.out.println("Transaction Amt : "+transactions[i].getAmount());
					System.out.println("Cash : "+cash);
					System.out.println("Amount being set : "+(newCash));
					customerDAO.updateCash(transactions[i].getCustomer_id(),newCash);

					if (positionDAO.read(fundID,customerID) != null) {
						PositionBean onePosition = positionDAO.read(fundID,customerID);
						double currentShares = onePosition.getShares();
						System.out.println("newShares :" + currentShares
								+ roundedShares);
						
						positionDAO.updateShares(fundID, customerID, currentShares + roundedShares);

					} else {
						PositionBean newPosition = new PositionBean();
						newPosition.setCustomer_id(customerID);
						newPosition.setFund_id(fundID);
						newPosition.setShares(roundedShares);
						System.out.println("roundedShares :" + roundedShares);
						newPosition.setAvailable_shares(roundedShares);
						// insert new data into position table.
						positionDAO.create(newPosition);
					}

					break;
				}
				case "sell": {
					System.out.println("action : sell");
					int customerID = transactions[i].getCustomer_id();
					int fundID = transactions[i].getFund_id();
					double newFundPrice = fundpriceHistoryDAO
							.findLatestPrice(transactions[i].getFund_id());

					double amount = transactions[i].getShares() * newFundPrice;
					double newShares = transactions[i].getShares();

					////////////////////////////////////////////////////
					
					System.out.println("original amount : "+amount);
					BigDecimal bdAmount = new BigDecimal(amount);
					bdAmount = bdAmount.setScale(2, RoundingMode.HALF_UP);
					double roundedAmount = bdAmount.doubleValue();    
					System.out.println("rounded roundedAmount : "+roundedAmount);
					
					if(amount < 0.01){
						errors.add("Transaction id: "+ transactions[i].getTransaction_id()+ " failed ! Amount cannot be less than $0.01.");
						transactions[i].setIs_success(false);
						transactions[i].setIs_complete(true);
						transactions[i].setExecute_date(form
								.getTransitionDateAsDate());
						transactionDAO.update(transactions[i]);
						continue;
					}
					
					double amountAdded = amount;
					double newCash = cash + amountAdded;

					System.out.println("original newCash : "+newCash);
					BigDecimal bdCash = new BigDecimal(newCash);
					bdCash = bdCash.setScale(2, RoundingMode.HALF_UP);
					newCash = bdCash.doubleValue();    
					System.out.println("rounded newCash : "+newCash);
					
					/////////////////////////////////////////////////////

					transactions[i].setAmount(amount);
					transactions[i].setExecute_date(form
							.getTransitionDateAsDate());
					transactions[i].setShares(newShares);
					System.out.println("newShares :" + newShares);
					transactions[i].setIs_complete(true);
					transactions[i].setIs_success(true);
					transactionDAO.update(transactions[i]);

					if (positionDAO.read(fundID,customerID) != null) { // need
																		// to be
																		// done
						// TODO POSITIONDAO.READ ORDER
						PositionBean onePosition = positionDAO.read(fundID,customerID);
						double currentShares = onePosition.getShares();
						if( currentShares - newShares <0){
							throw new RollbackException("Some errors with funds");
						}
						else if((currentShares - newShares) == 0.0){
							positionDAO.delete(fundID, customerID);
						}
						else{
							positionDAO.updateShares(fundID, customerID, currentShares - newShares);
						}
					} else {
						// TODO EXCEPTION DETAIL
						throw new RollbackException("Some errors with funds");
					}
					customerDAO.updateCash(transactions[i].getCustomer_id(),newCash);
					break;
				} // end last case
				} // end switch

			} // end for loop;
			
			successes.add("Transition Day was Successful !");

		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (ParseException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (AmountOutOfBoundException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "transitionDay.jsp";
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
		return "manage.jsp";
	}
}
