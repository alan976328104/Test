package ac.drsi.nestor.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ac.drsi.common.ExUtils;
import ac.drsi.nestor.dao.RelationDao;
import ac.drsi.nestor.dao.SVDS_FilesDao;
import ac.drsi.nestor.entity.Phenomenon;
import ac.drsi.nestor.entity.Rule;
import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.entity.SVDS_Relation;
import ac.drsi.nestor.entity.SVDS_Rule;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.entity.Tabs;
import ac.drsi.nestor.service.SVDS_SessionService;

/**
 * 2019年3月13日 曹泽凯
 * 用于关联检索增删改查
 */
@RestController
public class RelationController {
	@Autowired
	SVDS_SessionService service;
	@Autowired
	RelationDao dao;
	@Autowired
	SVDS_FilesDao filesDao;

	/**
	 * 2019年3月13日 曹泽凯
	 * 添加一个关联检索
	 * 
	 * @param rcontent 关联检索内容
	 * @param relationname 关联检索名称
	 * @param menuid 关联检索应用的项目
	 * @return
	 */
	@RequestMapping(value = "/addRelation", method = RequestMethod.POST)
	public Integer addRelation(String rcontent[], String relationname,
			String menuid) {
		try {
			dao.addRelation(relationname, menuid,
					dao.getMenename(Integer.parseInt(menuid)));// 添加关联检索

			for (int i = 0; i < rcontent.length; i++) {
				// 添加关联检索具体内容
				dao.addrule(rcontent[i], rcontent[i], i,
						dao.findRelationidbyname(relationname),
						Integer.parseInt(menuid));
			}

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 查询关联检索名称是否重复
	 * 
	 * @param name 名称
	 * @return
	 */
	@RequestMapping(value = "/Relationishavaname", method = RequestMethod.GET)
	public Integer ishavaname(String name) {

		return dao.ishavaname(name);
	}

	/**
	 * 2019年3月13日 曹泽凯
	 * 获得关联检索列表
	 * @param request http请求
	 * @return
	 */
	@RequestMapping(value = "/findallrelation", method = RequestMethod.GET)
	public List<SVDS_Relation> findall(HttpServletRequest request) {
		SVDS_User loginUser = service.getSessionByIp(request);
		return dao.findallrelation(loginUser.getUserId());
	}

	/**
	 * 2019年3月13日 曹泽凯
	 * 删除关联检索
	 * @param id 关联检索id
	 * @return
	 */
	@RequestMapping(value = "/delrelation", method = RequestMethod.GET)
	public Integer delrelation(Integer id) {
		try {
			dao.delRule(id);
			dao.delRelation(id);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	@RequestMapping(value = "/getXstlcell", method = RequestMethod.GET)
	public String getXstlcell(Integer fileid) {
		String result = dao.getXstlcell(dao.findMenuidByFileid(fileid), fileid,
				"%" + fileid + "%");
		if (result == null) {
			return "null";
		} else {
			try {
				// String arr[] = result.split("\\$");
				// System.out.println(arr[0]);
				return result.split("\\$")[0];
			} catch (Exception e) {
				return "null";
			}
		}

	}

	/**
	 * 关联规则算法 
	 * 2019年3月13日 曹泽凯
	 * @param name 关联规则名称
	 * @param fileid 文件id
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getRelationSearch", method = RequestMethod.GET)
	public List<SVDS_Files> getRelationSearch(String name, Integer fileid)
			throws IOException {
		List<SVDS_Files> data = new ArrayList<>();// 创建一个返回值
		Integer Relationid = dao.getRelationByid(
				dao.findMenuidByFileid(fileid), fileid, "%" + fileid + "%");// 获得关联规则id
		Integer count = dao.getRuleCount(Relationid);// 获得关联规则下关联内容的数量
		List<String> names = new ArrayList<String>();// 万一关联内容中存在1对多的情况
		names.add(name);// 将初始点击的内容存在所有查找名称追踪
		for (int i = 1; i < count; i++) {
			String rulename = dao.getRuleName(dao.findMenuidByFileid(fileid),
					Relationid, i); // 循环rule循环内容表并获得 id 名称 等信息
			String rulenames[] = rulename.split("\\$");// 将id名称 第几列 等内容进行分解
			if (rulename.indexOf(",") < 0) { // 小于0的就是选择的Excle
				String url = filesDao.getFilesById(
						Integer.parseInt(rulenames[2])).getFileUrl();
				List<String[]> list1 = ExUtils.readExcel(new File(url));

				i++;

				String rulename1 = dao.getRuleName(
						dao.findMenuidByFileid(fileid), Relationid, i); // 循环rule循环内容表并获得
																		// id 名称
																		// 等信息
				String rulenames1[] = rulename1.split("\\$");// 将id名称 第几列
																// 等内容进行分解
				// String
				// url1=filesDao.getFilesById(Integer.parseInt(rulenames1[2])).getFileUrl();
				// List<String[]> list2=ExUtils.readExcel(new File(url1));
				names = findnames(list1, rulenames[0], rulenames1[0], names);
			} else { // 等于2的就是文件夹

				String tabsids[] = rulename.split(",");
				System.out.println("-----------" + tabsids.length);
				for (int j = 0; j < names.size(); j++) { // 循环该文件夹下符合所有names的文件

					for (int j2 = 0; j2 < tabsids.length; j2++) {
						String tab[] = tabsids[j2].split("\\$");
						// System.out.println(tabsids[j2]);
						// System.out.println(tab.length);
						data.addAll(dao.gettabsidbyfile(
								Integer.parseInt(tab[0]), "%" + names.get(j)
										+ "%"));
					}

				}

				return data;
			}

		}

		return null;
	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 文件是否存在关联规则
	 * 
	 * @param fileid 文件id
	 * @return
	 */
	@RequestMapping(value = "/isHavaRelation", method = RequestMethod.GET)
	public Integer isHavaRelation(Integer fileid) {

		return dao.isHave(fileid, "%" + fileid + "%");
	}

	public List<String> findnames(List<String[]> list, String list1index,
			String list2index, List<String> names) {
		List<String> newnames = new ArrayList<>();

		for (int i = 0; i < names.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j)[Integer.parseInt(list1index)].indexOf(names
						.get(i)) >= 0) {
					newnames.add(list.get(j)[Integer.parseInt(list2index)]);
				}
			}
		}

		return newnames;
	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 查询全部关联检索内容
	 * 
	 * @param request http协议
	 * @return
	 */
	@RequestMapping(value = "/findAllrelationProject")
	public List<Phenomenon> findAllrelationProject(HttpServletRequest request) {
		SVDS_User loginUser = service.getSessionByIp(request);
		return dao.findAllrelationProject(loginUser.getUserId(), dao.findids()
				.split(","));
	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 查询关联规则对应项目下的所有内容
	 * 
	 * @param id 关联规则id
	 * @param project 项目id
	 * @return
	 */
	@RequestMapping(value = "/findrulebyrelationid")
	public List<Rule> findrulebyrelationid(Integer id, String project) {
		List<Rule> rules = dao.findrulebyrelationid(id,
				Integer.parseInt(project.split(",")[0]));
		for (int i = 0; i < rules.size(); i++) {
			rules.get(i).setName(rules.get(i).getName().split("\\$")[1]);
		}
		return rules;
	}

	/**
	 * 2019年3月13日 曹泽凯 
	 * 添加关联规则到对应项目瞎
	 * @param obj 对应内容
	 * @param relationid 关联规则id
	 * @return
	 */
	@RequestMapping(value = "/addRelationByProject")
	public Integer addRelationByProject(String obj, Integer relationid) {
		SVDS_Relation relation = dao.findrelationbyid(relationid);
		Integer menuid = Integer.parseInt(relation.getProject().split(",")[0]);
		List<SVDS_Rule> rule = dao
				.findruleBymenuidprojectid(relationid, menuid);
		System.out.println(obj);
		System.out.println(relationid);
		JSONArray arr = JSONArray.fromObject(obj);
		String ids = "";
		String texts = "";
		for (int i = 0; i < arr.size(); i++) {
			int id = arr.getJSONObject(i).getInt("id");
			String text = arr.getJSONObject(i).getString("text");
			for (int j = 0; j < rule.size(); j++) {
				String str[] = rule.get(j).getName().split("\\$");
				if (str.length > 2) {
					SVDS_Files file = dao.findfilesbymenuidorname(str[1], id);
					dao.addrule(
							str[0] + "$" + file.getFileName() + "$"
									+ file.getFileId(),
							str[0] + "$" + file.getFileName() + "$"
									+ file.getFileId(),
							Integer.parseInt(rule.get(j).getOrdersort()),
							relationid, id);
				} else {

					Tabs t = dao.findtabsbytabsid(Integer.parseInt(str[0]));
					Tabs tt = dao.findtabsbytabsidandname(t.getName(),
							t.getLv(), id);
					dao.addrule(tt.getId() + "$" + tt.getName() + ",",
							tt.getId() + "$" + tt.getName() + ",",
							Integer.parseInt(rule.get(j).getOrdersort()),
							relationid, id);
				}

			}

			if (i == arr.size() - 1) {
				ids += id;
				texts += text;

			} else {
				ids += id + ",";
				texts += text + ",";
			}
		}

		return dao.updateRelation(relation.getProject() + "," + ids,
				relation.getMenuname() + "," + texts, relationid);
	}

}
