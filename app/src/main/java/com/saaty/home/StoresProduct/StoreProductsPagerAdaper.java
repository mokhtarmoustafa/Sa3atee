package com.saaty.home.StoresProduct;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class StoreProductsPagerAdaper extends FragmentPagerAdapter {
    private static final String TAG =StoreProductsPagerAdaper.class.getSimpleName() ;
    String shape_type;

    public StoreProductsPagerAdaper(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
      if(position==0){
          Log.v(TAG,"New Clicked");
          return new StoreNewProductFragment("New");

      }else if(position==1){
          Log.v(TAG,"Old Clicked");
          return new StoreNewProductFragment("Used");

      }else {
          return null;
      }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
        case 0:
        return   "        new         ";
        case 1:
        return "       used        ";
        default:
        return null;
    }
    }
}
