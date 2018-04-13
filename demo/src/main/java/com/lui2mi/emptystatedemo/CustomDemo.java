package com.lui2mi.emptystatedemo;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lui2mi.emptystate.EmptyStateList;

import java.util.ArrayList;

public class CustomDemo extends AppCompatActivity implements View.OnClickListener{
    EmptyStateList list;
    ListView customList;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_demo);
        list=findViewById(R.id.es_prevew);
        customList=new ListView(this);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                android.R.id.text1,new ArrayList());
        list.addCustomListView(customList);
        customList.setAdapter(adapter);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                list.setIsEmpty(adapter.getCount()==0);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.show_top:
                list.showTopProgress();
                break;
            case R.id.hide_top:
                list.hideTopProgress();
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
