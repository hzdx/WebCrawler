package com.mycom.webcrawler.persistence;

import java.util.ArrayList;
import java.util.List;

import com.mycom.webcrawler.model.Property;

public class PropertyService {
	private static String saveSql = "insert into prop_info(id,title,salePrice,downPay"
			+ ",unitPrice,community,position,roomNum" + ",hallNum,toiletNum,acreage,orientation"
			+ ",totalFloor,floor,decoration,type) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	public static void saveProperty(Property property) throws Exception {
		CommonDao.insert(saveSql, property.getId(), property.getTitle(), property.getSalePrice(), property.getDownPay(),
				property.getUnitPrice(), property.getCommunity(), property.getPosition(), property.getRoomNum(),
				property.getHallNum(), property.getToiletNum(), property.getAcreage(), property.getOrientation(),
				property.getTotalFloor(), property.getFloor(), property.getDecoration(), property.getType());
	}

	public static void saveUrl(String url) throws Exception {
		String sql = "insert into prop_url(url) values (?)";
		CommonDao.insert(sql, url);
	}

	public static List<String> selectAllUrl() throws Exception {
		List<Object[]> urlList = CommonDao.select("select distinct url from prop_url");
		List<String> urlStrList = new ArrayList<>(urlList.size());
		for (Object[] obj : urlList) {
			urlStrList.add((String) obj[0]);
		}
		return urlStrList;
	}

}
