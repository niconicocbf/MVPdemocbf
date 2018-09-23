package com.niconicocbf.tokubailab.mvpdemo_cbf.model;

import android.widget.Toast;

import com.google.gson.Gson;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.DisplayView;
import com.niconicocbf.tokubailab.mvpdemo_cbf.bean.PicInfo;
import com.niconicocbf.tokubailab.mvpdemo_cbf.internet.RetrofitClient;
import com.niconicocbf.tokubailab.mvpdemo_cbf.utils.ToastUtils;
import com.niconicocbf.tokubailab.mvpdemo_cbf.view.activity.MainActivity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhotozoupicModel {
    public void getPhotoInfo(final DisplayView mDisplayView,String keyWord){
        retrofit2.Call<PicInfo> picCall = RetrofitClient.getmSerApi().getPicInfo(keyWord);
        picCall.enqueue(new retrofit2.Callback<PicInfo>() {
            @Override
            public void onResponse(retrofit2.Call<PicInfo> call, retrofit2.Response<PicInfo> response) {
                if(response.body()!=null){
                mDisplayView.getDataSuccess(response.body().getInfo().getPhoto());}
            }

            @Override
            public void onFailure(retrofit2.Call<PicInfo> call, Throwable t) {
                ToastUtils.showToast("Get Msg error");
            }
        });


    }
}
