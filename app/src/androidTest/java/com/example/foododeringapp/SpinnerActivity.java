package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SpinnerActivity extends Activity {

    TextView tv1,tv2;
    Spinner sp;
    View.OnClickListener ocl = null;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        tv1 = findViewById(R.id.spinner_textView1);
        tv2 = findViewById(R.id.spinner_textView2);

        sp = findViewById(R.id.spinner1);

        tv1.setText("请选择：");
        sp.setPrompt("选择项");

        spinner_set();

    }

    private void spinner_set(){
        adapter = ArrayAdapter.createFromResource(this, R.array.cities, R.layout.support_simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    tv2.setText("您的选择是：" + adapter.getItem(position));
                } else {
                    tv2.setText("您还没有选择！");
                }
            }

            public void onNothingSelected(AdapterView<?> position) {
                tv2.setText("您还没有选择");
            }
        });

    }
}