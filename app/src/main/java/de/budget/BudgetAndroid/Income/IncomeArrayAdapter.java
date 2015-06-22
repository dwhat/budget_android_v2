package de.budget.BudgetAndroid.Income;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.IncomeTO;
import de.budget.R;

/**
 * <p>
 *     Eine Erweiterung des ArrayAdapters um ein Income Objekt innerhalb einer ListView anzuzeigen und zu verwalten
 * </p>
 * Created by mark on 19/06/15.
 * @Author Mark
 */
@Author(name="Mark")
public class IncomeArrayAdapter extends ArrayAdapter<IncomeTO> {


        private final Context context;
        private final List<IncomeTO> values;


        public IncomeArrayAdapter(Context context, int resource, List<IncomeTO> values) {
            super(context, R.layout.listview_income, values);

            this.context    = context;
            this.values     = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.listview_income, parent, false);

            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

            TextView    incomeName       = (TextView) rowView.findViewById(R.id.income_name);
            TextView    incomeDate       = (TextView) rowView.findViewById(R.id.income_date);
            TextView    incomeNotice     = (TextView) rowView.findViewById(R.id.income_notice);
            TextView    incomeTotal      = (TextView) rowView.findViewById(R.id.income_total);

            incomeName        .setText(values.get(position).getName());
            incomeDate        .setText(DATE_FORMAT.format(values.get(position).getReceiptDate()));
            incomeNotice      .setText(values.get(position).getNotice());
            incomeTotal       .setText(String.valueOf(values.get(position).getAmount()*values.get(position).getQuantity()) + " €");

            return rowView;
        }
}
