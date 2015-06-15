package de.budget.BudgetAndroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;

import de.budget.BudgetAndroid.AsyncTasks.CreateOrUpdatePaymentTask;
import de.budget.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();

        if(myApp.getPayments().size()== 3) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("paymentsCash", myApp.getPaymentByName("Cash").isActive());
            editor.putBoolean("paymentsActiveGiro", myApp.getPaymentByName("Giro").isActive());
            editor.putString("paymentsNumberGiro", myApp.getPaymentByName("Giro").getNumber());
            editor.putString("paymentsBicGiro", myApp.getPaymentByName("Giro").getBic());
            editor.putBoolean("paymentsActiveCredit", myApp.getPaymentByName("Credit").isActive());
            editor.putString("paymentsNumberCredit", myApp.getPaymentByName("Credit").getNumber());
            editor.putString("paymentsIdCash", String.valueOf(myApp.getPaymentByName("Cash").getId()));
            editor.putString("paymentsIdGiro", String.valueOf(myApp.getPaymentByName("Giro").getId()));
            editor.putString("paymentsIdCredit", String.valueOf(myApp.getPaymentByName("Credit").getId()));
            editor.apply();
        }

    }

    @Override
    protected void onStop(){
        super.onStop();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean paymentsCash = prefs.getBoolean("paymentsCash", true);
        boolean paymentsActiveGiro = prefs.getBoolean("paymentsActiveGiro", false);
        String paymentsNumberGiro = prefs.getString("paymentsNumberGiro", "");
        String paymentsBicGiro = prefs.getString("paymentsBicGiro", "");
        boolean paymentsActiveCredit = prefs.getBoolean("paymentsActiveCredit", false);
        String paymentsNumberCredit = prefs.getString("paymentsNumberCredit", "");
        String paymentsIdCash = prefs.getString("paymentsIdCash", "0");
        String paymentsIdGiro = prefs.getString("paymentsIdGiro", "0");
        String paymentsIdCredit = prefs.getString("paymentsIdCredit", "0");

        String tmpActive;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Anlegen/Update Cash
            if(!paymentsCash){tmpActive = "false";}else{tmpActive= "true";};
            CreateOrUpdatePaymentTask taskCash = new CreateOrUpdatePaymentTask(this, myApp, this);
            taskCash.execute("Cash", "0", "0", tmpActive, paymentsIdCash);
            // Anlegen/Update Giro
            if(!paymentsActiveGiro){tmpActive = "false";}else{tmpActive= "true";};
            CreateOrUpdatePaymentTask taskGiro = new CreateOrUpdatePaymentTask(this, myApp, this);
            taskGiro.execute("Giro", paymentsNumberGiro, paymentsBicGiro, tmpActive, paymentsIdGiro);
            // Anlegen/Update Credit
            if(!paymentsActiveCredit){tmpActive = "false";}else{tmpActive= "true";};
            CreateOrUpdatePaymentTask taskCredit = new CreateOrUpdatePaymentTask(this, myApp, this);
            taskCredit.execute("Credit", paymentsNumberCredit, "0", tmpActive, paymentsIdCredit);
        }
        Log.d("INFO", "Zahlungsarten werden geupdated");
    }
}