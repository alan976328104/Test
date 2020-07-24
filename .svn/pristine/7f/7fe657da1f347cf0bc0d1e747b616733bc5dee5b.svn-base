package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Message;

public class SVDS_MessageSqlProvider {
	/**
	 * 添加消息
	 * @param message
	 * @return
	 */
	public String insertMessage(final SVDS_Message message) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_message");
				if (message.getMessageId() == null) {
					VALUES("messageId",
							"nvl((select max(messageId) from SVDS_message),0)+1");
				}
				if (message.getMessageDate() != null) {
					VALUES("messageDate", "#{messageDate}");
				}
				if (message.getFiles() != null) {
					VALUES("fileId", "#{files.fileId}");
				}
				if (message.getReceived() != null) {
					VALUES("received", "#{received.userId}");
				}
				if (message.getSharer() != null) {
					VALUES("sharer", "#{sharer.userId}");
				}
				if (message.getState() != null) {
					VALUES("state", "#{state}");
				}
			}
		}.toString();
	}

	/**
	 * 修改消息
	 * @param message
	 * @return
	 */
	public String updateMessage(final SVDS_Message message) {
		return new SQL() {
			{
				UPDATE("SVDS_message");
				if (message.getMessageDate() != null) {
					SET("messageDate = #{messageDate}");
				}
				if (message.getFiles() != null) {
					SET("fileId = #{files.fileId}");
				}
				if (message.getReceived() != null) {
					SET("received = #{received.userId}");
				}
				if (message.getSharer() != null) {
					SET("sharer = #{sharer.userId}");
				}
				if (message.getState() != null) {
					SET("state = #{state}");
				}
				WHERE("messageId = #{messageId}");
			}
		}.toString();
	}
	/**
	 * 条件查询消息
	 * @param file
	 * @param userId
	 * @param message
	 * @return
	 */
	public String selectMessageSql(final SVDS_Files file,final Integer userId,final SVDS_Message message){
		StringBuffer sql = new StringBuffer("select * from SVDS_message where ");
		if(userId!=null){
			sql.append(" sharer =#{userId} ");
		}
		if(file!=null){
			sql.append(" and fileId in( select fileId from svds_files where 1=1 ");
			if(file.getFileName()!=null){
				sql.append(" and  fileName like '%' || #{file.fileName} || '%' ");
			}
			if(file.getFormat()!=null){
				sql.append(" and format = #{file.format} ");
			}
			if(file.getSecurity()!=null){
				sql.append(" and securityId = #{file.security.securityId} ");
			}
			sql.append(" ) ");
		}
		if(message.getMessageDate()!=null){
			sql.append(" and messageDate like #{message.messageDate} || '%' ");
		}
		 sql.append(" order by messageDate desc");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
