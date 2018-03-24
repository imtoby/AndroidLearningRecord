package com.lonuery.ripple;

import com.lonuery.btnripple.R;
import com.lonuery.ripple.RippleLayout.RippleFinishListener;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ImportMenuView extends RelativeLayout implements RippleFinishListener, View.OnClickListener {

    Context context = null;
    private Handler mHandler;

    private boolean animating = false;

    TextView tv_top, tv_center, tv_bottom;
    LinearLayout ll_top, ll_center, ll_bottom;
    RippleLayout rl_close, rl_top, rl_center, rl_bottom;
    View view_shadow;

    public ImportMenuView(Context context) {
        this(context, null);
    }

    public ImportMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImportMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.popup_button_menu, this, true);

        mHandler = new Handler();

        tv_top = findViewById(R.id.tv_top);
        tv_center = findViewById(R.id.tv_center);
        tv_bottom = findViewById(R.id.tv_bottom);

        ll_top = findViewById(R.id.ll_top);
        ll_center = findViewById(R.id.ll_center);
        ll_bottom = findViewById(R.id.ll_bottom);

        ll_top.setOnClickListener(this);
        ll_center.setOnClickListener(this);
        ll_bottom.setOnClickListener(this);

        rl_close = findViewById(R.id.rl_close);
        rl_top = findViewById(R.id.rl_top);
        rl_center = findViewById(R.id.rl_center);
        rl_bottom = findViewById(R.id.rl_bottom);

        rl_close.setRippleFinishListener(this);
        rl_top.setRippleFinishListener(this);
        rl_center.setRippleFinishListener(this);
        rl_bottom.setRippleFinishListener(this);

        view_shadow = findViewById(R.id.v_shadow);
        view_shadow.setOnClickListener(this);

        setVisibility(View.GONE);
        setEnabled(false);
        rl_close.setEnabled(false);

        this.context = context;
    }

    public void animationEnter(Context context) {
        if (animating) {
            return;
        }
        animating = true;
        setEnabled(false);
        rl_close.setEnabled(false);
        Animation top_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_top_enter);
        ll_top.startAnimation(top_ball_anim);

        Animation center_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_center_enter);
        ll_center.startAnimation(center_ball_anim);

        Animation bottom_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_bottom_enter);
        ll_bottom.startAnimation(bottom_ball_anim);

        Animation view_fade_in_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_fade_in);
        view_shadow.startAnimation(view_fade_in_anim);

        Animation top_text_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_top_text_enter);
        tv_top.startAnimation(top_text_anim);

        Animation center_text_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_center_text_enter);
        tv_center.startAnimation(center_text_anim);

        Animation bottom_text_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_bottom_text_enter);
        tv_bottom.startAnimation(bottom_text_anim);

        bottom_text_anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rl_close.setEnabled(true);
                        ImportMenuView.this.setEnabled(true);
                        animating = false;
                    }
                },200);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void animationExit() {
        if (animating) {
            return;
        }
        animating = true;
        setEnabled(false);
        Animation top_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_top_exit);
        ll_top.startAnimation(top_ball_anim);

        Animation center_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_center_exit);
        ll_center.startAnimation(center_ball_anim);

        Animation bottom_ball_anim = AnimationUtils.loadAnimation(context,
                R.anim.popup_button_bottom_exit);
        ll_bottom.startAnimation(bottom_ball_anim);

        top_ball_anim.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation view_fade_in_anim = AnimationUtils.loadAnimation(context,
                        R.anim.popup_button_fade_out);
                closeExitAnimation();
                view_shadow.startAnimation(view_fade_in_anim);
                setEnabled(true);
                animating = false;
            }
        });
    }

    public void closeEnterAnimation() {
        ObjectAnimator object
                = ObjectAnimator.ofFloat(rl_close, "rotation", 0, -45);
        object.setDuration(250);
        object.setRepeatCount(0);
        object.start();
    }

    public void closeExitAnimation() {
        ObjectAnimator object
                = ObjectAnimator.ofFloat(rl_close, "rotation", -45, 0);
        object.setDuration(200);
        object.setRepeatCount(0);
        object.addListener(new AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
                setEnabled(false);
                setFocusable(false);
                if (null != actionsListener) {
                    actionsListener.exitFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        object.start();
    }


    @Override
    public void rippleFinish(int id) {
        if (animating) {
            return;
        }
        switch (id) {
            case R.id.rl_close:
                animationExit();
                break;
            case R.id.rl_top:
                if (null != actionsListener) {
                    actionsListener.itemClicked(0);
                }
                animationExit();
                break;
            case R.id.rl_center:
                if (null != actionsListener) {
                    actionsListener.itemClicked(1);
                }
                animationExit();
                break;
            case R.id.rl_bottom:
                if (null != actionsListener) {
                    actionsListener.itemClicked(2);
                }
                animationExit();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (animating) {
            return;
        }
        switch (v.getId()) {
            case R.id.ll_top:
                if (null != actionsListener) {
                    actionsListener.itemClicked(0);
                }
                break;
            case R.id.ll_center:
                if (null != actionsListener) {
                    actionsListener.itemClicked(1);
                }
                break;
            case R.id.ll_bottom:
                if (null != actionsListener) {
                    actionsListener.itemClicked(2);
                }
                break;
        }
        animationExit();
    }

    public void show(Context context) {
        setVisibility(View.VISIBLE);
        setEnabled(true);
        setFocusable(true);
        closeEnterAnimation();
        animationEnter(context);
        bringToFront();
    }

    public interface ActionsListener {
        void itemClicked(int itemIndex);
        void exitFinish();
    }

    public void setActionsListener(
            ActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    private ActionsListener actionsListener = null;
}
