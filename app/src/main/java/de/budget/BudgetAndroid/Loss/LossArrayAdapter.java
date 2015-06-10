package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.R;

/**
 * Created by mark on 09/06/15.
 */
@Author(name="Mark")
public class LossArrayAdapter extends ArrayAdapter<HashMap<String, String>> {

    private final Context context;
    private final ArrayList<HashMap <String, String>> values;

    public LossArrayAdapter(Context context, int resource, ArrayList<HashMap <String, String>> values) {

        super(context, R.layout.loss_listview, values);

        this.context    = context;
        this.values     = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.loss_listview, parent, false);

        TextView    lossName       = (TextView) rowView.findViewById(R.id.loss_name);
        TextView    lossDate       = (TextView) rowView.findViewById(R.id.loss_date);
        TextView    lossNotice     = (TextView) rowView.findViewById(R.id.loss_notice);
        TextView    lossTotal      = (TextView) rowView.findViewById(R.id.loss_total);

                    lossName        .setText(values.get(position).get(Loss.NAME));
                    lossDate        .setText(values.get(position).get(Loss.DATE));
                    lossNotice      .setText(values.get(position).get(Loss.NOTICE));
                    lossTotal       .setText(values.get(position).get(Loss.TOTAL));


        return rowView;
    }
}
