package com.saaty.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkAvailable  {
    Context context;


    public NetworkAvailable(Context context) {
        this.context = context;
    }

    public boolean isNetworkAvailable(){


        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityMgr.getActiveNetworkInfo();
        /// if no network is available networkInfo will be null
        return networkInfo != null && networkInfo.isConnected();

    }
}
