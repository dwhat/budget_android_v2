package de.budget.BudgetAndroid.Loss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.BasketTO;
import de.budget.R;

/**
 * <p>
 *     Eine Erweiterung des ArrayAdapters um ein Basket Objekt innerhalb einer ListView anzuzeigen und zu verwalten
 * </p>
 * Created by mark on 19/06/15.
 * @Author Mark
 */
@Author(name="Mark")
public class LossArrayAdapter extends ArrayAdapter<BasketTO> {

    private final Context context;
    private final List<BasketTO> values;


    public LossArrayAdapter(Context context, int resource, List<BasketTO> values) {

        super(context, R.layout.listview_loss, values);

        this.context    = context;
        this.values     = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_loss, parent, false);

        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

        TextView    lossName       = (TextView) rowView.findViewById(R.id.loss_name);
        TextView    lossDate       = (TextView) rowView.findViewById(R.id.loss_date);
        TextView    lossNotice     = (TextView) rowView.findViewById(R.id.loss_notice);
        TextView    lossTotal      = (TextView) rowView.findViewById(R.id.loss_total);

                    lossName        .setText(values.get(position).getName());
                    lossDate        .setText(DATE_FORMAT.format(values.get(position).getPurchaseDate()));
        if(values.get(position).getItems() != null)
                    lossNotice      .setText(values.get(position).getItems().toString());
                    lossTotal       .setText(String.valueOf(values.get(position).getAmount()) + " €");

        return rowView;
    }
}
