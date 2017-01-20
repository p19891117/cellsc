package feiqq.method;

import java.sql.ResultSet;

import feiqq.bean.UserInfo;

public class UserInfoDao extends BaseDao {

	/**
	 * getByUserId  通过用户ID获取UserInfo	<br/>
	 * @author SongFei
	 * @param value  用户ID
	 * @return	<br/>
	 * @since JDK 1.7
	 */
	public UserInfo getByUserId(String value) {
		try {
			String sql = "select * from fqq_user_info fui where fui.user_id = " + Integer.valueOf(value);
			ResultSet result = select(sql);
			if (null != result && result.next()) {
				String id = String.valueOf(result.getInt("id"));
				String userId = String.valueOf(result.getInt("user_id"));
				String userAddress = result.getString("user_address");
				String userPort = String.valueOf(result.getInt("user_port"));
				String userStatus = String.valueOf(result.getInt("user_status"));
				String userRealStatus = String.valueOf(result.getInt("user_real_status"));
				return new UserInfo(id, userId, userAddress, userPort, userStatus, userRealStatus);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * saveUserInfo  保存UserInfo	<br/>
	 * @author SongFei
	 * @param id  用户ID	<br/>
	 * @param port  用户socket端口	<br/>
	 * @since JDK 1.7
	 */
	public UserInfo saveUserInfo(String id, String address, String port) {
		try {
			String sql = "insert into fqq_user_info values(zz_fqq_user_info.nextval, "+Integer.valueOf(id)+", '"
					+address+"', "+ Integer.valueOf(port)+", 0, 0)";
			operate(sql);
			return getByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * saveUserInfo  修改UserInfo	<br/>
	 * @author SongFei
	 * @param id  用户ID	<br/>
	 * @param port  用户socket端口	<br/>
	 * @since JDK 1.7
	 */
	public UserInfo updateUserInfo(String id, String address, String port) { // 暂不考虑状态
		try {
			String sql = "update fqq_user_info fui set fui.user_address = '"+address+"', fui.user_port = "
						+Integer.valueOf(port)+" where fui.user_id = " + Integer.valueOf(id);
			operate(sql);
			return getByUserId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public UserInfo saveOrUpdate(String id, String address, String port) {
		try {
			UserInfo userInfo = getByUserId(id);
			if (null == userInfo) {
				userInfo = saveUserInfo(id, address, port);
			} else {
				userInfo = updateUserInfo(id, address, port);
			}
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
