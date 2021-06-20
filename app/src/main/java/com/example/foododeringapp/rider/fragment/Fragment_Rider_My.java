package com.example.foododeringapp.rider.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Rider;
import com.example.foododeringapp.rider.Activity_Edit_Account;
import com.example.foododeringapp.rider.Activity_Questions;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.rider.service.RiderRequestUtility;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Rider_My extends Fragment {
    private Toolbar toolbar;
    private String rider_id;

    private String userName, riderAdvatar;

    private LinearLayout my_account, question;

    private Button logout;
    private TextView user_name, user_nickname, user_phone;
    private CircleImageView user_img;
    private Activity mActivity;
    private ViewPager2 fragment_merchant_my_pager;
    Intent intent;



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
        return view; //所有fragment 的java文件都是返回这个
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //跟oncreate同样会在打开时被加载，可以用来处理传来的数据并展示
        //需要设置头像、姓名、昵称和号码
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    
    private void initView() {
        //intent = getActivity().getIntent();
        SharedPreferences preferences = getActivity().getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        rider_id = preferences.getString("userId","");
        Log.i(rider_id,"骑手id");
        Rider rider = RiderRequestUtility.GetRiderInfo(rider_id);

        my_account = getActivity().findViewById(R.id.my_account);
        question = getActivity().findViewById(R.id.quetions);
        logout = getActivity().findViewById(R.id.logout);

        //获取所有需要写上信息的控件
        user_name = getActivity().findViewById(R.id.user_name);
        user_nickname = getActivity().findViewById(R.id.user_nickname);
        user_phone= getActivity().findViewById(R.id.user_phone);
        user_img= getActivity().findViewById(R.id.user_img);

        //控件写上骑手信息
        user_name.setText(rider.getName());
        user_nickname.setText(rider.getNickName());
        user_phone.setText(rider.getPhoneNumber());
        riderAdvatar = rider.getAdvatar();//只有图片名没有后缀名
        int image_id = getResources().getIdentifier(riderAdvatar,"mipmap",getActivity().getPackageName());
        user_img.setImageResource(image_id);



//      为三个需要加监听的控件加上控件呀
        my_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), Activity_Edit_Account.class);
                intent2.putExtra("rider_id", rider_id);
                //直接启动一个Activity startActivity(intent);
                //启动一个有返回值的Activity startActivityForResult
                getActivity().startActivity(intent2);

            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //反馈问题，提交后又回来
                Intent intent3 = new Intent();
                intent3.putExtra("rider_id", rider_id);
                intent3.setClass(getActivity(), Activity_Questions.class);
                //直接启动一个Activity
                //       startActivity(intent);
                //启动一个有返回值的Activity
                getActivity().startActivity(intent3);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先询问确定要退出吗，是，就真的退出。
                //退出要怎么写
            }
        });

    }




}





