package de.budget.BudgetService.Response;

import java.util.List;

import de.budget.BudgetService.dto.BasketTO;

/**
 * @date 19.05.2015
 * @author Marco
 *Klasse f�r eine Liste von basketTO Objecten als Antwort auf Anfragen
 */
public class BasketListResponse extends ReturnCodeResponse{

	private static final long serialVersionUID = 1L;
	
	private List<BasketTO> basketList;
		
	/**
	 * DefaultConstructor
	 * @author Marco
	 * @date 19.05.2015
	 */
	public BasketListResponse() {

	}

	public List<BasketTO> getBasketList() {
		return basketList;
	}

	public void setBasketList(List<BasketTO> basketList) {
		this.basketList = basketList;
	}

}
