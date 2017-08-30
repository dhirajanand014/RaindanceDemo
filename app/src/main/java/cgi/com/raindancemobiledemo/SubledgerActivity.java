package cgi.com.raindancemobiledemo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SubledgerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    protected ViewPager mViewPager;
    protected Integer mFragmentCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subledger);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        protected static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 final Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_subledger, container, false);
            int fragmentTabInteger = getArguments().getInt(ARG_SECTION_NUMBER);
            ListView listView = (ListView) rootView.findViewById(R.id.list);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    replaceWithNeededFragment(position, container);
                }
            });
            switch (fragmentTabInteger) {
                case 1:
                    createARPOptions(rootView, inflater, listView);
                    break;
                case 2:
                    createAPPOptions(rootView, inflater, listView);
                    break;
            }
            return rootView;
        }

        private void replaceWithNeededFragment(int position, ViewGroup container) {
            for (Fragment fragment : getActivity().getSupportFragmentManager().getFragments()) {
                if (Integer.valueOf((Integer) fragment.getArguments().get(ARG_SECTION_NUMBER)) == (((SubledgerActivity) getActivity()).mViewPager.getCurrentItem() + 1)) {
                    ((SubledgerActivity) getActivity()).mFragmentCount = (((SubledgerActivity) getActivity()).mViewPager.getCurrentItem() + 1);
                    Bundle args = new Bundle();
                    args.putInt("Fragment", (((SubledgerActivity) getActivity()).mViewPager.getCurrentItem() + 1));
                    args.putInt("Request", position);
                    if (position == 0) {
                        InvoiceFragment invoiceFragment = new InvoiceFragment();
                        invoiceFragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.layercontainer, invoiceFragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((SubledgerActivity) getActivity()).mViewPager.getAdapter().notifyDataSetChanged();
                        break;
                    } else if (position == 1) {
                        InvoiceViewFragment invoiceViewFragment = new InvoiceViewFragment();
                        invoiceViewFragment.setArguments(args);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.layercontainer, invoiceViewFragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        ((SubledgerActivity) getActivity()).mViewPager.getAdapter().notifyDataSetChanged();
                        break;
                    }
                }
            }
        }


        private void createAPPOptions(View rootView, LayoutInflater inflater, ListView listView) {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.SupplierInvoiceProcess, android.R.layout.simple_list_item_1);
            listView.setAdapter(adapter);
        }

        private void createARPOptions(View rootView, LayoutInflater inflater, ListView listView) {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),
                    R.array.CustomerInvoiceProcesses, android.R.layout.simple_list_item_1);
            listView.setAdapter(adapter);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public int getItemPosition(Object object) {
            PlaceholderFragment placeholderFragment = (PlaceholderFragment) object;
            if (Integer.valueOf((Integer) placeholderFragment.getArguments().get(PlaceholderFragment.ARG_SECTION_NUMBER)).equals(((SubledgerActivity) placeholderFragment.getActivity()).mFragmentCount)) {
                return POSITION_UNCHANGED;
            }
            return POSITION_NONE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "ARP";
                case 1:
                    return "APP";
            }
            return null;
        }
    }
}
