package io.github.seriouszyx.trivialim.model.bean;

// 邀请信息bean类
public class InvitationInfo {

    private UserInfo user;  // 联系人
    private GroupInfo group;   // 群组
    private String reason;  // 邀请原因
    private InvitationStatus status;    // 邀请状态

    public InvitationInfo() {
    }

    public InvitationInfo(UserInfo user, GroupInfo group, String reason, InvitationStatus status) {
        this.user = user;
        this.group = group;
        this.reason = reason;
        this.status = status;
    }

    @Override
    public String toString() {
        return "InvitationInfo{" +
                "user=" + user +
                ", group=" + group +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }

    public enum InvitationStatus {
        // 联系人邀请信息状态
        NEW_INVITE, // 新邀请
        INVITE_ACCEPT,  // 接受邀请
        INVITE_ACCEPT_BY_PEER,  // 邀请被接受

        // -- 以下是群组邀请信息状态 --
        NEW_GROUP_INVITE,
        NEW_GROUP_APPLICATION,
        GROUP_INVITE_ACCEPTED,
        GROUP_APPLICATION_ACCEPTED,
        GROUP_INVITE_DECLINED,
        GROUP_APPLICATION_DECLINED,
        GROUP_ACCEPT_INVITE,
        GROUP_ACCEPT_APPLICATION,
        GROUP_REJECT_APPLICATION,
        GROUP_REJECT_INVITE

    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public GroupInfo getGroup() {
        return group;
    }

    public void setGroup(GroupInfo group) {
        this.group = group;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }
}
