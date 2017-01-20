package feiqq.bean;

/**
 * Description: 用户IP、状态信息 <br/>
 * Date: 2014年11月23日 下午1:41:19 <br/>
 * 
 * @author SongFei
 * @version
 * @since JDK 1.7
 * @see
 */
public class UserInfo {

	/** ID */
	private String id;
	/** 用户ID */
	private String userId;
	/** 用户IP */
	private String userAddress;
	/** 用户端口 */
	private String userPort;
	/** 展示状态 */
	private String userStatus;
	/** 真实状态 */
	private String userRealStatus;

	public UserInfo() {

	}
	
	public UserInfo(String userId, String userAddress, String userPort) {
		this.userId = userId;
		this.userAddress = userAddress;
		this.userPort = userPort;
	}
	
	public UserInfo(String userId, String userAddress, String userPort,
			String userStatus, String userRealStatus) {
		this.userId = userId;
		this.userAddress = userAddress;
		this.userPort = userPort;
		this.userStatus = userStatus;
		this.userRealStatus = userRealStatus;
	}

	public UserInfo(String id, String userId, String userAddress, String userPort,
			String userStatus, String userRealStatus) {
		this.id = id;
		this.userId = userId;
		this.userAddress = userAddress;
		this.userPort = userPort;
		this.userStatus = userStatus;
		this.userRealStatus = userRealStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserPort() {
		return userPort;
	}

	public void setUserPort(String userPort) {
		this.userPort = userPort;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserRealStatus() {
		return userRealStatus;
	}

	public void setUserRealStatus(String userRealStatus) {
		this.userRealStatus = userRealStatus;
	}

}
