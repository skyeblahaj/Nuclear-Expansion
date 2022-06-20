package xyz.omobasil.mc.util;

import java.util.List;

public class Functions {

	public static <T extends Number> double total(List<T> list){
		double ret = 0;
		for (T t : list) {
			ret += t.doubleValue();
		}
		return ret;
	}
	
	public static <T extends Number> double clamp(T val, T min, T max) {
		return Math.max((double)min,Math.min((double)max,(double)val));
	}
	
}
