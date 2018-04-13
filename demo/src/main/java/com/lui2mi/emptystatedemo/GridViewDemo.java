package com.lui2mi.emptystatedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.lui2mi.emptystate.EmptyStateList;

import java.util.ArrayList;

public class GridViewDemo extends AppCompatActivity implements View.OnClickListener{
    EmptyStateList list;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridview_demo);
        list=findViewById(R.id.es_prevew);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,new ArrayList());
        list.setAdapter(adapter);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_top:
                list.showTopProgress();
                break;
            case R.id.show_center:
                list.showCenterProgress();
                break;
            case R.id.hide_top:
                list.hideTopProgress();
                break;
            case R.id.hide_center:
                list.hideCenterProgress();
                break;
            case R.id.add:
                adapter.add("Item");
                adapter.notifyDataSetChanged();
                break;
            case R.id.remove:
                adapter.clear();
                adapter.notifyDataSetChanged();
                break;

        }
    }

}
