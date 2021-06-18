package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.user.service.UserRequestUtility;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Activity_Add_Address extends Activity{
//    <!--    uaName uaPhone uaAddress radioGroup_sex:ua_male ua_female btnChangeInfo-->
    RadioGroup radioGroup_sex;
    RadioButton ua_male, ua_female;
    TextView uaName, uaPhone, uaAddress;
    Button btnChangeInfo;
    Address add_address;
    private int addressId;
    private int userId;     //用户编号
    private String receiveName;//收件人
    private String sex;        //性别
    private int receivePhone;    //联系电话
    private String addressName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__address);
        getContentData();
        initView();
    }

    private void getContentData() {
        Intent intent = getIntent();
        userId = intent.getIntExtra("userID", -1);
    }

    private void initView() {
        findViewByID();
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveName = uaName.getText().toString();
                receivePhone = Integer.parseInt(uaPhone.getText().toString());
                radioGroup_sex.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
                    RadioButton radbtn = (RadioButton) findViewById(checkedId);
                    sex = (String) radbtn.getText();
                });
                UserRequestUtility.addAddress(userId, receiveName, sex, receivePhone, addressName);
            }
        });
    }

    private void findViewByID() {
        radioGroup_sex = findViewById(R.id.radioGroup_sex);
        ua_male = findViewById(R.id.ua_male);
        ua_female = findViewById(R.id.ua_female);
        uaName = findViewById(R.id.uaName);
        uaPhone = findViewById(R.id.uaPhone);
        uaAddress = findViewById(R.id.uaAddress);
        btnChangeInfo = findViewById(R.id.btnChangeInfo);
    }
}