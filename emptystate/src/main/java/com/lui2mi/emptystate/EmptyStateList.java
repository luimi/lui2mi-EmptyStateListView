package com.lui2mi.emptystate;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
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

public class EmptyStateList extends LinearLayout {
    private RelativeLayout default_es;
    private LinearLayout list, emptyState, llGV, llEL, llCL, custom_es;
    private GridView listGV;
    private ExpandableListView listEL;
    private TextView title, text;
    private ImageView image;
    private ProgressBar topProgress, centerProgress;
    private boolean isImage = false, isTitle = false, isText = false, isCustomList = false,
            isCustomEmptyState = false, isGridView = true, isExpandable = false, isEmpty=true;

    public EmptyStateList(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }
    public EmptyStateList(Context context){
        super(context);
        setup(context,null);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.emptystate_template, this);

        list = findViewById(R.id.ll_list);
        emptyState = findViewById(R.id.ll_emptystate);

        topProgress = findViewById(R.id.pb_topprogress);
        topProgress.setVisibility(GONE);
        centerProgress = findViewById(R.id.pb_centerprogress);
        centerProgress.setVisibility(GONE);

        llGV = findViewById(R.id.ll_gv);
        llEL = findViewById(R.id.ll_el);
        llCL = findViewById(R.id.ll_cl);


        listGV = findViewById(R.id.gv_list);
        listEL = findViewById(R.id.el_list);

        title = findViewById(R.id.tv_title);
        text = findViewById(R.id.tv_text);
        image = findViewById(R.id.iv_image);

        custom_es=findViewById(R.id.custom_es);
        default_es=findViewById(R.id.default_es);



        try {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs, R.styleable.EmptyState, 0, 0);
            String title = a.getString(R.styleable.EmptyState_title);
            setTitle(title);
            String text = a.getString(R.styleable.EmptyState_text);
            setText(text);
            Drawable image = a.getDrawable(R.styleable.EmptyState_image);
            if (image != null) {
                this.image.setImageDrawable(image);
                Integer width = a.getDimensionPixelSize(R.styleable.EmptyState_image_width, LayoutParams.WRAP_CONTENT);
                Integer heigth = a.getDimensionPixelSize(R.styleable.EmptyState_image_height, LayoutParams.WRAP_CONTENT);
                this.image.getLayoutParams().width = width;
                this.image.getLayoutParams().height = heigth;
                this.image.requestLayout();
                isImage = true;
            } else {
                this.image.setVisibility(GONE);
                isImage = false;
            }

            if (isGridView) {
                int columns = a.getInt(R.styleable.EmptyState_columns, 1);
                listGV.setNumColumns(columns);
            }


        } catch (Exception ignored) {
        }
        updateUI();
    }

    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            listGV.setAdapter(adapter);
            isGridView = true;
            isExpandable = false;
            isCustomList = false;
            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    setIsEmpty(listGV.getAdapter().getCount() == 0);
                }
            });
        }
    }

    public void setAdapter(BaseExpandableListAdapter adapter) {
        if (adapter != null) {
            listEL.setAdapter(adapter);
            isGridView = false;
            isExpandable = true;
            isCustomList = false;
            adapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    setIsEmpty(listEL.getAdapter().getCount() == 0);
                }
            });
        }
    }

    public void setTitle(String title) {
        if (title == null || title.trim().equals("")) {
            isTitle = false;
        } else {
            this.title.setText(title);
            isTitle = true;
        }
        this.title.setVisibility(isTitle ? VISIBLE : GONE);
    }

    public void setText(String text) {
        if (text == null || text.trim().equals("")) {
            isText = false;
        } else {
            this.text.setText(text);
            isText = true;
        }
        this.text.setVisibility(isText ? VISIBLE : GONE);
    }

    public void showTopProgress() {
        topProgress.setVisibility(VISIBLE);
    }

    public void showCenterProgress() {
        if (!isCustomEmptyState) {
            emptyState.setVisibility(VISIBLE);
            list.setVisibility(GONE);
            centerProgress.setVisibility(VISIBLE);
            if (isImage) image.setVisibility(GONE);
            if (isTitle) title.setVisibility(GONE);
            if (isText) text.setVisibility(GONE);
        } else {
            Log.e("showCenterProgress", "Can't show CenterProgress because you are using a custom emptyState");
        }
    }

    public void hideTopProgress() {
        topProgress.setVisibility(GONE);
    }

    public void hideCenterProgress() {
        if(!isCustomEmptyState){
            centerProgress.setVisibility(GONE);
            if (isImage) image.setVisibility(VISIBLE);
            if (isTitle) title.setVisibility(VISIBLE);
            if (isText) text.setVisibility(VISIBLE);
            if(isGridView){
                setIsEmpty(listGV.getAdapter().getCount()==0);
            }else if(isExpandable){
                setIsEmpty(listEL.getAdapter().getCount()==0);
            }
        }else{
            Log.e("hideCenterProgress", "Can't hide CenterProgress because you are using a custom emptyState");
        }
    }

    public void updateUI() {
        list.setVisibility(isEmpty ? GONE : VISIBLE);
        emptyState.setVisibility(isEmpty ? VISIBLE : GONE);

        llCL.setVisibility(isCustomList ? VISIBLE : GONE);
        llEL.setVisibility(isExpandable ? VISIBLE : GONE);
        llGV.setVisibility(isGridView ? VISIBLE : GONE);

        custom_es.setVisibility(isCustomEmptyState ? VISIBLE : GONE);
        default_es.setVisibility(isCustomEmptyState ? GONE : VISIBLE);
    }

    public GridView getGridView() {
        return listGV;
    }

    public ExpandableListView getExpandableListView() {
        return listEL;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v instanceof CustomList) {
                isGridView = false;
                isExpandable = false;
                isCustomList = true;
                addCustomView(llCL, v);
            } else if (v instanceof CustomEmptyState) {
                isCustomEmptyState = true;
                addCustomView(custom_es, v);
            }
        }
        updateUI();

    }

    private void addCustomView(LinearLayout target, View view) {
        ((ViewGroup) view.getParent()).removeView(view);
        view.setLayoutParams(getLayoutParams());
        target.addView(view);

    }
    public void setIsEmpty(boolean isEmpty){
        this.isEmpty=isEmpty;
        updateUI();
    }
    public void addCustomListView(View view){
        isGridView = false;
        isExpandable = false;
        isCustomList = true;
        view.setLayoutParams(getLayoutParams());
        llCL.addView(view);
    }

}
