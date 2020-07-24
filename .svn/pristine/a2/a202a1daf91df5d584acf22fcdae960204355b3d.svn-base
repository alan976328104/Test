package ac.drsi.nestor.sql;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_CollectFile;

public class SVDS_CollectFileSqlProvider {
	/**
	 * 添加收藏文件
	 * 
	 * @param collectFile
	 * @return
	 */
	public String insertCollectFile(final SVDS_CollectFile collectFile) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_collectFile");
				if (collectFile.getColId() == null) {
					VALUES("colId",
							"nvl((select max(colId) from SVDS_collectFile),0)+1");
				}
				if (collectFile.getColDate() != null) {
					VALUES("colDate", "#{colDate}");
				}
				if (collectFile.getFiles() != null) {
					VALUES("fileId", "#{files.fileId}");
				}
				if (collectFile.getFolder() != null) {
					VALUES("folderId", "#{folder.folderId}");
				}
			}
		}.toString();
	}

	/**
	 * 修改收藏文件
	 * 
	 * @param collectFile
	 * @return
	 */
	public String updateCollectFile(final SVDS_CollectFile collectFile) {
		return new SQL() {
			{
				UPDATE("SVDS_collectFile");
				if (collectFile.getColDate() != null) {
					SET("colDate = #{colDate}");
				}
				if (collectFile.getFiles() != null) {
					SET("fileId = #{files.fileId}");
				}
				if (collectFile.getFolder() != null) {
					SET("folderId=#{folder.folderId}");
				}
				WHERE("colId = #{colId}");
			}
		}.toString();
	}
}
