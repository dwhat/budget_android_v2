package de.budget.BudgetAndroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.budget.BudgetAndroid.AsyncTasks.GetCategoriesTask;
import de.budget.BudgetAndroid.AsyncTasks.GetIncomeTask;
import de.budget.BudgetAndroid.AsyncTasks.GetPaymentsTask;
import de.budget.BudgetAndroid.AsyncTasks.GetVendorsTask;
import de.budget.R;

/**
 * @author christopher
 * @date 15.06,2015
 */
public class SyncActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        TextView quote = (TextView) findViewById(R.id.quote);
        String[] quotes = getResources().getStringArray(R.array.quotes);
        int idx = new Random().nextInt(quotes.length);
        String random = (quotes[idx]);
        quote.setText(random);
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
        GetCategoriesTask categorysTask = new GetCategoriesTask(this, myApp);
        categorysTask.execute();
        GetVendorsTask vendorsTask = new GetVendorsTask(this, myApp);
        vendorsTask.execute();
        GetPaymentsTask paymentsTask = new GetPaymentsTask(this, myApp);
        paymentsTask.execute();
        GetIncomeTask incomeTask = new GetIncomeTask(this, myApp);
        incomeTask.execute();

        ArrayList<AsyncTask> tasks = new ArrayList<>();
        tasks.add(categorysTask);
        tasks.add(vendorsTask);
        tasks.add(paymentsTask);
        tasks.add(incomeTask);
        myApp.setRunningTasks(tasks);

    }

    @Override
    protected void onStart(){
        super.onStart();
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
        ArrayList<AsyncTask> result = myApp.getRunningTasks();
/*
        for(int i=0; i< result.size(); i++) {
            while (result.get(i).getStatus() != AsyncTask.Status.FINISHED) {
                if(result.get(i).getStatus() == AsyncTask.Status.FINISHED){
                    myApp.increaseInitialDataCounter();
                }
            }
        }*/
        //Nächste Activity anzeigen
        Intent intent = new Intent(this,MainActivity.class);
        this.startActivity(intent);
        Log.d("INFO", "Initiale Daten wurden erfolgreich geladen.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sync, menu);
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
}
