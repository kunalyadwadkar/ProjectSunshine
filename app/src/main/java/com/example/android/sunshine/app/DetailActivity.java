package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sunshine.app.util.IntentsUtil;


public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                return true;
            case R.id.action_show_map:
                showOnMap();
                return true;
            default:
                 //fall through
        }


        return super.onOptionsItemSelected(item);
    }

    private void showOnMap() {
        SharedPreferences preferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String zipCode = preferences.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        Intent showOnMapIntent = IntentsUtil.buildMapFromZipcode(zipCode);

        if (showOnMapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(showOnMapIntent);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private String forecastDetail;

        public PlaceholderFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

            getActivity().getMenuInflater().inflate(R.menu.detail_fragment_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.action_share);
            ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

            if (shareActionProvider != null) {
                String textToShare = String.format("%s #SunshineApp", forecastDetail);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
                shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                shareIntent.setType("text/plain");
                shareActionProvider.setShareIntent(shareIntent);
            } else {
                Log.d("PlaceholderFragment", "Unable to set share intent.");
            }

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            forecastDetail = getActivity().getIntent().getStringExtra(Intent.EXTRA_TEXT);


            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
            TextView detailTextView = (TextView) rootView.findViewById(R.id.detail_text);
            detailTextView.setText(forecastDetail);

            return rootView;
        }
    }
}
