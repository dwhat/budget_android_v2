package de.budget.BudgetAndroid;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetService.BudgetOnlineService;
import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.BudgetService.dto.CategoryTO;

/**
 * @Author Christopher
 * @date 01.06.2015
 */
public class BudgetAndroidApplication extends Application{
    private int sessionId;
    private BudgetOnlineService budgetOnlineService;
    private List<CategoryTO> categories;

    public BudgetAndroidApplication() {
        this.budgetOnlineService = new BudgetOnlineServiceImpl();
    }

    public int getSession() {
        return this.sessionId;
    }

    public void setSession(int sessionId) {
        this.sessionId = sessionId;
    }

    public BudgetOnlineService getBudgetOnlineService() {
        return this.budgetOnlineService;
    }

    public void setCategories(List<CategoryTO> list){
        this.categories = list;
    }

    public List<CategoryTO> getCategories(){
        return this.categories;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
