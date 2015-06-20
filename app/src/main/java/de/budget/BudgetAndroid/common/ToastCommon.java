package de.budget.BudgetAndroid.common;

import android.content.Context;
import android.widget.Toast;

import de.budget.R;

/**
 * Created by mark on 20/06/15.
 */
public class ToastCommon {

    public static void NetworkMissing(Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.network_missing_error), Toast.LENGTH_SHORT).show();
    }

    public static void RequireFields (Context context) {
        Toast.makeText(context, context.getResources().getString(R.string.required_fields_missing_error), Toast.LENGTH_SHORT).show();
    }
}


