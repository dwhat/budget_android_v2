package de.budget.BudgetAndroid;

import android.graphics.Color;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.*;

import java.util.ArrayList;

import de.budget.R;

/**
 * Created by christopher on 17.06.15.
 */
public final class ChartMethods {

    public static PieChart configureChart(PieChart chart, String centerText) {
        chart.setHoleRadius(50f);
        chart.setDescription("");
        chart.setTransparentCircleRadius(5f);
        chart.setDrawCenterText(true);
        chart.setDrawHoleEnabled(false);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setUsePercentValues(false);
        chart.setCenterText(centerText);
        chart.setDrawHoleEnabled(true);
        chart.setPadding(50, 50, 50, 50);
        chart.setCenterTextSize(25f);
        Legend l =chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setTextSize(15f);
        l.setTextColor(Color.BLACK);
        return chart;
    }

    public static PieChart setData(PieChart chart, String xDesc, String yDesc, int xData, int yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(xData, 0));
        yVals1.add(new Entry(yData, 1));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(xDesc);
        xVals.add(yDesc);
        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(135,206,250));
        colors.add(Color.rgb(0, 191, 255));
        set1.setColors(colors);
        PieData data = new PieData(xVals, set1);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
        return chart;
    }
}
