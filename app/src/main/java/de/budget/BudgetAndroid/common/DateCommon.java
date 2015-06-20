package de.budget.BudgetAndroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mark on 20/06/15.
 */
public class DateCommon {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");

    public static String format(Long l) {
        return dateFormat.format(l);
    }

    public static String format(Date d) {
        return dateFormat.format(d);
    }
}
