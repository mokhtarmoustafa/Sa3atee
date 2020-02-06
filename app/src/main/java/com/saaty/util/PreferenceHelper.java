package com.saaty.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String APP_Language = "APP_Language";


    // properties
//    private static final String SOME_STRING_VALUE = "SOME_STRING_VALUE";
    public static final String select_lang = "Language";
    public static final String first_time = "firstTime";

    // other properties...

    private PreferenceHelper() {
    }

    public static final SharedPreferences getSharedPreference(Context context){
        return  context.getSharedPreferences(APP_Language,Context.MODE_PRIVATE);
    }


    public static String getValue(Context context){
        return getSharedPreference(context).getString(select_lang,"");
    }

    public static  void setValue(Context context,String newValue){
        SharedPreferences.Editor editor=getSharedPreference(context).edit();
        editor.putString(select_lang,newValue);
        editor.commit();
    }

 public static Boolean getFirstTimeValue(Context context){
        return getSharedPreference(context).getBoolean(first_time,true);
    }

    public static  void setFirstTimeValue(Context context,Boolean firstTime){
        SharedPreferences.Editor editor=getSharedPreference(context).edit();
        editor.putBoolean(first_time,firstTime);
        editor.commit();
    }



}
