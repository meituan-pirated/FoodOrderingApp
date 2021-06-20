package com.example.foododeringapp.user.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Merchant;
import com.example.foododeringapp.user.Activity_User_Main;
import com.example.foododeringapp.user.adapter.Adapter_LeftRecycle;
import com.example.foododeringapp.user.adapter.Adapter_User_allMerchants;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.HashMap;
import java.util.List;


public class Fragment_User_First extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener{

    private List<Merchant> merchantList;     //待处理的订单合集

    private View mEmptyView;//无数据视图
    private SliderLayout slider;

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmptyRecyclerView recyclerView;
    private RecyclerView leftRecycle;
    private EditText rebot_info;
    private Button btn_send;
    public Integer user_ID = 201821101;

    private Adapter_User_allMerchants adapter;
   private Adapter_LeftRecycle left_adapter;

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private ProgressDialog pg;

    private String findform = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__user__first, container, false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        pg = new ProgressDialog(getActivity());
        showList();
    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_User_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    merchantList = UserRequestUtility.getAllMerchants();
//                    for(int i =0; i<merchantList.size(); i++){
//                        Log.i("Fragment-u",merchantList.get(i).getName()+i);
//                    }
                    handler.post(runnableOrderList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableOrderList = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();

            if (merchantList != null && merchantList.size() > 0) {
//               给ordersList创建RecyclerView
                adapter = new Adapter_User_allMerchants(merchantList, Fragment_User_First.this, getActivity(), user_ID);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setEmptyView(mEmptyView);
            }else{
                Toast.makeText(getActivity(), "没有商家数据", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initView() {
        bottomSheetLayout =  getActivity().findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        slider = getActivity().findViewById(R.id.slider);

        recyclerView = getActivity().findViewById(R.id.user_merchant_RecyclerView);
//        leftRecycle = getActivity().findViewById(R.id.leftRecycle);

        btn_send = getActivity().findViewById(R.id.btn_send);
        rebot_info = getActivity().findViewById(R.id.rebot_info);

        // 轮播图
        HashMap<String, Integer> photos = new HashMap<>();
        photos.put("pizza 新品", R.drawable.pizza);
        photos.put("超好吃的牛肉饭", R.drawable.beefrice);
        photos.put("香香的小鱼干", R.drawable.fish);
        photos.put("广告位招租", R.drawable.button_add);

        for (String name : photos.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(name)
                    .image(photos.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);
            slider.addSlider(textSliderView);
        }
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(2000);
        slider.addOnPageChangeListener(this);

//        left_adapter = new Adapter_LeftRecycle(getActivity());
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        leftRecycle.setLayoutManager(layoutManager);
//        leftRecycle.setAdapter(left_adapter);
//        leftRecycle.setScrollingTouchSlop(0);

        btn_send.setOnClickListener(v -> {
            findform = rebot_info.getText().toString();
            if(findform != null){
//                Log.i("lookfor:", findform);
                merchantList = UserRequestUtility.getMerchantsByLook(findform);
                if (merchantList != null && merchantList.size() > 0){
                    adapter = new Adapter_User_allMerchants(merchantList, Fragment_User_First.this, getActivity(), user_ID);
                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager1);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getActivity(), "没有相关的商家！", Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(getActivity(), "请输入要查找的内容", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        showList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //刷新控件停止两秒后消失
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    //性能优化。当页面显示时进行自动播放
    @Override
    public void onStart() {
        super.onStart();
        slider.startAutoCycle();
    }

    //性能优化。当页面不显示时暂停自动播放
    @Override
    public void onStop() {
        super.onStop();
        slider.stopAutoCycle();
    }
}