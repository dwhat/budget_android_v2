package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.Response.AmountListResponse;

/*
    * @Author Christopher
    * @Date 19.06.2015
    */
public class GetItemsAmountForCategoriesTask extends AsyncTask<String, Integer, AmountListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetItemsAmountForCategoriesTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
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
            AmountListResponse repsonse = myApp.getBudgetOnlineService().getItemsAmountForCategories(myApp.getSession());
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

                myApp.setItemsCategoriesAmount(result.getAmountList());
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