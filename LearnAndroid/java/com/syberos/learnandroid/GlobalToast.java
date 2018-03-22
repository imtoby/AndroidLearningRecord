package com.syberos.learnandroid;

import android.annotation.SuppressLint;
import android.widget.Toast;

/**
 * Created by toby on 18-1-9.
 */

class GlobalToast {

    private static Toast toast = null;

    @SuppressLint("ShowToast")
    public static void show(String text, int toastDuration){
        try{
            toast.getView().isShown();
            toast.setText(text);
        } catch (Exception e) {
            toast = Toast.makeText(MyApplication.globalContext(), text, toastDuration);
        }
        toast.show();
    }
}
