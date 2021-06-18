package com.example.foododeringapp.merchant.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.OrderIngForM;
import com.example.foododeringapp.merchant.Activity_Merchant_Main;
import com.example.foododeringapp.merchant.adapter.Adapter_OrdersIng;
import com.example.foododeringapp.merchant.service.MerchantRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

public class Fragment_Merchant_Order_Ing2 extends LazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    private List<OrderIngForM> ordersList;     //待处理的订单合集
    private String mParam1;

    private View mEmptyView;//无数据视图

    private EmptyRecyclerView recyclerView;

    //xml文件控件
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private Integer business_id= 12345678;
    private Adapter_OrdersIng orderIngAdapter;

    //网络请求时，弹出此框等待网络数据
    private ProgressDialog pg;

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    private boolean changedState = false;

    private boolean isCreateView = false;
    private boolean isLoadData = false;


    private View mFragmentView;

//    private static final String FRAGMENT_INDEX = "fragment_index";
//    private final int FIRST_FRAGMENT = 0;
//    private final int SECOND_FRAGMENT = 1;
//    private final int THIRD_FRAGMENT = 2;
//
//    private TextView mFragmentView;
//
//    private int mCurIndex = -1;
//    /** 标志位，标志已经初始化完成 */
//    private boolean isPrepared;
//    /** 是否已被加载过一次，第二次就不再去请求数据了 */
//    private boolean mHasLoadedOnce;

    public Fragment_Merchant_Order_Ing2() {
    }

    public Fragment_Merchant_Order_Ing2(int business_id) {
        Bundle bundle = new Bundle();
        bundle.putInt("business_id", business_id);
        setArguments(bundle);
    }

    public static Fragment_Merchant_Order_Ing2 newInstance(int business_id){
        Fragment_Merchant_Order_Ing2 fragment = new Fragment_Merchant_Order_Ing2();
        Bundle bundle = new Bundle();
        bundle.putInt("business_id", business_id);
//        bundle.putInt(FRAGMENT_INDEX, index);
        fragment.setArguments(bundle);
        return fragment;
    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if(getArguments() != null){
//            mParam1 = getArguments().getString("Ing");
//        }
//    }




//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment__merchant__order__ing, container, false);
//        initView(view);
//        isCreateView = true;
////        if (null == mFragmentView) {
////            mFragmentView = inflater.inflate(R.layout.fragment__merchant__order__ing, container, false);
////            initView(mFragmentView);
//////            mListView = (ListView) mFragmentView .findViewById(R.id.mm_listview);
//////            mListView.setAdapter(mAdapter);
////        }
////        return mFragmentView ;
//        return view;
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.i("ing","onActivityCreated");
        super.onActivityCreated(savedInstanceState);

//        pg = new ProgressDialog(getActivity());
//        if(getUserVisibleHint()){
//            Log.i("ing-show", "hint");
//            showList();
//        }


    }

    @Override
    protected int getLayoutRes() {
        Log.i("getLayoutRes",R.layout.fragment__merchant__order__ing+"");
        return R.layout.fragment__merchant__order__ing;
    }


    @Override
    protected void initView(View view) {
        bottomSheetLayout =  view.findViewById(R.id.bottom_sheet_layout);
        //刷新控件
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        recyclerView = view.findViewById(R.id.IngRecyclerView);

        pg = new ProgressDialog(getActivity());
    }

    @Override
    protected void onFragmentLoad(){
        showList();
    }

    /**
     * 加载进行中订单信息
     */
    private void showList() {
        if(!changedState){
            pg.setMessage("数据加载中...");
            pg.show();
        }

        changedState = false;
        Log.i("ing", "showlist");
        if (Activity_Merchant_Main.networkState == 0) {
            Toast.makeText(getActivity(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }

        new Thread() {
            public void run() {
                try {
                    ordersList = MerchantRequestUtility.getIngOrders(business_id);
                    Log.i("ing-List",ordersList.size()+"");
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

            if (ordersList != null && ordersList.size() > 0) {
//                给ordersList创建RecyclerView
                Log.i("orderList", ordersList.get(0).getOrderState());
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(layoutManager);
                orderIngAdapter = new Adapter_OrdersIng(ordersList,  getActivity(), Fragment_Merchant_Order_Ing2.this);
                recyclerView.setAdapter(orderIngAdapter);
                recyclerView.setEmptyView(mEmptyView);
            }
        }
    };


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.i("ing-visible","lazyload");
//        if(isVisibleToUser && isCreateView){
//            Log.i("ing-visible","lazyload");
//            lazyLoad();
//        }
//    }

    //懒加载
    private void lazyLoad(){
        if(!isLoadData){
            showList();
            isLoadData = true;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("ing","resume");
//        if (isFirstLoad){
//            initView();
//            showList();
//            isFirstLoad = false;
//        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("ing","pause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("ing","destroy");
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.i("ing","destroyView");
//        if (null != mFragmentView) {
//            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
//        }
//
//    }

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
}
