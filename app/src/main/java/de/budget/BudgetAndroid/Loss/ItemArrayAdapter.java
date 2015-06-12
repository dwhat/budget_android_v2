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
public class ItemArrayAdapter extends ArrayAdapter<HashMap <String, String>> {

    private final Context context;
    private final ArrayList<HashMap <String, String>> values;

    public ItemArrayAdapter(Context context, int resource, ArrayList<HashMap <String, String>> values) {

        super(context, R.layout.item_listview, values);

        this.context    = context;
        this.values     = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_listview, parent, false);

        TextView itemName       = (TextView) rowView.findViewById(R.id.item_name);
        TextView itemCategory   = (TextView) rowView.findViewById(R.id.item_category);
        TextView itemNotice     = (TextView) rowView.findViewById(R.id.item_notice);
        TextView itemAmount     = (TextView) rowView.findViewById(R.id.item_amount);
        TextView itemValue      = (TextView) rowView.findViewById(R.id.item_value);
        TextView itemTotal      = (TextView) rowView.findViewById(R.id.item_total);

        itemName        .setText(values.get(position).get(Item.NAME));
        itemCategory    .setText(values.get(position).get(Item.CATEGORY));
        itemNotice      .setText(values.get(position).get(Item.NOTICE));
        itemAmount      .setText(values.get(position).get(Item.AMOUNT));
        itemValue       .setText(values.get(position).get(Item.VALUE));
        itemTotal       .setText(values.get(position).get(Item.TOTAL));

        return rowView;
    }
}
