package de.budget.BudgetAndroid;

import android.graphics.Color;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.DefaultValueFormatter;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetService.dto.AmountTO;
import de.budget.BudgetService.dto.ObjectTO;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;

/**
 * @author christopher
 * @date 18.06.2015
 */
public final class ChartMethods {

    public static PieChart setDataPercent(PieChart chart, String xDesc, String yDesc, int xData, int yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(xData, 0));
        yVals1.add(new Entry(yData, 1));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(xDesc);
        xVals.add(yDesc);
        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(135, 206, 250));
        colors.add(Color.rgb(0, 191, 255));
        set1.setColors(colors);
        PieData data = new PieData(xVals, set1);
        data.setValueTextSize(15f);
        data.setValueFormatter(new PercentFormatter());
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
        return chart;
    }

    public static PieChart setDataNormal(PieChart chart, String xDesc, String yDesc, int xData, int yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(xData, 0));
        yVals1.add(new Entry(yData, 1));
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add(xDesc);
        xVals.add(yDesc);
        PieDataSet set1 = new PieDataSet(yVals1, "");
        set1.setSliceSpace(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(135, 206, 250));
        colors.add(Color.rgb(0, 191, 255));
        set1.setColors(colors);
        PieData data = new PieData(xVals, set1);
        data.setValueTextSize(15f);
        data.setValueFormatter(new EuroValueFormatter());
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
        return chart;
    }


    public static PieChart setChartFundamentals(PieChart chart, String centerText){
        chart.setHoleRadius(50f);
        chart.setDescription("");
        chart.setTransparentCircleRadius(5f);
        chart.setDrawCenterText(true);
        chart.setDrawHoleEnabled(false);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
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

    public static HorizontalBarChart setDataOfHorizontalBarChart(HorizontalBarChart chart, List<AmountTO> values) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < values.size(); i++) {
            xVals.add(values.get(i).getName());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < values.size(); i++) {
            yVals1.add(new BarEntry((float) values.get(i).getValue(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "DataSet");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        chart.setData(data);
        return  chart;
    }

    public static HorizontalBarChart configureChart(HorizontalBarChart chart, String centerText) {

        return chart;
    }

    public static HorizontalBarChart setData(HorizontalBarChart chart, String xDesc, String yDesc, int xData, int yData) {

        return chart;
    }
}
