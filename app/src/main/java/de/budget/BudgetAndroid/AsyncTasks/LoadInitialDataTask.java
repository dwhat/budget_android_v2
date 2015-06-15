package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class LoadInitialDataTask extends AsyncTask<String, Integer, Boolean>
{
    private Context context;
    private static MainActivity activity;
    private static BudgetAndroidApplication myApp;
    public static MainActivity nextActivity = new MainActivity();

    public LoadInitialDataTask(Context context, BudgetAndroidApplication myApp, MainActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }


    @Override
    protected Boolean doInBackground(String... params){
        try {
            GetCategorysTask categorysTask = new GetCategorysTask(context, myApp, nextActivity);
            categorysTask.execute();
            GetVendorsTask vendorsTask = new GetVendorsTask(context, myApp, nextActivity);
            vendorsTask.execute();
            GetPaymentsTask paymentsTask = new GetPaymentsTask(context, myApp, nextActivity);
            paymentsTask.execute();
            GetIncomeTask incomeTask = new GetIncomeTask(context, myApp, nextActivity);
            incomeTask.execute();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    protected void onProgessUpdate(Integer... values)
    {
        Intent intent = new Intent(context, SyncActivity.class);
        activity.startActivity(intent);
    }

    protected void onPostExecute(boolean result)
    {
        if(result != false)
        {
            Log.d("INFO", "Initiale Daten erfolgreich geladen.");
        }
        else
        {
            Log.d("INFO", "Initiale Daten konnten nicht geladen werden.");

        }
    }
}