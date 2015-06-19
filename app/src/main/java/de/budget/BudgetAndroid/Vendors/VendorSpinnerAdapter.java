package de.budget.BudgetAndroid.Vendors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import de.budget.BudgetAndroid.Annotations.Author;
import de.budget.BudgetService.dto.VendorTO;
import de.budget.R;

/**
 * Created by mark on 19/06/15.
 */
@Author(name="Mark")
public class VendorSpinnerAdapter extends BaseAdapter {

        private final Context context;
        private List<VendorTO> values;

        public VendorSpinnerAdapter(Context context, int resource, List<VendorTO> values) {

            this.context    = context;
            this.values     = values;
        }

        @Override
        public int getCount() {
            return this.values.size();
        }

        @Override
        public VendorTO getItem(int position) {
            return this.values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return this.values.get(position).getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =  inflater.inflate(R.layout.spinner_vendor, parent, false);

            TextView    vendorName = (TextView) convertView.findViewById(R.id.vendor_name);
                        vendorName.setText(getItem(position).getName());

            return convertView;
        }


}
