package feiqq.bean;

public class GroupUser {

	private String id;
	/** 群组ID */
	private String groupId;
	/** 成员ID */
	private String userId;

	public GroupUser(String id, String groupId, String userId) {
		this.id = id;
		this.groupId = groupId;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
