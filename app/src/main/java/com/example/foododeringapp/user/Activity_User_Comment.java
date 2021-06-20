package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foododeringapp.R;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class Activity_User_Comment extends AppCompatActivity {
    private EditText userComment;
    RatingBar ratingMerchant, ratingRider;
    int mrating, rrating;
    Button addComment;
    int orderID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__comment);
        findViewByID();
        getIntentData();
        initView();
    }

    private void initView() {
        ratingMerchant.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mrating = (int) rating;
                Toast.makeText(getApplicationContext(), "rating:"+rating, Toast.LENGTH_LONG).show();
            }
        });
        ratingRider.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rrating = (int) rating;
                Toast.makeText(getApplicationContext(), "rating:"+rating, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getIntentData() {
        Intent intent = new Intent();
        orderID = intent.getIntExtra("orderID", -1);
    }

    private void findViewByID() {
        userComment = findViewById(R.id.userComment);
        ratingMerchant = (RatingBar) findViewById(R.id.ratingMerchant);
        ratingRider = (RatingBar) findViewById(R.id.ratingRider);
        addComment = findViewById(R.id.addComment);
    }
}