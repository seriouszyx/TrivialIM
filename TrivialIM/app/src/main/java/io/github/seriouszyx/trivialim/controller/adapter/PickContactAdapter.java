package io.github.seriouszyx.trivialim.controller.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.github.seriouszyx.trivialim.R;
import io.github.seriouszyx.trivialim.model.bean.PickContactInfo;

/**
 * 选择联系人的适配器
 */
public class PickContactAdapter extends BaseAdapter {

    private Context mContext;
    private List<PickContactInfo> mPicks = new ArrayList<>();

    public PickContactAdapter(Context context, List<PickContactInfo> picks) {
        mContext = context;
        if (picks != null && picks.size() >= 0) {
            mPicks.clear();
            mPicks.addAll(picks);
        }
    }



    @Override
    public int getCount() {
        return mPicks == null ? 0 : mPicks.size();
    }

    @Override
    public Object getItem(int position) {
        return mPicks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = View.inflate(mContext, R.layout.item_pick, null);

            holder.cb = convertView.findViewById(R.id.cb_pick);
            holder.tv_name = convertView.findViewById(R.id.tv_pick_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PickContactInfo pickContactInfo = mPicks.get(position);

        holder.tv_name.setText(pickContactInfo.getUser().getName());
        holder.cb.setChecked(pickContactInfo.isChecked());

        return convertView;
    }

    // 获取选择的联系人
    public List<String> getPickContacts() {
        List<String> picks = new ArrayList<>();
        for (PickContactInfo pick : mPicks) {
            // 判断是否选中
            if (pick.isChecked() == true) {
                picks.add(pick.getUser().getName());
            }
        }
        return picks;
    }

    private class ViewHolder {
        private CheckBox cb;
        private TextView tv_name;
    }
}
