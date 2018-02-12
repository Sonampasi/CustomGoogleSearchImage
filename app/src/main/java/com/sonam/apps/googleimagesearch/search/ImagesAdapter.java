package com.sonam.apps.googleimagesearch.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.sonam.apps.googleimagesearch.R;
import com.sonam.apps.googleimagesearch.model.Image;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sonam on 2/11/2018.
 */

class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<Image> mImages;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivImage)
        ImageView ivImage;

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    ImagesAdapter(Context context, List<Image> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {

        String url = mImages.get(position).getLink();
        Glide.with(mContext).load(url).apply(new RequestOptions().override(300, 300).placeholder(R.drawable.ic_launcher_background).fitCenter().diskCacheStrategy(DiskCacheStrategy.ALL)).into(vh.ivImage);
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    void updateBooks(List<Image> images) {
        mImages = images;
        notifyDataSetChanged();
    }
}
