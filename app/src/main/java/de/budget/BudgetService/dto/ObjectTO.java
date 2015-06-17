package de.budget.BudgetService.dto;

import java.io.Serializable;

/**
 * Created by mark on 17/06/15.
 */
public abstract class ObjectTO implements Serializable {


    private static final long serialVersionUID = 1L;
    private int id;

    public ObjectTO() {

    }

    public ObjectTO(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
