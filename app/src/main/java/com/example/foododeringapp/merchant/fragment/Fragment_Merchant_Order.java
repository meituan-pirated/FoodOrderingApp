package com.example.foododeringapp.merchant.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.merchant.adapter.Adapter_OrderPage;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Merchant_Order extends Fragment {



    private TabLayout orderPage_indicator;
    private ViewPager orderPages;
    private List<String> mData = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__merchant__order, container, false);
        initView(view);
        return view;
    }

    private void initData(){
        fragmentList.add(new Fragment_Merchant_Order_Ing2().newInstance(12345678));
        fragmentList.add(new Fragment_Merchant_Order_Done2().newInstance(12345678));
//        fragmentList.add(new Fragment_Merchant_Order_Canceled().newInstance(12345678));
        mData.add("进行中");
        mData.add("已完成");
//        mData.add("已取消");
    }


    private void initView(View view){

        orderPage_indicator = view.findViewById(R.id.orderPage_indicator);
        orderPages = view.findViewById(R.id.orderPages);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        orderPages.setLayoutManager(layoutManager);
//        Adapter_OrderPage adapter = new Adapter_OrderPage(getActivity(), fragmentList);
        Adapter_OrderPage adapter = new Adapter_OrderPage(getChildFragmentManager(),fragmentList, mData);
        orderPages.setAdapter(adapter);
//        orderPages.setOffscreenPageLimit(0);
        orderPage_indicator.setTabMode(TabLayout.MODE_FIXED);
//        orderPages.setOnCapturedPointerListener(this);
        for(int i=0;i< mData.size(); i++){
            orderPage_indicator.addTab(orderPage_indicator.newTab().setText(mData.get(i)));
        }


//        fragmentList.add(Fragment_Merchant_Order_Ing.newInstance())
//        new TabLayoutMediator(orderPage_indicator, orderPages, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                //设置导航栏的文字
//                tab.setText(mData.get(position));
//            }
//        }).attach();

        orderPage_indicator.setupWithViewPager(orderPages);
    }



}