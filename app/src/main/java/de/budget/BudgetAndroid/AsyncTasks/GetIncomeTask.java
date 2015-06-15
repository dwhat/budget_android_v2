package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

/*
    * @Author Christopher
    * @Date 14.06.2015
    */
public class GetIncomeTask extends AsyncTask<String, Integer, IncomeListResponse>
{
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetIncomeTask(Context context, BudgetAndroidApplication myApp)
    {
        this.context = context;
        this.myApp = myApp;
    }


    @Override
    protected IncomeListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            IncomeListResponse myIncome = myApp.getBudgetOnlineService().getIncomes(myApp.getSession(), myApp);
            Integer rt =  myIncome.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myIncome;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    @Override
    protected void onPostExecute(IncomeListResponse result)
    {
        if(result != null)
        {
            if (result.getReturnCode() == 200){

                myApp.setIncome(result.getIncomeList());
                myApp.increaseInitialDataCounter();
                Log.d("INFO", "Liste der Einnahmen erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Einnahmen konnten nicht geladen werden.");

        }
    }
}