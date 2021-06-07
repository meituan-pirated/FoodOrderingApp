package com.example.foododeringapp;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class ListLayoutManager  extends RecyclerView.LayoutManager {

    public ListLayoutManager() {
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {

        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
