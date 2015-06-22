package de.budget.BudgetAndroid.common;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <p>
 *     Eine Objekt um Zugriff auf die Netzwerkprüfung zu bieten
 * </p>
 * Created by mark on 20/06/15.
 * @Author Mark
 */
public class NetworkCommon {

    public static boolean getStatus(Activity activity) {

        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
