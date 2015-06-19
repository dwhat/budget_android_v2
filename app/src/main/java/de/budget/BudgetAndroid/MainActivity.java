package de.budget.BudgetAndroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.AsyncTasks.GetItemsTask;
import de.budget.BudgetAndroid.AsyncTasks.LogoutTask;
import de.budget.BudgetAndroid.AsyncTasks.OnTaskCompleted;
import de.budget.BudgetAndroid.Categories.CategoryAnalysisFragment;
import de.budget.BudgetAndroid.Categories.CategoryFragment;
import de.budget.BudgetAndroid.Categories.CategoryListFragment;
import de.budget.BudgetAndroid.Categories.CategoryActivity;
import de.budget.BudgetAndroid.Dashboard.DashboardFragment;
import de.budget.BudgetAndroid.Dashboard.DashboardHomeFragment;
import de.budget.BudgetAndroid.Dashboard.DashboardIncomeAndLossAnalysisFragment;
import de.budget.BudgetAndroid.Dashboard.DashboardIncomeFragment;
import de.budget.BudgetAndroid.Income.IncomeAnalysisFragment;
import de.budget.BudgetAndroid.Income.IncomeFragment;
import de.budget.BudgetAndroid.Income.IncomeListFragment;
import de.budget.BudgetAndroid.Income.IncomeActivity;
import de.budget.BudgetAndroid.Loss.LossActivity;
import de.budget.BudgetAndroid.Loss.LossFragment;
import de.budget.BudgetAndroid.Vendors.VendorActivity;
import de.budget.BudgetAndroid.Vendors.VendorAnalysisFragment;
import de.budget.BudgetAndroid.Vendors.VendorFragment;
import de.budget.BudgetAndroid.Vendors.VendorListFragment;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.R;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, CategoryListFragment.OnFragmentInteractionListener, VendorListFragment.OnFragmentInteractionListener,
                    IncomeListFragment.OnFragmentInteractionListener, LossFragment.OnFragmentInteractionListener, CategoryFragment.OnFragmentInteractionListener,
                    DashboardFragment.OnFragmentInteractionListener, DashboardIncomeFragment.OnFragmentInteractionListener, DashboardHomeFragment.OnFragmentInteractionListener,
                    CategoryAnalysisFragment.OnFragmentInteractionListener, DashboardIncomeAndLossAnalysisFragment.OnFragmentInteractionListener,
                    IncomeFragment.OnFragmentInteractionListener, IncomeAnalysisFragment.OnFragmentInteractionListener,
                    VendorFragment.OnFragmentInteractionListener, VendorAnalysisFragment.OnFragmentInteractionListener{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    /*
     * Speichert die geöffnete Fragment Instanz als Position im Navigationdarwer
     */
    private int savedNavigationPosition;
    public static final String FRAGMENT_NAVIGATION = "FRAGMENT_NAVIGATION_POSITION";

    // Erzeuge zwischenspeicher
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;

    // Preference Modus
    private static final int PREFERENCE_MODE_PRIVATE = 0;
    // Preference File Bezeichnung
    private static final String PREFERENCE_NAVIGATION_STATE_FILE = "PREFERENCE_NAVIGATION_STATE";

    private BudgetAndroidApplication myApp;

    private Menu menu;
    private MenuItem syncIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myApp = (BudgetAndroidApplication) getApplication();

        // Get the Navigation Fragment
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        // Get the Shared Preference
        sharedPreferences = getSharedPreferences(PREFERENCE_NAVIGATION_STATE_FILE, PREFERENCE_MODE_PRIVATE);
        // Set up preference editor
        sharedPreferencesEditor = sharedPreferences.edit();

        // Get Bundle
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)  {
            // Wechsel zum gewünschten Fragment
            int fragmentNavigation = bundle.getInt(FRAGMENT_NAVIGATION);

            Log.d("INFO", getFragmentByPosition(fragmentNavigation).toString());
            onNavigationDrawerItemSelected(fragmentNavigation);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        if(position==5) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if(networkInfo != null && networkInfo.isConnected()){

                if(!myApp.getFirstStart()) {
                    LogoutTask logoutTask = new LogoutTask(this, myApp, this);
                    int sessionId = myApp.getSession();
                    logoutTask.execute(sessionId);
                }
            }
            else {
                CharSequence text = "Keine Netzwerkverbindung! :(";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, text, duration);
                toast.show();
            }

        }
        else{
            // Speichere den Satatus des ausgewählten Items
            savedNavigationPosition = position;

            // update the main content by replacing fragments
            Fragment fragment = getFragmentByPosition(position);

            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack("fragback").commit();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Only show items in the action bar relevant to this screen
        // if the drawer is not showing. Otherwise, let the drawer
        // decide what to show in the action bar.
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        RotateAnimation rotation = new RotateAnimation(30, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotation.setRepeatCount(Animation.INFINITE);

        // Wähle welches Bedienelement geklickt wurde
        switch (id) {
            case R.id.action_sync:
                synchronizeApplication();
                return true;

            case R.id.action_loss:
                changeActivity(LossActivity.class);
                return true;
            // @author Christopher
            // @date 11.06.2015
            case R.id.action_settings:
                changeActivity(PreferencesActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Speichere das ausgeäwhlte Fragment, sobald die Acitivty gewechselt wird
        sharedPreferencesEditor.putInt(FRAGMENT_NAVIGATION, savedNavigationPosition);
        sharedPreferencesEditor.commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Wenn ein spezielles Bundle Navigation gewünscht ist benutzte sharedPreference nicht
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            // Hole das in onPause gespeicherte Fragment und lade es in die Activity
            sharedPreferences = getSharedPreferences(PREFERENCE_NAVIGATION_STATE_FILE, PREFERENCE_MODE_PRIVATE);
            onNavigationDrawerItemSelected(sharedPreferences.getInt(FRAGMENT_NAVIGATION, 0));
        }
    }

    @Override
    public void onCategoriesMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onVendorsMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onIncomeMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onLossMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onDashboardMainFragmentInteraction(Uri uri){
    }
    @Override
    public void onDashboardIncomeFragmentInteraction(Uri uri){
    }
    @Override
    public void onCategoryFragmentInteraction(Uri uri){
    }
    @Override
    public void onCategoryAnalysisFragmentInteraction(Uri uri){
    }
    @Override
    public void onIncomeFragmentInteraction(Uri uri){
    }
    @Override
    public void onIncomeAnalysisFragmentInteraction(Uri uri){
    }
    @Override
    public void onVendorFragmentInteraction(Uri uri){
    }
    @Override
    public void onVendorAnalysisFragmentInteraction(Uri uri){
    }
    @Override
    public void onDashboardHomeFragmentInteraction(Uri uri){
    }

    @Override
    public void onDashboardIncomeAndLossAnalysisFragmentInteraction(Uri uri){

    }


    public void onSectionAttached(int number) {
        String[] stringArray = getResources().getStringArray(R.array.section_titles);
        if (number >= 1) {
            mTitle = stringArray[number - 1];
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);

        // @Author Mark
        // entfernt den Schatten der Actionbar
        // //http://stackoverflow.com/questions/12246388/remove-shadow-below-actionbar
        actionBar.setElevation(0);

        // @Author Mark
        // entfernt den Titel
        // http://stackoverflow.com/questions/7655874/how-do-you-remove-the-title-text-from-the-android-actionbar
        actionBar.setDisplayShowTitleEnabled(false);

    }

    /*
     * Öffne Händler Maske zum anlegen
     */
    @Author(name="Mark")
    public void newVendor (View v){
        changeActivity(VendorActivity.class);
    }

    @Author(name="Mark")
    public void newCategory (View v){
        changeActivity(CategoryActivity.class);
    }

    @Author(name="Mark")
    public void newLoss (View v){
        changeActivity(LossActivity.class);
    }

    @Author(name="Mark")
    public void newIncome (View v){
        changeActivity(IncomeActivity.class);
    }

    /*
     * Toggle das Bottom Sheet Menu
     * Zeige das Menü an oder nicht
     * schiebe den FAB auf die Position des oberen Rands des Bottom Sheet Menüs
     */
    @Author(name="Mark")
    public void toggleBottomSheetMenu (View v) {

        // Hole die Menüliste
        LinearLayout bsm = (LinearLayout) findViewById(R.id.bottom_sheet_menu);

        // Hole den FAB
        ImageButton fab = (ImageButton) findViewById(R.id.fab);

        // Wähle ob Menü sichtbar oder nicht
        if (bsm.getVisibility() != View.VISIBLE){
            bsm.setVisibility(View.VISIBLE);
            fab.setImageDrawable(getDrawable(R.drawable.ic_clear_w));
            fab.bringToFront();
            ViewPropertyAnimator vpa = fab.animate();
            vpa.translationYBy(-535);
        } else {
            bsm.setVisibility(View.INVISIBLE);
            fab.setImageDrawable(getDrawable(R.drawable.ic_add_w));
            //TODO berechnen der translation evtl durch bsm.getHeight(), Padding und größe vom fab ermitteln und davon abziehen
            fab.animate().translationYBy(535);
        }

    }

    /*
     * Liefert das Fragment je nach Positionsnummer im Array
     */
    @Author(name="Mark")
    public Fragment getFragmentByPosition(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new DashboardFragment();
                break;
            case 1:
                fragment = new IncomeFragment();
                break;
            case 2:
                fragment = new LossFragment();
                break;
            case 3:
                fragment = new CategoryFragment();
                break;
            case 4:
                fragment = new VendorFragment();
                break;
            default:
                fragment = new DashboardFragment();
                break;
        }

        return fragment;

    }

    /*
     * Methode zum synchroniseren der Applikation, bei Fehlerfall, evtl für später bei mehrern Frontends
     *
     */
    @Author(name = "Mark")
    private void synchronizeApplication() {

        Toast.makeText(MainActivity.this, "Synchronisierung des Systems", Toast.LENGTH_SHORT).show();

        Iterator<BasketTO> baskets = myApp.getBasket().iterator();
        while(baskets.hasNext()) {
            BasketTO basket = baskets.next();
            if (basket.getItems() == null && !basket.isOccupied()){
                GetItemsTask itemsTask = new GetItemsTask(myApp.getApplicationContext(), myApp, basket, new OnTaskCompleted() {
                    @Override
                    public void onTaskCompleted(Object o) {

                        // syncIcon.getActionView().clearAnimation();
                    }
                });
                basket.setOccupied(true);
                itemsTask.execute();
            }
        }

        Toast.makeText(MainActivity.this, "Synchronisierung abgeschlossen", Toast.LENGTH_SHORT).show();

    }

    /*
     * Methode zum ändern der Activity je nach eingegebener Klasse
     */
    @Author(name = "Mark")
    private void changeActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
}
