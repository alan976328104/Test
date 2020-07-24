package ac.drsi.nestor.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AliasDao {
	
	/**
	 * 获得最大的标签等级
	 * @return
	 */
	@Select("select max(lv) from svds_alias")
	public Integer getMaxlv();
	
	 
	
}
