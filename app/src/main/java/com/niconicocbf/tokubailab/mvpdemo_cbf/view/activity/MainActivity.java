package com.niconicocbf.tokubailab.mvpdemo_cbf.view.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
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

public class MainActivity extends BaseActivity<PhotoZoupicpresenter> implements DisplayView<List<PicInfo.InfoBean.PhotoBean>>, SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView picRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

    }

    @Override
    public void getDataSuccess(final List<PicInfo.InfoBean.PhotoBean> successMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final PicListAdapter picListAdapter = new PicListAdapter(getApplicationContext(), successMsg);
                picRecyclerView.setAdapter(picListAdapter);
                picRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        if (newState == RecyclerView.SCROLL_STATE_IDLE
                                && 10 + 1 == picListAdapter.getItemCount()) {
                            mSwipeRefreshLayout.setRefreshing(true);
                            // 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
                           // handler.sendEmptyMessageDelayed(0, 3000);
                        }


                    }});
            }
        });


    }

    @Override
    public void getDataFail(List<PicInfo.InfoBean.PhotoBean> errorMsg) {

    }

    @Override
    public void onRefresh() {

    }
}
