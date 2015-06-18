package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.ItemTO;
import de.budget.R;

/**
 * Created by mark on 09/06/15.
 */
@Author(name="Mark")
public class ItemArrayAdapter extends ArrayAdapter<ItemTO> {

    private final Context context;
    private final List<ItemTO> values;

    public ItemArrayAdapter(Context context, int resource, List<ItemTO> values) {

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
        TextView itemAmount     = (TextView) rowView.findViewById(R.id.item_amount);
        TextView itemValue      = (TextView) rowView.findViewById(R.id.item_value);
        TextView itemTotal      = (TextView) rowView.findViewById(R.id.item_total);

        double value = values.get(position).getPrice();
        double quantity = values.get(position).getQuantity();
        BigDecimal total = round(value * quantity, 2);

        // TODO Categories
        itemName        .setText(values.get(position).getName());
//        itemCategory    .setText(values.get(position).getCategory().getName());
//        itemAmount      .setText(String.valueOf(quantity));
//        itemValue       .setText(String.valueOf(value));
        itemTotal       .setText( total + " €");

        return rowView;
    }

    @Author(name="Mark")
    public static BigDecimal round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

    public List<ItemTO> getValues () {
        return this.values;
    }
}
