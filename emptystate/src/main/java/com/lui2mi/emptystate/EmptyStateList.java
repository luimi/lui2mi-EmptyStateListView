package com.lui2mi.emptystate;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by lui2mi on 15/02/18.
 */

public class EmptyStateList extends LinearLayout {
    private RelativeLayout emptyState;
    private LinearLayout groupList;
    private GridView listGV;
    private ExpandableListView listEL;
    private RecyclerView listRV;
    private TextView title, text;
    private ImageView image;
    private ProgressBar topProgress, centerProgress;
    private int type = 0;
    private boolean isImage=false,isTitle=false,isText=false;

    private final static int TYPE_GRIDVIEW = 0, TYPE_EXPANDABLE = 1, TYPE_RECYCLER = 2;

    public EmptyStateList(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        setLayoutParams(lp);
        setOrientation(LinearLayout.VERTICAL);
        View v = inflate(getContext(), R.layout.emptystate_template, null);
        emptyState = v.findViewById(R.id.rl_emptystate);
        groupList = v.findViewById(R.id.ll_list);
        listGV = v.findViewById(R.id.gv_list);
        listEL = v.findViewById(R.id.el_list);
        listRV = v.findViewById(R.id.rv_list);
        title = v.findViewById(R.id.tv_title);
        text = v.findViewById(R.id.tv_text);
        image = v.findViewById(R.id.iv_image);
        topProgress = v.findViewById(R.id.pb_topprogress);
        topProgress.setVisibility(GONE);
        centerProgress = v.findViewById(R.id.pb_centerprogress);
        centerProgress.setVisibility(GONE);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.EmptyState, 0, 0);
        try {
            String title = a.getString(R.styleable.EmptyState_title);
            setTitle(title);
            String text = a.getString(R.styleable.EmptyState_text);
            setText(text);
            Drawable image = a.getDrawable(R.styleable.EmptyState_image);
            if(image!=null){
                this.image.setImageDrawable(image);
                Integer width=a.getDimensionPixelSize(R.styleable.EmptyState_image_width,LayoutParams.WRAP_CONTENT);
                Integer heigth=a.getDimensionPixelSize(R.styleable.EmptyState_image_height,LayoutParams.WRAP_CONTENT);
                this.image.getLayoutParams().width=width;
                this.image.getLayoutParams().height=heigth;
                this.image.requestLayout();
                isImage=true;
            }else{
                this.image.setVisibility(GONE);
                isImage=false;
            }

            type = a.getInt(R.styleable.EmptyState_type, TYPE_GRIDVIEW);
            if (type == TYPE_GRIDVIEW) {
                int columns = a.getInt(R.styleable.EmptyState_columns, 1);
                listGV.setNumColumns(columns);
            }


        } catch (Exception ignored) {
        }
        updateShowList();
        checkData();
        v.setLayoutParams(lp);
        addView(v);
    }

    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            listGV.setAdapter(adapter);
            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkData();
                }
            });
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null) {
            listRV.setAdapter(adapter);

        }
    }
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager){
        listRV.setLayoutManager(layoutManager);
    }
    public void setType(int type) {
        this.type = type;
        updateShowList();
    }
    public void setTitle(String title){
        if(title==null || title.trim().equals("")){
            this.title.setVisibility(GONE);
            isTitle=false;
        }else{
            this.title.setText(title);
            isTitle=true;
        }
    }
    public void setText(String text){
        if(text==null || text.trim().equals("")){
            this.text.setVisibility(GONE);
            isText=false;
        }else{
            this.text.setText(text);
            isText=true;
        }
    }
    private void updateShowList() {
        listGV.setVisibility(type == TYPE_GRIDVIEW ? VISIBLE : GONE);
        listEL.setVisibility(type == TYPE_EXPANDABLE ? VISIBLE : GONE);
        listRV.setVisibility(type == TYPE_RECYCLER ? VISIBLE : GONE);
    }

    public void showTopProgress() {
        topProgress.setVisibility(VISIBLE);
    }

    public void showCenterProgress() {
        centerProgress.setVisibility(VISIBLE);
        if(isImage) image.setVisibility(GONE);
        if(isTitle) title.setVisibility(GONE);
        if(isText) text.setVisibility(GONE);
    }

    public void hideTopProgress() {
        topProgress.setVisibility(GONE);
    }

    public void hideCenterProgress() {
        centerProgress.setVisibility(GONE);
        if(isImage) image.setVisibility(VISIBLE);
        if(isTitle) title.setVisibility(VISIBLE);
        if(isText) text.setVisibility(VISIBLE);
    }

    public void checkDataChange() {
        checkData();
    }

    private void checkData() {
        boolean isEmpty = isEmpty();
        groupList.setVisibility(isEmpty ? GONE : VISIBLE);
        emptyState.setVisibility(isEmpty ? VISIBLE : GONE);
    }

    private boolean isEmpty() {
        boolean isEmpty = true;
        switch (type) {
            case TYPE_GRIDVIEW:
                isEmpty = listGV.getAdapter() == null || listGV.getAdapter().getCount() == 0;
                break;
            case TYPE_EXPANDABLE:
                isEmpty = listEL.getAdapter() == null || listEL.getAdapter().getCount() == 0;
                break;
            case TYPE_RECYCLER:
                isEmpty = listRV.getAdapter() == null || listRV.getAdapter().getItemCount() == 0;
                break;
        }
        return isEmpty;
    }


}
