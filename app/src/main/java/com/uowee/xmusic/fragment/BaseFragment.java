package com.uowee.xmusic.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * Created by GuoWee on 2018/3/1.
 */

public class BaseFragment extends Fragment {

    //在fragment中，可以通过getActivity()方法获取到当前依附的activity实例。
    // 但是如果在使用的时候直接获取有时候可能会报空指针，那么可以在fragment生命周期的onAttach(Context context)方法中获取到并提升为全局变量
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
