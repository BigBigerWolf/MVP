package com.example.rxretrofitdaggermvp.ui.adapters;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.rxretrofitdaggermvp.R;
import com.example.rxretrofitdaggermvp.mvp.module.entity.NewsInfo;

import javax.inject.Inject;

/**
 * Created by MrKong on 2017/4/5.
 */
public class NewsListAdapter extends BaseQuickAdapter<NewsInfo.DataBean, BaseViewHolder> {

    @Inject
    Activity activity;

    @Inject
    public NewsListAdapter() {
        super(R.layout.news_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsInfo.DataBean data) {
        helper.setText(R.id.title, data.getTitle())
                .setText(R.id.authorName, data.getAuthor_name())
                .setText(R.id.date, data.getDate());
        Glide.with(activity)
                .load(data.getThumbnail_pic_s())
                .error(R.mipmap.error)
                .into((ImageView) helper.getView(R.id.pic));
    }
}
