package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Income.IncomeActivity;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetService.Response.ReturnCodeResponse;

/*
    * @Author Christopher
    * @Date 16.06.2015
    */
public class DeleteIncomeTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>
{
    private static BudgetAndroidApplication myApp;
    private static IncomeActivity activity;
    public static MainActivity nextActivity = new MainActivity();
    private Integer incomeId;
    private Context context;

    public DeleteIncomeTask(Context context, BudgetAndroidApplication myApp, IncomeActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }


    @Override
    protected ReturnCodeResponse doInBackground(Integer... params){
        if(params.length != 1)
            return null;
        incomeId = params[0];
        try {
            ReturnCodeResponse myResponse = myApp.getBudgetOnlineService().deleteIncome(myApp.getSession(), incomeId);
            Integer rt =  myResponse.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            return myResponse;
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
    protected void onPostExecute(ReturnCodeResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            if (result.getReturnCode() == 200){

                myApp.deleteIncome(incomeId);
                Log.d("INFO", "Einnahme erfolgreich gelöscht.");
                //Toast anzeigen
                CharSequence text = "Einnahme gelöscht!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                activity.startActivity(intent);
            }
        }
        else
        {
            Log.d("INFO", "Einnahme konnte nicht gelöscht werden.");
            CharSequence text = "Einnahme konnte nicht gelöscht werden!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}