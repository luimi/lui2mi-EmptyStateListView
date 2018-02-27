package com.lui2mi.emptystatelistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lui2mi on 15/02/18.
 */

public class ListView extends LinearLayout {
    private RelativeLayout emptyState;
    private GridView list;
    private TextView text;
    private ImageView image;
    private ProgressBar topProgress,centerProgress;

    public ListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        setOrientation(LinearLayout.VERTICAL);
        View v=inflate(getContext(), R.layout.emptystatelist,null);
        emptyState=v.findViewById(R.id.rl_emptystate);
        list=v.findViewById(R.id.gv_list);
        text=v.findViewById(R.id.tv_text);
        image=v.findViewById(R.id.iv_image);
        topProgress=v.findViewById(R.id.pb_topprogress);
        topProgress.setVisibility(GONE);
        centerProgress=v.findViewById(R.id.pb_centerprogress);
        centerProgress.setVisibility(GONE);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,  R.styleable.EmptyState, 0, 0);
        try{
            String text=a.getString(R.styleable.EmptyState_text);
            this.text.setText(text);
            Drawable image=a.getDrawable(R.styleable.EmptyState_image);
            this.image.setImageDrawable(image);
            int columns=a.getInt(R.styleable.EmptyState_columns,1);
            list.setNumColumns(columns);
        }catch (Exception ignored){}
        checkData();
        v.setLayoutParams(lp);
        addView(v);
    }
    public void setAdapter(ListAdapter adapter){
        if(adapter!=null){
            list.setAdapter(adapter);
            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkData();
                }
            });
        }
    }
    public void showTopProgress(){
        topProgress.setVisibility(VISIBLE);
    }
    public void showCenterProgress(){
        centerProgress.setVisibility(VISIBLE);
        image.setVisibility(GONE);
        text.setVisibility(GONE);
    }
    public void hideTopProgress(){
        topProgress.setVisibility(GONE);
    }
    public void hideCenterProgress(){
        centerProgress.setVisibility(GONE);
        image.setVisibility(VISIBLE);
        text.setVisibility(VISIBLE);
    }
    private void checkData(){
        boolean isEmpty=list.getAdapter()==null || list.getAdapter().getCount()==0;
        list.setVisibility(isEmpty?GONE:VISIBLE);
        emptyState.setVisibility(isEmpty?VISIBLE:GONE);
    }


}
