package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.math.BigDecimal;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.IncomeListResponse;
import de.budget.BudgetService.Response.VendorListResponse;

    /**
     *  * <p>  Asynchroner Task um eine Liste Incomes zu erhalten.
     *
     *      Der Task nimmt keine Parameter entgegen. Der Task schickt die aktuelle SessionID zur Ermittlung
     *      der Incomes.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     *  </p>
    * @Author Christopher
    * @Date 14.06.2015
    */
public class GetIncomeTask extends AsyncTask<String, Integer, IncomeListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetIncomeTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
    {
        this.context = context;
        this.myApp = myApp;
        this.listener = listener;
    }


    @Override
    protected IncomeListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            IncomeListResponse myIncome = myApp.getBudgetOnlineService().getIncomes(myApp.getSession());
            Integer rt =  myIncome.getReturnCode();

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
            if (result.getReturnCode() == 200 || result.getReturnCode() == 404 ){

                myApp.setIncome(result.getIncomeList());
                myApp.increaseInitialDataCounter();
                Log.d("INFO", "Liste der Einnahmen erfolgreich angelegt.");

                // Berechnung der ersten Seite im Dashboard
                BigDecimal income = new BigDecimal(0);
                for (int i= 0; i < myApp.getIncome().size(); i++){
                    income = income.add(BigDecimal.valueOf(myApp.getIncome().get(i).getAmount()*myApp.getIncome().get(i).getQuantity()));
                }
                myApp.setIncomeLastPeriod(income.doubleValue());
                listener.onTaskCompleted(true);

            }
        }
        else
        {
            Log.d("INFO", "Einnahmen konnten nicht geladen werden.");
            listener.onTaskCompleted(false);

        }
    }
}