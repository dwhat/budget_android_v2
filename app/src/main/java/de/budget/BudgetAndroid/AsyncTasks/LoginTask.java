package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Login;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetService.Response.UserLoginResponse;
import de.budget.BudgetAndroid.AsyncTasks.*;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class LoginTask extends AsyncTask<String, Integer, UserLoginResponse>
{
    private Context context;
    private static Login activity;
    public static MainActivity nextActivity = new MainActivity();

    public LoginTask(Context context, Login pActivity)
    {
        this.context = context;
        this.activity = pActivity;
    }

    @Override
    protected UserLoginResponse doInBackground(String... params){
        if(params.length != 2)
            return null;
        String username = params[0];
        String password = params[1];
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) activity.getApplication();
        try {
            UserLoginResponse myUser = myApp.getBudgetOnlineService().login(username, password);
            Integer rt =  myUser.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            // Speichern der Benutzerdaten
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.commit();
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

    protected void onPostExecute(UserLoginResponse result)
    {
        int duration = Toast.LENGTH_SHORT;
        if(result != null)
        {
            //erfolgreich eingeloggt
            if (result.getReturnCode() == 200){

                BudgetAndroidApplication myApp = (BudgetAndroidApplication) activity.getApplication();
                myApp.setSession(result.getSessionId());
                Log.d("INFO", "Login erfolgreich, SessionId: " + myApp.getSession());
                //Toast anzeigen
                CharSequence text = "Login erfolgreich!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                // Daten Holen
                getCategorysTask categorysTask = new getCategorysTask(context, myApp, nextActivity);
                categorysTask.execute();
                getVendorsTask vendorsTask = new getVendorsTask(context, myApp, nextActivity);
                vendorsTask.execute();

                //Nächste Activity anzeigen
                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra(MainActivity.FRAGMENT_NAVIGATION,0);
                activity.startActivity(intent);
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Login fehlgeschlagen!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // nur für entwicklung, muss wieder weg!
            Intent intent = new Intent(context, MainActivity.class);
            activity.startActivity(intent);
        }
    }
}

