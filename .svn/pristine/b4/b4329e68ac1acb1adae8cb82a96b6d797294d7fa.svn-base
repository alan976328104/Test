package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import ac.drsi.nestor.entity.SVDS_InfoData;

@Mapper
public interface ExprotExcleDao {
	/**
	 * 将基本信息表插入数据库
	 * @param menuid
	 * @return
	 */
	@Select("select mark,name,value from svds_infodata where menuid = #{menuid} order by id ")
	public List<SVDS_InfoData> exprotExcelBymenuid(Integer menuid);
}
