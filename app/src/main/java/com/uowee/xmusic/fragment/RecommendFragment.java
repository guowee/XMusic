package com.uowee.xmusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uowee.xmusic.R;
import com.uowee.xmusic.XMApplication;
import com.uowee.xmusic.entry.FocusItemInfo;
import com.uowee.xmusic.entry.RecommendListNewAlbumInfo;
import com.uowee.xmusic.entry.RecommendListRecommendInfo;
import com.uowee.xmusic.net.HttpUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by GuoWee on 2018/3/1.
 */

public class RecommendFragment extends BaseFragment {

    private ViewGroup mViewGroup;
    private LayoutInflater mLayoutInflater;

    private View mRecommendView, mLoadView;

    private Banner banner;
    private ArrayList<String> mPathList = new ArrayList<>();
    private ArrayList<String> mTitleList = new ArrayList<>();

    private ArrayList<RecommendListNewAlbumInfo> mNewAlbumsList = new ArrayList<>();

    private ArrayList<RecommendListRecommendInfo> mRecommendList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recommend_container, container, false);
        mLayoutInflater = LayoutInflater.from(mActivity);
        mRecommendView = mLayoutInflater.inflate(R.layout.fragment_recommend, container, false);
        banner = mRecommendView.findViewById(R.id.banner_view);
        initBanner();

        mLoadView = mLayoutInflater.inflate(R.layout.loading, null, false);

        mViewGroup.addView(mRecommendView);
        mViewGroup.addView(mLoadView);
        return mViewGroup;
    }


    private void initBanner() {

        new AsyncTask<Integer, Void, Void>() {

            @Override
            protected Void doInBackground(Integer... params) {


                String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.6.5.6&format=json&method=baidu.ting.plaza.getFocusPic&num=8";

                JsonObject object = HttpUtils.getResponseFromJson(mActivity, url, false);

                JsonArray focusArray = object.get("pic").getAsJsonArray();

                for (int i = 0; i < focusArray.size(); i++) {
                    mPathList.add(XMApplication.gsonInstance().fromJson(focusArray.get(i), FocusItemInfo.class).getRandpic());
                    mTitleList.add(XMApplication.gsonInstance().fromJson(focusArray.get(i), FocusItemInfo.class).getRandpic_desc());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                //设置内置样式
                banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                //设置图片加载器
                banner.setImageLoader(new MyLoader());
                //设置图片网址或地址的集合
                banner.setImages(mPathList);
                //设置轮播的动画效果，内含多种特效
                banner.setBannerAnimation(Transformer.Default);
                //设置轮播图的标题集合
                banner.setBannerTitles(mTitleList);
                //设置轮播间隔时间
                banner.setDelayTime(3000);
                //设置是否为自动轮播，默认是“是”。
                banner.isAutoPlay(true);
                //设置指示器的位置，小点点，左中右。
                banner.setIndicatorGravity(BannerConfig.CENTER)
                        //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
                        .setOnBannerListener(new OnBannerListener() {
                            @Override
                            public void OnBannerClick(int position) {

                            }
                        })
                        //必须最后调用的方法，启动轮播图。
                        .start();
            }
        }.execute(0);


    }

    class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    class LoadRecommend extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected Integer doInBackground(Integer... params) {
            String url = "http://tingapi.ting.baidu.com/v1/restserver/ting?from=android&version=5.8.1.0&channel=ppzs&operator=3&method=baidu.ting.plaza.index&cuid=89CF1E1A06826F9AB95A34DC0F6AAA14";
            JsonObject list = HttpUtils.getResponseFromJson(mActivity, url, false);
            JsonObject object = list.get("result").getAsJsonObject();
            JsonArray newAlbumArray = object.get("mix_1").getAsJsonObject().get("result").getAsJsonArray();
            JsonArray recommendArray = object.get("diy").getAsJsonObject().get("result").getAsJsonArray();

            for (int i = 0; i < newAlbumArray.size(); i++) {
                mNewAlbumsList.add(XMApplication.gsonInstance().fromJson(newAlbumArray.get(i), RecommendListNewAlbumInfo.class));
                mRecommendList.add(XMApplication.gsonInstance().fromJson(recommendArray.get(i), RecommendListRecommendInfo.class));
            }
            for (RecommendListRecommendInfo info : mRecommendList) {
                Log.i("TAG", info.toString());
            }

            return params[0];
        }

    }
}
