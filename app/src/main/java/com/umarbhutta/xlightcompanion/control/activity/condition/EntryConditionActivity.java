package com.umarbhutta.xlightcompanion.control.activity.condition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.umarbhutta.xlightcompanion.App;
import com.umarbhutta.xlightcompanion.R;
import com.umarbhutta.xlightcompanion.Tools.Logger;
import com.umarbhutta.xlightcompanion.Tools.UserUtils;
import com.umarbhutta.xlightcompanion.control.activity.dialog.DialogActivity;
import com.umarbhutta.xlightcompanion.control.adapter.EntryConditionListAdapter;
import com.umarbhutta.xlightcompanion.control.bean.Ruleconditions;
import com.umarbhutta.xlightcompanion.main.SimpleDividerItemDecoration;
import com.umarbhutta.xlightcompanion.okHttp.HttpUtils;
import com.umarbhutta.xlightcompanion.okHttp.NetConfig;
import com.umarbhutta.xlightcompanion.okHttp.model.Condition;
import com.umarbhutta.xlightcompanion.okHttp.model.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 * 启动条件
 */

public class EntryConditionActivity extends AppCompatActivity {

    private String TAG = EntryConditionActivity.class.getSimpleName();

    private LinearLayout llBack;
    private TextView btnSure;
    private TextView tvTitle;

    private int requestCode = 111;

    private List<String> settingStr = new ArrayList<String>();
    private List<Integer> imgInter = new ArrayList<Integer>();

    public ArrayList<String> listStr = new ArrayList<String>();

    EntryConditionListAdapter entryConditionListAdapter;
    RecyclerView settingRecyclerView;

    private Schedule mSchedule;

    private Condition mCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        //hide nav bar
        getSupportActionBar().hide();

        ((App)getApplicationContext()).setActivity(this);
        mSchedule= new Schedule();
        mCondition = new Condition();

        settingRecyclerView = (RecyclerView) findViewById(R.id.settingRecyclerView);
        entryConditionListAdapter = new EntryConditionListAdapter(this, settingStr,imgInter);
        settingRecyclerView.setAdapter(entryConditionListAdapter);

        //set LayoutManager for recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        //attach LayoutManager to recycler view
        settingRecyclerView.setLayoutManager(layoutManager);
        //divider lines
        settingRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

        llBack = (LinearLayout) findViewById(R.id.ll_back);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSure = (TextView) findViewById(R.id.tvEditSure);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setText("启动条件");
        btnSure.setVisibility(View.GONE);

        settingStr.add("定时");
        imgInter.add( R.drawable.rule_time);
        settingStr.add("亮度");
        imgInter.add( R.drawable.rule_brightness);
        settingStr.add("检测到活动");
        imgInter.add( R.drawable.rule_activity);
        settingStr.add("检测到声音");
        imgInter.add( R.drawable.rule_souce);
        settingStr.add("温度");
        imgInter.add( R.drawable.rule_tem);
        settingStr.add("离家");
        imgInter.add( R.drawable.rule_fromhome);
        settingStr.add("回家");
        imgInter.add( R.drawable.rule_gohome);
        settingStr.add("气体");
        imgInter.add( R.drawable.rule_gas);

        entryConditionListAdapter.notifyDataSetChanged();
        entryConditionListAdapter.setmOnItemClickListener(new EntryConditionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0://定时
                        listStr.clear();
                        onFabPressed(TimingActivity.class,listStr);
                        break;
                    case 1://亮度
                        listStr.clear();
                        requestCode = 111;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                    case 2://检测到活动
                        listStr.clear();
//                        listStr.add("活动一");
//                        listStr.add("活动二");
//                        listStr.add("活动三");
//                        listStr.add("活动四");
                        requestCode = 112;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                    case 3://检测到声音
                        listStr.clear();
//                        listStr.add("声音一");
//                        listStr.add("声音二");
//                        listStr.add("声音三");
//                        listStr.add("声音四");
                        requestCode = 113;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                    case 4://温度
                        listStr.clear();
                        requestCode = 114;
                        onFabPressed(TemControlActivity.class,listStr);
                        break;
                    case 5://离家
                        listStr.clear();
                        requestCode = 115;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                    case 6://回家
                        listStr.clear();
                        requestCode = 116;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                    case 7://气体
                        listStr.clear();
                        requestCode = 117;
                        onFabPressed(DialogActivity.class,listStr);
                        break;
                }
            }
        });

        getRuleconditions();//获取规则条件详细信息
    }

    /**
     * 获取规则条件详细信息
     */
    private void getRuleconditions() {
        HttpUtils.getInstance().getRequestInfo(NetConfig.URL_RULES_RULECONDITIONS+"?access_token=" + UserUtils.getUserInfo(getApplicationContext()).getAccess_token(),
                Ruleconditions.class, new HttpUtils.OnHttpRequestCallBack() {
                    @Override
                    public void onHttpRequestSuccess(Object result) {
                        //
                        Ruleconditions ruleconditions = (Ruleconditions)result;
                        Logger.e(TAG,ruleconditions.toString());
                    }

                    @Override
                    public void onHttpRequestFail(int code, String errMsg) {
                        Logger.e(TAG,"code="+code+";errMsg="+errMsg);
                    }
                });
    }

    private void onFabPressed(Class activity,ArrayList<String> listStr) {
        Intent intent = new Intent(this, activity);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("DILOGLIST",listStr);
        bundle.putInt("TYPE",0);
        bundle.putSerializable("SCHEDULE",mSchedule);
        bundle.putSerializable("CONDITION",mCondition);
        intent.putExtra("BUNDLE",bundle);
        startActivityForResult(intent,requestCode);
    }

    /**
     * 退出登录
     */
    private void logout() {
        UserUtils.saveUserInfo(getApplicationContext(), null);
    }
}