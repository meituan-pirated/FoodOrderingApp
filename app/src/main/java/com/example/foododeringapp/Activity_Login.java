package com.example.foododeringapp;

import android.app.Activity;
import com.example.foododeringapp.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foododeringapp.bean.Login;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.control.ActivityCollector;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.merchant.Activity_Merchant_Main;
import com.example.foododeringapp.rider.Activity_Rider_Main;
import com.example.foododeringapp.service.PostUtility;
import com.example.foododeringapp.tools.VerificationCode;
import com.example.foododeringapp.user.Activity_User_Main;
import com.example.foododeringapp.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Activity_Login extends BaseActivity implements View.OnClickListener {

    private static final String[] m = {"用户", "商家", "骑手"};
    private Handler loginHandler = new Handler();
    private Button btn_Exchange;
    private ImageView Image_Code;
    private ProgressDialog progress;
    //private BottomMenu menuWindow;
    private TextView go_id_login, go_phone_login, tv_forget_password;
    private Spinner mSpinner;
    private ArrayAdapter<String> adapter;
    private Button login;//登录按钮
    private EditText etUserPhone, etPwd, etUserId, VF_Code;
    private String userPhone, userId, pwd;
    private Integer userType;

    private int statusCode = 0;
    private String postForm;//接受登录返回结果

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login);
        initView();
        go_id_login.setOnClickListener(this);
        go_phone_login.setOnClickListener(this);
        login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
    }

    private void initView() {
        etPwd = (EditText) findViewById(R.id.etPassword);
        etUserPhone = (EditText) findViewById(R.id.etUserPhone);
        etUserId = (EditText) findViewById(R.id.etUserId);

        //选类型
        mSpinner = (Spinner) findViewById(R.id.spinner);

        //验证码
        VF_Code = (EditText) findViewById(R.id.Code);
        Image_Code = (ImageView) findViewById(R.id.Image_Code);
        btn_Exchange = (Button) findViewById(R.id.Exchange);
        btn_Exchange.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        //登录那块
        go_id_login = (TextView) findViewById(R.id.go_id_login);
        go_phone_login = (TextView) findViewById(R.id.go_phone_login);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        login = (Button) findViewById(R.id.login);


        Image_Code.setImageBitmap(VerificationCode.getVerificationCode());

        btn_Exchange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Image_Code.setImageBitmap(VerificationCode
                        .getVerificationCode());
            }
        });

        Image_Code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Image_Code.setImageBitmap(VerificationCode
                        .getVerificationCode());
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
        //设置下拉列表的风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);//simple_list_item_checked

        //        int dp2px = Utils.dp2px(mContext, 40);
        //        mSpinner.setDropDownVerticalOffset(dp2px); //这个是第二种方式，防止下拉框遮挡显示框的办法
        //设置下拉选择框的背景,不要应该也行？？我找不到
        //mSpinner.setPopupBackgroundResource(R.layout.support_simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        mSpinner.setAdapter(adapter);

        //添加事件Spinner事件监听
        mSpinner.setOnItemSelectedListener(new SpinnerSelectedListener());


        SharedPreferences preferences = getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        String phone = preferences.getString("phoneNumber", "");
        String id = preferences.getString("userId","");
        String pwd = preferences.getString("password", "");
        Integer type = preferences.getInt("userType",0);
        if (phone != null && !phone.isEmpty()) {
            //为啥不为空就塞这个，说明之前登录过？拿上面getString里第二个参数是啥意思
            etUserPhone.setText(phone);
        }
        if (id != null && !id.isEmpty()) {
            //为啥不为空就塞这个，说明之前登录过？拿上面getString里第二个参数是啥意思
            etUserId.setText(id);
        }
        if (pwd != null && !pwd.isEmpty()) {
            etPwd.setText(pwd);
        }
        if(type!=0) {
            userType = type;
        }


    }

    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View arg1, int position,
                                   long id) {
            userType = position+1; //要注意到底是不是从0开始，是的！
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.go_id_login:
                go_phone_login.setVisibility(View.VISIBLE);
                etUserId.setVisibility(View.VISIBLE);
                etUserPhone.setVisibility(View.GONE);
                go_id_login.setVisibility(View.GONE);
                break;
            case R.id.go_phone_login:
                go_phone_login.setVisibility(View.GONE);
                etUserId.setVisibility(View.GONE);
                etUserPhone.setVisibility(View.VISIBLE);
                go_id_login.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_forget_password:
                Util.showToast(getApplication(), "敬请期待...");
                break;
            case R.id.login:
                progress = ProgressDialog.show(this, "请稍候", "正在登录...", true);
//                progress.setCancelable(false);
                if (VerificationCode.checkVerificationCode(VF_Code.getText()
                        .toString())) {
                    userId = etUserId.getText().toString();
                    userPhone = etUserPhone.getText().toString();
                    pwd = etPwd.getText().toString();
                    if (!Util.checkNetwork(this)) {
                        progress.dismiss();
                        return;
                    }
                    if (go_id_login.getVisibility()==View.VISIBLE){
                        //当前是电话登录
                        if (!"".equals(userPhone) && !"".equals(pwd)) {
                            //密码电话都不为空
                            login(1);
                        } else if (userPhone.toString().length() == 0) {
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的手机号码不能为空，请注意输入!");
                            return;
                        } else if (pwd.toString().length() == 0) {
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的密码不能为空，请注意输入!");
                            return;
                        }else {
                            //两项都没填
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的电话号码和密码不能为空，请注意输入!");
                            return;
                        }
                    }else{
                        //用户Id登录
                        if (!"".equals(userId) && !"".equals(pwd)) {
                            //密码电话都不为空
                            login(2);
                        } else if (userId.toString().length() == 0) {
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的ID不能为空，请注意输入!");
                            return;
                        } else if (pwd.toString().length() == 0) {
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的密码不能为空，请注意输入!");
                            return;
                        }else {
                            //两项都没填
                            progress.dismiss();
                            Util.showToast(Activity_Login.this, "您的ID和密码不能为空，请注意输入!");
                            return;
                        }
                    }

                } else {
                    //验证码错误
                    progress.dismiss();
                    Toast.makeText(Activity_Login.this, "验证码错误，请重新输入！",
                            Toast.LENGTH_LONG).show();
                    Image_Code.setImageBitmap(VerificationCode
                            .getVerificationCode());
                    VF_Code.setText("");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login(int type) {
        if(type==1) {//电话登录
            new Thread() {
                public void run() {
                    try {
                        postForm = PostUtility.LoginByPhone(userPhone, pwd,userType);
//                        Log.i("postform", postForm);
                        loginHandler.post(runnableLogin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else{//ID登录
            new Thread() {
                public void run() {
                    try {
                        postForm = PostUtility.LoginById(userId, pwd,userType);
//                        Log.i("postform", postForm);
                        loginHandler.post(runnableLogin);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }

    private Runnable runnableLogin = new Runnable() {

        @Override
        public void run() {
            Gson gson = new Gson();
            RestFulBean<Integer> restFulBean = gson.fromJson(postForm, new TypeToken<RestFulBean<Integer>>(){}.getType());
            if (restFulBean.getData() == null) {
                String fault = restFulBean.getMsg(); //从消息里获取出错的原因
                Util.showToast(Activity_Login.this, fault);
                progress.dismiss();
                return;
            } else {

                Integer login_id = restFulBean.getData();
                System.out.println(login_id);
                Util.showToast(Activity_Login.this, "登录成功！");

                //保存用户登录信息到本地
                SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editors = sharedPreferences.edit();//获取编辑器
                editors.putString("phone",userPhone);
                editors.putString("password", pwd);
                editors.putString("userId",String.valueOf(login_id));
                editors.putInt("userType",userType);
                editors.commit();//提交修改

                //准备跳转
                if (login_id > 2110000&&login_id<211000000) {
                //是商家啊
                    Intent intent = new Intent(Activity_Login.this, Activity_Merchant_Main.class);
                    intent.putExtra("merchant_id",login_id);
                    startActivity(intent);
            }else if(login_id>211000000&&login_id<2110000000){
                    Intent intent = new Intent(Activity_Login.this, Activity_Rider_Main.class);
                    intent.putExtra("rider_id",login_id);
                    startActivity(intent);
                }else if(login_id>2110000000){
                    Intent intent = new Intent(Activity_Login.this, Activity_User_Main.class);
                    intent.putExtra("user_id",login_id);
                    startActivity(intent);

                }
            Util.showToast(Activity_Login.this, "正在跳转");
        }
            progress.dismiss();
    }
};



@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        ActivityCollector.finishAll();
        return true;
        }
        return super.onKeyDown(keyCode, event);
        }
        }

