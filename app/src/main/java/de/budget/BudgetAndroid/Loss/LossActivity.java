package de.budget.BudgetAndroid.Loss;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.R;

public class LossActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String name = bundle.getString("LOSS_NAME");
            EditText editText = (EditText) findViewById(R.id.loss_name);
            editText.setText(name);
        }
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
            save(null);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * Methode zum speichern des Objekts
     */
    @Author(name="Mark")
    public void save(View v) {
        showDialog();
        //TODO Die eingengeben Werte an den Server schicken
        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

    }

    /*
     * Zeige ein Dialog an um den Namen der Ausgabe und den Wert zu ermitteln
     */
    @Author(name="Mark")
    private void showDialog() {
        FragmentManager fm = getSupportFragmentManager();
        LossDialog lossDialog = new LossDialog();
        lossDialog.show(fm, "activity_loss_dialog");
    }
}