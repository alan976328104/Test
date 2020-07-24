package ac.drsi.nestor.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import ac.drsi.common.Indexer;

@Service
public class SearchService {

	/**
	 * 根据关键字检索文件
	 * 
	 * @param indexDir
	 *            存放索引的目录
	 * @param key
	 *            关键字
	 * @throws IOException
	 * @throws ParseException
	 */
	public List<String> search(String indexDir, String key) throws IOException,
			ParseException {
		List<String> fileUrls = new ArrayList<String>();
		Directory directory = FSDirectory.open(Paths.get(indexDir));
		IndexReader reader = DirectoryReader.open(directory);
		System.out.println(key);
		Term t = new Term("fileContents", "*" + key + "*");
		WildcardQuery query = new WildcardQuery(t);
		IndexSearcher searcher = new IndexSearcher(reader);
		long startTime = System.currentTimeMillis();
		TopDocs topDocs = searcher.search(query, 10);
		long endTime = System.currentTimeMillis();
		System.out.println("匹配" + key + "总共花费：" + (endTime - startTime)
				+ "毫秒，查询到" + topDocs.totalHits + "条记录");
		for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
			Document document = searcher.doc(scoreDoc.doc);
			fileUrls.add(document.get("filePath"));
			System.out.println(document.get("filePath"));
		}
		return fileUrls;
	}
	
	public void indexerStart(String indexDirpath,String filePath) {
		int indexFileCount = 0;
		Indexer indexer = null;
		try {
			indexer = new Indexer(indexDirpath);
			long startTime = System.currentTimeMillis();
			indexFileCount = indexer.CreateIndex(filePath);
			long endTime = System.currentTimeMillis();
			System.out.println("索引文件共花费：" + (endTime - startTime) + " 毫秒");
			System.out.println("一共索引了" + indexFileCount + "个文件");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				indexer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
