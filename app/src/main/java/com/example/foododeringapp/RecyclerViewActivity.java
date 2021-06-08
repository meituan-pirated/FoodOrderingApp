package com.example.foododeringapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.rider.adapter.Adapter_OrdersNew;

public class RecyclerViewActivity extends BaseActivity {

    private RecyclerView test_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_new_recyclerview);
        test_recycler = (RecyclerView)findViewById(R.id.NewRecyclerView);
        test_recycler.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this));
        Adapter_OrdersNew adapter = new Adapter_OrdersNew(RecyclerViewActivity.this);
        test_recycler.setAdapter(adapter);
        adapter.buttonSetOnclick(new Adapter_OrdersNew.ButtonInterface() {
            @Override
            public void onclick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "点击条目上的按钮"+position, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
