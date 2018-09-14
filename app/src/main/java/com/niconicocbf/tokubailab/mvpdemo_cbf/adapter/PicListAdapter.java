package com.niconicocbf.tokubailab.mvpdemo_cbf.adapter;
import android.content.Context;
import android.content.res.Resources;
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

public class PicListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    List<PicInfo.InfoBean.PhotoBean> photoList;
    Context context;
    public PicListAdapter(Context context,  List<PicInfo.InfoBean.PhotoBean> photoList) {
        this.photoList=photoList;
        this.context=context;
    }
    public class PicInfoViewHolder extends RecyclerView.ViewHolder {
        public RoundImageView displayImageView;
        public PicInfoViewHolder(View itemView) {
            super(itemView);
            displayImageView =  itemView.findViewById(R.id.iv_pic);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_list_item,null);
        return new PicInfoViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        PicInfoViewHolder viewHolder = (PicInfoViewHolder)holder;
        Glide.with(context).load(photoList.get(position).getImage_url()).into(viewHolder.displayImageView);
        viewHolder.displayImageView.setmTextString(photoList.get(position).getPhoto_title());



    }
    //添加数据
    public void addItem( List<PicInfo.InfoBean.PhotoBean> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(photoList);
        photoList.clear();
        photoList.addAll(newDatas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
