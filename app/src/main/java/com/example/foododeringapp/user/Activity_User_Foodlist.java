package com.example.foododeringapp.user;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foododeringapp.R;
import com.example.foododeringapp.bean.Foods;
import com.example.foododeringapp.user.adapter.Adapter_User_SelectFoodList;
import com.example.foododeringapp.user.adapter.Adapter_User_allFoods;
import com.example.foododeringapp.user.service.UserRequestUtility;
import com.example.foododeringapp.widget.EmptyRecyclerView;
import com.flipboard.bottomsheet.BottomSheetLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_User_Foodlist extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private EmptyRecyclerView recyclerView; //foodRecycleView
    private BottomSheetLayout bottomSheetLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewGroup anim_mask_layout;
    private RecyclerView rvSelected;
    private LinearLayout bottom;
    private ImageView imgCart;
    private TextView tvCount, tvCost, tvSubmit, tvTips;//购买数量，总价格，结算，10元起送

    private View mEmptyView;

    private Adapter_User_allFoods adapter;
    private List<Foods> foodsList;

    private int merchantID;
    private int userID;

    private int statusCode = 200;

    private Handler mHanlder;
    private View bottomSheet = null;
    private double cost = 0;
    // Adapter_Select = Adapter_User_SelectFoods
    private Adapter_User_SelectFoodList adapterSelect;
    private NumberFormat nf;
    private SparseArray<Foods> selectedList;        //选择的商品集合

    //用于刷新
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    private ProgressDialog pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg = new ProgressDialog(this);
        setContentView(R.layout.activity__user__foodlist);
        getIntentData();
        initView();
        selectedList = new SparseArray<>();
        showList();
        tvSubmit.setOnClickListener(this);
        bottom.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        // intent.putExtra("merchantID", merchant.getId());
        // intent.putExtra("userID", userID);
        merchantID = intent.getIntExtra("merchantID", -1);
        userID = intent.getIntExtra("userID", -1);
        Log.i("userID", String.valueOf(userID));
    }

    private void showList() {
        pg.setMessage("数据加载中...");
        pg.show();
        if (Activity_User_Main.networkState == 0) {
            Toast.makeText(getApplicationContext(), "网络连接失败，请检查网络连接设置！", Toast.LENGTH_SHORT).show();
            pg.dismiss();
            return;
        }
        new Thread() {
            public void run() {
                try {
                    foodsList = UserRequestUtility.getAllFoods(merchantID);
                    handler.post(runnableFoodList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    Runnable runnableFoodList = new Runnable() {
        @Override
        public void run() {
            if (pg != null){
                pg.dismiss();

            }
            //这里导入adapter
            if (foodsList != null && foodsList.size() > 0) {
                adapter = new Adapter_User_allFoods(foodsList, Activity_User_Foodlist.this);

                LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_User_Foodlist.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setEmptyView(mEmptyView);
//                adapter.setOnItemClickListener(new .OnItemClickListener() {//点击条目跳转到食品详情页
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Foods foods = foodsList.get(position);
//                        int foodId = foods.getId();
//                        int count = foods.count;
//
////                        跳转到商品具体信息页面
////                        Intent intent = new Intent(getApplicationContext(), Activity_FoodsDetails.class);
////                        intent.putExtra("foodId", foodId);
////                        intent.putExtra("count", count);
////                        startActivityForResult(intent, 1);
//                    }
//                });
            }
        }
    };

    private void initView() {
        // mEmptyView = findViewById(R.id.id_empty_view);
        recyclerView = findViewById(R.id.foodRecycleView);
        bottom = findViewById(R.id.bottom);
        tvCount = findViewById(R.id.tvCount);
        tvCost = findViewById(R.id.tvCost);
        tvTips = findViewById(R.id.tvTips);
        tvSubmit = findViewById(R.id.tvSubmit);
        imgCart = findViewById(R.id.imgCart);
        anim_mask_layout = (RelativeLayout) findViewById(R.id.containerLayout);
        bottomSheetLayout = findViewById(R.id.bottomSheetLayout);
        nf = NumberFormat.getCurrencyInstance();//数字格式化输出，返回语言环境的金融格式
        nf.setMaximumFractionDigits(2);//设置数值的小数部分允许的最大位数
        mHanlder = new Handler(getMainLooper());//刷新控件
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 3) {
                    int count = data.getIntExtra("count", -1);
                    int foodId = data.getIntExtra("foodId", -1);
                    Foods foods = null;
                    for (int i = 0; i < foodsList.size(); i++) {
                        if (foodsList.get(i).getId() == foodId) {
                            foods = foodsList.get(i);
                            break;
                        }
                    }
                    if (count > 0) {//添加商品
                        for (int i = 0; i < count; i++) {
                            add(foods, true);
                        }
                    }
                    if (count < 0) {//移除商品
                        for (int i = 0; i < -(count); i++) {
                            remove(foods, true);
                        }
                    }
                }
                break;
        }
    }

    public void playAnimation(int[] start_location) {
        ImageView img = new ImageView(Activity_User_Foodlist.this);
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(64, 64);//LayoutParams类可以改变view大小
        img.setLayoutParams(mParams);
        img.setImageResource(R.drawable.button_add);
        int count = 0;
        for (int i = 0; i < selectedList.size(); i++) {
            Foods item = selectedList.valueAt(i);
            count += item.count;
        }
        if (count > 1) {//小于1时购物车隐藏，抛物线动画会异常
            setAnim(img, start_location);
        }
    }

    /**
     * 创建动画 平移动画直接传递偏移量
     *
     * @param startX
     * @param startY
     * @return
     */
    private Animation createAnim(int startX, int startY) {
        int[] des = new int[2];
        imgCart.getLocationInWindow(des);
        AnimationSet set = new AnimationSet(false);
        Animation translationX = new TranslateAnimation(0, des[0] - startX, 0, 0);
        translationX.setInterpolator(new LinearInterpolator());
        Animation translationY = new TranslateAnimation(0, 0, 0, des[1] - startY);
        translationY.setInterpolator(new AccelerateInterpolator());
        Animation alpha = new AlphaAnimation(1, 0.5f);
        set.addAnimation(translationX);
        set.addAnimation(translationY);
        set.addAnimation(alpha);
        set.setDuration(500);
        return set;
    }

    /**
     * 计算动画view在根部局中的坐标 添加到根部局中
     *
     * @param vg
     * @param view
     * @param location
     */
    private void addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        int[] loc = new int[2];
        vg.getLocationInWindow(loc);
        view.setX(x);
        view.setY(y - loc[1]);
        vg.addView(view);
    }

    /**
     * 设置动画结束移除动画view
     *
     * @param v
     * @param start_location
     */
    private void setAnim(final View v, int[] start_location) {
        addViewToAnimLayout(anim_mask_layout, v, start_location);
        Animation set = createAnim(start_location[0], start_location[1]);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(final Animation animation) {
                //直接remove可能会因为界面仍在绘制中成而报错
                mHanlder.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        anim_mask_layout.removeView(v);
                    }
                }, 100);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        v.startAnimation(set);
    }


    /**
     * 添加商品
     *
     * @param item
     * @param refreshFoodList
     */
    public void add(Foods item, boolean refreshFoodList) {
        Foods temp = selectedList.get(item.getId());
        if (temp == null) {
            item.count = 1;
            selectedList.append(item.getId(), item);
        } else {
            selectedList.get(item.getId()).count++;

        }
        update(refreshFoodList);
    }


    /**
     * 移除商品
     *
     * @param item
     * @param refreshFoodList
     */
    public void remove(Foods item, boolean refreshFoodList) {
        Foods temp = selectedList.get(item.getId());
        if (temp != null) {
            if (temp.count == 1 ) {
                selectedList.remove(item.getId());
            } else {
                //item.count--;
                selectedList.get(item.getId()).count--;
            }
        }
        update(refreshFoodList);
    }

    /**
     * 根据商品id获取当前商品的采购数量
     *
     * @param id
     * @return
     */
    public int getSelectedItemCountById(int id) {
        Foods temp = selectedList.get(id);
        if (temp == null) {
            return 0;
        }
        return temp.count;
    }

    /**
     * 刷新布局 总价、购买数量等
     *
     * @param refreshFoodList
     */
    public void update(boolean refreshFoodList) {
        int size = selectedList.size();
        int count = 0;
        cost = 0;
        for (int i = 0; i < size; i++) {
            Foods item = selectedList.valueAt(i);
            count += item.count;
            cost += item.count * item.getPrice();
        }

        if (count <= 0) {
            tvCount.setVisibility(View.GONE);
        } else {
            tvCount.setVisibility(View.VISIBLE);
        }
        tvCount.setText(String.valueOf(count));

        if (cost >= 9.9) {//9.9元以上显示结算按钮
            tvTips.setVisibility(View.GONE);
            tvSubmit.setVisibility(View.VISIBLE);
        } else {
            tvSubmit.setVisibility(View.GONE);
            tvTips.setVisibility(View.VISIBLE);
        }
        tvCost.setText(nf.format(cost));

        if (adapter != null && refreshFoodList) {
            adapter.notifyDataSetChanged();
        }
        if (adapterSelect != null) {
            adapterSelect.notifyDataSetChanged();
        }
//        if (bottomSheetLayout.isSheetShowing() && selectedList.size() < 1) {
//            bottomSheetLayout.dismissSheet();
//        }
        //按数量显示隐藏底部结算栏
        if (count <= 0) {
            bottom.setVisibility(View.GONE);
        } else {
            bottom.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 清空购物车
     */
    public void clearCart() {
        //对话框提示确定清空购物车
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示？");
        dialog.setMessage("您确定要清空购物车中所有商品？");
        dialog.setCancelable(false);
        dialog.setPositiveButton("确定", (dialog1, which) -> {
            selectedList.clear();
            update(true);
        });
        dialog.setNegativeButton("取消", (dialog12, which) -> {
        });
        dialog.show();
    }

    /**
     * 创建购物车view
     *
     * @return
     */
    private View createBottomSheetView() {
        View view = LayoutInflater.from(this).inflate(R.layout.shopping_cart, (ViewGroup) getWindow().getDecorView(), false);
        rvSelected = view.findViewById(R.id.selectRecyclerView);
        rvSelected.setLayoutManager(new LinearLayoutManager(Activity_User_Foodlist.this));
        TextView clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(this);
        adapterSelect = new Adapter_User_SelectFoodList(this, selectedList);
        rvSelected.setAdapter(adapterSelect);
//            Log.i("listNumber", String.valueOf(selectedList.size()));
//            for(int i = 0; i < selectedList.size(); i++){
//            Foods temp = selectedList.get(i);
//            Log.i("F",temp.getName());
//        }
        return view;
    }

    /**
     * 弹出购物车view
     */
    private void showBottomSheet() {
        if (bottomSheet == null) {
            bottomSheet = createBottomSheetView();
        }
        if (bottomSheetLayout.isSheetShowing()) {
            bottomSheetLayout.dismissSheet();
        } else {
            if (selectedList.size() != 0) {
                bottomSheetLayout.showWithSheetView(bottomSheet);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom://弹出购物车
                showBottomSheet();
                break;
            case R.id.clear://清空购物车
                clearCart();
                break;
            case R.id.tvSubmit://结算
                //getUserData();
                if (statusCode == 200) {
                    ArrayList<Foods> arrayList = new ArrayList();
                    for (int i = 0; i < selectedList.size(); i++) {
                        arrayList.add(selectedList.valueAt(i));
//                        Log.i("Balance:", selectedList.valueAt(i).getName());
                    }
                    Intent intent = new Intent(Activity_User_Foodlist.this, Activity_User_Balance.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectList", arrayList);
                    bundle.putInt("userID", userID);
                    bundle.putInt("merchantID", merchantID);
                    bundle.putInt("statusCode", statusCode);
                    intent.putExtras(bundle);
                    startActivity(intent);}
//                } else {
//                    //Toast.makeText(this, "请先登录!", Toast.LENGTH_SHORT).show();
//                    //Intent intent = new Intent(this, Activity_Login.class);
//                    //startActivity(intent);
//                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onRefresh() {
        showList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //刷新控件停止两秒后消失
                    Thread.sleep(1000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}