package de.budget.BudgetAndroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import de.budget.R;

public class PreferencesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
    }
}