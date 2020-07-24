package ac.drsi.nestor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SolrConfileDao {
	
	@Select("select fileid from SVDS_files")
	public List<Integer> getAllfileid();
}
