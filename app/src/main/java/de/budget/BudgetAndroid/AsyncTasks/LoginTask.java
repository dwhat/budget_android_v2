package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Login;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.UserLoginResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class LoginTask extends AsyncTask<String, Integer, UserLoginResponse>
{
    private Context context;
    private static Login activity;
    public static SyncActivity nextActivity = new SyncActivity();

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
        String md5 = new String();
        try {
            // PW MD5 Hash bilden
            MessageDigest mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(password.getBytes(), 0, password.length());
            md5 = new BigInteger(1, mdEnc.digest()).toString(16);
        }
        catch (NoSuchAlgorithmException e){
            Log.d("Error", e.getMessage());
        }
        try {
            UserLoginResponse myUser = myApp.getBudgetOnlineService().login(username, md5);
            Integer rt =  myUser.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
            // Speichern der Benutzerdaten
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", username);
            editor.putString("password", password);
            editor.apply();
            return myUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgessUpdate(Integer... values)
    {
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

                //Nächste Activity anzeigen
                Intent intent = new Intent(context,SyncActivity.class);
                activity.startActivity(intent);
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Login fehlgeschlagen!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}

