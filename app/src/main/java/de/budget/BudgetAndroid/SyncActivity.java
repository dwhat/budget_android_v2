package de.budget.BudgetAndroid;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.budget.BudgetAndroid.AsyncTasks.GetBasketTask;
import de.budget.BudgetAndroid.AsyncTasks.GetCategoriesTask;
import de.budget.BudgetAndroid.AsyncTasks.GetIncomeTask;
import de.budget.BudgetAndroid.AsyncTasks.GetPaymentsTask;
import de.budget.BudgetAndroid.AsyncTasks.GetVendorsTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.R;

/**
 * <p>Ein Wartebildschirm bis die Synchronisation mti dem Server abgeschlossen ist.</p>
 * @author christopher
 * @date 15.06,2015
 */
public class SyncActivity extends ActionBarActivity implements OnTaskCompleted{
    private int loadCounter = 0;
    private ImageView logo;
    private Map<Integer, Integer> piggery = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();

        // Füllen des Schweinestalls

        piggery.put(1, R.drawable.budget_intro_1);
        piggery.put(2, R.drawable.budget_intro_2);
        piggery.put(3, R.drawable.budget_intro_3);
        piggery.put(4, R.drawable.budget_intro_4);
        piggery.put(5, R.drawable.budget_intro_5);

        // Animation des Schweins
        ImageView img_animation = (ImageView) findViewById(R.id.budget_euro);
        logo = (ImageView) findViewById(R.id.budget_logo);

        TranslateAnimation animation = new TranslateAnimation(30.0f, 30.0f, -50.0f, 620.0f);
        animation.setDuration(875);
        animation.setRepeatCount(10);
        animation.setRepeatMode(1);
        animation.setFillAfter(true);
        img_animation.startAnimation(animation);
        img_animation.setVisibility(View.VISIBLE);

        // Zufällige Auswahl des Spruchs

        TextView quote = (TextView) findViewById(R.id.quote);
        String[] quotes = getResources().getStringArray(R.array.quotes);
        int idx = new Random().nextInt(quotes.length);
        String random = (quotes[idx]);
        quote.setText(random);

        // Ausführung der Tasks zum laden der initialen Daten

        GetCategoriesTask categoriesTask = new GetCategoriesTask(this, myApp, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object o) {
                loadCounter++;
                logo.setImageResource(piggery.get(loadCounter));
            }
        });
        categoriesTask.execute();
        GetVendorsTask vendorsTask = new GetVendorsTask(this, myApp, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object o) {
                loadCounter++;
                logo.setImageResource(piggery.get(loadCounter));
            }
        });
        vendorsTask.execute();
        GetPaymentsTask paymentsTask = new GetPaymentsTask(this, myApp, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object o) {
                loadCounter++;
                logo.setImageResource(piggery.get(loadCounter));
            }
        });
        paymentsTask.execute();
        GetBasketTask basketTask = new GetBasketTask(this, myApp, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object o) {
                loadCounter++;
                logo.setImageResource(piggery.get(loadCounter));
            }
        });
        basketTask.execute();
        GetIncomeTask incomeTask = new GetIncomeTask(this, myApp, new OnTaskCompleted() {
            @Override
            public void onTaskCompleted(Object o) {
                loadCounter++;
                logo.setImageResource(piggery.get(loadCounter));
            }
        });
        incomeTask.execute();

    }

    @Override
    public void onTaskCompleted(Object o) {
        loadCounter++;
        logo.setImageResource(piggery.get(loadCounter));
        //Log.d("INFO", "Schwein Nr : " + loadCounter);
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
