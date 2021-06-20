package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.example.foododeringapp.R;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.rider.Activity_Rider_Main;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_My;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_New;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_Sta;
import com.example.foododeringapp.rider.fragment.Fragment_Rider_Underway;
import com.example.foododeringapp.user.fragment.Fragment_User_Order_Done;
import com.example.foododeringapp.user.fragment.Fragment_User_Order_Ing;

public class Activity_User_Main extends BaseActivity {
    private Toolbar toolbar;
    private TextView toolbarText;
    private BottomNavigationBar bottomNavigationBar;

    private Context context;

    private Handler handler = new Handler();
    private ProgressDialog pg;

    // 网络连接参数
    private IntentFilter intentFilter;
    //自定义内部类，封装了网络变化判断的过程
    private Activity_User_Main.NetworkChangeReceiver networkChangeReceiver;
    public static int networkState = -1;//0：网络不可用  1：网络可用

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = Activity_User_Main.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__main);
        pg = new ProgressDialog(context);
        initStaticView();
    }

    /**
     * 先加载不需要动态获取数据的静态布局，有利于用户体验
     */
    private void initStaticView() {
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        toolbar.setTitle("");
        toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setText("首页");

//        setSupportActionBar(toolbar);//使用toolbar,外观功能和ActionBar一致

//        final ActionBar actionBar = getSupportActionBar();//得到ActionBar实例
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);//让导航按钮显示出来
//            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);//设置导航按钮图标
//        }
        //加载底部导航栏及选中事件
        loadBottomNavigationBar();
    }

    /**
     * 动态添加fragment（碎片）
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }

    /**
     * 加载底部导航栏及选中事件
     */
    private void loadBottomNavigationBar() {
        toolbarText.setText("处理中");
        replaceFragment(new Fragment_User_Order_Ing());
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.nav_wait, "新订单"))
                .addItem(new BottomNavigationItem(R.mipmap.nav_order, "处理中"))
                .addItem(new BottomNavigationItem(R.mipmap.nav_store, "订单统计"))
                .addItem(new BottomNavigationItem(R.mipmap.nav_my, "我的"))
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .setActiveColor(R.color.colorPrimary)
                .setFirstSelectedPosition(0)
                .initialise();
        //监听底部导航栏，实现页面转换
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {//未选中 -> 选中
                switch (position) {
                    case 0:
                        toolbarText.setText("新订单");
                        replaceFragment(new Fragment_User_Order_Ing());
                        break;
                    case 1:
                        toolbarText.setText("已完成");
                        replaceFragment(new Fragment_User_Order_Done());
                        break;
                    case 2:
                        toolbarText.setText("订单统计");
                        replaceFragment(new Fragment_User_Order_Ing());
                        break;
                    case 3:
                        toolbarText.setText("我的");
                        replaceFragment(new Fragment_User_Order_Ing());
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {//选中 -> 未选中

            }

            @Override
            public void onTabReselected(int position) {//选中 -> 选中

            }
        });
    }

    /**
     * 获取网络连接状态
     */
    private void getNetworkState() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");//网络状态发生改变时发出这一条广播
        networkChangeReceiver = new Activity_User_Main.NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
    }


    /**
     * 自定义类
     */
    class NetworkChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //getSystemService()方法得到ConnectivityManager实例，这是一个系统服务类，用于管理网络连接
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //得到NetworkInfo实例
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {//网络可用
                networkState = 1;
            } else {//网络不可用
                networkState = 0;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**
         *  判断requestCode, resultCode 来确定要执行的代码
         */
        /*if (requestCode == 1111 && resultCode == 1222) {
            toolbarText.setText("处理中");
            replaceFragment(new Fragment_Rider_Underway());
            // 在这设置选中你要显示的fragment
        }*/

    }


}

