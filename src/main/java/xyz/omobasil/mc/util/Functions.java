package xyz.omobasil.mc.util;

import java.util.List;

public class Functions {

	public static <T extends Number> double total(List<T> list){
		double ret = 0;
		for (T t : list) {
			ret += (double) t;
		}
		return ret;
	}
	
	public static int clampInt(int val, int min, int max) {
		return Math.max(min,Math.min(max,val));
	}
	
}
