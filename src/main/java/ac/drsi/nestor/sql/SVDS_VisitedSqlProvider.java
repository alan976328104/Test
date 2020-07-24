package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Visited;

public class SVDS_VisitedSqlProvider {
	/**
	 * 添加信息
	 * @param visited
	 * @return
	 */
	public String insertVisited(final SVDS_Visited visited) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_Visited");
				if (visited.getvId() == null) {
					VALUES("vId",
							"nvl((select max(vId) from SVDS_Visited),0)+1");
				}
				if (visited.getFile() != null) {
					VALUES("fileId", "#{file.fileId}");
				}
				if (visited.getCounts() != null) {
					VALUES("counts", "#{counts}");
				}
				if (visited.getLastTime() != null) {
					VALUES("lastTime", "#{lastTime}");
				}
				if (visited.getUser()!= null) {
					VALUES("userId", "#{user.userId}");
				}
			}
		}.toString();
	}
/**
 * 修改信息
 * @param Visited
 * @return
 */
	public String updateVisited(final SVDS_Visited Visited) {
		return new SQL() {
			{
				UPDATE("SVDS_Visited");
				if (Visited.getLastTime() != null) {
					SET("lastTime = #{lastTime}");
				}
				if (Visited.getCounts() != null) {
					SET("counts = #{counts}");
				}
				if (Visited.getUser() != null) {
					SET("userId = #{user.userId}");
				}
				if (Visited.getFile() != null) {
					SET("fileId = #{file.fileId}");
				}
				WHERE("vId = #{vId}");
			}
		}.toString();
	}
}
