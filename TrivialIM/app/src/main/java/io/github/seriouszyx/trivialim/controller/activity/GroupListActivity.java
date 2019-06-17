package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.controller.adapter.GroupListAdapter;
import io.github.seriouszyx.trivialim.model.Model;

public class GroupListActivity extends BaseActivity {

    private ListView lv_grouplist;
    private GroupListAdapter groupListAdapter;
    private LinearLayout ll_grouplist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_group_list);

        initView();

        initData();
        
        initListener();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 0);
    }

    private void initListener() {
        // 条目的点击事件
        lv_grouplist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    return;

                Intent intent = new Intent(GroupListActivity.this, ChatActivity.class);

                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_GROUP);
                EMGroup emGroup = EMClient.getInstance().groupManager().getAllGroups().get(position - 1);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, emGroup.getGroupId());

                startActivity(intent);
            }
        });

        // 跳转到新建群
        ll_grouplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupListActivity.this, NewGroupActivity.class);

                startActivity(intent);
            }
        });
    }

    private void initData() {
        groupListAdapter = new GroupListAdapter(this);
        lv_grouplist.setAdapter(groupListAdapter);

        // 从环信服务器获取所有群的信息
        getGroupsFromServer();

    }

    private void getGroupsFromServer() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final List<EMGroup> mGroups = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupListActivity.this, "加载群信息成功", Toast.LENGTH_SHORT).show();

                            refresh();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GroupListActivity.this, "加载群信息失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void refresh() {
        groupListAdapter.refresh(EMClient.getInstance().groupManager().getAllGroups());
    }

    private void initView() {
        // 添加头布局
        lv_grouplist = findViewById(R.id.lv_grouplist);
        View headerView = View.inflate(this, R.layout.header_grouplist, null);
        lv_grouplist.addHeaderView(headerView);

        ll_grouplist = headerView.findViewById(R.id.ll_grouplist);
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }
}
