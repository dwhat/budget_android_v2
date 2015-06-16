package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.R;

public class LossActivity extends ActionBarActivity {

    ListView listView;
    EditText editTextItemName;
    EditText editTextItemAmount;
    EditText editTextItemValue;
    Spinner spinnerCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);


        //editTextItemName = (EditText) findViewById(R.id.item_name);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            String lossName     = bundle.getString(Loss.NAME);
            String lossDate     = bundle.getString(Loss.DATE);
            String lossTotal    = bundle.getString(Loss.TOTAL);
            String lossNotice   = bundle.getString(Loss.NOTICE);

            Log.d(this.getClass().toString(), lossName);

            EditText editTextName   = (EditText) findViewById(R.id.loss_name);
            EditText editTextDate   = (EditText) findViewById(R.id.loss_date);
            EditText editTextTotal  = (EditText) findViewById(R.id.loss_value);
            EditText editTextNotice = (EditText) findViewById(R.id.loss_notice);

            Log.d(this.getClass().toString(), "Lade: " + editTextName.toString());

            editTextName.setText(lossName);
            editTextDate.setText(lossDate);
            editTextTotal.setText(lossTotal);
            editTextNotice.setText(lossNotice);
        }
    }
/*
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View rootView = super.onCreateView(name, context, attrs);

        // Author Mark
        // Createing Mock Objects
        // TODO: Holen der Vendors vom Server
        ArrayList<HashMap<String, String>> array = new ArrayList ();

        HashMap<String, String> firstItem = new HashMap();
        firstItem.put(Item.NAME,"Erstes Item");
        firstItem.put(Item.CATEGORY, "Haushalt");
        firstItem.put(Item.NOTICE, "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam");
        firstItem.put(Item.AMOUNT, "2x");
        firstItem.put(Item.VALUE, "0.50€");
        firstItem.put(Item.TOTAL, "1.00€");

        array.add(firstItem);

        // Starten des Array Adapters
        ItemArrayAdapter ArrayAdapter = new ItemArrayAdapter (this, R.layout.item_listview, array);
        // Listview ermitteln
        listView = (ListView) findViewById(R.id.listView_item);

        // ListView setzten mit entsprehcenden Objekten aus dem Adapter
        listView.setAdapter(ArrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap  item = (HashMap) listView.getItemAtPosition(position);

                editTextItemName.setText((String) item.get(Item.NAME));
                editTextItemAmount.setText((String) item.get(Item.AMOUNT));
                editTextItemValue.setText((String) item.get(Item.VALUE));
            }

        });


        return rootView;


    }*/


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
