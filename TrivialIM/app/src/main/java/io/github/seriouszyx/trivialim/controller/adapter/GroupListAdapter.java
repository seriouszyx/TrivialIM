package io.github.seriouszyx.trivialim.controller.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.seriouszyx.trivialim.R;

/**
 * 群组列表适配器
 */
public class GroupListAdapter extends BaseAdapter {

    private Context mContext;
    private List<EMGroup> mGroups = new ArrayList<>();

    public GroupListAdapter(Context context) {
        mContext = context;
    }

    // 刷新
    public void refresh(List<EMGroup> groups) {
        if (groups != null && groups.size() >= 0) {
            mGroups.clear();
            mGroups.addAll(groups);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mGroups == null ? 0 : mGroups.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null ) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.item_grouplist, null);
            holder.name = convertView.findViewById(R.id.tv_grouplist_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EMGroup emGroup = mGroups.get(position);

        holder.name.setText(emGroup.getGroupName());

        return convertView;
    }

    private class ViewHolder {
        TextView name;
    }
}
