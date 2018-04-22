package com.example.yzh.lab5;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedHashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    private int times;
    private boolean clickshop;
    private String name;
    private String price;
    private String detail;
    private int src;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailmessage);
        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("name");
        price = bundle.getString("price");
        detail = bundle.getString("detail");
        src = bundle.getInt("src");
        times = 0;
        clickshop = false;
        ImageView addshop = (ImageView) findViewById(R.id.addshop);
        final ImageView star = (ImageView) findViewById(R.id.star);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView goodsimage = (ImageView) findViewById(R.id.goodsimage);
        TextView goodsname = (TextView) findViewById(R.id.goodsname) ;
        TextView goodsprice = (TextView) findViewById(R.id.goodsprice);
        TextView goodsdetail = (TextView) findViewById(R.id.detail);


        goodsimage.setImageResource(src);
        goodsdetail.setText(detail);
        goodsname.setText(name);
        goodsprice.setText(price);

        addshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickshop = true;
                Toast.makeText(DetailActivity.this,"商品已添加到购物车",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent("dynamicbrocast");
                intent.putExtra("name", name);
                intent.putExtra("price",price);
                intent.putExtra("detail", detail);
                intent.putExtra("src", src);


                Map<String, Object> temp = new LinkedHashMap<>();
                temp.put("letter", String.valueOf(name.charAt(0)).toUpperCase());
                temp.put("name", name);
                temp.put("price", price);
                temp.put("detail", detail);
                temp.put("src", src);
                EventBus.getDefault().post(temp);

                IntentFilter dynamic_filter = new IntentFilter();
                dynamic_filter.addAction("dynamicbrocast");
                MyDynamicReceiver mdr = new MyDynamicReceiver();
                registerReceiver(mdr, dynamic_filter);
                sendBroadcast(intent);
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                times++;
                if (times%2 == 1) {
                    star.setImageResource(R.drawable.full_star);
                } else {
                    star.setImageResource(R.drawable.empty_star);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                /*if (clickshop) {
                    intent.putExtra("name", name);
                    intent.putExtra("price",price);
                    intent.putExtra("detail", detail);
                    intent.putExtra("src", src);
                    DetailActivity.this.setResult(RESULT_OK, intent);
                } else {
                    intent.putExtra("name", "1");
                    intent.putExtra("price","1");
                    intent.putExtra("detail", detail);
                    intent.putExtra("src", src);
                    DetailActivity.this.setResult(RESULT_CANCELED, intent);
                }*/
                DetailActivity.this.setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            /*Intent intent = new Intent();
            if (clickshop) {
                intent.putExtra("name", name);
                intent.putExtra("price",price);
                intent.putExtra("detail", detail);
                intent.putExtra("src", src);
                DetailActivity.this.setResult(RESULT_OK, intent);
            } else {
                intent.putExtra("name", "1");
                intent.putExtra("price","1");
                intent.putExtra("detail", detail);
                intent.putExtra("src", src);
                DetailActivity.this.setResult(RESULT_CANCELED, intent);
            }*/
            DetailActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
