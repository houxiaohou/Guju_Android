package cn.com.guju.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemConstant {
	
	
	public static String BASE_URL = "http://www.guju.com.cn:8080";
	public static String CATEGORY_REQUEST = "/getFeaturedSpaces/offset=";
	public static String SIMAGES = "/simages/";
	public static String AUTHORIZE = "/authorize/";
	public static String PHOTO_VERSION = "_0_8-.jpg";
	
	public final static Map<String, String> STYLES_ID_MAP = new LinkedHashMap<String, String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -1034061253582043283L;
		{
			put("全部风格", "0");
			put("东方风韵", "2");
			put("当代风格", "3");
			put("折中主义", "4");
			put("地中海风情", "9");
			put("现代主义", "5");
			put("传统格调", "7");
			put("热带风情", "8");
		}
	};
	
	public final static Map<String, String> CATEGORIES_ID_MAP = new LinkedHashMap<String, String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = 4463445279692707813L;
		{
			put("全部空间", "0");
			put("浴室", "1007");
			put("卧室", "1008");
			put("更衣间", "1021");
			put("餐厅", "1006");
			put("玄关", "1002");
			put("外景", "1001");
			put("活动室", "1004");
			put("走廊", "1012");
			put("书房", "1010");
			put("儿童房", "1009");
			put("厨房", "1005");
			put("园艺", "1014");
			put("洗衣房", "1020");
			put("客厅", "1003");
			put("家庭影院", "1017");
			put("庭院", "1013");
			put("池", "1015");
			put("阳台", "1019");
			put("化妆间", "1018");
			put("楼梯", "1011");
			put("酒窖", "1016");
		}
	};
	
}
