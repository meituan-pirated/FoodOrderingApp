package com.example.foododeringapp.rider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.foododeringapp.bean.OrderDetailsForR;
import com.example.foododeringapp.bean.Rider;
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
    private ArrayList<FoodRecordForR> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rorder_details);
//        getIntentData();
        initView();

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
        arrayList = orderDetails.getFoodList(); //把食物记录先抓出来

        //获得所有控件
        findViewById();

        //为三个按键设置监听
        btn_contact.setOnClickListener(this);
        btn_state.setOnClickListener(this);
        close_order.setOnClickListener(this);

        //初始Adapter
        adapter = new Adapter_Food_Record(Activity_Order_details.this, arrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvFoods.setLayoutManager(layoutManager);
        rvFoods.setAdapter(adapter);

        // 计算出总价格
        nf = NumberFormat.getCurrencyInstance();
        nf.setMaximumFractionDigits(2);
        for (int i = 0; i < arrayList.size(); i++) {
            FoodRecordForR item = arrayList.get(i);
            cost += item.count * item.getPrice();
        }
        cost += 1; //加上配送费，配送费锁死就是1
        tv_cost.setText(nf.format(cost));

//        tv_userName.setText(userName);
//        tv_phoneNumber.setText(phoneNumber);
//        tv_address.setText(address);
//        if (sex != null) {
//            if (sex.equals("男")) {
//                tv_sex.setText("先生");
//            } else if (sex.equals("女")) {
//                tv_sex.setText("女士");
//            } else {
//                tv_sex.setText(sex);
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tvBalance://结算
//                if (longitude <= 0 || latitude <= 0) {
//                    Util.showToast(Activity_Balance.this, "此收货地址未包含经纬度信息，请重新选择或新建地址");
//                } else {
//                    Activity_Payment.actionStart(Activity_Balance.this, userId, cost, statusCode, userName, email, sex, phoneNumber, nickname, address, avatar, arrayList, remarkContent, longitude, latitude);
//                }
//                break;
//            case R.id.ly_remarkInfo://备注信息
//                Intent intentRemark = new Intent(Activity_Balance.this, Activity_Remark.class);
//                startActivityForResult(intentRemark, 2);
//                break;
//            case R.id.ly_AddressInfo://地址信息
//                Intent intent = new Intent(context, Activity_Address.class);
//                intent.putExtra("remark", "结算页面");
//                startActivityForResult(intent, 1);
//                break;
//            default:
//                break;
//        }
    }

    /**
     * 获取从地址页面回传的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case 1://地址
//                if (resultCode == RESULT_OK) {
//                    userName = data.getStringExtra("name");
//                    phoneNumber = data.getStringExtra("phoneNumber");
//                    sex = data.getStringExtra("sex");
//                    address = data.getStringExtra("address");
//                    longitude = data.getDoubleExtra("longitude", -1);
//                    latitude = data.getDoubleExtra("latitude", -1);
//
//                    tv_userName.setText(userName);
//                    tv_phoneNumber.setText(phoneNumber);
//                    tv_address.setText(address);
//                    if (sex.equals("男")) {
//                        tv_sex.setText("先生");
//                    } else if (sex.equals("女")) {
//                        tv_sex.setText("女士");
//                    } else {
//                        tv_sex.setText(sex);
//                    }
//                }
//                break;
//            case 2://备注
//                if (resultCode == RESULT_OK) {
//                    remarkContent = data.getStringExtra("remarkContent");
//                    tv_remarkContent.setText(remarkContent);
//                }
//                break;
//            default:
//                break;
//        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    private void getIntentData() {
//        Intent intent = getIntent();
//        orderID = intent.getStringExtra("orderId");
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
}