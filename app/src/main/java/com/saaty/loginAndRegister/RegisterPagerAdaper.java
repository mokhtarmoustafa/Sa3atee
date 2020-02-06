package com.saaty.loginAndRegister;

import android.view.View;

import com.saaty.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class RegisterPagerAdaper extends FragmentPagerAdapter {
    public RegisterPagerAdaper(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new UserRegisterFragment();
            case 1:
                return new TraderRegisterFragment();

                default:
                    return null;
        }



    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "Register User   ";
            case 1:
                return  "   Register Trader";
                default:
                    return null;
        }
    }
}
