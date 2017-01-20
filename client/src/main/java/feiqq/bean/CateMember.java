package feiqq.bean;

public class CateMember {

	private String id;
	/** 分组ID */
	private String cateId;
	/** 所属者ID */
	private String ownerId;
	/** 成员ID */
	private String memberId;

	public CateMember() {
		// TODO Auto-generated constructor stub
	}

	public CateMember(String id, String cateId, String ownerId, String memberId) {
		this.id = id;
		this.cateId = cateId;
		this.ownerId = ownerId;
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCateId() {
		return cateId;
	}

	public void setCateId(String cateId) {
		this.cateId = cateId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
