package com.syberos.learnandroid;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by toby on 18-3-22.
 */

public class WorkItemButton extends LinearLayout {

    private ImageView iv_button_icon;
    private TextView tv_button_text;

    public WorkItemButton(Context context) {
        super(context);
    }

    public WorkItemButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater
                = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.layout_work_item_button, WorkItemButton.this);
            iv_button_icon = (ImageView) findViewById(R.id.iv_button_icon);
            tv_button_text = (TextView) findViewById(R.id.tv_button_text);
        }

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WorkItemButton);

        int resId = a.getResourceId(R.styleable.WorkItemButton_button_icon, -1);
        String text = a.getString(R.styleable.WorkItemButton_button_text);

        setImageResource(resId);
        setTextViewText(text);

        a.recycle();
    }

    public void setImageResource(int resId) {
        iv_button_icon.setImageResource(resId);
    }

    public void setTextViewText(String text) {
        tv_button_text.setText(text);
    }

}
