package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.R;

/**
 * Created by mark on 09/06/15.
 */
@Author(name="Mark")
public class LossArrayAdapter extends ArrayAdapter<BasketTO> {

    private final Context context;
    private final List<BasketTO> values;


    public LossArrayAdapter(Context context, int resource, List<BasketTO> values) {

        super(context, R.layout.loss_listview, values);

        this.context    = context;
        this.values     = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.loss_listview, parent, false);

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

        TextView    lossName       = (TextView) rowView.findViewById(R.id.loss_name);
        TextView    lossDate       = (TextView) rowView.findViewById(R.id.loss_date);
        TextView    lossNotice     = (TextView) rowView.findViewById(R.id.loss_notice);
        TextView    lossTotal      = (TextView) rowView.findViewById(R.id.loss_total);
                    // TODO Refactor Listview Loss Name nicht existend

                    lossName        .setText("Loss Name gibt es nicht");
                    lossDate        .setText(DATE_FORMAT.format(values.get(position).getPurchaseDate()));
                    lossNotice      .setText(values.get(position).getNotice());
                    lossTotal       .setText(String.valueOf(values.get(position).getAmount()));

        return rowView;
    }
}
