package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.AmountListResponse;

    /**
     *  * <p>  Asynchroner Task um eine Liste Incomes der Categorys zu erhalten.
     *
     *      Der Task nimmt keine Parameter entgegen. Der Task schickt die aktuelle SessionID zur Ermittlung
     *      der aktiven Incomes je Category zum Interface.
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     *  </p>
    * @Author Christopher
    * @Date 19.06.2015
    */
public class GetIncomeAmountForCategoriesTask extends AsyncTask<String, Integer, AmountListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetIncomeAmountForCategoriesTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
    {
        this.context = context;
        this.myApp = myApp;
        this.listener = listener;
    }


    @Override
    protected AmountListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            AmountListResponse repsonse = myApp.getBudgetOnlineService().getIncomesAmountForCategories(myApp.getSession());
            Integer rt =  repsonse.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return repsonse;
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
    protected void onPostExecute(AmountListResponse result)
    {
        if(result != null)
        {
            if (result.getReturnCode() == 200){

                myApp.setIncomeCategoriesAmount(result.getAmountList());
                listener.onTaskCompleted(true);
                Log.d("INFO", "Diagrammdaten erfolgreich geladen.");
            }
            else if(result.getReturnCode() == 404) {
                listener.onTaskCompleted(false);
            }
        }
        else
        {
            Log.d("INFO", "Diagrammdaten konnten nicht geladen.");
            listener.onTaskCompleted(false);
        }
    }
}