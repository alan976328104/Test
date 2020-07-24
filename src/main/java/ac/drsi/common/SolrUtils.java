package ac.drsi.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.omg.CORBA.OMGVMCID;

public class SolrUtils {
	public static final String URL = "http://127.0.0.1:80/solr/collection1";
	public static final String CSU_URL = "/update/extract";
	public static HttpSolrServer httpSolrServer = new HttpSolrServer(URL);

	// 查询
	public static QueryResponse Query(String name) {
		try {
		//	QueryResponse c;
			SolrQuery query = new SolrQuery();
			query.setQuery("name:*" + name + "* OR attr_content:*" + name + "*");
			// 开启高亮组件
			query.setHighlight(true).setHighlightSnippets(1);
			query.addHighlightField("name");// 高亮字段
			query.addHighlightField("attr_content");// 高亮字段
			// 标记，高亮关键字前缀 后缀
			query.setHighlightSimplePre("<font color='red'>");
			query.setHighlightSimplePost("</font>");
			query.setHighlightFragsize(100); // 每个分片的最大长度，默认为100。
			QueryResponse response = httpSolrServer.query(query);
			return response;
		} catch (SolrServerException e) {
			e.printStackTrace();
			return null;
		}
	}

	
//	public static QueryResponse Querys(String name..) {
//		try {
//			SolrQuery query = new SolrQuery();
//			query.setQuery("name:*" + name + "* OR attr_content:*" + name + "*");
//			// 开启高亮组件
//			query.setHighlight(true).setHighlightSnippets(1);
//			query.addHighlightField("name");// 高亮字段
//			query.addHighlightField("attr_content");// 高亮字段
//			// 标记，高亮关键字前缀 后缀
//			query.setHighlightSimplePre("<font color='red'>");
//			query.setHighlightSimplePost("</font>");
//			query.setHighlightFragsize(100); // 每个分片的最大长度，默认为100。
//			QueryResponse response = httpSolrServer.query(query);
//			return response;
//		} catch (SolrServerException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	// 普通添加一个数据
	public static boolean addDocument(SolrInputDocument document) {
		try {
			httpSolrServer.add(document);
			httpSolrServer.commit();
			return true;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 添加一个bean
	public static boolean addBean(Object t) {
		try {
			httpSolrServer.addBean(t);
			httpSolrServer.commit();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 添加一个集合bean
	public static <E> boolean addBeans(List<E> t) {
		try {
			httpSolrServer.addBeans(t);
			httpSolrServer.commit();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 添加一个文件
	public static boolean addFile(File file, String fileId) {
		ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
				"/update/extract");
		try {
			System.out.println("添加索引");
			up.addFile(file, getFileType(file.getName()));
			up.setParam("literal.id", fileId);
			up.setParam("name", file.getName().toString());
			up.setParam("fmap.content", "attr_content");
			up.setParam("literal.url", file.getPath());
			up.setAction(ACTION.COMMIT, true, true);
			httpSolrServer.request(up);
			httpSolrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// 获得文件类型
	private static String getFileType(String name) {
		name = name.substring(name.indexOf(".") + 1);
		if (name.equals("doc")) {
			return "application/msword";
		} else if (name.equals("docx")) {
			return "application/msword";
		} else if (name.equals("txt")) {
			return "text/plain";
		} else if (name.equals("png")) {
			return "image/png";
		} else if (name.equals("gif")) {
			return "image/gif";
		} else if (name.equals("jpg")) {
			return "image/jepg";
		} else if (name.equals("jepg")) {
			return "image/jepg";
		} else if (name.equals("mp3")) {
			return "audio/mp3";
		} else if (name.equals("mp4")) {
			return "video/mpeg4";
		} else if (name.equals("avi")) {
			return "video/avi";
		} else if (name.equals("wmv")) {
			return "video/x-ms-wmv";
		} else if (name.equals("wmx")) {
			return "video/x-ms-wmx";
		} else if (name.equals("xml")) {
			return "text/xml";
		} else if (name.equals("xls")) {
			return "application/vnd.ms-excel";
		} else if (name.equals("xlsx")) {
			return "application/vnd.ms-excel";
		} else if (name.equals("pdf")) {
			return "application/pdf";
		} else if (name.equals("bmp")) {
			return "application/x-bmp";
		}
		return null;
	}

	// 根据id删除
	public static boolean removeById(String id) {
		try {
			httpSolrServer.deleteById(id);
			httpSolrServer.commit();
			return true;
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 删除全部
	public static boolean removeAll() {
		try {
			httpSolrServer.deleteByQuery("*:*");
			httpSolrServer.commit();
			return true;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// 根据query删除
	public static boolean removeByQuery(String query) {
		try {
			httpSolrServer.deleteByQuery(query);
			httpSolrServer.commit();
			return false;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	// 根据一个ids删除
	public static boolean removeByIds(List<String> ids) {
		try {
			httpSolrServer.deleteById(ids);
			httpSolrServer.commit();
			return true;
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static List<Integer> queryAllid()  {
		List<Integer> allid =new ArrayList<>();
		//QueryResponse c;
		//SolrQuery query = new SolrQuery();
	// 定义查询内容 *代表查询所有 这个是基于结果集
	try {
		//SolrQuery query1 = ;
		
		
		SolrQuery query = new SolrQuery("*:*"); // 定义查询内容
		query.setStart(0);// 起始页
		query.setRows((int) httpSolrServer.query(new SolrQuery("*:*")).getResults().getNumFound());// 每页显示数量
		QueryResponse rsp = httpSolrServer.query(query);
			SolrDocumentList results = rsp.getResults();
			System.out.println(results.getNumFound());// 查询总条数
			for (SolrDocument doc : results) {
				allid.add(Integer.parseInt(doc.get("id").toString()));
				//System.out.println(doc.get(key));
			}
			return allid;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}
	}
	public static void main(String[] args) {
		
		removeAll();
	}
}
