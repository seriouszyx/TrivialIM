package io.github.seriouszyx.push;

import android.text.TextUtils;

public class Presenter implements IPresenter {

    private IView mView;

    public Presenter(IView view) {
        mView = view;
    }

    @Override
    public void search() {
        String inputString = mView.getInputString();
        if (TextUtils.isEmpty(inputString)) {
            // 为空直接返回
            return;
        }

        int hashcode = inputString.hashCode();

        IUserService service = new UserService();
        String serviceResult = inputString + service.search(hashcode);
        String result = "Result:" + serviceResult;
        mView.setResultString(result);
    }
}
