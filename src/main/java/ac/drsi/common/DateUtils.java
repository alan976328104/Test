package ac.drsi.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DateUtils {
	/**
	 * 获得系统时间
	 * @return
	 */
	public static String getDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String date = df.format(new Date());
		return date;
	}
	/**
	 * 转换成时间类型
	 * @param datestr 时间字符
	 * @return
	 */
	public static Date stringByDate(String datestr){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		Date date = null;
		try {
			date = df.parse(datestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	

}
