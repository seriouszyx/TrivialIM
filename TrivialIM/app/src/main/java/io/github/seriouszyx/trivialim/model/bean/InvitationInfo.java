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


    }

}
