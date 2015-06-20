package de.budget.BudgetAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import de.budget.BudgetAndroid.common.NetworkCommon;
import de.budget.BudgetAndroid.common.ToastCommon;
import de.budget.R;
import de.budget.BudgetAndroid.AsyncTasks.*;

/**
 * @author christopher
 * @date 01.06,2015
 */
public class LoginActivity extends ActionBarActivity {

    private EditText txtUserName;
    private EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String prefUsername = prefs.getString("username", "");
        String prefPassword = prefs.getString("password", "");

        txtUserName = (EditText) findViewById(R.id.username);
        txtUserName.setText(prefUsername);

        txtPassword = (EditText) findViewById(R.id.password);
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


        this.txtUserName    = (EditText) findViewById(R.id.username);
        this.txtPassword    = (EditText) findViewById(R.id.password);
        String username     = txtUserName.getText().toString().toLowerCase();
        String password     = txtPassword.getText().toString();

        if(!"".equals(username) && !"".equals(password))
        {
            if(NetworkCommon.getStatus(this))
                new LoginTask(view.getContext(), this).execute(username, password);
            else
                ToastCommon.NetworkMissing(this);
        }
        else
            Toast.makeText(this, getResources().getString(R.string.login_missing_error), Toast.LENGTH_SHORT).show();

    }

    /** Called when the user clicks the Register button */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


}
