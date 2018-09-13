package com.niconicocbf.tokubailab.mvpdemo_cbf.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.niconicocbf.tokubailab.mvpdemo_cbf.R;
import com.niconicocbf.tokubailab.mvpdemo_cbf.adapter.PicListAdapter;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.BaseActivity;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.BasePresenter;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.DisplayView;
import com.niconicocbf.tokubailab.mvpdemo_cbf.bean.PicInfo;
import com.niconicocbf.tokubailab.mvpdemo_cbf.presenter.PhotoZoupicpresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<PhotoZoupicpresenter> implements DisplayView<List<PicInfo.InfoBean.PhotoBean>> {


    private RecyclerView picRecyclerView;

    @Override
    protected PhotoZoupicpresenter createPresenter() {
        return new PhotoZoupicpresenter();
    }

    @Override
    protected int getViewResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        picRecyclerView = findViewById(R.id.recycler_view);
        picRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        getmPresenter().getPicInfo();
    }


    @Override
    public void getDataSuccess(final List<PicInfo.InfoBean.PhotoBean> successMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                picRecyclerView.setAdapter(new PicListAdapter(getApplicationContext(),successMsg));
            }
        });


    }

    @Override
    public void getDataFail(List<PicInfo.InfoBean.PhotoBean> errorMsg) {

    }
}
