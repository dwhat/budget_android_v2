package de.budget.BudgetAndroid.AsyncTasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Logout;
import de.budget.BudgetAndroid.MainActivity;
import de.budget.BudgetAndroid.Register;
import de.budget.BudgetService.Response.UserLoginResponse;

/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class RegisterTask extends AsyncTask<String, Integer, UserLoginResponse>
{
    private Context context;
    private static Register activity;
    private static BudgetAndroidApplication myApp;
    public static MainActivity nextActivity = new MainActivity();


    public RegisterTask(Context context, BudgetAndroidApplication myApp, Register pActivity)
    {
        this.context = context;
        this.activity = pActivity;
        this.myApp = myApp;
    }
    @Override
    protected UserLoginResponse doInBackground(String... params){
        if(params.length != 3)
            return null;
        String username = params[0];
        String password = params[1];
        String email = params[2];
        try {
            UserLoginResponse myUser = myApp.getBudgetOnlineService().setUser(username, password,email);
            Integer rt =  myUser.getReturnCode();
            Log.d("INFO", "Returncode: " + rt.toString());
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

                myApp.setSession(result.getSessionId());
                Log.d("INFO", "Registrierung erfolgreich, SessionId: " + myApp.getSession());
                //Toast anzeigen
                CharSequence text = "Registrierung erfolgreich!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                // Daten holen
                getCategorysTask categorysTask = new getCategorysTask(context, myApp, nextActivity);
                categorysTask.execute();
                getVendorsTask vendorsTask = new getVendorsTask(context, myApp, nextActivity);
                vendorsTask.execute();
                //Nächste Activity anzeigen
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(MainActivity.FRAGMENT_NAVIGATION,0);
                activity.startActivity(intent);
            }
            else if(result.getReturnCode() == 409){
                CharSequence text = "Registrierung fehlgeschlagen, User bereits vorhanden!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else if(result.getReturnCode() == 500){
                CharSequence text = "Registrierung fehlgeschlagen, Passwort zu kurz!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            else{
                CharSequence text = "Registrierung fehlgeschlagen!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
        else
        {
            CharSequence text = "Registrierung fehlgeschlagen!";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}