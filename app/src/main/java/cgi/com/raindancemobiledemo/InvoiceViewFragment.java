package cgi.com.raindancemobiledemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Dhiraj on 15-08-2017.
 */

public class InvoiceViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.invoice_view, container, false);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        Set<String> keySet = sharedPreferences.getAll().keySet();
        TableLayout invoice_view = (TableLayout) rootView.findViewById(R.id.invoiceViewTable);

        TextView headerText = (TextView) rootView.findViewById(R.id.custSupTextView);
        if (((SubledgerActivity) getActivity()).mFragmentCount == 1) {
            headerText.setText("CustomerType");
        } else if (((SubledgerActivity) getActivity()).mFragmentCount == 2) {
            headerText.setText("SupplierType");
        }
        for (String key : keySet) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setBackground(getResources().getDrawable(R.drawable.border));
            Set<String> valuesSet = sharedPreferences.getStringSet(key, Collections.<String>emptySet());
            if (((SubledgerActivity) getActivity()).mFragmentCount == 1 && key.startsWith(getString(R.string.Customer)) || ((SubledgerActivity) getActivity()).mFragmentCount == 2 && key.startsWith(getString(R.string.Supplier))) {
                key = String.valueOf(key.charAt(key.length() - 1));
                TextView keyTextView = new TextView(getContext());
                keyTextView.setText(key);
                tableRow.addView(keyTextView);
                List<String> stringList = new ArrayList<>(valuesSet);

                for (String value : stringList) {
                    keyTextView = new TextView(getContext());
                    keyTextView.setText(value);
                    tableRow.addView(keyTextView);
                }
                invoice_view.addView(tableRow);
            }
        }

        return rootView;
    }
}
