package io.github.seriouszyx.trivialim.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.model.bean.InvitationInfo;
import io.github.seriouszyx.trivialim.model.bean.UserInfo;

/**
 * 邀请信息列表页面的适配器
 */
public class InviteAdapter extends BaseAdapter {

    private Context mContext;

    private List<InvitationInfo> mInvitationInfos = new ArrayList<>();

    private OnInviteListener mOnInviteListener;

    public InviteAdapter(Context context, OnInviteListener onInviteListener) {
        mContext = context;
        mOnInviteListener = onInviteListener;
    }

    // 刷新数据的方法
    public void refresh(List<InvitationInfo> invitationInfos) {
        if (invitationInfos != null && invitationInfos.size() >= 0) {
            mInvitationInfos.clear();

            mInvitationInfos.addAll(invitationInfos);

            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mInvitationInfos == null ? 0 : mInvitationInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mInvitationInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1 获取或创建一个ViewHolder
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_invite, null);

            holder.name = convertView.findViewById(R.id.tv_invite_name);
            holder.reason = convertView.findViewById(R.id.tv_invite_reason);
            holder.accept = convertView.findViewById(R.id.bt_invite_accept);
            holder.reject = convertView.findViewById(R.id.bt_invite_reject);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // 2 获取当前item数据
        final InvitationInfo invitationInfo = mInvitationInfos.get(position);

        // 3 显示当前Item数据
        UserInfo user = invitationInfo.getUser();

        if (user != null) {
            // 当前时联系人
            holder.name.setText(invitationInfo.getUser().getName());

            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
            if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.NEW_INVITE) {
                // 新邀请
                if (invitationInfo.getReason() == null) {
                    holder.reason.setText("添加好友");
                } else {
                    holder.reason.setText(invitationInfo.getReason());
                }

                holder.accept.setVisibility(View.VISIBLE);
                holder.reject.setVisibility(View.VISIBLE);

            } else if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT) {
                // 接受邀请
                if (invitationInfo.getReason() == null) {
                    holder.reason.setText("接受邀请");
                } else {
                    holder.reason.setText(invitationInfo.getReason());
                }
            } else if (invitationInfo.getStatus() == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER) {
                // 邀请被接受
                if (invitationInfo.getReason() == null) {
                    holder.reason.setText("邀请被接受");
                } else {
                    holder.reason.setText(invitationInfo.getReason());
                }
            }

            // 按钮的处理
            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onAccept(invitationInfo);
                }
            });

            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnInviteListener.onReject(invitationInfo);
                }
            });

        } else {
            // 群组

        }

        // 4 返回View
        return convertView;
    }

    private class ViewHolder {
        private TextView name;
        private TextView reason;

        private Button accept;
        private Button reject;
    }

    public interface OnInviteListener {
        // 联系人接受按钮的点击事件
        void onAccept(InvitationInfo invitationInfo);
        // 联系人拒绝按钮的点击事件
        void onReject(InvitationInfo invitationInfo);
    }
}
