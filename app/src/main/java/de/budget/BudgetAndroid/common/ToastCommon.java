package de.budget.BudgetAndroid.common;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mark on 20/06/15.
 */
public class ToastCommon {

    public static void NetworkMissing(Context context) {
        Toast.makeText(context, "Keine Netzwerkverbindung! :(!", Toast.LENGTH_SHORT).show();
    }

    public static void RequireFields (Context context) {
        Toast.makeText(context, "Bitte alle Felder ausfüllen!", Toast.LENGTH_SHORT).show();
    }
}


