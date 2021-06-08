package com.example.foododeringapp.rider.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foododeringapp.R;

public class Fragment_Rider_My extends Fragment {
    private Toolbar toolbar;

    private String userName, userImg;

    private LinearLayout my_account, question;

    private Button logout;

    private ViewPager2 fragment_merchant_my_pager;



    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ProgressDialog pg;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rider_my, container, false);
        initView(view);
        return view; //所有fragment 的java文件都是返回这个
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //跟oncreate同样会在打开时被加载，可以用来处理传来的数据并展示
        //需要设置头像、姓名、昵称和号码
        super.onActivityCreated(savedInstanceState);


    }

    private void initView(View view) {

        my_account = view.findViewById(R.id.my_account);
        question = view.findViewById(R.id.quetions);

        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //利用浮窗修改or另一个activity，那返回就会有困难
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //利用浮窗反馈问题，提交后又回来
            }
        });

        logout = view.findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先询问确定要退出吗，是，就真的退出。
                //退出要怎么写
            }
        });

    }


}
