package io.github.seriouszyx.trivialim.controller.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.controller.activity.AddContactActivity;
import io.github.seriouszyx.trivialim.controller.activity.InviteActivity;
import io.github.seriouszyx.trivialim.utils.Constant;
import io.github.seriouszyx.trivialim.utils.SpUtils;

// 联系人列表页面
public class ContactListFragment extends EaseContactListFragment {


    private ImageView iv_contact_red;
    private LocalBroadcastManager mLBM;
    private BroadcastReceiver ContactInviteChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 更新红红点显示
            iv_contact_red.setVisibility(View.VISIBLE);
            SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, true);

        }
    };
    private LinearLayout ll_contact_invite;

    @Override
    protected void initView() {
        super.initView();
        // 布局显示加号
        titleBar.setRightImageResource(R.drawable.em_add);

        // 头布局添加
        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);
        listView.addHeaderView(headerView);

        // 获取红点对象
        iv_contact_red = headerView.findViewById(R.id.iv_contact_red);

        // 获取邀请信息条目的对象
        ll_contact_invite = headerView.findViewById(R.id.ll_contact_invite);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // 添加按钮的点击事件处理
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });

        // 初始化红点显示
        boolean isNewInvite = SpUtils.getInstance().getBoolean(SpUtils.IS_NEW_INVITE, false);
        iv_contact_red.setVisibility(isNewInvite ? View.VISIBLE : View.GONE);

        // 邀请信息条目的点击事件
        ll_contact_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 红点处理
                iv_contact_red.setVisibility(View.GONE);
                SpUtils.getInstance().save(SpUtils.IS_NEW_INVITE, false);
                // 跳转到邀请信息列表页面
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);
            }
        });

        // 注册广播
        mLBM = LocalBroadcastManager.getInstance(getActivity());
        mLBM.registerReceiver(ContactInviteChangeReceiver, new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
    }
}
