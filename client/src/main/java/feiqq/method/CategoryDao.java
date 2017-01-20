package feiqq.method;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import feiqq.bean.Category;
import feiqq.util.Constants;
import feiqq.util.StringUtil;

public class CategoryDao extends BaseDao {

	/**
	 * 通过Id查询
	 * @param id 分组Id
	 * @return
	 */
	public Category getById(String id) {
		try {
			String sql = "select * from fqq_category where id = " + Integer.valueOf(id);
			ResultSet result = select(sql);
			if (null != result && result.next()) {
				return assembleCategory(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * getListByIdAndType: 根据所属者ID和type来获取list	<br/>
	 * @author SongFei
	 * @param ownerId  所属者ID
	 * @param type     类型（好友、群组）
	 * @return List	<br/>
	 * @since JDK 1.7
	 */
	public List<Category> getListByUIdAndType(String ownerId, String type) {
		List<Category> list = new ArrayList<Category>();
		try {
			String sql = "select * from fqq_category fc where fc.owner_id = "+ Integer.valueOf(ownerId);
			if (!StringUtil.isEmpty(type)) {
				sql += " and fc.category_type = '"+type+"' ";
			}
			ResultSet result = select(sql);
			while (null != result && result.next()) {
				Category category = assembleCategory(result);
				list.add(category);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * getByCondition: 通过所属者Id、分组类型、分组名称来查询	<br/>
	 * @author SongFei
	 * @param ownerId 所属者Id
	 * @param type 分组类型
	 * @param name 分组名称
	 * @return Category	<br/>
	 * @since JDK 1.7
	 */
	public Category getByCondition(String ownerId, String type, String name) {
		try {
			String sql = "select * from fqq_category fc where fc.owner_id = "+ 
					Integer.valueOf(ownerId) + " and fc.category_type = '"+type+"' and fc.name = '"+name+"' ";
			ResultSet result = select(sql);
			if (null != result && result.next()) {
				return assembleCategory(result);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取默认分组
	 * @param ownerId 所属者Id
	 * @param type 类型
	 * @return
	 */
	public Category getDefaultCate(String ownerId, String type) {
		return getByCondition(ownerId, type, Constants.DEFAULT_CATE);
	}
	
	/**
	 * saveCategory: 新增分组	<br/>
	 * @author SongFei
	 * @param ownerId 所属者Id
	 * @param type 分组类型
	 * @param name 分组名称
	 * @return	<br/>
	 * @since JDK 1.7
	 */
	public Category saveCategory(String ownerId, String type, String name) {
//		Category cate = getByCondition(ownerId, type, name);
//		if (null != cate) {
//			return cate;
//		}
		String sql = "insert into fqq_category values(zz_fqq_category.nextval,"
				+ "'"+name+"',"+Integer.valueOf(ownerId)+",'"+type+"') ";
		int num = operate(sql);
		if (num > 0) {
			return getRecentCate(ownerId, type);
		}
		return null;
	}
	
	/**
	 * 获取该用户最新创建的分组（刚新增的时候用到）
	 * @param ownerId 所属者Id
	 * @param type 分组类型
	 * @return Category
	 */
	private Category getRecentCate(String ownerId, String type) {
		try {
			String sql = "select max(fc.id) from fqq_category fc "
					+ "where fc.owner_id = "+Integer.valueOf(ownerId)+" and fc.category_type = '"+type+"' ";
			ResultSet result = select(sql);
			if (null != result && result.next()) {
				String maxId = String.valueOf(result.getInt(1));
				return getById(maxId);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改分组名称
	 * @param id 主键
	 * @param name 名称
	 * @return
	 */
	public Category editCategory(String id, String name) {
		String sql = "update fqq_category fc set fc.name = '"+name+"' where fc.id = " + Integer.valueOf(id);
		int num = operate(sql);
		if (num > 0) {
			return getById(id);
		}
		return null;
	}
	
	/**
	 * 删除分组
	 * @param id 分组Id
	 */
	public void deleteCate(String id) {
		String sql = "delete from fqq_category where id = " + Integer.valueOf(id);
		operate(sql);
	}
	
	private Category assembleCategory(ResultSet result) {
		try {
			String id = String.valueOf(result.getInt("id"));
			String cateName = result.getString("name");
			String cateOwnerId  = result.getString("owner_id");
			String cateType = result.getString("category_type");
			return new Category(id, cateName, cateOwnerId, cateType);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
