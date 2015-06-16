package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Login;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.ReturnCodeResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class LogoutTask extends AsyncTask<Integer, Integer, ReturnCodeResponse>
{
    private Context context;
    private static MainActivity activity;
    private static BudgetAndroidApplication myApp;

    public LogoutTask(Context context, BudgetAndroidApplication myApp, MainActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }

    @Override
    protected ReturnCodeResponse doInBackground(Integer... params){
        if(params.length != 1)
            return null;
        int sessionId = params[0];
        try {
            ReturnCodeResponse myUser = myApp.getBudgetOnlineService().logout(sessionId);
            Integer rt = myUser.getReturnCode();
            Log.d("INFO", rt.toString());
            return myUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
        //wird in diesem Beispiel nicht verwendet
    }

    protected void onPostExecute(ReturnCodeResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                myApp.reset();
                Intent intent = new Intent(activity.getBaseContext(), Login.class);
                activity.startActivity(intent);
            }
        }
        else
        {

        }
    }
}