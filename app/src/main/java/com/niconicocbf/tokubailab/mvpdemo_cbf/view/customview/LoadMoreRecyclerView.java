package com.niconicocbf.tokubailab.mvpdemo_cbf.view.customview;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.niconicocbf.tokubailab.mvpdemo_cbf.adapter.PicListAdapter;

import java.util.ArrayList;
import java.util.List;

public class LoadMoreRecyclerView extends RecyclerView {
    private final int STATE_NONE = 0x00;
    private final int STATE_LOADING = 0x01;
    private final int STATE_FAILURE = 0x02;
    private final int STATE_COMPLETE = 0x03;

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }
//    class MySrollListener extends RecyclerView.OnScrollListener{
//
//        private int lastCompletelyVisibleItemPosition;
//
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//                final PicListAdapter adapter = (PicListAdapter) recyclerView.getAdapter();
//            LayoutManager layoutManager = getLayoutManager();
//
//            if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastCompletelyVisibleItemPosition + 1 ==adapter.getItemCount()) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        List<String> newDatas = new ArrayList<String>();
//                        for (int i = 0; i< 5; i++) {
//                            int index = i +1;
//                            newDatas.add("more item" + index);
//                        }
//                       // adapter.addItem();
//                    }
//                },1000);
//            }
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//            GridLayoutManager gridLayoutManager  = (GridLayoutManager) recyclerView.getLayoutManager();
//            lastCompletelyVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
//        }
//    }
}
