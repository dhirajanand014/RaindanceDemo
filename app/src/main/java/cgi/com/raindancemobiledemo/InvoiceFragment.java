package cgi.com.raindancemobiledemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashSet;

/**
 * Created by Dhiraj on 06-08-2017.
 */

public class InvoiceFragment extends Fragment {
    private AutoCompleteTextView mCustomer, mSupplier;
    private AutoCompleteTextView mCustomerType, mSupplierType;
    private Button mSaveButton;
    private Integer mAmount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.create_invoice_fragment, container, false);
        final int fragmentId = getArguments().getInt("Fragment");
        final int listReq = getArguments().getInt("Request");
        mCustomer = (AutoCompleteTextView) rootView.findViewById(R.id.customer);
        ArrayAdapter customersList = ArrayAdapter.createFromResource(getActivity(),
                R.array.Customers, android.R.layout.simple_dropdown_item_1line);
        mCustomer.setAdapter(customersList);
        mCustomer.setVisibility(listReq == 0 && fragmentId == 1 ? rootView.VISIBLE : rootView.GONE);
        if (listReq == 0 && fragmentId == 2) {
            mCustomer.setHint("");
        } else if (listReq == 0 && fragmentId == 1) {
            mCustomer.setHint("Customer");
        }

        mCustomerType = (AutoCompleteTextView) rootView.findViewById(R.id.invoiceType);
        ArrayAdapter customerTypeAdaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.CustomerTypes, android.R.layout.simple_dropdown_item_1line);
        mCustomerType.setAdapter(customerTypeAdaptor);
        mCustomerType.setVisibility(listReq == 0 && fragmentId == 1 ? rootView.VISIBLE : rootView.GONE);
        if (listReq == 0 && fragmentId == 2) {
            mCustomerType.setHint("");
        } else if (listReq == 0 && fragmentId == 1) {
            mCustomerType.setHint("CustomerType");
        }

        mSupplier = (AutoCompleteTextView) rootView.findViewById(R.id.supplier);
        ArrayAdapter supplierAdaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.Suppliers, android.R.layout.simple_dropdown_item_1line);
        mSupplier.setAdapter(supplierAdaptor);
        mSupplier.setVisibility(listReq == 0 && fragmentId == 2 ? rootView.VISIBLE : rootView.GONE);
        if (listReq == 0 && fragmentId == 1) {
            mSupplier.setHint("");
        } else if (listReq == 0 && fragmentId == 2) {
            mSupplier.setHint("Supplier");
        }

        mSupplierType = (AutoCompleteTextView) rootView.findViewById(R.id.supplierType);
        ArrayAdapter supplierTypeAdaptor = ArrayAdapter.createFromResource(getActivity(),
                R.array.SupplierTypes, android.R.layout.simple_dropdown_item_1line);
        mSupplierType.setAdapter(supplierTypeAdaptor);
        mSupplierType.setVisibility(listReq == 0 && fragmentId == 2 ? rootView.VISIBLE : rootView.GONE);
        if (listReq == 0 && fragmentId == 1) {
            mSupplierType.setHint("");
        } else if (listReq == 0 && fragmentId == 2) {
            mSupplierType.setHint("SupplierType");
        }

        mSaveButton = (Button) rootView.findViewById(R.id.createInvoice);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((SubledgerActivity) getActivity()).mFragmentCount == 1) {
                    if (listReq == 0 && fragmentId == 1 && (mCustomer.getText().toString().isEmpty() || mCustomer.getText() == null)) {
                        mCustomer.setError(getString(R.string.error_field_required));
                        return;
                    } else if (listReq == 0 && fragmentId == 1 && (mCustomerType.getText().toString().isEmpty() || mCustomerType.getText() == null)) {
                        mCustomerType.setError(getString(R.string.error_field_required));
                        return;
                    }
                } else {
                    if (listReq == 0 && fragmentId == 2 && (mSupplier.getText().toString().isEmpty() || mSupplier.getText() == null)) {
                        mSupplier.setError(getString(R.string.error_field_required));
                        return;
                    } else if (listReq == 0 && fragmentId == 2 && (mSupplierType.getText().toString().isEmpty() || mSupplierType.getText() == null)) {
                        mSupplierType.setError(getString(R.string.error_field_required));
                        return;
                    }
                }
                saveInvoice();
                closeFragment();
//                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//                sharedPreferences.toString();
            }
        });

        return rootView;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(this);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();
        ((SubledgerActivity) getActivity()).mViewPager.getAdapter().notifyDataSetChanged();
        Toast.makeText(getContext(), "SuccessFully Added", Toast.LENGTH_SHORT).show();

    }

    private void saveInvoice() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int size = sharedPreferences.getAll().size();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.remove(getString(R.string.Customer) + 0);
        HashSet<String> set = new HashSet<>();
        if (((SubledgerActivity) getActivity()).mFragmentCount == 1) {
            set.add(String.valueOf(mCustomer.getText()));
            set.add(String.valueOf(mCustomerType.getText()));
            editor.putStringSet(getString(R.string.Customer) + size, set);
        } else {
            set.add(String.valueOf(mSupplier.getText()));
            set.add(String.valueOf(mSupplierType.getText()));
            editor.putStringSet(getString(R.string.Supplier) + size, set);
        }

        editor.apply();
    }

}
