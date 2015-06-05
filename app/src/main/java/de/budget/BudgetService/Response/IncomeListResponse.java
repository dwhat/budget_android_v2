package de.budget.BudgetService.Response;

import java.util.List;

import de.budget.BudgetService.dto.IncomeTO;

/**
 * @date 28.05.2015
 * @author Marco
 *
 */
public class IncomeListResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;

	private List<IncomeTO> incomeList;
	
	/**
	 * Default Constructor
	 */
	public IncomeListResponse() {
		
	}

	/**
	 * @return the incomeList
	 */
	public List<IncomeTO> getIncomeList() {
		return incomeList;
	}

	/**
	 * @param incomeList the incomeList to set
	 */
	public void setIncomeList(List<IncomeTO> incomeList) {
		this.incomeList = incomeList;
	}
}
