package com.niconicocbf.tokubailab.mvpdemo_cbf.internet;

import com.niconicocbf.tokubailab.mvpdemo_cbf.bean.PicInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceApi {
    @GET("search_public.json")
    Call<PicInfo> getPicInfo(@Query("keyword")String keyword);

}
