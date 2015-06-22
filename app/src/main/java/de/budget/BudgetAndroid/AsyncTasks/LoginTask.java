package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapFault;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.LoginActivity;
import de.budget.BudgetAndroid.SyncActivity;
import de.budget.BudgetService.Response.UserLoginResponse;

    /**
     *  * <p>  Asynchroner Task um den User zu autentisieren.
     *
     *      Der Task nimmt zwei Parameter entgegen.
     *      Das übergebene Passwort wird mittels MD5 gehasht und so zusammen mit dem Usernamen an den Server geschickt.
     *
     *
     *      Die Antwort des Servers wird in einer Response gespeichert.
     *
     *      Nach Abschluss des Tasks wird die Response geprüft und je nach ReturnCode entschieden
     *      welche Interaktion durchgeführt werden soll.
     *  </p>
    * @Author Christopher
    * @Date 09.06.2015
    */
public class LoginTask extends AsyncTask<String, Integer, UserLoginResponse>
{
    private Context context;
    private static LoginActivity activity;
    public static SyncActivity nextActivity = new SyncActivity();

    private String username;
    private String password;

    public LoginTask(Context context, LoginActivity pActivity)
    {
        this.context = context;
        this.activity = pActivity;
    }

    @Override
    protected UserLoginResponse doInBackground(String... params) {
        if(params.length != 2)
            return null;
        username = params[0];
        password = params[1];
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

                //Log.d("INFO", "Returncode: " + rt.toString());
                // Speichern der Benutzerdaten
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity.getBaseContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();


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

