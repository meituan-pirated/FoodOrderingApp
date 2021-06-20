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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_Add_Address extends Activity{
//    <!--    uaName uaPhone uaAddress radioGroup_sex:ua_male ua_female btnChangeInfo-->
    RadioGroup radioGroup_sex;
    RadioButton ua_male, ua_female;
    EditText uaName, uaPhone, uaAddress;
    Button btnChangeInfo;
    Intent intent;
    Address add_address;
    private int addressId;
    private int userId, result;     //用户编号
    private String receiveName;//收件人
    private String sex;        //性别
    private String receivePhone;    //联系电话
    private String addressName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add__address);
        intent = getIntent();
        userId = intent.getIntExtra("userID", -1);
        initView();
    }

    private void initView() {
        findViewByID();
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveName = uaName.getText().toString();
                receivePhone = uaPhone.getText().toString();
                addressName = uaAddress.getText().toString();
                sex = "男";
                radioGroup_sex.setOnCheckedChangeListener((group, checkedId) -> {
                    RadioButton radbtn = findViewById(checkedId);
                    sex = (String) radbtn.getText();
                });
                result = UserRequestUtility.addAddress(userId, receiveName, sex, receivePhone, addressName);
                if(result == 1){
                    Toast.makeText(getApplicationContext(), "新增地址成功！", Toast.LENGTH_SHORT).show();
                }
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 1500);//1.5秒后执行返回
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