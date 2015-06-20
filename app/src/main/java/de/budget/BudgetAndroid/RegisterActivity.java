package de.budget.BudgetAndroid;


import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.budget.R;
import de.budget.BudgetAndroid.AsyncTasks.RegisterTask;


/*
    * @Author Christopher
    * @Date 09.06.2015
    */
public class RegisterActivity extends ActionBarActivity {

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
        int duration = Toast.LENGTH_SHORT;
        EditText txtUsername = (EditText) findViewById(R.id.username);
        EditText txtPassword = (EditText) findViewById(R.id.password);
        EditText txtEmail = (EditText) findViewById(R.id.email);
        String username = txtUsername.getText().toString().toLowerCase();
        String password = txtPassword.getText().toString();
        String email = txtEmail.getText().toString();



        if(!"".equals(username) && !"".equals(password) && !"".equals(email))
        {
            if( password.length() > 7){
                if(isEmailValid(email)){
                    BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
                    RegisterTask task = new RegisterTask(view.getContext(), myApp, this);
                    task.execute(username, password, email);
                }
                else{
                    CharSequence text = "Bitte gültige Email eingeben!";
                    Toast toast = Toast.makeText(this, text, duration);
                    toast.show();
                }
            }
            else {
                CharSequence text = "Bitte Passwort größer sieben Zeichen wählen!";
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }
        }
        else
        {
            //Toast anzeigen
            CharSequence text = "Bitte alle Felder ausfüllen!";
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    }

    public boolean isEmailValid(String email)
    {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence emailToCheck = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(emailToCheck);

        if(matcher.matches())
            return true;
        else
            return false;
    }


}
