package feiqq.method;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import feiqq.bean.CateMember;

public class CateMemberDao extends BaseDao {

	/**
	 * getListByCateId: 根据分组Id查询下面的成员集合	<br/>
	 * @author SongFei
	 * @param cateId	分组Id
	 * @return List	<br/>
	 * @since JDK 1.7
	 */
	public List<CateMember> getListByCateId(String cateId) {
		List<CateMember> list = new ArrayList<CateMember>();
		try {
			String sql = "select * from fqq_category_member fcm where fcm.category_id = " + Integer.valueOf(cateId);
			ResultSet result = select(sql);
			while (null != result && result.next()) {
				String id = String.valueOf(result.getInt("id"));
				String cid = String.valueOf(result.getInt("category_id"));
				String oid = String.valueOf(result.getInt("owner_id"));
				String mid = String.valueOf(result.getInt("member_id"));
				CateMember cm = new CateMember(id, cid, oid, mid);
				list.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * getByCidAndMid: 根据分组Id和成员Id查询 <br/>
	 * @author SongFei
	 * @param cateId	分组Id
	 * @param memberId	成员Id
	 * @return CateMember	<br/>
	 * @since JDK 1.7
	 */
	public CateMember getByCidAndMid(String cateId, String memberId) {
		try {
			String sql = "select * from fqq_category_member fcm where fcm.category_id = "+
					Integer.valueOf(cateId)+" and fcm.member_id = " + Integer.valueOf(memberId);
			ResultSet result = select(sql);
			if (null != result && result.next()) {
				String id = String.valueOf(result.getInt("id"));
				String cid = String.valueOf(result.getInt("category_id"));
				String oid = String.valueOf(result.getInt("owner_id"));
				String mid = String.valueOf(result.getInt("member_id"));
				return new CateMember(id, cid, oid, mid);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * saveCateMember: 保存分组、成员关系	<br/>
	 * @author SongFei
	 * @param cateId	分组Id
	 * @param ownerId	所属者Id
	 * @param memberId	成员Id
	 * @return CateMember	<br/>
	 * @since JDK 1.7
	 */
	public CateMember saveCateMember(String cateId, String ownerId, String memberId) {
		CateMember cm = getByCidAndMid(cateId, memberId);
		if (null != cm) {
			return cm;
		}
		String sql = "insert into fqq_category_member values (zz_fqq_category_member.nextval, "+
				Integer.valueOf(cateId)+","+ Integer.valueOf(ownerId) +","+Integer.valueOf(memberId)+") ";
		int num = operate(sql);
		if (num > 0) {
			return getByCidAndMid(cateId, memberId);
		}
		return null;
	}

	/**
	 * 删除分组下的成员
	 * @param list 分组下的成员集合
	 */
	public void deleteByList(List<CateMember> list) {
		if (null != list && list.size() > 0) {
			for (CateMember member : list) {
				delete(member.getId());
			}
		}
	}
	
	/**
	 * 删除cateMember
	 * @param id 数据库主键
	 */
	public void delete(String id) {
		String sql = "delete from fqq_category_member where id = " + Integer.valueOf(id);
		operate(sql);
	}
	
	/**
	 * 删除cateMember
	 * @param cateId 分组Id
	 * @param memberId 成员Id
	 */
	public void deleteByCidAndMid(String cateId, String memberId) {
		String sql = "delete from fqq_category_member fcm where fcm.category_id = "+
				Integer.valueOf(cateId)+" and fcm.member_id = " + Integer.valueOf(memberId);
		operate(sql);
	}
	
	/**
	 * 删除cateMember
	 * @param ownerId 所属者Id
	 * @param memberId 成员Id
	 */
	public void deleteByOidAndMid(String ownerId, String memberId) {
		String sql = "delete from fqq_category_member fcm where fcm.owner_id = "+
				Integer.valueOf(ownerId)+" and fcm.member_id = " + Integer.valueOf(memberId);
		operate(sql);
	}
	
}
