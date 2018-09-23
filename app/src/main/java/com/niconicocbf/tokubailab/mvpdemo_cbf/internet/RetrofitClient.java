package com.niconicocbf.tokubailab.mvpdemo_cbf.internet;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static ServiceApi getmSerApi() {
        return mSerApi;
    }

    private final static ServiceApi mSerApi;
    static {
        OkHttpClient mOkHttpClient=new OkHttpClient();
        Retrofit mRetrofit=new Retrofit.Builder().baseUrl("https://api.photozou.jp/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(mOkHttpClient)
                .build();

        //创建一个Retrofit实例对象。
        mSerApi=mRetrofit.create(ServiceApi.class);

    }
}
