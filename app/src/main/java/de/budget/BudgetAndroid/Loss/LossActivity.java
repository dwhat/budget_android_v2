package de.budget.BudgetAndroid.Loss;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.AsyncTasks.CreateOrUpdateBasketTask;
import de.budget.BudgetAndroid.AsyncTasks.DeleteBasketTask;
import de.budget.BudgetAndroid.AsyncTasks.DeleteIncomeTask;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetAndroid.Categories.CategorySpinnerAdapter;
import de.budget.BudgetAndroid.PaymentSpinnerAdapter;
import de.budget.BudgetAndroid.Vendors.VendorSpinnerAdapter;
import de.budget.BudgetAndroid.common.SwipeDismissListViewTouchListener;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.BudgetService.dto.CategoryTO;
import de.budget.BudgetService.dto.ItemTO;
import de.budget.BudgetService.dto.PaymentTO;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;

public class LossActivity extends ActionBarActivity {

    private BudgetAndroidApplication    myApp;
    private List <CategoryTO>           categories;
    private List <VendorTO>             vendors;
    private List <PaymentTO>            payments;
    private BasketTO                    basket;
    private static Date                 date;

    private EditText        editTextName;
    private static EditText editTextDate;
    private EditText        editTextTotal;
    private EditText        editTextNotice;
    private Spinner         spinnerPayment;
    private Spinner         spinnerVendor;

    private ListView    listView;
    private EditText    editTextItemName;
    private EditText    editTextItemAmount;
    private EditText    editTextItemValue;
    private Spinner     spinnerCategory;

    private CategorySpinnerAdapter  spinnerCategoryArrayAdapter;
    private VendorSpinnerAdapter    spinnerVendorArrayAdapter;
    private PaymentSpinnerAdapter   spinnerPaymentArrayAdapter;
    private ItemArrayAdapter        itemArrayAdapter;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loss);


        /*
         * Setup ApplikationTO
         */
        myApp       = (BudgetAndroidApplication) getApplication();
        categories  = myApp.getCategoriesByFormat(false);
        vendors     = myApp.getVendors();
        payments    = myApp.getPayments();
        date        = new Date();


        /*
         * Basket Details
         */
        editTextName   = (EditText) findViewById(R.id.loss_name);
        editTextDate   = (EditText) findViewById(R.id.loss_date);
        editTextTotal  = (EditText) findViewById(R.id.loss_value);
        editTextNotice = (EditText) findViewById(R.id.loss_notice);
        spinnerVendor  = (Spinner)  findViewById(R.id.loss_vendor);
        spinnerPayment = (Spinner)  findViewById(R.id.loss_payment);

        /*
         * Item Details
         */
        editTextItemName    = (EditText) findViewById(R.id.item_name);
        editTextItemAmount  = (EditText) findViewById(R.id.item_amount);
        editTextItemValue   = (EditText) findViewById(R.id.item_value);
        spinnerCategory     = (Spinner)  findViewById(R.id.item_category);
        listView            = (ListView) findViewById(R.id.listView_item);


        /*
         * Starten eines Vorhandenen Baskets
         */
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            int pos = bundle.getInt("POSITION");
            basket = myApp.getBasket().get(pos);


            /*
            * Set Basket Details
            */
            editTextName    .setText(basket.getName());
            editTextDate    .setText(dateFormat.format(basket.getPurchaseDate()));
            editTextTotal   .setText(String.valueOf(basket.getAmount()));
            editTextNotice  .setText(basket.getNotice());
            spinnerVendor   .setSelection(myApp.getVendors().indexOf(basket.getVendor()));
            spinnerPayment  .setSelection(myApp.getPayments().indexOf(basket.getPayment()));

            /*
            * Set Item Details
            */
            itemArrayAdapter = new ItemArrayAdapter(this, myApp, R.layout.listview_item, basket.getItems());

        } else {
            /*
            * Set Basket and Item Default Details
            */
            List<ItemTO> items = new ArrayList<>();
            itemArrayAdapter = new ItemArrayAdapter(this, myApp, R.layout.listview_item, items);
            editTextDate.setText(dateFormat.format(date));
        }

        /*
        * Set Basket References
        */
        spinnerCategoryArrayAdapter = new CategorySpinnerAdapter(this, R.layout.spinner_category, categories);
        spinnerVendorArrayAdapter = new VendorSpinnerAdapter(this, R.layout.spinner_vendor, vendors);
        spinnerPaymentArrayAdapter = new PaymentSpinnerAdapter(this, R.layout.spinner_payment, payments);

        spinnerCategory.setAdapter(spinnerCategoryArrayAdapter);
        spinnerVendor.setAdapter(spinnerVendorArrayAdapter);
        spinnerPayment.setAdapter(spinnerPaymentArrayAdapter);

        listView.setAdapter(itemArrayAdapter);

        /*
        * Setup Edit Item Details
        */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ItemTO item = (ItemTO) listView.getItemAtPosition(position);

            editTextItemName    .setText((String) item.getName());
            editTextItemAmount  .setText(String.valueOf(item.getQuantity()));
            editTextItemValue   .setText(String.valueOf(item.getPrice()));

            itemArrayAdapter.remove(item);
            itemArrayAdapter.notifyDataSetChanged();

            }

        });

        // Create a ListView-specific touch listener. ListViews are given special treatment because
        // by default they handle touches for their list items... i.e. they're in charge of drawing
        // the pressed state (the list selector), handling list item clicks, etc.
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        listView,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    itemArrayAdapter.remove(itemArrayAdapter.getItem(position));
                                }
                                itemArrayAdapter.notifyDataSetChanged();
                            }
                        });
        listView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        listView.setOnScrollListener(touchListener.makeScrollListener());
    }
    /*
     * Start the Actionbar Menu,
     * Delete Button invisible, wenn kein bestehender Basket geöffnet
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_loss, menu);
        if(basket==null) {
            menu.findItem(R.id.action_delete).setVisible(false);
        }
        return true;
    }

    /*
     * Bei auswahl eines Menü Items aus der Actionbar
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            save(null);
            return true;
        }else if (id == R.id.action_delete) {
            delete(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
     * Methode zum speichern des Objekts,
     * bedient sich der App und ruft einen AsyncTask auf der das Interface bedient
     */
    @Author(name="Mark")
    public void save(View v) {

        Toast.makeText(this, "Speichern", Toast.LENGTH_SHORT).show();

        int basketId = 0;

        if (basket != null) {
            basketId = basket.getId();
            Log.d("Update", basketId + " " + basket.getName());
        }

        Log.d("Update", String.valueOf(basketId));

        String          name        =                       editTextName.getText().toString();
        String          notice      =                       editTextNotice.getText().toString();
        Double          amount      = Double.parseDouble(editTextTotal.getText().toString());
        Long            purchaseDate=                       date.getTime();
        VendorTO        vendor      = (VendorTO)            spinnerVendor.getSelectedItem();
        PaymentTO       payment     = (PaymentTO)           spinnerPayment.getSelectedItem();
        List<ItemTO>    items       =                       itemArrayAdapter.getValues();

        Double itemSum = getItemSum(items);
        if(itemSum < amount) Toast.makeText(this, "Gesamtsumme stimmt nicht!", Toast.LENGTH_SHORT).show();

        Log.d("INFO", "Gesamt eingegeben: " + amount + " Gesamt berechnet " + itemSum);


        if(!"".equals(name) && !"".equals(amount) && !"".equals(purchaseDate) && vendor!=null && payment!=null)
        {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if(networkInfo != null && networkInfo.isConnected()){

                CreateOrUpdateBasketTask task = new CreateOrUpdateBasketTask(this,myApp);
                task.execute(basketId,  name, notice, amount, purchaseDate, payment.getId(), vendor.getId(), items);

            } else {
                Toast.makeText(this, "Keine Netzwerkverbindung! :(!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
        }
    }

    @Author(name="Mark")
    public void delete (View v) {

        Toast.makeText(this, "Löschen", Toast.LENGTH_SHORT).show();
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            DeleteBasketTask task = new DeleteBasketTask(this, myApp);
            task.execute(this.basket.getId());
        }
        else {
            CharSequence text = "Keine Netzwerkverbindung! :(";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }

    };

    /*
     *
     */
    @Author(name="Mark")
    public void add(View v){

        String name         = editTextItemName.getText().toString();
        String amount       = editTextItemAmount.getText().toString();
        String value        = editTextItemValue.getText().toString();
        CategoryTO category = (CategoryTO) spinnerCategory.getSelectedItem();
        ItemTO item         = new ItemTO();

        if (name.isEmpty()) Toast.makeText(this, "Bitte Item Namen eingeben!", Toast.LENGTH_SHORT).show();
        else {

            amount = validateInput(amount);
            value = validateInput(value);

            item.setName(name);
            item.setPrice(Double.parseDouble(value));
            item.setQuantity(Double.parseDouble(amount));
            item.setCategory(category.getId());

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
    public Double getItemSum(List<ItemTO> items) {
        Double itemSum = 0.0;
        Iterator<ItemTO> i = items.iterator();
        while(i.hasNext()) {
            ItemTO item = i.next();
            itemSum += item.getPrice() * item.getQuantity();
        }
        return itemSum;
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    /**
     * @Author Christopher
     * @date 14.06.2015
     */
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            date = new Date(year, month, day);
            editTextDate.setText(dateFormat.format(date));
        }
    }

}
