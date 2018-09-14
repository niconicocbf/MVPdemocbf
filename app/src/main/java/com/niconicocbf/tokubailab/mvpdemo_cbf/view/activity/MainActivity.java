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
import android.widget.Toast;

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
    private List<PicInfo.InfoBean.PhotoBean> totalSuccessMsg;
    private ArrayList<PicInfo.InfoBean.PhotoBean> dataLoadOneTime;
    private PicListAdapter picListAdapter;
    private int itemMarks=10;
    private List<PicInfo.InfoBean.PhotoBean> newDatas;

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
        newDatas = new ArrayList<>();

    }

    @Override
    public void getDataSuccess(final List<PicInfo.InfoBean.PhotoBean> successMsg) {
        totalSuccessMsg = successMsg;
        dataLoadOneTime = new ArrayList<>();

        for(int index=0;index<10;index++){
            dataLoadOneTime.add(totalSuccessMsg.get(index));
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                picListAdapter = new PicListAdapter(getApplicationContext(), dataLoadOneTime);
                picRecyclerView.setAdapter(picListAdapter);
            }
        });




    }

    @Override
    public void getDataFail(String errorMsg) {

    }

    @Override
    public void onRefresh() {
        itemMarks+=10;
        for (int i = itemMarks; i <itemMarks+10; i++) {
            if(i>totalSuccessMsg.size()-1){
                picListAdapter.addItem(newDatas);
                break;
                }
            newDatas.add(totalSuccessMsg.get(i));
        }
        picListAdapter.addItem(newDatas);
        newDatas.clear();
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "更新了五条数据..."+itemMarks, Toast.LENGTH_SHORT).show();

    }
}
