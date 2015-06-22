package de.budget.BudgetAndroid.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *     Eine Objekt um Zugriff auf ein einheitliches Dateformat zu bieten
 * </p>
 * Created by mark on 20/06/15.
 *  @Author Mark
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
