package de.budget.BudgetAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import de.budget.BudgetService.Response.CategoryListResponse;
import de.budget.R;
import android.util.Log;
import de.budget.BudgetAndroid.AsyncTasks.*;

/**
 * @author christopher
 * @date 01.06,2015
 */
public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefUsername = prefs.getString("username", "");
        EditText txtUserName = (EditText) findViewById(R.id.username);
        txtUserName.setText(prefUsername);

        String prefPassword = prefs.getString("password", "");
        EditText txtPassword = (EditText) findViewById(R.id.password);
        txtPassword.setText(prefPassword);
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


        EditText txtUsername = (EditText) findViewById(R.id.username);
        EditText txtPassword = (EditText) findViewById(R.id.password);
        String username = txtUsername.getText().toString().toLowerCase();
        String password = txtPassword.getText().toString();

        if(!"".equals(username) && !"".equals(password))
        {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){
                LoginTask loginTask = new LoginTask(view.getContext(), this);
                loginTask.execute(username, password);
            }
            else {
                CharSequence text = "Keine Netzwerkverbindung! :(";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Fehlende Logindaten bitte in den Einstellungen eintragen!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }

    /** Called when the user clicks the Register button */
    public void register(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }


}
