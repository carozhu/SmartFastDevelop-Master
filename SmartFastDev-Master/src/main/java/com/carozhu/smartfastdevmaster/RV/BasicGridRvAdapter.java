package com.carozhu.smartfastdevmaster.RV;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.carozhu.smartfastdevmaster.R;
import com.sunfusheng.GlideImageView;

import java.util.List;

/**
 * Author: carozhu
 * Date  : On 2018/7/26
 * Desc  :
 */
public class BasicGridRvAdapter extends RecyclerView.Adapter{

    private List<String> imageArrayList;

    public BasicGridRvAdapter(List<String> imageArrayList){
        this.imageArrayList = imageArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_imageview, parent, false);
        return new BasicGridRvAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BasicGridRvAdapterViewHolder viewHolder = (BasicGridRvAdapterViewHolder) holder;
        viewHolder.render(imageArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageArrayList == null?0:imageArrayList.size();
    }

    class BasicGridRvAdapterViewHolder extends RecyclerView.ViewHolder {
        GlideImageView iv_bg;
        public BasicGridRvAdapterViewHolder(View itemView) {
            super(itemView);
            iv_bg = (GlideImageView) itemView.findViewById(R.id.imageView);

        }

        public void render(String imageUrl){
            iv_bg.centerCrop().diskCacheStrategy(DiskCacheStrategy.DATA).placeholder(R.drawable.img_loading).error(R.drawable.img_loading).load(imageUrl);
        }

    }
}
