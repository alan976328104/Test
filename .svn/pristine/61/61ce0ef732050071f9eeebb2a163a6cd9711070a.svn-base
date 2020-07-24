package ac.drsi.nestor.controller;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ac.drsi.nestor.entity.SVDS_Files;
import ac.drsi.nestor.service.SVDS_FilesService;
import ac.drsi.nestor.service.SearchService;

@Controller
public class SVDS_SearchController {

	@Value("${filepath}")
	private String filePath;
	@Value("${indexDirpath}")
	private String indexDirpath;

	@Autowired
	private SearchService searchService;
	@Autowired
	private SVDS_FilesService filesService;

	@RequestMapping(value = "/lucene", method = RequestMethod.POST)
	@ResponseBody
	public String luceneSearch(@RequestParam int pageNumber, int pageSize,
			String inputall) throws Exception {
		List<SVDS_Files> files = null;
		List<SVDS_Files> pageInfo = null;
		int total = 0;
		//String fileUrl="F:\\lucene\\data\\TRC37.doc";
		JSONArray json = JSONArray.fromObject(inputall);
		if (json.size() > 0) {
			JSONObject job = json.getJSONObject(0);
			System.out.println(job.get("searchName").toString());
			if (job.get("searchName").toString().equals("1")) {
				searchService.indexerStart(indexDirpath, filePath);
				List<String> fileUrls = searchService.search(indexDirpath, job
						.get("searchVal").toString());
				if (fileUrls.size() != 0) {
					files = filesService.getFilesByUrl(fileUrls);
					pageInfo = filesService.getFilesByUrl(pageNumber, pageSize,
							fileUrls);
					total = files.size();
				}
			}
		}
		JSONObject result = new JSONObject();
		result.put("rows", pageInfo);
		result.put("total", total);
		System.out.println(result.toString());
		return result.toString();
	}
}
