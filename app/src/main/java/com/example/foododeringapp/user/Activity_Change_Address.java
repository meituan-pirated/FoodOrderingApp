package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Address;
import com.example.foododeringapp.control.BaseActivity;
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
import android.widget.Toast;

import java.util.TimerTask;

public class Activity_Change_Address extends BaseActivity {
    private int addressID, userID;
    private EditText caName, caPhone, caAddress;
    private Button btnEditInfo;
    private RadioGroup radioGroup_sex_edit;
    private RadioButton ca_male, ca_female;
    private Address address;

    Intent intent;
    private String receiveName;//收件人
    private String sex;        //性别
    private String receivePhone;    //联系电话
    private String addressName;

//    <!--    caName收件人 radioGroup_sex_edit性别:ca_male、ca_female
//    caPhone联系电话 caAddress地址 btnEditInfo修改地址按钮-->
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__change__address);
        getIntentData();
        findViewById();
        initView();
    }

    private void initView() {
        address = UserRequestUtility.getAddressByID(addressID);
        caName.setText(address.getReceiveName());
        caPhone.setText(address.getReceivePhone());
        caAddress.setText(address.getAddressName());
        if(address.getSex() != "女"){
            ca_female.setChecked(true);
        }else if(address.getSex() != "男"){
            ca_male.setChecked(true);
        }
        btnEditInfo.setOnClickListener(v -> {
            receiveName = caName.getText().toString();
            addressName = caAddress.getText().toString();
            receivePhone = caPhone.getText().toString();
            sex = address.getSex();
            radioGroup_sex_edit.setOnCheckedChangeListener((group, checkedId) -> {
                RadioButton radbtn = findViewById(checkedId);
                sex = (String) radbtn.getText();
            });
            int result = UserRequestUtility.editAddress(addressID, receiveName, sex, receivePhone, addressName);
            if(result == 1){
                Toast.makeText(getApplicationContext(), "修改地址成功！", Toast.LENGTH_SHORT).show();
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
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
        intent = getIntent();
        addressID = intent.getIntExtra("addressID", -1);
        userID = intent.getIntExtra("userID", -1);
//        Log.i("editAddress: userID", String.valueOf(userID));
    }
}