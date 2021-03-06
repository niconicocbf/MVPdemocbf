package com.niconicocbf.tokubailab.mvpdemo_cbf.view.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.michaldrabik.tapbarmenulib.TapBarMenu;
import com.niconicocbf.tokubailab.mvpdemo_cbf.R;
import com.niconicocbf.tokubailab.mvpdemo_cbf.adapter.PicListAdapter;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.BaseActivity;
import com.niconicocbf.tokubailab.mvpdemo_cbf.base.DisplayView;
import com.niconicocbf.tokubailab.mvpdemo_cbf.bean.PicInfo;
import com.niconicocbf.tokubailab.mvpdemo_cbf.internet.RetrofitClient;
import com.niconicocbf.tokubailab.mvpdemo_cbf.presenter.PhotoZoupicpresenter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity<PhotoZoupicpresenter> implements DisplayView<List<PicInfo.InfoBean.PhotoBean>>, SwipeRefreshLayout.OnRefreshListener ,SearchView.OnQueryTextListener, MenuItem.OnMenuItemClickListener
{


    private RecyclerView picRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<PicInfo.InfoBean.PhotoBean> totalSuccessMsg;
    private ArrayList<PicInfo.InfoBean.PhotoBean> dataLoadOneTime;
    private PicListAdapter picListAdapter;
    private int itemMarks=10;
    private List<PicInfo.InfoBean.PhotoBean> newDatas;
    private GridLayoutManager mGridLayoutManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private List<PicInfo.InfoBean.PhotoBean> filterList;
    private PhotoZoupicpresenter photoZoupicpresenter;


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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));//设置Toolbar的背景颜色
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        toolbar.setLogo(R.mipmap.app_icon);//设置logo
        toolbar.setTitle("Hi Title");//设置标题
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.picture_title_default);
        actionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        picRecyclerView = findViewById(R.id.recycler_view);
        mGridLayoutManager = new GridLayoutManager(this, 2);
        picRecyclerView.setLayoutManager(mGridLayoutManager);
        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_widget);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        newDatas = new ArrayList<>();
        filterList = new ArrayList<>();
        final SearchView etUpdatePicInfo = findViewById(R.id.updatePicInfo);
        final TapBarMenu tapBarMenu = findViewById(R.id.tapBarMenu);
        tapBarMenu.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                tapBarMenu.toggle();
                etUpdatePicInfo.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        photoZoupicpresenter.setKeyword(query);
                        photoZoupicpresenter.getPicInfo();
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });

            }
        });

        photoZoupicpresenter = getmPresenter();
        photoZoupicpresenter.setKeyword("sakura");
        photoZoupicpresenter.getPicInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar, menu);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.action_search_title).getActionView();
        mSearchView.setOnQueryTextListener(this);
        MenuItem imageUpdateBtn = menu.findItem(R.id.action_update_image);
        imageUpdateBtn.setOnMenuItemClickListener(this);

        return true;
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
        picRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (picListAdapter.isFadeTips() == false && lastVisibleItem + 1 == picListAdapter.getItemCount()) {
                        updateRecyclerView();
                    }

                }


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();

            }
        });




    }

    private void updateRecyclerView() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        },500);

    }

    @Override
    public void getDataFail(String errorMsg) {

    }

    @Override
    public void onRefresh() {
        if(itemMarks<=totalSuccessMsg.size()) {
            itemMarks += 10;
            for (int i = itemMarks; i < itemMarks + 10; i++) {
                if (i > totalSuccessMsg.size() - 1) {
                    picListAdapter.addItem(newDatas, true);
                    break;
                }
                newDatas.add(totalSuccessMsg.get(i));
            }
            picListAdapter.addItem(newDatas, true);
            newDatas.clear();
            mSwipeRefreshLayout.setRefreshing(false);
        }else{
            picListAdapter.addItem(newDatas, false);
            mSwipeRefreshLayout.setRefreshing(false);
        }
        Toast.makeText(this, "更新了10条数据..." + itemMarks, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        for(PicInfo.InfoBean.PhotoBean tmp:totalSuccessMsg){
            if(tmp.getPhoto_title().contains(newText)){
                filterList.add(tmp);
            }
        }
        picListAdapter.filterItem(filterList,false);
        filterList.clear();
        return false;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        item.getItemId();
        switch (item.getItemId()){
            case R.id.action_update_image:


                break;
        }
        return false;
    }

}
