package de.budget.BudgetService.Response;

import de.budget.BudgetService.dto.PaymentTO;

/**
 * Class for the Payment response
 * @author Marco
 * @date 20.05.2015
 */
public class PaymentResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
	
	private PaymentTO paymentTo;
	
	/**
	 * @author Marco
	 * Default Constructor
	 */
	public PaymentResponse() {
		
	}

	/**
	 * @return the paymentTo
	 */
	public PaymentTO getPaymentTo() {
		return paymentTo;
	}

	/**
	 * @param paymentTo the paymentTo to set
	 */
	public void setPaymentTo(PaymentTO paymentTo) {
		this.paymentTo = paymentTo;
	}

}
