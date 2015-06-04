package de.budget.BudgetAndroid.Loss;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import de.budget.BudgetAndroid.MainActivity;
import de.budget.R;

public class LossNew extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss_new);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loss_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveLossNew();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveLossNew() {
        //TODO Die eingengeben Werte an den Server schicken
        Toast.makeText(this, "Speiechern", Toast.LENGTH_SHORT).show();

        //Nächste Activity anzeigen
        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = intent.getExtras();
        bundle.putString("class", this.getClass().toString());
        startActivity(intent, bundle);
    }
}
