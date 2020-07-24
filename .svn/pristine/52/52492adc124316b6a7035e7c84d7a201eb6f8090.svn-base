package ac.drsi.nestor.entity;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Plupload实体类固定格式，属性名不可修改 因为MultipartFile要用到Spring
 * web的依赖，而该依赖在web模块中才引入，所以不把该实体类放在entity模块
 */
public class Plupload {
	/** 文件原名 */
	private String name;
	/** 用户上传资料被分解总块数 */
	private int chunks = -1;
	/** 当前块数（从0开始计数） */
	private int chunk = -1;
	/** HttpServletRequest对象，不会自动赋值，需要手动传入 */
	private HttpServletRequest request;
	/** 保存文件上传信息，不会自动赋值，需要手动传入 */
	private MultipartFile multipartFile;

	/**
	 * 获取文件原名
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置文件原名
	 * 
	 * @param name
	 *            文件原名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取总块数
	 * 
	 * @return
	 */
	public int getChunks() {
		return chunks;
	}

	/**
	 * 设置总块数
	 * 
	 * @param chunks
	 *            总块数
	 */
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}

	/**
	 * 获取当前块数
	 * 
	 * @return
	 */
	public int getChunk() {
		return chunk;
	}

	/**
	 * 设置当前块数
	 * 
	 * @param chunk
	 *            当前块数
	 */
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

	/**
	 * 获取请求对象
	 * 
	 * @return
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * 设置请求对象
	 * 
	 * @param request
	 *            请求对象
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取文件信息
	 * 
	 * @return
	 */
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	/**
	 * 设置文件信息
	 * 
	 * @param multipartFile
	 *            文件信息
	 */
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
}
