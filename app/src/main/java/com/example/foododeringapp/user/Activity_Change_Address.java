package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.user.service.UserRequestUtility;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Activity_Change_Address extends AppCompatActivity {
    private int addressID, userID;
    private EditText caName, caPhone, caAddress;
    private Button btnEditInfo;
    private RadioGroup radioGroup_sex_edit;
    private RadioButton ca_male, ca_female;
    private Address address;

    private String receiveName;//收件人
    private String sex;        //性别
    private int receivePhone;    //联系电话
    private String addressName;

//    <!--    caName收件人 radioGroup_sex_edit性别:ca_male、ca_female
//    caPhone联系电话 caAddress地址 btnEditInfo修改地址按钮-->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__change__address);
        getIntentData();
        initView();
    }

    private void initView() {
        findViewById();
        address = UserRequestUtility.getAddressByID(addressID);
        caName.setText(address.getReceiveName());
        caPhone.setText(address.getReceivePhone());
        caAddress.setText(address.getAddressName());
        if(address.getSex() != "女"){
            ca_female.setChecked(true);
        }else if(address.getSex() != "男"){
            ca_male.setChecked(true);
        }
        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveName = caName.getText().toString();
                receivePhone = Integer.parseInt(caPhone.getText().toString());
                radioGroup_sex_edit.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener) (group, checkedId) -> {
                    RadioButton radbtn = (RadioButton) findViewById(checkedId);
                    sex = (String) radbtn.getText();
                });
                UserRequestUtility.editAddress(addressID, receiveName, sex, receivePhone, addressName);
            }
        });
    }

    private void findViewById() {
        caName = findViewById(R.id.caName);
        caPhone = findViewById(R.id.caPhone);
        caAddress = findViewById(R.id.caAddress);
        btnEditInfo = findViewById(R.id.btnEditInfo);
        radioGroup_sex_edit = findViewById(R.id.radioGroup_sex_edit);
        ca_male = findViewById(R.id.ca_male);
        ca_female = findViewById(R.id.ca_female);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        addressID = intent.getIntExtra("addressID", -1);
        userID = intent.getIntExtra("userID", -1);
        Log.i("editAddress: userID", String.valueOf(userID));
    }
}