package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Login;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.CategoryListResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class getCategorysTask extends AsyncTask<String, Integer, CategoryListResponse>
{
    private Context context;
    private static MainActivity activity;
    private static BudgetAndroidApplication myApp;

    public getCategorysTask(Context context, BudgetAndroidApplication myApp, MainActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }


    @Override
    protected CategoryListResponse doInBackground(String... params){
        if(params.length != 0)
            return null;

        try {
            CategoryListResponse myCategorys = myApp.getBudgetOnlineService().getCategorys(myApp.getSession());
            Integer rt =  myCategorys.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
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

    protected void onPostExecute(CategoryListResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                myApp.setCategories(result.getCategoryList());
                Log.d("INFO", "KategorieListe erfolgreich angelegt.");
            }
        }
        else
        {
            Log.d("INFO", "Kategorien konnten nicht geladen werden.");

        }
    }
}