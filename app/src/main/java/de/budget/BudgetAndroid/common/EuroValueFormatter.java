package de.budget.BudgetAndroid.common;

import com.github.mikephil.charting.utils.ValueFormatter;

import java.text.DecimalFormat;

/**
 * <p>
 *     Eine Objekt um Zugriff auf ein einheitliches Euroformat zu bieten
 * </p>
 * @author christopher
 * @date 19.06.2015
 */
public class EuroValueFormatter implements ValueFormatter {

    private DecimalFormat mFormat;

    public EuroValueFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value) {
        return mFormat.format(value) + " €"; // append a dollar-sign
    }
}
