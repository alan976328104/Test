package ac.drsi.nestor.controller;

import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ac.drsi.common.DateUtils;
import ac.drsi.common.FileUtils;
import ac.drsi.common.IpAddressUtils;
import ac.drsi.nestor.entity.SVDS_Log;
import ac.drsi.nestor.entity.SVDS_Photo;
import ac.drsi.nestor.entity.SVDS_User;
import ac.drsi.nestor.service.SVDS_LogService;
import ac.drsi.nestor.service.SVDS_PhotoService;
import ac.drsi.nestor.service.SVDS_SessionService;
import ac.drsi.nestor.service.SVDS_UserService;

@RestController
public class SVDS_PhotoController {
	@Autowired
	SVDS_PhotoService photoService;
	@Autowired
	SVDS_UserService userService;
	@Autowired
	SVDS_LogService logService;
	@Value("${photopath}")
	private String photopath;
	@Autowired
	SVDS_SessionService sessionService;

	/**
	 * 根据用户ID查询用户头像
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUserPhoto", method = RequestMethod.POST)
	@ResponseBody
	public String getByUserPhohtoId(Integer userId) throws Exception {
		SVDS_Photo photo = photoService.getByUserId(userId);
		if (photo != null) {
			return photo.getPhotoUrl();
		} else {
			return "error";
		}
	}

	/**
	 * 根据用户ID查询用户头像
	 * 
	 * @param userId
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping(value = "/updateLogo", method = RequestMethod.POST)
	//@ResponseBody
	public void updateLogo(@RequestParam("file") MultipartFile file,HttpServletRequest request) throws UnknownHostException
			 {
		String path = ClassUtils.getDefaultClassLoader().getResource("")
				.getPath()
				+ "static/assets/images/";
		System.out.println(path);
		if (FileUtils.upload(file, path, "logo.png")) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改系统logo操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			//return 1;
		}
		//return 0;
	}

	/**
	 * 添加头像
	 * 
	 * @param btnName
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/insertPhoto", method = RequestMethod.POST)
	@ResponseBody
	public Integer insertPhoto(String userId,
			@RequestParam("file") MultipartFile file,HttpServletRequest request) throws Exception
			 {
		SVDS_Photo photo = new SVDS_Photo();
		Integer photoId = null;
		String fileName = file.getOriginalFilename();
		InputStream inputStream = file.getInputStream();
		byte[] pictureData = new byte[(int) file.getSize()];
		inputStream.read(pictureData);
		/*
		 * String path = ClassUtils.getDefaultClassLoader().getResource("")
		 * .getPath() + "static/assets/images/";
		 */
		SVDS_Photo photoById = photoService.getByUserId(Integer
				.parseInt(userId));
		if (photoById != null) {
			FileUtils.upload(file.getBytes(), photopath, fileName);
			photoById.setPhotoUrl(photopath + "\\" + fileName);
			photoById.setPicture(pictureData);
			photoById.setPhotoType(fileName.substring(
					fileName.lastIndexOf(".") + 1, fileName.length()));
			photoById.setPhotoName(fileName.substring(0,
					fileName.lastIndexOf(".")));
			photoId = photoService.updatePhoto(photoById);
		} else {
			FileUtils.upload(file.getBytes(), photopath, fileName);
			photo.setPhotoUrl(photopath + "\\" + fileName);
			photo.setPicture(pictureData);
			photo.setPhotoType(fileName.substring(
					fileName.lastIndexOf(".") + 1, fileName.length()));
			photo.setPhotoName(fileName.substring(0, fileName.lastIndexOf(".")));
			SVDS_User user = userService.getUserById(Integer.parseInt(userId));
			photo.setUser(user);
			photoId = photoService.insertPhoto(photo);
		}
		if (photoId > 0) {
			SVDS_User loginUser = sessionService.getSessionByIp(request);
			logService.insertLog(new SVDS_Log("执行了修改用户头像操作", DateUtils
					.getDate(), loginUser,IpAddressUtils.getIpAddress(request),"成功"));
			return photo.getPhotoId();
		} else {
			return null;
		}
	}

	/**
	 * 根据ID修改头像
	 * 
	 * @param Photo
	 * @return
	 */
	@RequestMapping(value = "/updatePhoto", method = RequestMethod.POST)
	@ResponseBody
	public Integer updatePhoto(SVDS_Photo Photo) {
		if (photoService.updatePhoto(Photo) > 0) {
			return photoService.updatePhoto(Photo);
		}
		return null;
	}

	/**
	 * 根据ID删除头像
	 * 
	 * @param PhotoIds
	 * @return
	 */
	@RequestMapping(value = "/deletePhoto", method = RequestMethod.POST)
	@ResponseBody
	public Integer deletePhoto(
			@RequestParam(value = "btnIds[]", required = false, defaultValue = "") Integer[] btnIds) {
		List<Integer> ids = Arrays.asList(btnIds);
		if (photoService.deletePhoto(ids) > 0) {
			return photoService.deletePhoto(ids);
		}
		return null;
	}
}
