package de.budget.BudgetService.Response;


/**
 * Response Class to transport only Double Values to Client
 * 
 * @author Moritz
 * @date 07.06.2015
 *
 *
 */
public class AmountResponse extends ReturnCodeResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -255977161244602475L;
	
	private double Value;
	
	
	public double getValue() {
		return this.Value;
	}
	
	
	public void setValue(double value) {
		this.Value = value;
		
	}

}
