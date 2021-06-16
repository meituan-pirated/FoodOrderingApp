package com.example.foododeringapp.merchant.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.merchant.Activity_Merchant_ProductInfoList;
import com.example.foododeringapp.merchant.Activity_Merchant_StoreInfo;

public class Fragment_Merchant_Store extends Fragment{
//    Integer business_id;
    Integer business_id=12345678;

    LinearLayout store_info_item_view, product_item_view;
    //网络请求时，弹出此框等待网络数据
//    private ProgressDialog pg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__merchant__store, container, false);
        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
//        pg = new ProgressDialog(getActivity());
        store_info_item_view = getActivity().findViewById(R.id.store_info_item_view);
        product_item_view = getActivity().findViewById(R.id.product_item_view);

        //跳转入店铺信息管理页面
        store_info_item_view.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Merchant_StoreInfo.class);
                intent.putExtra("business_id", business_id);
                startActivityForResult(intent, 301);
            }
        });
//        showList();
        //跳转入商品信息管理页面
        product_item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击跳转到商品信息页面

                Intent intent = new Intent(getActivity(), Activity_Merchant_ProductInfoList.class);
                intent.putExtra("business_id", business_id);
                startActivityForResult(intent, 302);
            }
        });

    }


    /**
     * 加载fragment_merchant_store.xml的组件
     */
    private void initView() {


    }

    /**
     * 从详细页面返回
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1:
//                if (resultCode == 3) {
//                    int count = data.getIntExtra("count", -1);
//                    int foodId = data.getIntExtra("foodId", -1);
//                    Foods foods = null;
//                    for (int i = 0; i < foodsList.size(); i++) {
//                        if (foodsList.get(i).getId() == foodId) {
//                            foods = foodsList.get(i);
//                            break;
//                        }
//                    }
//                    if (count > 0) {//添加商品
//                        for (int i = 0; i < count; i++) {
//                            add(foods, true);
//                        }
//                    }
//                    if (count < 0) {//移除商品
//                        for (int i = 0; i < -(count); i++) {
//                            remove(foods, true);
//                        }
//                    }
//                }
//                break;
//        }
        Log.i("从product_info返回",String.valueOf(data));
    }


}