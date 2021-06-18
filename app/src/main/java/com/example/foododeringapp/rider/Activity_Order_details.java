package com.example.foododeringapp.rider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.FoodRecordForR;
import com.example.foododeringapp.bean.OrderDetails;
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.control.BaseActivity;
import com.example.foododeringapp.rider.adapter.Adapter_Food_Record;
import com.example.foododeringapp.rider.service.RiderRequestUtility;

import java.text.NumberFormat;
import java.util.ArrayList;

public class Activity_Order_details extends BaseActivity implements View.OnClickListener {

    private Context context;
    private TextView merchant_name,order_id,merchant_add,buyer_add;
    private TextView buyer_name,buyer_sex,buyer_phone,order_time;
    private Button btn_delivering,btn_get;//取和送的图标
    private TextView order_note,tv_cost;
    private Button btn_contact, btn_state;
    private ImageView close_order;
    private RecyclerView rvFoods;
    private Adapter_Food_Record adapter;
    private NumberFormat nf;
    private int statusCode = 0;

    private String orderID;//从intent那取来的万恶之源
    double cost = 0, deliver_fee = 0;//总价格,配送费
    private OrderDetailsForR orderDetails;
    private ArrayList<OrderDetails> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_rorder_details);
        getIntentData();
        initView();
    }


    private void getIntentData() {
        Intent intent = getIntent();
        orderID = intent.getStringExtra("orderID");

    }

    private void findViewById() {
        merchant_name = (TextView) findViewById(R.id.merchant_name);
        order_id = (TextView) findViewById(R.id.order_id);
        merchant_add = (TextView) findViewById(R.id.merchant_add);
        buyer_add = (TextView) findViewById(R.id.buyer_add);
        buyer_name = (TextView) findViewById(R.id.buyer_name);
        buyer_sex = (TextView) findViewById(R.id.buyer_sex);
        buyer_phone = (TextView) findViewById(R.id.buyer_phone);
        order_time = (TextView) findViewById(R.id.order_time);
        order_note = (TextView) findViewById(R.id.order_note);
        tv_cost = (TextView) findViewById(R.id.cost);
        rvFoods = (RecyclerView) findViewById(R.id.foodRecyclerView);

        //需要监听的
        btn_contact = (Button)findViewById(R.id.btn_contact);
        btn_state = (Button)findViewById(R.id.btn_state);
        close_order = (ImageView)findViewById(R.id.close_order);

        btn_get = (Button)findViewById(R.id.btn_get);
        btn_delivering = (Button)findViewById(R.id.btn_delivering);

    }

    private void initView() {
        context = Activity_Order_details.this;

        //更改工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        toolbarText.setText("订单详情");

        //获取订单详细数据
        orderDetails = RiderRequestUtility.getOrderDetailsForR(orderID);
        arrayList = orderDetails.getOrder().getOrderDetailsList(); //把食物记录先抓出来


        //获得所有控件
        findViewById();

        //为三个按键设置监听
        btn_contact.setOnClickListener(this);
        btn_state.setOnClickListener(this);
        close_order.setOnClickListener(this);

        //初始Adapter
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_Order_details.this);
        rvFoods.setLayoutManager(layoutManager);
        adapter = new Adapter_Food_Record(Activity_Order_details.this, arrayList);
        rvFoods.setAdapter(adapter);

        // 计算出总价格
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        for (int i = 0; i < arrayList.size(); i++) {
            OrderDetails item = arrayList.get(i);
            cost += item.getAmount() * item.getProduct().getSalePrice();
        }
        cost += 1; //加上配送费，配送费锁死就是1
        tv_cost.setText(nf.format(cost));

        //将订单信息都填到控件里
        order_id.setText(orderID);
        merchant_name.setText(orderDetails.getBusinessInfo().getBusinessName());
        merchant_add.setText(orderDetails.getBusinessInfo().getRefectory()); ;
        buyer_add.setText(orderDetails.getOrder().getAddress().getAddressName());
        buyer_name.setText(orderDetails.getOrder().getAddress().getReceiveName());
        buyer_phone.setText(orderDetails.getOrder().getAddress().getReceivePhone());
        order_time.setText(orderDetails.getOrder().getOrderTime());
        order_note.setText(orderDetails.getOrder().getOrderNote());
        //称呼根据性别规定
        String sex = orderDetails.getOrder().getAddress().getSex();
        if (sex != null) {
            if (sex.equals("男")) {
                buyer_sex.setText("(先生)");
            } else if (sex.equals("女")) {
                buyer_sex.setText("(女士)");
            } else {
                buyer_sex.setText(sex);
            }
        }

        //若已到店，取字图标亮起并且按钮内容变成已送达
        String state = orderDetails.getOrder().getOrderState();
        if(state.equals("raccept")){
            btn_state.setText("我已到店");
        }else {
            btn_state.setText("已送达");
            btn_get.setBackgroundResource(R.drawable.states_finished);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_contact://想要拨号
                //根据电话拨号！
                String phoneNum = orderDetails.getOrder().getAddress().getReceivePhone();
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + phoneNum);
                intent.setData(data);
                Activity_Order_details.this.startActivity(intent);
//               还有拨打电话的权限 <uses-permission android:name="android.permission.CALL_PHONE" />
                break;
            case R.id.btn_state://更新状态
                Log.i("内部","？？？？");
                String state = orderDetails.getOrder().getOrderState();
                if(state.equals("raccept")){
                    //如果是还没到店的状态，下一步就是确认是否到店
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Order_details.this);
                    builder.setMessage("确认已到店？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_state.setText("已送达");
                        btn_get.setBackgroundResource(R.drawable.states_finished);
                        Integer res = RiderRequestUtility.ChangeOrderStateForR(orderID,"at_res");
                        dialog.dismiss();
                    }});
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    { @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                    });
                    builder.create().show();

                }else {
                    //已到店之后，不论是商家出未出餐，都将是询问是否已送达
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Order_details.this);
                    builder.setMessage("确认已送达？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Integer res = RiderRequestUtility.ChangeOrderStateForR(orderID,"arrive");
                        res = RiderRequestUtility.MarkOrderArriveTime(orderID);
                        dialog.dismiss();
                        Activity_Order_details.this.finish();//订单完成，这一页也该炸了
                    }});
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    { @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                    });
                    builder.create().show();
                }
                break;
            case R.id.close_order://关闭详细页面,并且指定返回的是上一个activity的哪个fragment
                setResult(3222);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取从地址页面回传的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*switch (requestCode) {
            case 1://地址
                if (resultCode == RESULT_OK) {
                    userName = data.getStringExtra("name");
                    phoneNumber = data.getStringExtra("phoneNumber");
                    sex = data.getStringExtra("sex");
                    address = data.getStringExtra("address");
                    longitude = data.getDoubleExtra("longitude", -1);
                    latitude = data.getDoubleExtra("latitude", -1);

                    tv_userName.setText(userName);
                    tv_phoneNumber.setText(phoneNumber);
                    tv_address.setText(address);
                    if (sex.equals("男")) {
                        tv_sex.setText("先生");
                    } else if (sex.equals("女")) {
                        tv_sex.setText("女士");
                    } else {
                        tv_sex.setText(sex);
                    }
                }
                break;
            case 2://备注
                if (resultCode == RESULT_OK) {
                    remarkContent = data.getStringExtra("remarkContent");
                    tv_remarkContent.setText(remarkContent);
                }
                break;
            default:
                break;
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Activity_Order_details.this.setResult(2);
                Activity_Order_details.this.finish();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}