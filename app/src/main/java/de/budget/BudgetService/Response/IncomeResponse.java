package de.budget.BudgetService.Response;

import de.budget.BudgetService.dto.IncomeTO;

/**
 * @date 28.05.2015
 * @author Marco
 *
 */
public class IncomeResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;

	private IncomeTO incomeTo;
	
	/**
	 * Default Constructor
	 */
	public IncomeResponse() {
		
	}

	/**
	 * @return the incomeTo
	 */
	public IncomeTO getIncomeTo() {
		return incomeTo;
	}

	/**
	 * @param incomeTo the incomeTo to set
	 */
	public void setIncomeTo(IncomeTO incomeTo) {
		this.incomeTo = incomeTo;
	}
}
