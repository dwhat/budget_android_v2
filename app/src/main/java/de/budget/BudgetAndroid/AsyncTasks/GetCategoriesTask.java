package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Login;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.CategoryListResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class GetCategoriesTask extends AsyncTask<String, Integer, CategoryListResponse>
{
    private OnTaskCompleted listener = null;
    private Context context;
    private static BudgetAndroidApplication myApp;

    public GetCategoriesTask(Context context, BudgetAndroidApplication myApp, OnTaskCompleted listener)
    {
        this.context = context;
        this.myApp = myApp;
        this.listener = listener;
    }


    @Override
    protected CategoryListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            CategoryListResponse myCategorys = myApp.getBudgetOnlineService().getCategorys(myApp.getSession());
            Integer rt =  myCategorys.getReturnCode();
            //Log.d("INFO", "Returncode: " + rt.toString());
            return myCategorys;
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
    protected void onPostExecute(CategoryListResponse result)
    {
        if(result != null)
        {
            if (result.getReturnCode() == 200){

                myApp.setCategories(result.getCategoryList());
                myApp.increaseInitialDataCounter();
                listener.onTaskCompleted(true);
                Log.d("INFO", "KategorieListe erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Kategorien konnten nicht geladen werden.");
            listener.onTaskCompleted(false);
        }
    }
}