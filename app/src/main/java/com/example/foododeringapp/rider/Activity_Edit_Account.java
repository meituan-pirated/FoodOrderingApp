package com.example.foododeringapp.rider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashokvarma.bottomnavigation.utils.Utils;
import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.RestFulBean;
import com.example.foododeringapp.bean.Rider;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.rider.service.RiderPostUtility;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.rider.service.RiderRequestUtility;
import com.example.foododeringapp.util.BottomDialog;
import com.example.foododeringapp.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.NumberFormat;

public class Activity_Edit_Account extends BaseActivity  implements View.OnClickListener {
    private Context context;
    private ImageView change_img;
    private EditText etOrigiPwd, etNickName, etNewPwd, etRePwd;
    private RadioGroup radioGroup01;
    private RadioButton male, female;
    private Button btn_change_pwd, btn_update, btn_stop_change_pwd;
    private LinearLayout hiden_part;

    private NumberFormat nf;
    private int statusCode = 0;

    private String riderID;//???intent????????????????????????
    private String riderPwd, riderNickName, riderSex,riderAdvatar;//?????????????????????
    private Rider rider;

    private ProgressDialog pg;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private String postForm;
    private boolean selectClicked = false;
    BottomDialog fragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???????????????????????????
        setContentView(R.layout.activity_edit_account);
        pg = new ProgressDialog(Activity_Edit_Account.this);
        getIntentData(); //??????riderId???
        initView();

    }


    private void getIntentData() {
        Intent intent = getIntent();
        riderID = intent.getStringExtra("rider_id");
    }

    private void findViewById() {
        radioGroup01 = (RadioGroup) findViewById(R.id.rg);
        male = (RadioButton) findViewById(R.id.male);

        hiden_part = (LinearLayout) findViewById(R.id.hiden_part);

        change_img = findViewById(R.id.change_img);
        female = (RadioButton) findViewById(R.id.female);
        etOrigiPwd = (EditText) findViewById(R.id.etOrigiPwd);
        etNickName = (EditText) findViewById(R.id.etNickName);
        etNewPwd = (EditText) findViewById(R.id.etNewPwd);
        etRePwd = (EditText) findViewById(R.id.etRePwd);
        //????????????????????????
        btn_change_pwd = (Button) findViewById(R.id.btn_change_pwd);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_stop_change_pwd = (Button) findViewById(R.id.btn_stop_change_pwd);

        change_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectClicked = true;
                FragmentManager fm = getSupportFragmentManager();
                //???????????????
                fragment = new BottomDialog();
                if(selectClicked){
                    Log.i("infoclick", "infoclick");
                    fragment.show(fm, "fragment_bottom_dialog");
                }


            }
        });
    }

    private void initView() {
        context = Activity_Edit_Account.this;

        //??????????????????
        findViewById();

        //????????????????????????
        rider = RiderRequestUtility.GetRiderInfo(riderID);
        riderNickName = rider.getNickName();
        riderSex = rider.getSex();
        riderPwd = rider.getPassword();
        riderAdvatar = rider.getAdvatar();//??????????????????????????????
        int image_id = getResources().getIdentifier(riderAdvatar,"mipmap",this.getPackageName());
        change_img.setImageResource(image_id);


        //???????????????????????????
        btn_change_pwd.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_stop_change_pwd.setOnClickListener(this);

        //???????????????????????????(??????????????????
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("??????????????????");
        /*setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/
        etNickName.setText(rider.getNickName());
        if (riderSex.equals("???")) {
            female.setChecked(true);
        } else {
            male.setChecked(true);
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_change_pwd:
                //?????????????????????????????????part???????????????????????????
                hiden_part.setVisibility(View.VISIBLE);
                etOrigiPwd.setVisibility(View.VISIBLE);
                btn_change_pwd.setVisibility(View.GONE);
                break;
            case R.id.btn_stop_change_pwd:
                hiden_part.setVisibility(View.GONE);
                btn_change_pwd.setVisibility(View.VISIBLE);
                etOrigiPwd.setVisibility(View.INVISIBLE);
                break;
            case R.id.btn_update:

                //???????????????????????????????????????????????????
                String pwd = etOrigiPwd.getText().toString();
                String newPwd = etNewPwd.getText().toString();
                String rePwd = etRePwd.getText().toString();
                String newNickName;
                String newSex;
                String encodedString;
                //Uri uri = Uri.parse(sharePreference.getString("uri",""));

                change_img.setDrawingCacheEnabled(true);
                Bitmap bitmap = Bitmap.createBitmap(change_img.getDrawingCache());
                change_img.setDrawingCacheEnabled(false);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // ????????????
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                byte[] byte_arr = stream.toByteArray();
                // Base64???????????????String
                encodedString = Base64.encodeToString(byte_arr, 0);

                if (btn_change_pwd.getVisibility() == View.GONE) {
                    //????????????,?????????????????????
                    if (pwd.length() * newPwd.length() * rePwd.length() == 0) {
                        Toast.makeText(Activity_Edit_Account.this, "?????????????????????", Toast.LENGTH_SHORT).show();
                    } else if (!pwd.equals(riderPwd)) {
                        Toast.makeText(Activity_Edit_Account.this, "??????????????????", Toast.LENGTH_SHORT).show();
                    } else if (!rePwd.equals(newPwd)) {    //??????????????????????????????
                        Toast.makeText(Activity_Edit_Account.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                    } else if(newPwd.length()<6||newPwd.length()>18) {
                        Toast.makeText(Activity_Edit_Account.this, "??????????????????6????????????18??????", Toast.LENGTH_SHORT).show();
                    }else{
                        //??????????????????
                        pg.setMessage("?????????...");
                        pg.show();
                        int checkedBtnId = radioGroup01.getCheckedRadioButtonId();
                        if (checkedBtnId == R.id.male) {
                            newSex = "???";
                        } else {
                            newSex = "???";
                        }
                        if (etNickName.getText().toString().length() == 0) {
                            newNickName = riderNickName;
                        } else {
                            newNickName = etNickName.getText().toString();
                        }
                        if (!Util.checkNetwork(Activity_Edit_Account.this)) {
                            pg.dismiss();
                            return;
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    String finalPwd = newPwd;
                                    Log.i("?????????",finalPwd);
                                    postForm = RiderPostUtility.ChangeRiderInfo(riderID, newNickName, newSex, finalPwd,encodedString);
                                    handler.post(runnableModfInfo);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();

                        break;

                    }

                    //radiogroup.getCheckedRadioButtonId();????????????????????????????????????

                }else{
                    //???????????????
                    String finalPwd = riderPwd;
                    int checkedBtnId = radioGroup01.getCheckedRadioButtonId();
                    if (checkedBtnId == R.id.male) {
                        newSex = "???";
                    } else {
                        newSex = "???";
                    }
                    if (etNickName.getText().toString().length() == 0) {
                        newNickName = riderNickName;
                    } else {
                        newNickName = etNickName.getText().toString();
                    }
                    if (!Util.checkNetwork(Activity_Edit_Account.this)) {
                        pg.dismiss();
                        return;
                    }
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                postForm = RiderPostUtility.ChangeRiderInfo(riderID, newNickName, newSex, finalPwd,encodedString);
                                handler.post(runnableModfInfo);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    break;

                }
            default:
                break;
        }


    }

    Runnable runnableModfInfo = new Runnable() {
        @Override
        public void run() {
            pg.dismiss();
            Gson gson = new Gson();
            RestFulBean<Integer> restFulBean = gson.fromJson(postForm, new TypeToken<RestFulBean<Integer>>(){}.getType());
            int res = restFulBean.getData();
            if (res==0) {
                Util.showToast(Activity_Edit_Account.this, "???????????????????????????????????????????????????");
                return;
            }
            if (res==-1) {
                Util.showToast(Activity_Edit_Account.this, "???????????????????????????????????????????????????????????????");
                return;
            } else {
                Util.showToast(Activity_Edit_Account.this, "???????????????????????????");
            }
        }
    };

    //?????????????????????
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                intent.putExtras(bundle);
                setResult(312, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //baseActibity???????????????????????????
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //??????????????????
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //???????????????
            //???Fragment_Home??????????????????
            Intent intent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt("business_id", business_id);
//            bundle.putString("product_id", "123123" );
//            intent.putExtras(bundle);
            setResult(312, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
