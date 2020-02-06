package com.saaty.loginAndRegister;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.saaty.R;
import com.saaty.util.BaseActivity;

public class RegisterActivity extends BaseActivity {
@BindView(R.id.tab_layout_id) TabLayout tabLayout;
@BindView(R.id.view_pager_id) ViewPager viewPager;
@BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
RegisterPagerAdaper adaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        tabLayout.setupWithViewPager(viewPager);
        adaper=new RegisterPagerAdaper(getSupportFragmentManager());
        viewPager.setAdapter(adaper);

        toolbarTxt.setText(getString(R.string.create_new_account));



    }

    @OnClick(R.id.toolbar_back_left_btn_id)
    void setToolbarTxt(){
        finish();
    }
}
