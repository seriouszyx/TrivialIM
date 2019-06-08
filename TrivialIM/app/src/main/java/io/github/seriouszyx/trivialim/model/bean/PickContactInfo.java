package io.github.seriouszyx.trivialim.model.bean;

/**
 * 选择联系人的bean类
 */
public class PickContactInfo {

    private UserInfo user;
    private boolean isChecked;

    public PickContactInfo() {
    }

    public PickContactInfo(UserInfo user, boolean isChecked) {
        this.user = user;
        this.isChecked = isChecked;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
