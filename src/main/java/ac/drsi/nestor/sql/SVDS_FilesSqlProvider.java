package ac.drsi.nestor.sql;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import ac.drsi.nestor.entity.SVDS_CollectFile;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SearchBean;

public class SVDS_FilesSqlProvider {
	/**
	 * 添加文件
	 * 
	 * @param file
	 * @return
	 */
	public String insertFiles(final SVDS_Files file) {
		return new SQL() {
			{
				INSERT_INTO("SVDS_files");
				if (file.getFileId() == null) {
					VALUES("fileId",
							"nvl((select max(fileId) from SVDS_files),0)+1");
				}
				if (file.getFileName() != null) {
					VALUES("fileName", "#{fileName}");
				}
				if (file.getFileUrl() != null) {
					VALUES("fileUrl", "#{fileUrl}");
				}
				if (file.getFileDate() != null) {
					VALUES("fileDate", "#{fileDate}");
				}
				if (file.getFileVersion() != null) {
					VALUES("fileVersion", "#{fileVersion}");
				}
				if (file.getState() != null) {
					VALUES("state", "#{state}");
				}
				if (file.getSecurity() != null) {
					VALUES("securityId", "#{security.securityId}");
				} else {
					VALUES("securityId", "1");
				}
				if (file.getUser() != null) {
					VALUES("userId", "#{user.userId}");
				}
				if (file.getTabsId() != null) {
					VALUES("tabsId", "#{tabsId}");
				}
				if (file.getMenu() != null) {
					VALUES("menuId", "#{menu.id}");
				}
				if (file.getFormat() != null) {
					VALUES("format", "#{format}");
				}
				if (file.getpId() != null) {
					VALUES("pId", "#{pId}");
				}
//				if (file.getMajor() != null) {
//					VALUES("majorId", "#{major.majorId}");
//				}
				if (file.getLocation() != null) {
					VALUES("location", "#{location}");
				}
				if (file.getAbstracts() != null) {
					VALUES("abstracts", "#{abstracts}");
				}
				if (file.getFileSize() != null) {
					VALUES("fileSize", "#{fileSize}");
				}
			}
		}.toString();
	}

	/**
	 * 更新文件
	 * 
	 * @param file
	 * @return
	 */
	public String updateFiles(final SVDS_Files file) {
		return new SQL() {
			{
				UPDATE("SVDS_files");
				if (file.getFileName() != null) {
					SET("fileName = #{fileName}");
				}
				if (file.getFileUrl() != null) {
					SET("fileUrl = #{fileUrl}");
				}
				if (file.getFileDate() != null) {
					SET("fileDate = #{fileDate}");
				}
				if (file.getFileVersion() != null) {
					SET("fileVersion= #{fileVersion}");
				}
				if (file.getSecurity() != null) {
					SET("securityId = #{security.securityId}");
				}
				if (file.getUser() != null) {
					SET("userId = #{user.userId}");
				}
				if (file.getTabsId() != null) {
					SET("tabsId=#{tabsId}");
				}
				if (file.getMenu() != null) {
					SET("menuId=#{menu.id}");
				}
				if (file.getpId() != null) {
					SET("pId=#{pId}");
				}
				if (file.getFormat() != null) {
					SET("format=#{format}");
				}
//				if (file.getMajor() != null) {
//					SET("majorId=#{major.majorId}");
//				}
				if (file.getLocation() != null) {
					SET("location=#{location}");
				}
				if (file.getAbstracts() != null) {
					SET("abstracts=#{abstracts}");
				}
				if (file.getFileSize() != null) {
					SET("fileSize=#{fileSize}");
				}
				WHERE("fileId = #{fileId}");
			}
		}.toString();
	}

	/**
	 * 多条件查询
	 * 
	 * @param file
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String selectWhitFilesSql(final SVDS_Files file,
			final Map<String, Object> param) {
		StringBuffer sql = new StringBuffer("select * from SVDS_files where ");
		List<Integer> idList = (List<Integer>) param.get("fileIds");
		if (file.getFileName() != null) {
			if (sql.toString().endsWith("where ")) {
				if (param.get("fileName").equals("0")) {
					sql.append("  ( fileName like '%' || #{file.fileName} || '%' ) and ");
				} else if (param.get("fileName").equals("1")) {
					sql.append(" ( fileName like '%' || #{file.fileName} || '%' ) or ");
				} else {
					sql.append("( fileName like '%' || #{file.fileName} || '%' )");
				}
			} else {
				if (param.get("fileName").equals("0")) {
					sql.append(" and ( fileName like '%' || #{file.fileName} || '%' )  ");
				} else if (param.get("fileName").equals("1")) {
					sql.append(" or ( fileName like '%' || #{file.fileName} || '%' ) ");
				} else {
					sql.append("( fileName like '%' || #{file.fileName} || '%'  )");
				}
			}
		}
		if (file.getFileDate() != null) {
			if (sql.toString().endsWith("where ")) {
				if (param.get("fileDate").equals("0")) {
					sql.append(" fileDate like #{file.fileDate} || '%' and ");
				} else if (param.get("fileDate").equals("0")) {
					sql.append(" fileDate like #{file.fileDate} || '%' or ");
				} else {
					sql.append(" fileDate like #{file.fileDate} || '%' ");
				}
			} else {
				if (param.get("fileDate").equals("0")) {
					sql.append(" and fileDate like #{file.fileDate} || '%' ");
				} else if (param.get("fileDate").equals("0")) {
					sql.append(" or fileDate like #{file.fileDate} || '%' ");
				} else {
					sql.append(" fileDate like #{file.fileDate} || '%' ");
				}
			}
		}
		if (file.getFormat() != null) {
			if (sql.toString().endsWith("where ")) {
				if (param.get("format").equals("0")) {
					sql.append(" format = #{file.format} and ");
				} else if (param.get("format").equals("1")) {
					sql.append(" format = #{file.format} or ");
				} else {
					sql.append(" format = #{file.format} ");
				}
			} else {
				if (param.get("format").equals("0")) {
					sql.append(" and format = #{file.format} ");
				} else if (param.get("format").equals("1")) {
					sql.append(" or format = #{file.format} ");
				} else {
					sql.append(" format = #{file.format} ");
				}
			}
		}
		if (file.getSecurity() != null) {
			if (sql.toString().endsWith("where ")) {
				if (param.get("securityId").equals("0")) {
					sql.append("  securityId = #{file.security.securityId} and ");
				} else if (param.get("securityId").equals("1")) {
					sql.append("  securityId = #{file.security.securityId} or ");
				} else {
					sql.append(" securityId = #{file.security.securityId} ");
				}
			} else {
				if (param.get("securityId").equals("0")) {
					sql.append(" and securityId = #{file.security.securityId} ");
				} else if (param.get("securityId").equals("1")) {
					sql.append(" or securityId = #{file.security.securityId} ");
				} else {
					sql.append(" securityId = #{file.security.securityId} ");
				}
			}

		}
		if (file.getMenu() != null) {
			if (sql.toString().endsWith("where ")) {
				if (param.get("item") != null) {
					if (param.get("item").equals("0")) {
						sql.append("  menuId = #{file.menu.id} and ");
					} else if (param.get("item").equals("1")) {
						sql.append("  menuId = #{file.menu.id} or ");
					} else {
						sql.append(" menuId = #{file.menu.id} ");
					}
				}
				if (param.get("basic") != null) {
					if (param.get("basic").equals("0")) {
						sql.append("  menuId = #{file.menu.id} and ");
					} else if (param.get("basic").equals("1")) {
						sql.append("  menuId = #{file.menu.id} or ");
					} else {
						sql.append(" menuId = #{file.menu.id} ");
					}
				}
			} else {
				if (param.get("item") != null) {
					if (param.get("item").equals("0")) {
						sql.append(" and menuId = #{file.menu.id} ");
					} else if (param.get("item").equals("1")) {
						sql.append(" or menuId = #{file.menu.id} ");
					} else {
						sql.append(" menuId = #{file.menu.id} ");
					}
				}
				if (param.get("basic") != null) {
					if (param.get("basic").equals("0")) {
						sql.append(" and menuId = #{file.menu.id} ");
					} else if (param.get("basic").equals("1")) {
						sql.append(" or menuId = #{file.menu.id} ");
					} else {
						sql.append(" menuId = #{file.menu.id} ");
					}
				}
			}

		}
		if (param.get("fileIds") != null) {
			sql.append(" and fileId in (");
			for (Integer id : idList) {
				if (idList.indexOf(id) > 0)
					sql.append(",");

				sql.append("'").append(id).append("'");
			}
			sql.append(") ");
		}
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
			sql.append(" state=0");
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
			sql.append(" and state=0");
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
			sql.append(" and state=0");
		} else {
			sql.append(" and state=0");
		}

		System.out.println("***************sql语句**************");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 多条件查询
	 * 
	 * @param map
	 * @return
	 */
	public String selectFilesSql(final Map map) {
		StringBuilder sql = new StringBuilder("select * from SVDS_files where ");
		@SuppressWarnings("unchecked")
		List<SearchBean> bean = (List<SearchBean>) map.get("list");
		MessageFormat mf = new MessageFormat("#'{'list[{0}].val}");
		for (int i = 0; i < bean.size(); i++) {
			if (bean.get(i).getName().equals("fileName")) {
				String str = " ( fileName like '%' ||"
						+ mf.format(new Object[] { i }) + "|| '%' )";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				} else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				}
			}
			if (bean.get(i).getName().equals("fileDate")) {
				String str = " ( fileDate like "
						+ mf.format(new Object[] { i }) + " || '%' )";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				} else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(" and ");
						sql.append(str);
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}
			}
			if (bean.get(i).getName().equals("format")) {
				String str = " ( format = upper( " + mf.format(new Object[] { i })
						+ " ) or format =  lower("+mf.format(new Object[] { i })+") )";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				}else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				} else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}
			}
			if (bean.get(i).getName().equals("securityId")) {
				String str = "  securityId = " + mf.format(new Object[] { i })
						+ " ";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				} else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				}
			}
			if (bean.get(i).getName().equals("item")) {
				String str = "  menuId = " + mf.format(new Object[] { i })
						+ " ";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				} else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				}
			}
			if (bean.get(i).getName().equals("basic")) {
				String str = "  menuId = " + mf.format(new Object[] { i })
						+ " ";
				if (sql.toString().endsWith("where ")) {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(str);
						sql.append(" or ");
					} else {
						sql.append(str);
					}
				} else if(sql.toString().endsWith(" and ")){
					if (bean.get(i).getWith().equals("0")) {
						sql.append(str);
						sql.append(" and ");
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}else {
					if (bean.get(i).getWith().equals("0")) {
						sql.append(" and ");
						sql.append(str);
					} else if (bean.get(i).getWith().equals("1")) {
						sql.append(" or ");
						sql.append(str);
					} else {
						sql.append(str);
					}
				}
			}
		}
		/*
		 * if (file.getFileDate() != null) { if
		 * (sql.toString().endsWith("where ")) { if
		 * (param.get("fileDate").equals("0")) {
		 * sql.append(" fileDate like #{file.fileDate} || '%' and "); } else if
		 * (param.get("fileDate").equals("0")) {
		 * sql.append(" fileDate like #{file.fileDate} || '%' or "); } else {
		 * sql.append(" fileDate like #{file.fileDate} || '%' "); } } else { if
		 * (param.get("fileDate").equals("0")) {
		 * sql.append(" and fileDate like #{file.fileDate} || '%' "); } else if
		 * (param.get("fileDate").equals("0")) {
		 * sql.append(" or fileDate like #{file.fileDate} || '%' "); } else {
		 * sql.append(" fileDate like #{file.fileDate} || '%' "); } } }
		 * 
		 * if (file.getMenu() != null) { if (sql.toString().endsWith("where "))
		 * { if (param.get("item") != null) { if (param.get("item").equals("0"))
		 * { sql.append("  menuId = #{file.menu.id} and "); } else if
		 * (param.get("item").equals("1")) {
		 * sql.append("  menuId = #{file.menu.id} or "); } else {
		 * sql.append(" menuId = #{file.menu.id} "); } } if (param.get("basic")
		 * != null) { if (param.get("basic").equals("0")) {
		 * sql.append("  menuId = #{file.menu.id} and "); } else if
		 * (param.get("basic").equals("1")) {
		 * sql.append("  menuId = #{file.menu.id} or "); } else {
		 * sql.append(" menuId = #{file.menu.id} "); } } } else { if
		 * (param.get("item") != null) { if (param.get("item").equals("0")) {
		 * sql.append(" and menuId = #{file.menu.id} "); } else if
		 * (param.get("item").equals("1")) {
		 * sql.append(" or menuId = #{file.menu.id} "); } else {
		 * sql.append(" menuId = #{file.menu.id} "); } } if (param.get("basic")
		 * != null) { if (param.get("basic").equals("0")) {
		 * sql.append(" and menuId = #{file.menu.id} "); } else if
		 * (param.get("basic").equals("1")) {
		 * sql.append(" or menuId = #{file.menu.id} "); } else {
		 * sql.append(" menuId = #{file.menu.id} "); } } }
		 * 
		 * }
		 */
		if (sql.toString().endsWith("where ")) {
			sql.delete(sql.lastIndexOf("where "), sql.length());
			sql.append(" state=0");
		} else if (sql.toString().endsWith("and ")) {
			sql.delete(sql.lastIndexOf(" and "), sql.length());
			sql.append(" and state=0");
		} else if (sql.toString().endsWith("or ")) {
			sql.delete(sql.lastIndexOf("or "), sql.length());
			sql.append(" and state=0");
		} else {
			sql.append(" and state=0");
		}

		System.out.println("***************sql语句**************");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 多条件查询收藏文件
	 * 
	 * @param file
	 * @param folderId
	 * @param collectFile
	 * @return
	 */
	public String selectCollectSql(final SVDS_Files file,
			final Integer folderId, final SVDS_CollectFile collectFile) {
		StringBuffer sql = new StringBuffer(
				"select * from svds_collectfile where 1=1 ");
		if (folderId != null) {
			sql.append(" and folderId=#{folderId} ");
		}
		if (file != null) {
			sql.append(" and fileId in (select fileId from SVDS_files where 1=1 ");
			if (file.getFileName() != null) {
				sql.append(" and  fileName like '%' || #{file.fileName} || '%' ");
			}
			if (file.getFormat() != null) {
				sql.append(" and format = #{file.format} ");
			}
			if (file.getSecurity() != null) {
				sql.append(" and securityId = #{file.security.securityId} ");
			}
			sql.append(" ) ");
		}
		if (collectFile.getColDate() != null) {
			sql.append(" and colDate like #{collectFile.colDate} || '%' ");
		}
		sql.append(" order by colDate desc");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 根据文件Id集合排序查询
	 * 
	 * @param fileIds
	 * @param sortOrder
	 * @return
	 */
	public String selectCollectSortSql(List<Integer> fileIds, String sortOrder) {
		StringBuffer sql = new StringBuffer("select * from SVDS_files where ");
		if (fileIds.size() > 0) {
			sql.append("  fileId in (");
			for (Integer i = 0; i < fileIds.size(); i++) {
				System.out.println(fileIds.get(i));
				if (i == fileIds.size() - 1) {
					sql.append(fileIds.get(i));
				} else {
					sql.append(fileIds.get(i));
					sql.append(",");
				}

			}
			sql.append(")");
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
}
