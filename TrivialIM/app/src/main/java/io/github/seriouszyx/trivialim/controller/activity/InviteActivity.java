package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.controller.adapter.InviteAdapter;
import io.github.seriouszyx.trivialim.model.Model;
import io.github.seriouszyx.trivialim.model.bean.InvitationInfo;

// 邀请页面
public class InviteActivity extends Activity {

    private ListView lv_invite;

    private InviteAdapter inviteAdapter;

    private InviteAdapter.OnInviteListener mOnInvitationListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InvitationInfo invitationInfo) {
            // 通知环信服务器，点击了接受按钮
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invitationInfo.getUser().getHxid());
                        // 数据库更新
                        Model.getInstance().getDbManager()
                                .getInviteTableDao().updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT, invitationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 页面发生变化
                                Toast.makeText(InviteActivity.this, "接受了邀请", Toast.LENGTH_SHORT).show();
                                // 刷新页面
                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "接受邀请失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        @Override
        public void onReject(final InvitationInfo invitationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().declineInvitation(invitationInfo.getUser().getHxid());

                        Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(invitationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝成功", Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this, "拒绝失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        initView();

        initData();
    }

    private void initData() {
        // 初始化 listView
        inviteAdapter = new InviteAdapter(this, mOnInvitationListener);
        lv_invite.setAdapter(inviteAdapter);

        // 刷新
        refresh();
    }

    private void refresh() {
        List<InvitationInfo> invitations = Model.getInstance().getDbManager().getInviteTableDao().getInvitations();
        inviteAdapter.refresh(invitations);
    }

    private void initView() {
        lv_invite = findViewById(R.id.lv_invite);
    }
}
