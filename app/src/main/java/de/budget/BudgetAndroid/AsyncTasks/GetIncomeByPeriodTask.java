package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.AmountResponse;
import de.budget.BudgetService.Response.IncomeListResponse;

/*
    * @Author Christopher
    * @Date 18.06.2015
    */
public class GetIncomeByPeriodTask extends AsyncTask<String, Integer, AmountResponse>
{
    private static BudgetAndroidApplication myApp;

    public GetIncomeByPeriodTask(BudgetAndroidApplication myApp)
    {
        this.myApp = myApp;
    }


    @Override
    protected AmountResponse doInBackground(String... params){
        if(params.length != 2)
            return null;
        Integer period = Integer.parseInt(params[0]);

        try {
            AmountResponse response = myApp.getBudgetOnlineService().getIncomeByPeriod(myApp.getSession(), period);
            Integer rt =  response.getReturnCode();
            //Log.d("INFO", "Response" + rt.toString());
            return response;
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
    protected void onPostExecute(AmountResponse result)
    {
        if(result != null)
        {
            if (result.getReturnCode() == 200){
                Log.d("test",String.valueOf(result.getValue()));
                myApp.setIncomeLastPeriod(result.getValue());
                Log.d("INFO", "Einnahme für Periode erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Einnahmen konnten nicht geladen werden.");

        }
    }
}