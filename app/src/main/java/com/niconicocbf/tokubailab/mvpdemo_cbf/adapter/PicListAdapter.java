package com.niconicocbf.tokubailab.mvpdemo_cbf.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.niconicocbf.tokubailab.mvpdemo_cbf.R;
import com.niconicocbf.tokubailab.mvpdemo_cbf.bean.PicInfo;
import com.niconicocbf.tokubailab.mvpdemo_cbf.view.customview.RoundImageView;

import java.util.List;

public class PicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<PicInfo.InfoBean.PhotoBean> photoList;
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示
    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler


    Context context;

    public PicListAdapter(Context context, List<PicInfo.InfoBean.PhotoBean> photoList) {
        this.photoList = photoList;
        this.context = context;
    }

    public class PicInfoViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView displayImageView;

        public PicInfoViewHolder(View itemView) {
            super(itemView);
            displayImageView = itemView.findViewById(R.id.iv_pic);

        }
    }

    public class PicInfoFootView extends RecyclerView.ViewHolder {
        public TextView mFootView;

        public PicInfoFootView(View itemView) {
            super(itemView);
            mFootView=itemView.findViewById(R.id.tv_footView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == normalType) {
           return new PicInfoViewHolder(LayoutInflater.from(context).inflate(R.layout.pic_list_item, null));
//                   (PicInfoFootView)LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_list_item, null);
        } else {
          return  new PicInfoFootView(LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_list_footview, null));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PicInfoViewHolder) {
            PicInfoViewHolder viewHolder = (PicInfoViewHolder) holder;
            Glide.with(context).load(photoList.get(position).getImage_url()).into(viewHolder.displayImageView);
            viewHolder.displayImageView.setmTextString(photoList.get(position).getPhoto_title());
        } else {
            PicInfoFootView mFootView = (PicInfoFootView) holder;
            mFootView.mFootView.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (photoList.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((PicInfoFootView) holder).mFootView.setText("正在加载更多...");
                }
            } else {
                if (photoList.size() > 0) {
                    ((PicInfoFootView) holder).mFootView.setText("没有更多数据啦！");
                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((PicInfoFootView) holder).mFootView.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }


        }


    }

    //添加数据
    public void addItem(List<PicInfo.InfoBean.PhotoBean> newDatas, boolean ifhasMore) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
//        newDatas.addAll(photoList);
//        photoList.clear();
//        photoList.addAll(newDatas);
        photoList.addAll(newDatas);
        notifyDataSetChanged();
        hasMore = ifhasMore;
    }
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }
    public int getRealLastPosition() {
        return photoList.size();
    }




    @Override
    public int getItemCount() {
        return photoList.size() + 1;
    }
}
