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
			put("日韩亚洲", "2");
			put("当代美学", "3");
			put("折衷主义", "4");
			put("地中海式", "9");
			put("现代简约", "5");
			put("复古传统", "7");
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
			put("壁橱", "1021");
			put("餐厅", "1006");
			put("玄关", "1002");
			put("室外景观", "1001");
			put("客厅", "1004");
			put("大厅", "1012");
			put("书房", "1010");
			put("儿童房", "1009");
			put("厨房", "1005");
			put("景观", "1014");
			put("洗衣间", "1020");
			put("起居室", "1003");
			put("媒体室", "1017");
			put("露台", "1013");
			put("游泳池", "1015");
			put("走廊", "1019");
			put("化妆间", "1018");
			put("楼梯", "1011");
			put("酒架", "1016");
		}
	};
}
