package feiqq.method;

import java.sql.ResultSet;
import java.sql.SQLException;

import feiqq.bean.User;
import feiqq.util.StringUtil;

public class UserDao extends BaseDao {

	/**
	 * login: 登陆   <br/>
	 * @author SongFei
	 * @param name	账号
	 * @param pass	密码
	 * @return	User <br/>
	 * @since JDK 1.7
	 */
	public User login(String name, String pass) {
		String sql = "select * from fqq_user fu where fu.user_name = "+
				Integer.valueOf(name)+" and fu.user_password = '"+pass+"'";
		ResultSet result = select(sql);
		return assembleUser(result);
	}
	
	/**
	 * saveUser: 保存用户信息 <br/>
	 * @author SongFei
	 * @param nick	昵称
	 * @param name	账号
	 * @param pass	密码
	 * @param sign	签名
	 * @return User	<br/>
	 * @since JDK 1.7
	 */
	public User saveUser(String nick, String name, String pass, String sign) {
		String sql = "insert into fqq_user values(zz_fqq_user.nextval,'"+nick+"',"+name+",'"+pass+"' ";
		if (!StringUtil.isEmpty(sign)) {
			sql += " ,'"+sign+"' ";
		}
		sql += ")";
		int num =  operate(sql);
		if (num > 0) {
			return getByUserName(name);
		}
		return null;
	}
	
	/**
	 * getById: 通过ID查询	<br/>
	 * @author SongFei
	 * @param id	用户ID（数据库记录ID）
	 * @return User	<br/>
	 * @since JDK 1.7
	 */
	public User getById(String id) {
		String sql = "select * from fqq_user fu where fu.id = " + Integer.valueOf(id);
		ResultSet result = select(sql);
		return assembleUser(result);
	}
	
	/**
	 * getByUserName: 通过账号查询 	<br/>
	 * @author SongFei
	 * @param value	账号
	 * @return User	<br/>
	 * @since JDK 1.7
	 */
	public User getByUserName(String value) {
		String sql = "select * from fqq_user fu where fu.user_name = " + Integer.valueOf(value);
		ResultSet result = select(sql);
		return assembleUser(result);
	}
	
	/**
	 * getByNickName: 通过昵称查询	<br/>
	 * @author SongFei
	 * @param value	昵称
	 * @return User	<br/>
	 * @since JDK 1.7
	 */
	public User getByNickName(String value) {
		String sql = "select * from fqq_user fu where fu.nick_name = '"+value+"' ";
		ResultSet result = select(sql);
		return assembleUser(result);
	}
	
	/**
	 * getByAccountOrNickName: 通过账号或昵称查询	<br/>
	 * @author SongFei
	 * @param value	账号或昵称
	 * @return User	<br/>
	 * @since JDK 1.7
	 */
	public User getByAccountOrNickName(String value) {
		String sql = "select * from fqq_user fu where fu.user_name = " +
				Integer.valueOf(value)+ " or fu.nick_name = '"+value+"' ";
		ResultSet result = select(sql);
		return assembleUser(result);
	}
	
	/**
	 * 删除用户
	 * @param id 用户Id
	 */
	public int deleteUser(String id) {
		String sql = "delete from fqq_user where id = " + Integer.valueOf(id);
		return operate(sql);
	}
	
	// 组装User对象
	private User assembleUser(ResultSet result) {
		try {
			if (null != result && result.next()) {
				String id = String.valueOf(result.getInt("id"));
				String userName = String.valueOf(result.getInt("user_name"));
				String nickName = result.getString("nick_name");
				String signature = result.getString("user_signature");
				return new User(id, userName, nickName, signature);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
