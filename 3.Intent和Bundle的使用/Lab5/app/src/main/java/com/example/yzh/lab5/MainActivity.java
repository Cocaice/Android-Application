package com.example.yzh.lab5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.RecyclerView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {
    public List<Map<String, Object>> listdata;
    public List<Map<String, Object>> recyclelistdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initdata();


        listdata = new ArrayList<>();
        Map<String, Object> temp = new LinkedHashMap<>();
        temp.put("letter","*");
        temp.put("name", "购物车");
        temp.put("price","价格");
        temp.put("detail", "");
        temp.put("src", "");
        listdata.add(temp);
        final ListView listview = (ListView) findViewById(R.id.shoppinglist);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listdata, R.layout.
                list_item, new String[]{"letter", "name", "price"}, new int[] {R.id.list_firstletter,R.id.shoppingname,R.id.price});
        listview.setAdapter(simpleAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return;
                //List<Map<String, Object>> temp =simpleAdapter.;
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", listdata.get(position).get("name").toString());
                bundle.putString("price", listdata.get(position).get("price").toString());
                bundle.putString("detail", listdata.get(position).get("detail").toString());
                bundle.putInt("src", (Integer) listdata.get(position).get("src"));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) return false;
                final int pos = position;

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listdata.remove(pos);
                        simpleAdapter.notifyDataSetChanged();
                        listview.setAdapter(simpleAdapter);
                    }

                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setTitle("移除商品");
                dialog.setMessage("从购物车移除" + listdata.get(pos).get("name").toString() + "?");
                dialog.show();
                return true;
            }
        });
        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        final CommonAdapter<Map<String, Object>> commonAdapter = new CommonAdapter<Map<String, Object>>(this, R.layout.recycle_item, recyclelistdata) {
            @Override
            public void convert(ViewHolder holder, Map<String, Object> s)
            {
                try
                {
                    TextView first = holder.getView(R.id.firstletter);
                    String m = s.get("name").toString();
                    first.setText(String.valueOf(m.charAt(0)).toUpperCase());
                    TextView name = holder.getView(R.id.goodsname);
                    name.setText(m);
                } catch (Exception e)
                {
                    Log.e("ssssssssssssssss","ssssss",e);
                }
            }
        };

        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());



        final FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.shopbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mRecyclerView.getVisibility() == View.VISIBLE) {
                    floatingActionButton.setImageResource(R.drawable.mainpage);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    listview.setVisibility(View.VISIBLE);

                } else {
                    floatingActionButton.setImageResource(R.drawable.shoplist);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    listview.setVisibility(View.INVISIBLE);
                }
            }

        });
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                List<Map<String, Object>> temp = commonAdapter.getList();
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", recyclelistdata.get(position).get("name").toString());
                bundle.putString("price", temp.get(position).get("price").toString());
                bundle.putString("detail", temp.get(position).get("details").toString());
                bundle.putInt("src", (Integer) temp.get(position).get("src"));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onLongClick(int position) {
                commonAdapter.remove(position);
                Toast.makeText(MainActivity.this, "移除第"+position+"个商品", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void initdata() {
        recyclelistdata = new ArrayList<>();
        recyclelistdata.add(addRMap("Enchated Forest", "¥ 5.00", R.drawable.enchatedforest,"作者    Johanna Basford"));
        recyclelistdata.add(addRMap("Arla Milk", "¥ 59.00", R.drawable.arla, "产地    德国"));
        recyclelistdata.add(addRMap("Devondale Milk", "¥ 79.00", R.drawable.devondale, "产地    澳大利亚"));
        recyclelistdata.add(addRMap("Kindle Oasis", "¥ 2399.00", R.drawable.kindle, "版本    8GB"));
        recyclelistdata.add(addRMap("waitrose 早餐麦片", "¥ 179.00", R.drawable.waitrose, "重量    2Kg"));
        recyclelistdata.add(addRMap("Mcvitie's 饼干", "¥ 14.90", R.drawable.mcvitie, "产地    英国"));
        recyclelistdata.add(addRMap("Ferrero Rocher", "¥ 132.59", R.drawable.ferrero, "重量    300g"));
        recyclelistdata.add(addRMap("Maltesers", "¥ 141.43", R.drawable.maltesers, "重量    118g"));
        recyclelistdata.add(addRMap("Lindt", "¥ 139.43", R.drawable.lindt, "重量    249g"));
        recyclelistdata.add(addRMap("Borggreve", "¥ 28.90", R.drawable.borggreve, "重量    640g"));



    }
    public Map<String, Object> addRMap(String n, String p, int s, String d) {
        Map<String, Object> temp = new LinkedHashMap<>();
        //temp.put("letter",n);
        temp.put("name", n);
        temp.put("price",p);
        temp.put("src", s);
        temp.put("details", d);
        return temp;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String listname = data.getExtras().getString("name");
            String listprice = data.getExtras().getString("price");
            String listdetail = data.getExtras().getString("detail");
            int listsrc = data.getExtras().getInt("src");
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("letter", String.valueOf(listname.charAt(0)).toUpperCase());
            temp.put("name", listname);
            temp.put("price", listprice);
            temp.put("detail", listdetail);
            temp.put("src", listsrc);
            listdata.add(temp);
        }
        final ListView listview = (ListView) findViewById(R.id.shoppinglist);
        final SimpleAdapter simpleAdapter = new SimpleAdapter(this, listdata, R.layout.list_item, new String[]{"letter", "name", "price"}, new int[] {R.id.list_firstletter,R.id.shoppingname,R.id.price});
        listview.setAdapter(simpleAdapter);
    }


}
