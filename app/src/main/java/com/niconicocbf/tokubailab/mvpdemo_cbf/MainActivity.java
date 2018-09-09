package com.niconicocbf.tokubailab.mvpdemo_cbf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DisplayView{

    private PhotoZoupicpresenter photoZoupicpresenter;
    private TextView dataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataView = findViewById(R.id.data_text);
        photoZoupicpresenter = new PhotoZoupicpresenter();
        photoZoupicpresenter.attachView(this);
        photoZoupicpresenter.getPicInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(photoZoupicpresenter!=null){
        photoZoupicpresenter.detachView();
        }
    }


    @Override
    public void getDataSuccess(final String successMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataView.setText(successMsg);
            }
        });
    }

    @Override
    public void getDataFail(final String errorMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dataView.setText(errorMsg);
            }
        });
    }
}
