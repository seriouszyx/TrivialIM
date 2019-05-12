package io.github.seriouszyx.trivialim.controller.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import io.github.seriouszyx.trivialim.R;

// 联系人列表页面
public class ContactListFragment extends EaseContactListFragment {

    @Override
    protected void initView() {
        super.initView();
        // 布局显示加号
        titleBar.setRightImageResource(R.drawable.em_add);

        // 头布局添加
        View headerView = View.inflate(getActivity(), R.layout.header_fragment_contact, null);

        listView.addHeaderView(headerView);
    }
}
