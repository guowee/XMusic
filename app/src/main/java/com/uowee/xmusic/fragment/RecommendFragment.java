package com.uowee.xmusic.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.uowee.xmusic.R;
import com.uowee.xmusic.XMApplication;
import com.uowee.xmusic.entry.FocusItemInfo;
import com.uowee.xmusic.entry.RecommendListNewAlbumInfo;
import com.uowee.xmusic.entry.RecommendListRadioInfo;
import com.uowee.xmusic.entry.RecommendListRecommendInfo;
import com.uowee.xmusic.net.HttpUtils;
import com.uowee.xmusic.util.PreferencesUtility;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by GuoWee on 2018/3/1.
 */

public class RecommendFragment extends BaseFragment {

    private ViewGroup mViewGroup;
    private LayoutInflater mLayoutInflater;

    private View mRecommendView, mLoadView;
    private int width = 160, height = 160;


    private String mPosition;
    private Banner banner;
    private ArrayList<String> mPathList = new ArrayList<>();
    private ArrayList<String> mTitleList = new ArrayList<>();
    private RecommendAdapter mRecomendAdapter;
    private RadioAdapter mRadioAdapter;
    private NewAlbumsAdapter mNewAlbumsAdapter;


    private RecyclerView mRecyclerView1, mRecyclerView2, mRecyclerView3;
    private GridLayoutManager mGridLayoutManager1, mGridLayoutManager2, mGridLayoutManager3;

    private ArrayList<RecommendListNewAlbumInfo> mNewAlbumsList = new ArrayList<>();
    private ArrayList<RecommendListRadioInfo> mRadioList = new ArrayList<>();
    private ArrayList<RecommendListRecommendInfo> mRecommendList = new ArrayList<>();

    private HashMap<String, View> mViewHashMap;
    private View v1, v2, v3;
    private LinearLayout mViewContent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPosition == null) {
            return;
        }
        String st = PreferencesUtility.getInstance(mActivity).getItemPosition();
        if (!st.equals(mPosition)) {
            mPosition = st;
            mViewContent.removeAllViews();
            addViews();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_recommend_container, container, false);
        mLayoutInflater = LayoutInflater.from(mActivity);
        mRecommendView = mLayoutInflater.inflate(R.layout.fragment_recommend, container, false);
        banner = mRecommendView.findViewById(R.id.banner_view);
        mViewContent = mRecommendView.findViewById(R.id.recommend_layout);
        mLoadView = mLayoutInflater.inflate(R.layout.loading, null, false);


        initBanner();

        new LoadRecommend().execute(0);
        mRecomendAdapter = new RecommendAdapter(null);
        mRadioAdapter = new RadioAdapter(null);
        mNewAlbumsAdapter = new NewAlbumsAdapter(null);

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
                if (!mPathList.isEmpty()) {
                    mPathList.clear();
                }

                if (!mTitleList.isEmpty()) {
                    mTitleList.clear();
                }
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
            JsonArray radioArray = object.get("radio").getAsJsonObject().get("result").getAsJsonArray();

            mNewAlbumsList.clear();
            mRecommendList.clear();
            mRadioList.clear();

            for (int i = 0; i < newAlbumArray.size(); i++) {
                mNewAlbumsList.add(XMApplication.gsonInstance().fromJson(newAlbumArray.get(i), RecommendListNewAlbumInfo.class));
                mRecommendList.add(XMApplication.gsonInstance().fromJson(recommendArray.get(i), RecommendListRecommendInfo.class));
                mRadioList.add(XMApplication.gsonInstance().fromJson(radioArray.get(i), RecommendListRadioInfo.class));
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            v1 = mLayoutInflater.inflate(R.layout.recommend_playlist, mViewContent, false);
            mRecyclerView1 = (RecyclerView) v1.findViewById(R.id.recommend_playlist_recyclerview);
            mGridLayoutManager1 = new GridLayoutManager(mActivity, 3);
            mRecyclerView1.setLayoutManager(mGridLayoutManager1);
            mRecyclerView1.setAdapter(mRecomendAdapter);

            v2 = mLayoutInflater.inflate(R.layout.recommend_newalbums, mViewContent, false);
            mRecyclerView2 = (RecyclerView) v2.findViewById(R.id.recommend_newalbums_recyclerview);
            mGridLayoutManager2 = new GridLayoutManager(mActivity, 3);
            mRecyclerView2.setLayoutManager(mGridLayoutManager2);
            mRecyclerView2.setAdapter(mNewAlbumsAdapter);

            v3 = mLayoutInflater.inflate(R.layout.recommend_radio, mViewContent, false);
            mRecyclerView3 = (RecyclerView) v3.findViewById(R.id.recommend_radio_recyclerview);
            mGridLayoutManager3 = new GridLayoutManager(mActivity, 3);
            mRecyclerView3.setLayoutManager(mGridLayoutManager3);
            mRecyclerView3.setAdapter(mRadioAdapter);


            mRecomendAdapter.update(mRecommendList);
            mRadioAdapter.update(mRadioList);
            mNewAlbumsAdapter.update(mNewAlbumsList);

            mViewHashMap = new HashMap<>();
            mViewHashMap.put("推荐歌单", v1);
            mViewHashMap.put("最新专辑", v2);
            mViewHashMap.put("主播电台", v3);
            mPosition = PreferencesUtility.getInstance(mActivity).getItemPosition();
            mViewContent.removeView(mLoadView);
            addViews();
        }
    }

    private void addViews() {

        String[] strs = mPosition.split("/");


        for (int i = 0; i < strs.length; i++) {
            mViewContent.addView(mViewHashMap.get(strs[i]));
        }

    }

    class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<RecommendListRecommendInfo> mList;
        SpannableString spanString;

        public RecommendAdapter(ArrayList<RecommendListRecommendInfo> list) {
            Bitmap b = BitmapFactory.decodeResource(getResources(), R.mipmap.index_icn_earphone);
            ImageSpan imgSpan = new ImageSpan(mActivity, b, ImageSpan.ALIGN_BASELINE);
            spanString = new SpannableString("icon");
            spanString.setSpan(imgSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            mList = list;
        }


        public void update(ArrayList<RecommendListRecommendInfo> list) {
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemView viewholder = new ItemView(layoutInflater.inflate(R.layout.recommend_playlist_item, parent, false));

            return viewholder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final RecommendListRecommendInfo info = mList.get(position);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(info.getPic()))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(((ItemView) holder).art.getController())
                    .setImageRequest(request)
                    .build();

            ((ItemView) holder).art.setController(controller);


            ((ItemView) holder).name.setText(info.getTitle());
            ((ItemView) holder).count.setText(spanString);

            int count = Integer.parseInt(info.getListenum());
            if (count > 10000) {
                count = count / 10000;
                ((ItemView) holder).count.append(" " + count + "万");
            } else {
                ((ItemView) holder).count.append(" " + info.getListenum());
            }
            ((ItemView) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            if (mList == null) {
                return 0;
            }

            if (mList.size() < 7) {
                return mList.size();
            } else {
                return 6;
            }
        }

        class ItemView extends RecyclerView.ViewHolder {
            private SimpleDraweeView art;
            private TextView name, count;

            public ItemView(View itemView) {
                super(itemView);
                art = (SimpleDraweeView) itemView.findViewById(R.id.playlist_art);
                name = (TextView) itemView.findViewById(R.id.playlist_name);
                count = (TextView) itemView.findViewById(R.id.playlist_listen_count);
            }
        }
    }

    class RadioAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<RecommendListRadioInfo> mList;

        public RadioAdapter(ArrayList<RecommendListRadioInfo> list) {
            mList = list;
        }

        public void update(ArrayList<RecommendListRadioInfo> list) {
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemView viewholder = new ItemView(layoutInflater.inflate(R.layout.recommend_newalbums_item, parent, false));

            return viewholder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final RecommendListRadioInfo info = mList.get(position);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(info.getPic()))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(((ItemView) holder).art.getController())
                    .setImageRequest(request)
                    .build();

            ((ItemView) holder).art.setController(controller);

            ((ItemView) holder).albumName.setText(info.getTitle());
            ((ItemView) holder).artsit.setText(info.getDesc());
            ((ItemView) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            if (mList == null) {
                return 0;
            }

            if (mList.size() < 7) {
                return mList.size();
            } else {
                return 6;
            }
        }

        class ItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
            private SimpleDraweeView art;
            private TextView albumName, artsit;

            public ItemView(View itemView) {
                super(itemView);
                art = (SimpleDraweeView) itemView.findViewById(R.id.album_art);
                albumName = (TextView) itemView.findViewById(R.id.album_name);
                artsit = (TextView) itemView.findViewById(R.id.artist_name);
            }

            @Override
            public void onClick(View v) {

            }
        }


    }


    class NewAlbumsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private ArrayList<RecommendListNewAlbumInfo> mList;

        public NewAlbumsAdapter(ArrayList<RecommendListNewAlbumInfo> list) {
            mList = list;
        }

        public void update(ArrayList<RecommendListNewAlbumInfo> list) {
            mList = list;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ItemView viewholder = new ItemView(layoutInflater.inflate(R.layout.recommend_newalbums_item, parent, false));

            return viewholder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            final RecommendListNewAlbumInfo info = mList.get(position);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(info.getPic()))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();

            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setOldController(((ItemView) holder).art.getController())
                    .setImageRequest(request)
                    .build();

            ((ItemView) holder).art.setController(controller);

            ((ItemView) holder).albumName.setText(info.getTitle());
            ((ItemView) holder).artsit.setText(info.getAuthor());
            ((ItemView) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mList == null) {
                return 0;
            }

            if (mList.size() < 7) {
                return mList.size();
            } else {
                return 6;
            }
        }


        class ItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
            private SimpleDraweeView art;
            private TextView albumName, artsit;

            public ItemView(View itemView) {
                super(itemView);
                art = (SimpleDraweeView) itemView.findViewById(R.id.album_art);
                albumName = (TextView) itemView.findViewById(R.id.album_name);
                artsit = (TextView) itemView.findViewById(R.id.artist_name);
            }

            @Override
            public void onClick(View v) {

            }
        }
    }
}
