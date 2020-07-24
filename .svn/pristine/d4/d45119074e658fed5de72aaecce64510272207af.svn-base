package ac.drsi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.annotation.Value;
/**
 * 用于操作本地文件
 * @author CZK
 *
 */
public class Indexer {

	private IndexWriter indexWriter;

	@Value("${filepath}")
	private String filePath;
	@Value("${indexDirpath}")
	private String indexDirpath;

	/**
	 * 判断文件夹是否存在
	 * 
	 * @param path
	 */
	public static boolean judeDirExists(String path) {
		File file = new File(path);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			System.out.println("//不存在");
			file.mkdir();
			return true;
		} else {
			System.out.println("//目录存在");
			return false;
		}
	}

	/***
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/***
	 * 删除文件夹
	 * 
	 * @param folderPath文件夹完整绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 实例化写索引
	 * 
	 * @param indexDir
	 *            保存索引的目录
	 * @throws Exception
	 */
	public Indexer(String indexDir) throws Exception {
		System.out.println(judeDirExists(indexDir));
		if(!judeDirExists(indexDir)){
			delFolder(indexDir);
		}
		Directory dir = FSDirectory.open(Paths.get(indexDir));
		// 标准分词器
		// Analyzer analyzer = new StandardAnalyzer();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(); // 中文分词器
		// Analyzer analyzer = new IKAnalyzer();//中文分词
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		indexWriter = new IndexWriter(dir, iwc);
	}

	/**
	 * 创建索引
	 * 
	 * @param dataFile
	 *            数据文件所在的目录
	 * @return 索引文件的数量
	 * @throws Exception
	 */
	public int CreateIndex(String dataFile) throws Exception {
		File[] files = new File(dataFile).listFiles();
		for (File file : files) {
			/**
			 * 被索引文件必须不能是 1.目录 2.隐藏 3. 不可读 4.不是txt文件， 否则不被索引
			 */
			if (!file.isDirectory() && !file.isHidden() && file.canRead()) {
				IndexFile(file);
			}
		}
		return indexWriter.numDocs();
	}

	/**
	 * 索引文件
	 */
	private void IndexFile(File file) throws Exception {
		System.out.println("文件全路径: " + file.getCanonicalPath());
		Document doc = getDocument(file);
		indexWriter.addDocument(doc);
	}

	public String readWord(String path) {
		String buffer = "";
		try {
			if (path.endsWith(".doc")) {
				InputStream is = new FileInputStream(new File(path));
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				ex.close();
			} else if (path.endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(path);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(
						opcPackage);
				buffer = extractor.getText();
				extractor.close();
			} else {
				System.out.println("此文件不是word文件！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 从文件中获取文档
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	private Document getDocument(File file) throws Exception {
		Document document = new Document();
		Field contentField = new TextField("fileContents",
				readWord(file.getCanonicalPath()), Field.Store.YES);
		/**
		 * Field.Store.YES表示把该Field的值存放到索引文件中，提高效率，一般用于文件的标题和路径等常用且小内容小的。
		 */
		Field fileNameField = new TextField("fileName", file.getName(),
				Field.Store.YES);
		Field filePathField = new TextField("filePath",
				file.getCanonicalPath(), Field.Store.YES);
		System.out.println(readWord(file.getCanonicalPath()));
		document.add(contentField);
		document.add(fileNameField);
		document.add(filePathField);

		return document;
	}

	/**
	 * Description:关闭写实例 Date
	 * 
	 * @throws Exception
	 */
	public void close() throws Exception {
		indexWriter.close();

	}

}
