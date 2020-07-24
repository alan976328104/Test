package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_Folder;

public class SVDS_FolderSqlProvider {
	/**
	 * 添加收藏夹
	 * @param folder
	 * @return
	 */
	public String insertFolder(final SVDS_Folder folder) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_folder");
				if (folder.getFolderId() == null) {
					VALUES("folderId",
							"nvl((select max(folderId) from SVDS_folder),0)+1");
				}
				if (folder.getFolderName() != null) {
					VALUES("folderName", "#{folderName}");
				}
				if (folder.getFolderDate() != null) {
					VALUES("folderDate", "#{folderDate}");
				}
				if(folder.getParentId()!=null){
					VALUES("parentId", "#{parentId}");
				}
				if (folder.getIcon() != null) {
					VALUES("icon", "#{icon}");
				}
				if (folder.getUser() != null) {
					VALUES("userId", "#{user.userId}");
				}
			}
		}.toString();
	}

	/**
	 * 修改收藏夹
	 * @param folder
	 * @return
	 */
	public String updateFolder(final SVDS_Folder folder) {
		return new SQL() {
			{
				UPDATE("SVDS_folder");
				if (folder.getFolderName() != null) {
					SET("folderName = #{folderName}");
				}
				if (folder.getFolderDate() != null) {
					SET("folderDate = #{folderDate}");
				}
				if (folder.getParentId() != null) {
					SET("parentId=#{parentId}");
				}
				if (folder.getUser() != null) {
					SET("userId = #{user.userId}");
				}
				WHERE("folderId = #{folderId}");
			}
		}.toString();
	}
}
