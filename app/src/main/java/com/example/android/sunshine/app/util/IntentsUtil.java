package com.example.android.sunshine.app.util;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * Created by kyadwadkar on 8/5/15.
 */
public class IntentsUtil {

    public static Intent buildMapFromZipcode(String zipCode) {
        Uri geoUri = (new Uri.Builder())
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", zipCode)
                .build();

        Log.d("showOnMap", geoUri.toString());
        Intent showOnMapIntent = new Intent(Intent.ACTION_VIEW);
        showOnMapIntent.setData(geoUri);
        return showOnMapIntent;
    }
}
