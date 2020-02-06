package com.saaty.util;

import android.app.ProgressDialog;
import android.content.Context;

public class DailogUtil {

    public DailogUtil() {
    }

    public static ProgressDialog  showProgressDialog(Context context,String mess,Boolean cancelable){
        ProgressDialog progressDialog=new ProgressDialog(context);
        progressDialog.setCancelable(cancelable);
        progressDialog.setMessage(mess);
        progressDialog.show();
        return progressDialog;

    }
}
