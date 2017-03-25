package com.umarbhutta.xlightcompanion.control.activity.dialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.umarbhutta.xlightcompanion.App;
import com.umarbhutta.xlightcompanion.R;
import com.umarbhutta.xlightcompanion.control.activity.AddControlRuleActivity;
import com.umarbhutta.xlightcompanion.control.adapter.DeviceNameAdapter;
import com.umarbhutta.xlightcompanion.control.adapter.DialogListAdapter;
import com.umarbhutta.xlightcompanion.control.bean.Ruleconditions;
import com.umarbhutta.xlightcompanion.glance.GlanceFragment;
import com.umarbhutta.xlightcompanion.okHttp.model.Condition;

/**
 * Created by Administrator on 2017/3/15.
 * 弹出框Activity
 */

public class DialogRowNameActivity extends Activity {

    DeviceNameAdapter dialogConditionListAdapter;
    ListView dialoglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_row_dialog);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ((App)getApplicationContext()).setActivity(this);
        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        dialoglist = (ListView) findViewById(R.id.dialoglist);
        dialogConditionListAdapter = new DeviceNameAdapter(DialogRowNameActivity.this.getApplicationContext(), GlanceFragment.deviceList);
        dialoglist.setAdapter(dialogConditionListAdapter);

        dialoglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("ROW", GlanceFragment.deviceList.get(position));
                setResult(35,intent);
                finish();
            }
        });
        dialogConditionListAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
