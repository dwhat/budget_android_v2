package de.budget.BudgetService.Response;

import de.budget.BudgetService.dto.ItemTO;


/**
 * Class for the Category response
 * @author Marco
 * @date 19.05.2015
 */
public class ItemResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private ItemTO itemTo;
	
	/**
	 * Default Constructor
	 */
	public ItemResponse() {
		
	}

	/**
	 * @return the itemTo
	 */
	public ItemTO getItemTo() {
		return itemTo;
	}

	/**
	 * @param itemTo the itemTo to set
	 */
	public void setItemTo(ItemTO itemTo) {
		this.itemTo = itemTo;
	}

}
