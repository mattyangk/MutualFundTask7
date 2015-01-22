package exception;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AmountOutOfBoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	double ownAmount;
	
	double trasactionAmount;
	
	public AmountOutOfBoundException(double ownAmount, double transactionAmount) {
		super();
		this.ownAmount = ownAmount;
		this.trasactionAmount = transactionAmount;	
	}
	
	@Override
	public String getMessage() {
		BigDecimal bdAmt = new BigDecimal(ownAmount);
		bdAmt = bdAmt.setScale(2, RoundingMode.HALF_UP);
		double displayOwnAmount = bdAmt.doubleValue();
		
		BigDecimal bdTrans = new BigDecimal(trasactionAmount);
		bdTrans = bdTrans.setScale(2, RoundingMode.HALF_UP);
		double displayTransAmt = bdTrans.doubleValue();
		
		DecimalFormat df = new DecimalFormat("#.00"); 
		
		return "Invalid Transaction !! Available Balance of $ " + df.format(displayOwnAmount) + " is less than Transaction Amount of $"+ df.format(displayTransAmt) + " !!\n";
	}
	

}
