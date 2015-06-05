package de.budget.BudgetService.Response;

import de.budget.BudgetService.dto.VendorTO;


/**
 * Class for the Vendor response
 * @author Marco
 * @date 19.05.2015
 */
public class VendorResponse extends ReturnCodeResponse {

	private static final long serialVersionUID = 1L;
		
	private VendorTO vendorTo;

	/**
	* @author Marco
	* @date 19.05.2015
	* Default Constructor
	*/
	public VendorResponse() {
			
	}

	/**
	 * @return the vendorTo
	 */
	public VendorTO getVendorTo() {
		return vendorTo;
	}

	/**
	 * @param vendorTo the vendorTo to set
	 */
	public void setVendorTo(VendorTO vendorTo) {
		this.vendorTo = vendorTo;
	}
}
