package de.budget.BudgetAndroid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import de.budget.R;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    /** Called when the user clicks the Login button */
    public void login(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Register button */
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private class LoginTask extends AsyncTask<String, Integer, User>
    {
        private Context context;

        //Dem Konstruktor der Klasse wird der aktuelle Kontext der Activity übergeben
        //damit auf die UI-Elemente zugegriffen werden kann und Intents gestartet werden können, usw.
        public LoginTask(Context context)
        {
            this.context = context;
        }

        @Override
        protected User doInBackground(String... params){
            if(params.length != 2)
                return null;
            String username = params[0];
            String password = params[1];
            XbankAndroidApplication myApp = (XbankAndroidApplication) getApplication();
            try {
                Customer myCustomer = myApp.getXbankOnlineService().login(username, password);
                return myCustomer;
            } catch (InvalidLoginException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgessUpdate(Integer... values)
        {
            //wird in diesem Beispiel nicht verwendet
        }

        protected void onPostExecute(Customer result)
        {
            if(result != null)
            {
                //erfolgreich eingeloggt
                XbankAndroidApplication myApp = (XbankAndroidApplication) getApplication();
                myApp.setUser(result);

                //Toast anzeigen
                CharSequence text = "Login erfolgreich! Angemeldeter Benutzername: " + result.getUserName();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //Nächste Activity anzeigen
                Intent i = new Intent(context, BankingActivity.class);
                startActivity(i);
            }
            else
            {
                //Toast anzeigen
                CharSequence text = "Login fehlgeschlagen!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }
    }
}
