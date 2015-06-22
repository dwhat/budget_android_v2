package de.budget.BudgetAndroid.common;

import android.graphics.Color;
import android.view.View;

import com.github.mikephil.charting.charts.*;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

import de.budget.BudgetAndroid.common.EuroValueFormatter;
import de.budget.BudgetService.dto.AmountTO;

/**
 * <p>
 *     Dient zur Darstellung der Charts innerhalb der Fragments
 * </p>
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


    public static PieChart setPieChartFundamentals(PieChart chart, String centerText){
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

    public static HorizontalBarChart setHorizontalBarChartFundamentals(HorizontalBarChart chart){
        chart.setVisibility(View.INVISIBLE);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setDescription("");
        chart.setMaxVisibleValueCount(60);
        chart.setPinchZoom(false);
        chart.setDrawGridBackground(false);

        XAxis xl = chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.TOP);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = chart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);

        YAxis yr = chart.getAxisRight();
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        return chart;
    }


    public static HorizontalBarChart setDataOfHorizontalBarChart(HorizontalBarChart chart, List<AmountTO> values, String yDesc) {

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < values.size(); i++) {
            xVals.add(values.get(i).getName());
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();

        for (int i = 0; i < values.size(); i++) {
            yVals1.add(new BarEntry((float) values.get(i).getValue(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, yDesc);
        set1.setBarSpacePercent(35f);
        set1.setColor(Color.rgb(0, 150, 136));

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        chart.setData(data);
        return  chart;
    }

    public static HorizontalBarChart setDataOfHorizontalBarChart(HorizontalBarChart chart, List<AmountTO> income, List<AmountTO> loss, String incomeDesc, String lossDesc) {

        // Income Verpacken

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < income.size(); i++) {
            xVals.add(income.get(i).getName());
        }

        ArrayList<BarEntry> yVals1Income = new ArrayList<BarEntry>();

        for (int i = 0; i < income.size(); i++) {
            yVals1Income.add(new BarEntry((float) income.get(i).getValue(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1Income, incomeDesc);
        set1.setBarSpacePercent(35f);
        set1.setColor(Color.rgb(0,150,136));

        // Loss verpacken

        ArrayList<BarEntry> yValsLoss = new ArrayList<BarEntry>();

        for (int i = 0; i < loss.size(); i++) {
            yValsLoss.add(new BarEntry((float) loss.get(i).getValue(), i));
        }

        BarDataSet set2 = new BarDataSet(yValsLoss, lossDesc);
        set2.setBarSpacePercent(35f);
        set2.setColor(Color.rgb(240,128,128));

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);

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
