package com.example.foododeringapp.merchant.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class LazyFragment extends Fragment {

    View rootView; //fragment根布局
    boolean isViewCreated = false; //根部局是否已经创建
    boolean currentVisibleState = false; //当前视图是否可见

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(getLayoutRes(), container,false);
            Log.i("getLayoutRes",getLayoutRes()+"");
        }
        Log.i("getLayoutResAfterIf",getLayoutRes()+"");
        initView(rootView);
        isViewCreated = true;
        if(getUserVisibleHint()){
            disPatchUserVisibleHint(true);
        }
        return rootView;
    }

    protected abstract int getLayoutRes();

    protected abstract void initView(View rootView);

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isViewCreated) {
            if (isVisibleToUser && !currentVisibleState ) {
                disPatchUserVisibleHint(true);
            } else if(!isVisibleToUser && currentVisibleState){
                disPatchUserVisibleHint(false);
            }
        }
    }

    //分发fragment可见事件
    private void disPatchUserVisibleHint(boolean isVisible){
        if(currentVisibleState == isVisible){
            return;
        }
        currentVisibleState = isVisible;
        if(isVisible){
            onFragmentLoad();
        }else{
            onFragmentLoadStop();
        }


    }

    protected void onFragmentLoad(){

    }

    protected void onFragmentLoadStop(){

    }

    @Override
    public void onResume() {
        super.onResume();
        if(!currentVisibleState && getUserVisibleHint()){
            disPatchUserVisibleHint(true);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if(currentVisibleState && getUserVisibleHint()){
            disPatchUserVisibleHint(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        currentVisibleState = false;
    }
}
