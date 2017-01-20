package feiqq.bean;

/**
 * Description: 用户基本信息	<br/>  
 * Date: 2014年11月23日 下午1:41:01    <br/>
 * @author   SongFei
 * @version  
 * @since    JDK 1.7
 * @see
 */
public class User {

	/** 用户ID */
	private String id;
	/** 用户昵称 */
	private String nickName;
	/** 用户账号 */
	private String userName;
	/** 用户签名 */
	private String signature;

	public User() {
		
	}
	
	public User(String id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	public User(String userName, String nickName, String signature) {
		this.nickName = nickName;
		this.userName = userName;
		this.signature = signature;
	}
	
	public User(String id, String userName, String nickName, String signature) {
		this.id = id;
		this.nickName = nickName;
		this.userName = userName;
		this.signature = signature;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
