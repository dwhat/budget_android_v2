package de.budget.BudgetAndroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Random;

import de.budget.BudgetAndroid.AsyncTasks.GetBasketTask;
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
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();
        BigDecimal loss = new BigDecimal(0);



        // Animation des Schweins
        ImageView img_animation = (ImageView) findViewById(R.id.budget_euro);

        TranslateAnimation animation = new TranslateAnimation(30.0f, 30.0f, -50.0f, 620.0f);
        animation.setDuration(875);
        animation.setRepeatCount(10);
        animation.setRepeatMode(1);
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);
        img_animation.setVisibility(View.VISIBLE);

        // Ausführung der Tasks zum laden der initialen Daten

        TextView quote = (TextView) findViewById(R.id.quote);
        String[] quotes = getResources().getStringArray(R.array.quotes);
        int idx = new Random().nextInt(quotes.length);
        String random = (quotes[idx]);
        quote.setText(random);
        GetCategoriesTask categoriesTask = new GetCategoriesTask(this, myApp);
        categoriesTask.execute();
        GetVendorsTask vendorsTask = new GetVendorsTask(this, myApp);
        vendorsTask.execute();
        GetPaymentsTask paymentsTask = new GetPaymentsTask(this, myApp);
        paymentsTask.execute();
        GetBasketTask basketTask = new GetBasketTask(this, myApp);
        basketTask.execute();
        GetIncomeTask incomeTask = new GetIncomeTask(this, myApp);
        incomeTask.execute();


        // manuelle Berechnung der ersten Dashboard Seite
        for (int i= 0; i < myApp.getBasket().size(); i++){
            loss = loss.add(BigDecimal.valueOf(myApp.getBasket().get(i).getAmount()));
        }

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
