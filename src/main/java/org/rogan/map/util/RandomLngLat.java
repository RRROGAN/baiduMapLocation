package org.rogan.map.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * 
 * @author Rogan
 *
 */
public class RandomLngLat {

	public static String[] randomLngLat(double min1, double max1,double min2, double max2) {
		List<String> list = new ArrayList<String>();
		Random random = new Random();		
		BigDecimal db = new BigDecimal(Math.random() * (max1 - min1) + min1);		
		String lng = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();//小数后6位
		db = new BigDecimal(Math.random() * (max2 - min2) + min2);
		String lat = db.setScale(6, BigDecimal.ROUND_HALF_UP).toString();
		String[] array = new String[]{lng, lat};
		return array;	
		}
	public static String[] randomLngLat(String min1, String max1,String min2, String max2) {
		return randomLngLat(Double.parseDouble(min1), Double.parseDouble(max1), 
				Double.parseDouble(min2), Double.parseDouble(max2));
			
		}
	public static void main(String[] args) {
		//111.827412,30.280613到112.612746,30.395813
		String[] array = randomLngLat(111.827412, 112.612746, 30.280613, 30.395813);
		System.out.println(array[0]+ "," + array[1]);
	}
}
