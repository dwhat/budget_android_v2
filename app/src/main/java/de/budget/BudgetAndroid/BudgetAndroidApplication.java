package de.budget.BudgetAndroid;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import de.budget.BudgetService.BudgetOnlineService;

/**
 * @Author Christopher
 * @date 01.06.2015
 */
public class BudgetAndroidApplication extends Application{
    private int sessionId;
    private BudgetOnlineService budgetOnlineService;

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


    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
