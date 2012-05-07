package android.guju.service;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemConstant {
	
	public final static Map<String, String> STYLES_ID_MAP = new LinkedHashMap<String, String>(){

		/**
		 * 
		 */
		private static final long serialVersionUID = -1034061253582043283L;
		{
			put("全部风格", "0");
			put("亚洲风", "2");
			put("当代风格", "3");
			put("不拘一格", "4");
			put("地中海式", "9");
			put("现代风格", "5");
			put("传统风格", "7");
			put("热带风格", "8");
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
			put("壁橱", "1021");
			put("餐厅", "1006");
			put("门厅", "1002");
			put("外观", "1001");
			put("客厅", "1004");
			put("大厅", "1012");
			put("办公间", "1010");
			put("儿童房", "1009");
			put("厨房", "1005");
			put("景观", "1014");
			put("洗衣房", "1020");
			put("起居室", "1003");
			put("媒体室", "1017");
			put("天井", "1013");
			put("池", "1015");
			put("门廊", "1019");
			put("化妆间", "1018");
			put("楼梯", "1011");
			put("酒窖", "1016");
		}
	};
}
