package de.budget.BudgetService.Response;

import de.budget.BudgetService.dto.CategoryTO;


/**
 * Class for the Category response
 * @author Marco
 * @date 19.05.2015
 */
public class CategoryResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
		
	private CategoryTO categoryTo;

	/**
	* @author Marco
	* @date 19.05.2015
	* Default Constructor
	*/
	public CategoryResponse() {
			
	}

	/**
	 * @return the categoryTo
	 */
	public CategoryTO getCategoryTo() {
		return categoryTo;
	}

	/**
	 * @param categoryTo the categoryTo to set
	 */
	public void setCategoryTo(CategoryTO categoryTo) {
		this.categoryTo = categoryTo;
	}

}
