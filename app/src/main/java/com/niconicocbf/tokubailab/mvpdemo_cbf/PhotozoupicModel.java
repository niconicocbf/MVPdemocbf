package com.niconicocbf.tokubailab.mvpdemo_cbf;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PhotozoupicModel {
    public static String URL="https://api.photozou.jp/rest/search_public.json?keyword=\"sakura\"";
    public void getPhotoInfo(final DisplayView mDisplayView){
        OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(URL)
                    .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                mDisplayView.getDataFail(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mDisplayView.getDataSuccess(response.body().string());
            }
        });
    }
}
