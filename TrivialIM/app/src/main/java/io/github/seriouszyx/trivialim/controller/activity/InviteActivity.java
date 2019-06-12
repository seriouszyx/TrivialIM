package io.github.seriouszyx.trivialim.controller.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
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
import io.github.seriouszyx.trivialim.utils.Constant;
//邀请信息列表页面
public class InviteActivity extends Activity {

    private ListView lv_invite;
    private InviteAdapter inviteAdapter;
    private LocalBroadcastManager mLBM;

    private BroadcastReceiver InviteChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新页面
            refresh();
        }
    };

    private InviteAdapter.OnInviteListener mOninviteListener = new InviteAdapter.OnInviteListener() {
        @Override
        public void onAccept(final InvitationInfo invitationInfo) {
            //通知环信服务器，点击了接受按钮
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().contactManager().acceptInvitation(invitationInfo.getUser().getHxid());

                        //数据库更新
                        Model.getInstance().getDbManager().getInviteTableDao().updateInvitationStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT,invitationInfo.getUser().getHxid());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //页面发生变化
                                Toast.makeText(InviteActivity.this,"接受了邀请",Toast.LENGTH_SHORT).show();

                                //刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"接受邀请失败",Toast.LENGTH_SHORT).show();
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
                        //数据库更新
                        Model.getInstance().getDbManager().getInviteTableDao().removeInvitation(invitationInfo.getUser().getHxid());

                        //页面变化
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝成功",Toast.LENGTH_SHORT).show();

                                //刷新页面
                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }

        //接受邀请按钮
        @Override
        public void onInviteAccept(final InvitationInfo invitationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //告诉环信服务器接受了邀请
                        EMClient.getInstance().groupManager().acceptInvitation(invitationInfo.getGroup().getGroupId(),invitationInfo.getGroup().getInvitePerson());

                        //本地数据库更新
                        invitationInfo.setStatus(InvitationInfo.InvitationStatus.GROUP_ACCEPT_INVITE);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

                        //内存数据变化
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"接受邀请",Toast.LENGTH_SHORT).show();

                                //刷新页面
                                refresh();
                            }
                        });
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"接受邀请失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
        //拒绝邀请按钮
        @Override
        public void onInviteReject(final InvitationInfo invitationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //告诉环信服务器拒绝了邀请
                        EMClient.getInstance().groupManager().declineInvitation(invitationInfo.getGroup().getGroupId(),invitationInfo.getGroup().getInvitePerson(),"拒绝邀请");

                        invitationInfo.setStatus(InvitationInfo.InvitationStatus.GROUP_REJECT_INVITE);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝邀请",Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //接受申请按钮
        @Override
        public void onApplicationAccept(final InvitationInfo invitationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //告诉环信服务器接受了申请
                        EMClient.getInstance().groupManager().acceptApplication(invitationInfo.getGroup().getGroupId(),invitationInfo.getGroup().getInvitePerson());

                        invitationInfo.setStatus(InvitationInfo.InvitationStatus.GROUP_ACCEPT_APPLICATION);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"接受申请",Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        //拒绝申请按钮
        @Override
        public void onApplicationReject(final InvitationInfo invitationInfo) {
            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //告诉环信服务器拒绝了申请
                        EMClient.getInstance().groupManager().declineApplication(invitationInfo.getGroup().getGroupId(),invitationInfo.getGroup().getInvitePerson(),"拒绝申请");

                        invitationInfo.setStatus(InvitationInfo.InvitationStatus.GROUP_REJECT_APPLICATION);
                        Model.getInstance().getDbManager().getInviteTableDao().addInvitation(invitationInfo);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝申请",Toast.LENGTH_SHORT).show();

                                refresh();
                            }
                        });

                    } catch (HyphenateException e) {
                        e.printStackTrace();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(InviteActivity.this,"拒绝申请失败",Toast.LENGTH_SHORT).show();
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
        initDate();
    }

    private void initView() {
        lv_invite = findViewById(R.id.lv_invite);
    }

    private void initDate() {
        //初始化Listview
        inviteAdapter = new InviteAdapter(this,mOninviteListener);
        lv_invite.setAdapter(inviteAdapter);

        //刷新
        refresh();

        //注册邀请信息变化的广播
        mLBM = LocalBroadcastManager.getInstance(this);
        mLBM.registerReceiver(InviteChangedReceiver,new IntentFilter(Constant.CONTACT_INVITE_CHANGED));
        mLBM.registerReceiver(InviteChangedReceiver,new IntentFilter(Constant.GROUP_INVITE_CHANGED));
    }

    private void refresh(){
        //获取数据库中的所有邀请信息
        List<InvitationInfo> invitations = Model.getInstance().getDbManager().getInviteTableDao().getInvitations();
        //刷新适配器
        inviteAdapter.refresh(invitations);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLBM.unregisterReceiver(InviteChangedReceiver);
    }
}
