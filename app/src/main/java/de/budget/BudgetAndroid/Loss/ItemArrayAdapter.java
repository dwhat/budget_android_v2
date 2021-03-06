package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetAndroid.BudgetAndroidApplication;
import de.budget.BudgetService.dto.ItemTO;
import de.budget.R;

/**
 * <p>
 *     Eine Erweiterung des ArrayAdapters um ein Item Objekt innerhalb einer ListView anzuzeigen und zu verwalten
 * </p>
 * Created by mark on 19/06/15.
 * @Author Mark
 */
@Author(name="Mark")
public class ItemArrayAdapter extends ArrayAdapter<ItemTO> {

    private final BudgetAndroidApplication myApp;
    private final Context context;
    private final List<ItemTO> values;

    public ItemArrayAdapter(Context context, BudgetAndroidApplication myApp, int resource, List<ItemTO> values) {
        super(context, R.layout.listview_item, values);

        this.context    = context;
        this.myApp      = myApp;
        this.values     = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_item, parent, false);

        TextView itemName       = (TextView) rowView.findViewById(R.id.item_name);
        TextView itemCategory   = (TextView) rowView.findViewById(R.id.item_category);
        TextView itemTotal      = (TextView) rowView.findViewById(R.id.item_total);

        double value    = values.get(position).getPrice();
        double quantity = values.get(position).getQuantity();

        BigDecimal total = round(value * quantity, 2);

        itemName        .setText(values.get(position).getName());
        itemCategory    .setText(myApp.getCategory(values.get(position).getCategory()).getName());
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
