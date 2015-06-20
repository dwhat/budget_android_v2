package de.budget.BudgetService.Response;

import java.util.List;

import de.budget.BudgetService.dto.ObjectTO;
import de.budget.BudgetService.dto.VendorTO;

/**
 * Created by mark on 20/06/15.
 */
public abstract class ObjectListResponse extends ReturnCodeResponse{


    private static final long serialVersionUID = -5754928488884226775L;

    private List<ObjectTO> objectList;

    /**
     * DefaultConstructor
     * @author Marco
     * @date 19.05.2015
     */
    public ObjectListResponse() {

    }

    public List<ObjectTO> getObectList() {
        return objectList;
    }

    public void setObjectList(List<ObjectTO> objectList) {
        this.objectList = objectList;
    }

    public void set(List<ObjectTO> objectList) {
        this.objectList = objectList;
    }

}
