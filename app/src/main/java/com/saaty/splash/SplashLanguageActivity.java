package com.saaty.splash;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.saaty.R;
import com.saaty.home.HomeActivity;
import com.saaty.loginAndRegister.LoginTraderUserActivity;
import com.saaty.models.Data;
import com.saaty.models.UserModel;
import com.saaty.util.BaseActivity;
import com.saaty.util.PreferenceHelper;

import java.util.Locale;

public class SplashLanguageActivity extends BaseActivity {

    @BindView(R.id.arabic_btn_id) MaterialButton arabicBtn;
    @BindView(R.id.english_btn_id) MaterialButton englishBtn;
    private String lang_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_language);
        ButterKnife.bind(this);

        Handler handler;


        SharedPreferences prefs = getSharedPreferences(LoginTraderUserActivity.MY_PREFS_NAME, MODE_PRIVATE);
        String user_data = prefs.getString("user_data", "");
        Gson gson = new Gson();
        UserModel userModel = gson.fromJson(user_data, UserModel.class);

        String register_data = prefs.getString("register_data", "");
        Gson gson1 = new Gson();
        Data data = gson1.fromJson(register_data, Data.class);

        //if (user_data != null && !user_data.equals("")||register_data !=null && !data.equals("")) {
        if (user_data != null && !user_data.equals("")) {
            Intent intent = new Intent(SplashLanguageActivity.this, HomeActivity.class);
            intent.putExtra("user_data", userModel);
            startActivity(intent);
            // overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
        } else if(PreferenceHelper.getValue(getApplicationContext()).equals("")){

            Toast.makeText(this, getString(R.string.choose_lang), Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(SplashLanguageActivity.this, LoginTraderUserActivity.class);
//                    startActivity(intent);


           //overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
        }




    }
    @OnClick(R.id.arabic_btn_id)
    void setArabicBtn(){
        lang_selected = "ar";
        PreferenceHelper.setValue(getApplicationContext(),lang_selected);
        PreferenceHelper.setFirstTimeValue(this,false);
        setConfig(getApplicationContext(),lang_selected);
        Intent intent=new Intent(getApplicationContext(), LoginTraderUserActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.v("TAG","sssss"+lang_selected);
        startActivity(intent);
        finish();
          }

    @OnClick(R.id.english_btn_id)
    void setEnglishBtn(){
        lang_selected = "en";
        PreferenceHelper.setValue(getApplicationContext(),lang_selected);
        PreferenceHelper.setFirstTimeValue(this,false);
        setConfig(getApplicationContext(),lang_selected);
       Intent intent=new Intent(getApplicationContext(), LoginTraderUserActivity.class);
       // Intent intent=getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Log.v("TAG","sssss"+lang_selected);
        startActivity(intent);
        finish();

    }


    private  void setConfig(Context context, String lang){
        Locale locale=new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration=new Configuration();
        configuration.locale=locale;
        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
}
