package de.budget.BudgetService.dto;

import java.io.Serializable;


/**
 * Dto Object to transfer a double value, which belongs to a special id
 * 
 * @author Marco
 * @date 19.06.2015
 *
 *
 */
public class AmountTO implements Serializable {

	private static final long serialVersionUID = -255977161244602475L;
	
	private double value;
	
	private String name;
	
	/**
	 * Default Constructor
	 */
	public AmountTO() {
		
	}
	
	/**
	 * Constructor
	 */
	public AmountTO(String name, double value) {
		this.setName(name);
		this.value = value;
	}
	
	public double getValue() {
		return this.value;
	}
	
	
	public void setValue(double value) {
		this.value = value;
		
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



}