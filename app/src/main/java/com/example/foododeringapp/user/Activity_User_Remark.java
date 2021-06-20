package com.example.foododeringapp.user;

import androidx.appcompat.app.AppCompatActivity;
import com.example.foododeringapp.R;
import com.example.foododeringapp.widget.FixWrapLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Activity_User_Remark extends AppCompatActivity implements View.OnClickListener {
//<!--    edit_content 备注内容  txt_count 字数 choosed_remark 可选备注-->
//    toolbarText 返回 textView
    private FixWrapLayout fixWrapLayout;//常用评论
    private List<String> remarks;
    private TextView toolbarText, txt_count;
    private EditText edit_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__user__remark);
        initView();
    }

    private void initView() {
        fixWrapLayout = findViewById(R.id.choosed_remark);
        setRemarksData();
        setCommonRemarks(remarks);

        edit_content= findViewById(R.id.edit_content);
        txt_count= findViewById(R.id.txt_count);
        toolbarText = findViewById(R.id.toolbar_text);
        toolbarText.setOnClickListener(this);
    }

    /**
     * 设置常用备注标签
     *
     * @param remarks
     */
    private void setCommonRemarks(List<String> remarks) {
        for (String remark : remarks) {
            TextView remarkTxt = (TextView) View.inflate(this, R.layout.include_common_remark, null);
            remarkTxt.setText(remark);
            remarkTxt.setTag(remark);
            remarkTxt.setOnClickListener(this);
            fixWrapLayout.addView(remarkTxt);
        }
    }

    /**
     * 设置常用备注数据
     */
    private void setRemarksData() {
        remarks = new ArrayList<>();
        remarks.add("少放辣");
        remarks.add("多放辣");
        remarks.add("不要放辣");
        remarks.add("辣到叫");
        remarks.add("不放辣椒我就哭");
        remarks.add("多点饭");
        remarks.add("不吃葱");
        remarks.add("不吃蒜");
        remarks.add("不吃香菜");
        remarks.add("不吃洋葱");
        remarks.add("不吃小米辣");
        remarks.add("多放葱");
        remarks.add("多放蒜");
        remarks.add("多放小米辣");
        remarks.add("多放香菜");
        remarks.add("多放洋葱");
        remarks.add("不要米饭");
        remarks.add("少放汤");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_text://完成
                toolbarText.setTextColor(getResources().getColor(R.color.white));
                getRemarkContent();
                //向结算页面返回备注信息
                Intent intent = new Intent();
                intent.putExtra("remarkContent", getRemarkContent());
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取备注内容
     * @return
     */
    private String getRemarkContent() {
        String content;
        content = edit_content.getText().toString();
        return content;
    }
}