package com.lonuery.ripple;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

import com.lonuery.btnripple.R;
import com.lonuery.ripple.RippleLayout.RippleFinishListener;

public class MainActivity extends Activity {

    private RippleLayout ripple;
    private ImportMenuView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ripple = findViewById(R.id.more2);
        menuView = findViewById(R.id.main_activity_import_menu);
        menuView.setEnabled(false);
        menuView.setActionsListener(new ImportMenuView.ActionsListener() {
            @Override
            public void itemClicked(int itemIndex) {

            }

            @Override
            public void exitFinish() {
                ripple.setVisibility(View.VISIBLE);
                ripple.bringToFront();
                ripple.setEnabled(true);
            }
        });
        ripple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ripple.setEnabled(false);
            }
        });
        ripple.setRippleFinishListener(new RippleFinishListener() {
            @Override
            public void rippleFinish(int id) {
                if (id == R.id.more2) {
                    menuView.show(MainActivity.this);
                    ripple.setVisibility(View.GONE);
                }
            }
        });
    }

}
