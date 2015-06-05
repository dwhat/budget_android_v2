package de.budget.BudgetAndroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.budget.BudgetService.Response.UserLoginResponse;
import de.budget.R;


public class Register extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void register(View view){
        EditText txtUsername = (EditText) findViewById(R.id.username);
        EditText txtPassword = (EditText) findViewById(R.id.password);
        EditText txtEmail = (EditText) findViewById(R.id.email);
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String email = txtEmail.getText().toString();

        if(!"".equals(username) && !"".equals(password) && !"".equals(email))
        {
            RegisterTask task = new RegisterTask(this);
            //Proxy asynchron aufrufen
            task.execute(username, password, email);
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Bitte alle Felder ausfüllen!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    private class RegisterTask extends AsyncTask<String, Integer, UserLoginResponse>
    {
        private Context context;

        //Dem Konstruktor der Klasse wird der aktuelle Kontext der Activity übergeben
        //damit auf die UI-Elemente zugegriffen werden kann und Intents gestartet werden können, usw.
        public RegisterTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected UserLoginResponse doInBackground(String... params){
            if(params.length != 3)
                return null;
            String username = params[0];
            String password = params[1];
            String email = params[2];
            BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
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
                if (result.getReturnCode() == 0){

                    BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
                    myApp.setSession(result.getSessionId());
                    Log.d("INFO", "Registrierung erfolgreich, SessionId: " + myApp.getSession());
                    //Toast anzeigen
                    CharSequence text = "Registrierung erfolgreich!";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    //Nächste Activity anzeigen
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
            else
            {
                //Toast anzeigen
                CharSequence text = "Registrierung fehlgeschlagen!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
