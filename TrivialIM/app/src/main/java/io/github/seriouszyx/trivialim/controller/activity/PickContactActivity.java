package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.controller.adapter.PickContactAdapter;
import io.github.seriouszyx.trivialim.model.Model;
import io.github.seriouszyx.trivialim.model.bean.PickContactInfo;
import io.github.seriouszyx.trivialim.model.bean.UserInfo;
import io.github.seriouszyx.trivialim.utils.Constant;

/**
 * 选择联系人页面
 */
public class PickContactActivity extends Activity {

    private TextView tv_pick_save;
    private ListView lv_pick;
    private List<PickContactInfo> mPicks;
    private PickContactAdapter pickContactAdapter;
    private List<String> mExistMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_contact);

        //获取传递过来的数据
        getData();
        initView();
        initData();
        initListener();
    }

    public void getData(){
        String groupId = getIntent().getStringExtra(Constant.GROUP_ID);

        if(groupId != null ){
            EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);

            //获取群中已经存在的所有群成员
            mExistMembers = group.getMembers();
        }

        if(mExistMembers == null){
            mExistMembers = new ArrayList<>();
        }
    }

    private void initView() {
        tv_pick_save = findViewById(R.id.tv_pick_save);
        lv_pick = findViewById(R.id.lv_pick);
    }

    private void initData() {
        //从本地数据库中获取所有联系人的信息
        List<UserInfo> contacts = Model.getInstance().getDbManager().getContactTableDao().getContacts();

        mPicks = new ArrayList<>();
        if(contacts != null && contacts.size() >= 0){
            //转换
            for(UserInfo contact : contacts){
                PickContactInfo pickContactInfo = new PickContactInfo(contact, false);
                mPicks.add(pickContactInfo);
            }
        }

        //初始化listView
        pickContactAdapter = new PickContactAdapter(this,mPicks,mExistMembers);

        lv_pick.setAdapter(pickContactAdapter);
    }

    private void initListener() {

        //listview条目的点击事件
        lv_pick.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //CheckBox切换
                CheckBox cb_pick =view.findViewById(R.id.cb_pick);
                cb_pick.setChecked(!cb_pick.isChecked());

                //修改数据
                PickContactInfo pickContactInfo = mPicks.get(position);
                pickContactInfo.setChecked(cb_pick.isChecked());

                //刷新页面
                pickContactAdapter.notifyDataSetChanged();
            }
        });

        //保存按钮的点击事件
        tv_pick_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取到已经选择的联系人
                List<String>  names = pickContactAdapter.getPickContacts();

                //给启动页面返回数据
                Intent intent = new Intent();
                intent.putExtra("members", names.toArray(new String[0]));
                setResult(RESULT_OK,intent);//设置返回的结果码
                finish();//结束当前页面

            }
        });
    }

}
