package com.toby.verticalstepsview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class VerticalStepsViewIndicator extends View {

    private Paint paint = new Paint();
    private Paint selectedPaint = new Paint();
    private Paint progressTextPaint = new Paint();
    private int mNumOfStep;
    private float mProgressStrokeWidth;
    private float mLineHeight;
    private float mCircleRadius;
    private float mMargins;
    private int mProgressColor;
    private int mBarColor;
    private int mProgressTextColor;
    private boolean hideProgressText;

    private float mCenterX;
    private float mLineX;
    private List<Float> mThumbContainerYPosition = new ArrayList<>();
    private int mCompletedPosition;
    private OnDrawListener mDrawListener;

    public VerticalStepsViewIndicator(Context context) {
        this(context, null);
    }

    public VerticalStepsViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepsViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mLineHeight = mProgressStrokeWidth;
    }

    public void setStepTotal(int size) {
        mNumOfStep = size;
        invalidate();
    }

    public void setDrawListener(OnDrawListener drawListener) {
        mDrawListener = drawListener;
    }


    public List<Float> getmThumbContainerYPosition() {
        return mThumbContainerYPosition;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mThumbContainerYPosition = new ArrayList<>();

        mCenterX = 0.5f * getWidth();
        float mTopY = mMargins;
        mLineX = mCenterX - (mLineHeight / 2);
        float mBottomY = getHeight() - mMargins;
        float mDelta = (mBottomY - mTopY) / (mNumOfStep - 1);

        mThumbContainerYPosition.add(mTopY);
        for (int i = 1; i < mNumOfStep - 1; i++) {
            mThumbContainerYPosition.add(mTopY + (i * mDelta));
        }
        mThumbContainerYPosition.add(mBottomY);
        Log.v("v steps view indicator", mThumbContainerYPosition.toString());

        mDrawListener.onReady();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width  = measureDimension(180, widthMeasureSpec);
        int height = measureDimension(getHeight(), heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    protected int measureDimension( int defaultSize, int measureSpec ) {

        int result = defaultSize;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(defaultSize, specSize);
        } else {
            result = defaultSize;
        }

        return result;
    }

    public void setCompletedPosition(int position) {
        mCompletedPosition = position;
    }

    public void reset() {
        setCompletedPosition(0);
    }

    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
    }

    public void setBarColor(int barColor) {
        mBarColor = barColor;
    }

    public void setProgressTextColor(int textColor) {
        mProgressTextColor = textColor;
    }

    public void setProgressStrokeWidth(float width) {
        mProgressStrokeWidth = width;
    }

    public void setMargins(float margin) {
        mMargins = margin;
    }

    public float getCircleRadius() {
        return mCircleRadius;
    }

    public void setCircleRadius(float radius) {
        mCircleRadius = radius;
    }

    public void setHideProgressText(boolean hide) {
        hideProgressText = hide;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDrawListener.onReady();
        // bar progress paint
        paint.setAntiAlias(true);
        paint.setColor(mBarColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);

        // progress paint
        selectedPaint.setAntiAlias(true);
        selectedPaint.setColor(mProgressColor);
        selectedPaint.setStyle(Paint.Style.STROKE);
        selectedPaint.setStrokeWidth(1);

        // progress text
        progressTextPaint.setAntiAlias(true);
        progressTextPaint.setTextSize(mCircleRadius);
        progressTextPaint.setColor(mProgressTextColor);

        paint.setStyle(Paint.Style.FILL);
        selectedPaint.setStyle(Paint.Style.FILL);

        // draw lines
        for (int i = 0; i < mThumbContainerYPosition.size() - 1; ++i) {
            final float pos = mThumbContainerYPosition.get(i);
            final float pos2 = mThumbContainerYPosition.get(i + 1);
            canvas.drawRect(mLineX, pos, mLineX + mProgressStrokeWidth, pos2,
                    (i < mCompletedPosition) ? selectedPaint : paint);

        }


        float quarterRadius = mCircleRadius / 4;
        // Draw circles

        for (int i = 0; i < mThumbContainerYPosition.size(); i++) {
            final float pos = mThumbContainerYPosition.get(i);
            canvas.drawCircle(mCenterX, pos, mCircleRadius,
                    (i <= mCompletedPosition) ? selectedPaint : paint);

            if (!hideProgressText) {
                canvas.drawText(String.valueOf(i + 1),
                        mCenterX - quarterRadius , pos + 5*quarterRadius/4, progressTextPaint);
            }
            // in current completed position color with alpha
            if (i == mCompletedPosition) {
                selectedPaint.setColor(getColorWithAlpha(mProgressColor, 0.2f));
                canvas.drawCircle(mCenterX, pos, mCircleRadius * 1.3f, selectedPaint);
            }
        }
    }

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public interface OnDrawListener {
        void onReady();
    }
}
