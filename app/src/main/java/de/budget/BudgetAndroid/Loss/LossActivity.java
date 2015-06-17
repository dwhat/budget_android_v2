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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.BudgetService.dto.ItemTO;
import de.budget.R;

public class LossActivity extends ActionBarActivity {

    private ListView listView;
    private EditText editTextItemName;
    private EditText editTextItemAmount;
    private EditText editTextItemValue;
    private Spinner spinnerCategory;

    private ItemArrayAdapter itemArrayAdapter;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);


        editTextItemName = (EditText) findViewById(R.id.item_name);
        editTextItemAmount = (EditText) findViewById(R.id.item_amount);
        editTextItemValue = (EditText) findViewById(R.id.item_value);
        listView = (ListView) findViewById(R.id.listView_item);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            int pos = bundle.getInt("POSITION");

            BudgetAndroidApplication myApp = (BudgetAndroidApplication) getApplication();

            List<BasketTO> baskets = myApp.getBasket();

            BasketTO basket = baskets.get(pos);

            Log.d(this.getClass().toString(), "Show Basket: " + basket.toString());

            EditText editTextName = (EditText) findViewById(R.id.loss_name);
            EditText editTextDate   = (EditText) findViewById(R.id.loss_date);
            EditText editTextTotal  = (EditText) findViewById(R.id.loss_value);
            EditText editTextNotice = (EditText) findViewById(R.id.loss_notice);

            editTextName.setText(basket.getName());
            editTextDate.setText(dateFormat.format(basket.getPurchaseDate()));
            editTextTotal.setText(String.valueOf(basket.getAmount()));
            editTextNotice.setText(basket.getNotice());

            itemArrayAdapter =  new ItemArrayAdapter(this, R.layout.item_listview, basket.getItems());
        }




        listView.setAdapter(itemArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemTO item = (ItemTO) listView.getItemAtPosition(position);




                editTextItemName.setText((String) item.getName());
                editTextItemAmount.setText(String.valueOf(item.getQuantity()));
                editTextItemValue.setText(String.valueOf(item.getPrice()));

                itemArrayAdapter.remove(item);
                itemArrayAdapter.notifyDataSetChanged();

            }

        });
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

    /*
     *
     */
    @Author(name="Mark")
    public void add(View v){

        String name = editTextItemName.getText().toString();
        String amount = editTextItemAmount.getText().toString();
        String value = editTextItemValue.getText().toString();

        ItemTO item = new ItemTO();


        if (name.isEmpty()) Toast.makeText(this, "Bitte Item Namen eingeben!", Toast.LENGTH_SHORT).show();
        else {

            amount = validateInput(amount);
            value = validateInput(value);

            item.setName(name);
            item.setPrice(Double.parseDouble(value));
            item.setQuantity(Double.parseDouble(amount));

            BigDecimal sum = round( (Float.parseFloat( amount ) * Float.parseFloat( value )) , 2);

            Log.d(this.getClass().toString(),
                    "ADD= " +
                            Item.NAME + ": " + name + ", " +
                            Item.AMOUNT + ": " + amount + ", " +
                            Item.VALUE + ": " + value
            );

            itemArrayAdapter.add(item);
        }

    }

    @Author(name="Mark")
    public static BigDecimal round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    @Author(name="Mark")
    private ArrayList<HashMap<String, String>> mock() {

        ArrayList <HashMap<String, String>> array = new ArrayList ();

        HashMap<String, String> firstItem = new HashMap();
        firstItem.put(Item.NAME,"Erstes Item");
        firstItem.put(Item.VALUE, "1.00");
        firstItem.put(Item.AMOUNT, "2");
        firstItem.put(Item.TOTAL, "3.00");

        array.add(firstItem);

        return array;
    }

    private String validateInput (String value) {
        if(value.isEmpty() || Double.parseDouble(value) <= 0) {
            Toast.makeText(this, "Input geändert auf 1", Toast.LENGTH_SHORT).show();
            value = "1";

        }
        return value;
    }

}
