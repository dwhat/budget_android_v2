package de.budget.BudgetService.Response;

import java.util.List;

import de.budget.BudgetService.dto.CategoryTO;

/**
 * @date 19.05.2015
 * @author Marco
 *Klasse f�r eine Liste von CategoryTO Objecten als Antwort auf Anfragen
 */
public class CategoryListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<CategoryTO> categoryList;
		
	/**
	 * DefaultConstructor
	 * @author Marco
	 * @date 19.05.2015
	 */
	public CategoryListResponse() {

	}

	public List<CategoryTO> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryTO> categoryList) {
		this.categoryList = categoryList;
	}

}
