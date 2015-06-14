package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

/*
    * @Author Christopher
    * @Date 14.06.2015
    */
public class getIncomeTask extends AsyncTask<String, Integer, IncomeListResponse>
{
    private Context context;
    private static MainActivity activity;
    private static BudgetAndroidApplication myApp;

    public getIncomeTask(Context context, BudgetAndroidApplication myApp, MainActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
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

    protected void onPostExecute(IncomeListResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                myApp.setIncome(result.getIncomeList());
                Log.d("INFO", "Liste der Einnahmen erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Einnahmen konnten nicht geladen werden.");

        }
    }
}