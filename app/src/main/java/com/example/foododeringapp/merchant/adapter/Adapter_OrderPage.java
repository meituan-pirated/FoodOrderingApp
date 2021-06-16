package com.example.foododeringapp.merchant.adapter;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foododeringapp.merchant.fragment.Fragment_Merchant_Order;

import java.util.List;

/**
 * viewPage2的adapter
 */
public class Adapter_OrderPage extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> mData;

    public Adapter_OrderPage(FragmentManager fragmentManager,List<Fragment> fragments,List<String> mData) {
        super(fragmentManager);
        this.mData = mData;
        this.fragments = fragments;
    }


    //    public Adapter_OrderPage(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragments) {
//        super(fragmentActivity);
//        this.fragments = fragments;
//    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
        Log.i("orderPager","destroyItem");
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        Fragment_Merchant_Order fragment = (Fragment_Merchant_Order) super.instantiateItem(container, position);
//        fragment.updateArguments(position);
//        return fragment;
//    }

}
