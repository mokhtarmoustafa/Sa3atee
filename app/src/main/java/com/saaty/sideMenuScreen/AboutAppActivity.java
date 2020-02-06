package com.saaty.sideMenuScreen;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.saaty.R;
import com.saaty.models.ProductDataModel;
import com.saaty.util.ApiClient;
import com.saaty.util.ApiServiceInterface;
import com.saaty.util.BaseActivity;
import com.saaty.util.NetworkAvailable;

import java.util.HashMap;
import java.util.Map;

public class AboutAppActivity extends BaseActivity {

    @BindView(R.id.about_app_txt) TextView aboutAppTxt;
    @BindView(R.id.progress_id) ProgressBar progressBar;
    @BindView(R.id.toolbar_txt_id) TextView toolbarTxt;
    ApiServiceInterface apiServiceInterface;
    NetworkAvailable networkAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        networkAvailable=new NetworkAvailable(this);
        toolbarTxt.setText(getString(R.string.about_app));
        getAboutAppData();
    }

    private void getAboutAppData() {
        if (networkAvailable.isNetworkAvailable()) {
            apiServiceInterface = ApiClient.getClientService();
            Map<String, Object> map = new HashMap<>();
            map.put("key", "aboutapp");
            progressBar.setVisibility(View.VISIBLE);
            Call<ProductDataModel> call = apiServiceInterface.getAboutAppData(map);
            call.enqueue(new Callback<ProductDataModel>() {
                @Override
                public void onResponse(Call<ProductDataModel> call, Response<ProductDataModel> response) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(AboutAppActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    } else {
                        Toast.makeText(AboutAppActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<ProductDataModel> call, Throwable t) {

                }
            });


        } else {
            Toast.makeText(this, getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }
}
