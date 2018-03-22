package com.syberos.learnandroid;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private static List<Map<String, String>> functionInfo;

    private static final String KEY_TITLE = "title";
    private static final String KEY_CONTENT = "content";

    static {
        functionInfo = new ArrayList<>();
        Map<String, String> tmpMap = new ArrayMap<>();
        tmpMap.put(KEY_TITLE, "Light");
        tmpMap.put(KEY_CONTENT, "Light Sensor Test");
        functionInfo.add(tmpMap);
        tmpMap = new ArrayMap<>();
        tmpMap.put(KEY_TITLE, "Accelerometer");
        tmpMap.put(KEY_CONTENT, "Accelerometer Sensor Test");
        functionInfo.add(tmpMap);
        tmpMap = new ArrayMap<>();
        tmpMap.put(KEY_TITLE, "Compass");
        tmpMap.put(KEY_CONTENT, "Compass Sensor Test");
        functionInfo.add(tmpMap);

        tmpMap = new ArrayMap<>();
        tmpMap.put(KEY_TITLE, "SendPersonUseSerializable");
        tmpMap.put(KEY_CONTENT, "Send Person Use Serializable");
        functionInfo.add(tmpMap);

        tmpMap = new ArrayMap<>();
        tmpMap.put(KEY_TITLE, "SendPersonUseParcelable");
        tmpMap.put(KEY_CONTENT, "Send Person Use Parcelable");
        functionInfo.add(tmpMap);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter());


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("some things")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: 18-3-20
                    }
                })
                .setNegativeButton("取消", null)
                .create();
        dialog.show();
        // the follow code must after the dialog.show();
        dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return functionInfo.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            if (functionInfo.size() <= 0) {
                return convertView;
            }

            ViewHolder holder;
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item, container,
                        false);
                holder = new ViewHolder();
                holder.tv_title = convertView.findViewById(R.id.title);
                holder.tv_content = convertView.findViewById(R.id.content);

                convertView.setTag(holder);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "current clicked position is " + position);
                        openActivity(position);
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_title.setText(functionInfo.get(position).get(KEY_TITLE));
            holder.tv_content.setText(functionInfo.get(position).get(KEY_CONTENT));

            return convertView;
        }
    }

    private static class ViewHolder {
        public TextView tv_title;
        public TextView tv_content;
    }

    private void openActivity(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, LightActivity.class);
                break;
            case 1:
                intent = new Intent(this, AccelerometerActivity.class);
                break;
            case 2:
                intent = new Intent(this, CompassActivity.class);
                break;
            case 3:
                SendPersonUseSerializable personUseSerializable = new SendPersonUseSerializable();
                personUseSerializable.setName("Toby");
                personUseSerializable.setAge(30);
                intent = new Intent(this, ReceivePersonActivity.class);
                intent.putExtra("person_serializable_data", personUseSerializable);
                break;

            case 4:
                SendPersonUseParcelable personUseParcelable = new SendPersonUseParcelable();
                personUseParcelable.setName("Tina");
                personUseParcelable.setAge(28);
                intent = new Intent(this, ReceivePersonActivity.class);
                intent.putExtra("person_parcelable_data", personUseParcelable);
                break;
            default:
                break;
        }
        if (null != intent) {
            startActivity(intent);
        }
    }
}