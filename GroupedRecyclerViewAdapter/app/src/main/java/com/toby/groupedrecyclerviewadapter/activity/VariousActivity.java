package com.toby.groupedrecyclerviewadapter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.toby.groupedrecyclerviewadapter.R;
import com.syberos.shuili.widgets.grouped_adapter.adapter.GroupedRecyclerViewAdapter;
import com.toby.groupedrecyclerviewadapter.adapter.VariousAdapter;
import com.syberos.shuili.widgets.grouped_adapter.holder.BaseViewHolder;
import com.toby.groupedrecyclerviewadapter.model.GroupModel;

/**
 * 头、尾和子项都支持多种类型的分组列表
 */
public class VariousActivity extends AppCompatActivity {

    private TextView tvTitle;
    private RecyclerView rvList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvList = (RecyclerView) findViewById(R.id.rv_list);

        tvTitle.setText(R.string.various);

        rvList.setLayoutManager(new LinearLayoutManager(this));
        VariousAdapter adapter = new VariousAdapter(this, GroupModel.getGroups(10, 5));
        adapter.setOnHeaderClickListener(new GroupedRecyclerViewAdapter.OnHeaderClickListener() {
            @Override
            public void onHeaderClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(VariousActivity.this, "组头：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnFooterClickListener(new GroupedRecyclerViewAdapter.OnFooterClickListener() {
            @Override
            public void onFooterClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                      int groupPosition) {
                Toast.makeText(VariousActivity.this, "组尾：groupPosition = " + groupPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        adapter.setOnChildClickListener(new GroupedRecyclerViewAdapter.OnChildClickListener() {
            @Override
            public void onChildClick(GroupedRecyclerViewAdapter adapter, BaseViewHolder holder,
                                     int groupPosition, int childPosition) {
                Toast.makeText(VariousActivity.this, "子项：groupPosition = " + groupPosition
                                + ", childPosition = " + childPosition,
                        Toast.LENGTH_LONG).show();
            }
        });
        rvList.setAdapter(adapter);

    }

    public static void openActivity(Context context) {
        Intent intent = new Intent(context, VariousActivity.class);
        context.startActivity(intent);
    }
}