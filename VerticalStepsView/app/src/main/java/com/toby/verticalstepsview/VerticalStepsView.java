package com.toby.verticalstepsview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class VerticalStepsView extends LinearLayout
        implements VerticalStepsViewIndicator.OnDrawListener {

    private VerticalStepsViewIndicator mVerticalStepsViewIndicator;
    private FrameLayout mLabelsLayout;
    private String[] mLabels;
    private int mProgressColorIndicator = Color.YELLOW;
    private int mLabelColorIndicator = Color.BLACK;
    private int mBarColorIndicator = Color.BLACK;
    private float mLabelTextSize = 20;
    private int mCompletedPosition = 0;
    private int mTotalSteps;

    public VerticalStepsView(Context context) {
        this(context, null);
    }

    public VerticalStepsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepsView(Context context, AttributeSet attrs,
                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        TypedArray arr = context.obtainStyledAttributes(attrs,
                R.styleable.VerticalStepsView);
        int numSteps = arr.getInt(R.styleable.VerticalStepsView_numOfSteps, 1);
        int completePos = arr.getInt(R.styleable.VerticalStepsView_completePosition, 0);
        int labelsResId = arr.getResourceId(R.styleable.VerticalStepsView_labels, 0);
        int barColor = arr.getColor(R.styleable.VerticalStepsView_barColor, Color.GRAY);
        int progressColor = arr.getColor(R.styleable.VerticalStepsView_progressColor, ContextCompat.getColor(context, R.color.orange));
        int labelColor = arr.getColor(R.styleable.VerticalStepsView_labelColor, Color.BLACK);
        int progressTextColor = arr.getColor(R.styleable.VerticalStepsView_progressTextColor, Color.WHITE);
        boolean hideProgressText = arr.getBoolean(R.styleable.VerticalStepsView_hideProgressText, false);
        float labelSize =arr.getFloat(R.styleable.VerticalStepsView_labelSize, 20);
        float progressMargin = arr.getFloat(R.styleable.VerticalStepsView_progressMargin, 100);
        float circleRadius = arr.getFloat(R.styleable.VerticalStepsView_circleRadius, 50);
        float progressStrokeWidth = arr.getFloat(R.styleable.VerticalStepsView_progressStrokeWidth, 5);

        mVerticalStepsViewIndicator.setStepTotal(numSteps);
        mTotalSteps = numSteps;
        if (labelsResId > 0) {
            this.setLabels(getResources().getStringArray(labelsResId));
        }
        this.setCompletedPosition(completePos);
        this.setBarColorIndicator(barColor);
        this.setProgressColorIndicator(progressColor);
        this.setLabelColorIndicator(labelColor);
        this.setProgressTextColor(progressTextColor);
        this.setHideProgressText(hideProgressText);
        this.setLabelTextSize(labelSize);
        this.setProgressMargins(progressMargin);
        this.setCircleRadius(circleRadius);
        this.setProgressStrokeWidth(progressStrokeWidth);
        arr.recycle();
        // initial draw view
        drawView();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_vertical_steps_view, this);
        mVerticalStepsViewIndicator = (VerticalStepsViewIndicator) rootView.findViewById(R.id.steps_indicator_view);
        mVerticalStepsViewIndicator.setDrawListener(this);
        mLabelsLayout = (FrameLayout) rootView.findViewById(R.id.labels_container);
    }

    public String[] getLabels() {
        return mLabels;
    }

    public VerticalStepsView setLabels(String[] labels) {
        mLabels = labels;
        if (labels.length > mTotalSteps) {
            mVerticalStepsViewIndicator.setStepTotal(labels.length);
            mTotalSteps = labels.length;
        }
        return this;
    }

    public int getProgressColorIndicator() {
        return mProgressColorIndicator;
    }

    public VerticalStepsView setProgressColorIndicator(int progressColorIndicator) {
        mProgressColorIndicator = progressColorIndicator;
        mVerticalStepsViewIndicator.setProgressColor(mProgressColorIndicator);
        return this;
    }

    public int getLabelColorIndicator() {
        return mLabelColorIndicator;
    }

    public VerticalStepsView setLabelColorIndicator(int labelColorIndicator) {
        mLabelColorIndicator = labelColorIndicator;
        return this;
    }

    public int getBarColorIndicator() {
        return mBarColorIndicator;
    }

    public VerticalStepsView setBarColorIndicator(int barColorIndicator) {
        mBarColorIndicator = barColorIndicator;
        mVerticalStepsViewIndicator.setBarColor(mBarColorIndicator);
        return this;
    }

    public int getCompletedPosition() {
        return mCompletedPosition;
    }

    public VerticalStepsView setCompletedPosition(int completedPosition) {
        mCompletedPosition = completedPosition;
        mVerticalStepsViewIndicator.setCompletedPosition(mCompletedPosition);
        return this;
    }

    public VerticalStepsView setLabelTextSize(float size) {
        mLabelTextSize = size;
        return this;
    }

    public VerticalStepsView setProgressStrokeWidth(float width) {
        mVerticalStepsViewIndicator.setProgressStrokeWidth(width);
        return this;
    }

    public VerticalStepsView setProgressMargins(float margin) {
        mVerticalStepsViewIndicator.setMargins(margin);
        return this;
    }

    public VerticalStepsView setCircleRadius(float radius) {
        mVerticalStepsViewIndicator.setCircleRadius(radius);
        return this;
    }

    public VerticalStepsView setProgressTextColor(int textColor) {
        mVerticalStepsViewIndicator.setProgressTextColor(textColor);
        return this;
    }

    public VerticalStepsView setHideProgressText(boolean hide) {
        mVerticalStepsViewIndicator.setHideProgressText(hide);
        return this;
    }

    public void drawView() {
        if (mTotalSteps == 0) {
            throw new IllegalArgumentException("Total steps cannot be zero.");
        }

        if (mCompletedPosition < 0 || mCompletedPosition > mTotalSteps - 1) {
            throw new IndexOutOfBoundsException(String.format("Index : %s, Size : %s", mCompletedPosition, mLabels.length));
        }

        mVerticalStepsViewIndicator.invalidate();
    }

    @Override
    public void onReady() {
        drawLabels();
    }

    private void drawLabels() {
        List<Float> indicatorPosition = mVerticalStepsViewIndicator.getmThumbContainerYPosition();

        if (mLabels != null) {
            for (int i = 0; i < mLabels.length; i++) {
                TextView textView = new TextView(getContext());
                textView.setText(mLabels[i]);
                textView.setTextColor(mLabelColorIndicator);
                textView.setTextSize(mLabelTextSize);
                textView.setGravity(Gravity.CENTER);
                textView.setLayoutParams(
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.measure(0, 0);
                float textHeight = textView.getMeasuredHeight();
                textView.setX(mVerticalStepsViewIndicator.getCircleRadius());
                textView.setY(indicatorPosition.get(i) - (textHeight / 2));

                mLabelsLayout.addView(textView);
            }
        }
    }
}
