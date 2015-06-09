package de.budget.BudgetService.Response;


		import de.budget.BudgetService.dto.BasketTO;

/**
 * Class for the Basket response
 * @author Marco
 * @date 19.05.2015
 */
public class BasketResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
	
	private BasketTO basketTo;

	/**
	 * @author Marco
	 * @date 19.05.2015
	 * Default Constructor
	 */
	public BasketResponse() {
		
	}
	
	/**
	 * @return the basketTo
	 */
	public BasketTO getBasketTo() {
		return basketTo;
	}

	/**
	 * @param basketTo the basketTo to set
	 */
	public void setBasketTo(BasketTO basketTo) {
		this.basketTo = basketTo;
	}
	
	

}
