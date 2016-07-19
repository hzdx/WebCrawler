package com.mycom.webcrawler.model;

import java.util.Properties;

public class Property {

	public Property() {
	}

	private String splitText0(String text, String flag1, String flag2) {
		try {
			int startIndex = flag1 == null ? 0 : text.indexOf(flag1) + 1;
			int endIndex = flag2 == null ? text.length() : text.indexOf(flag2);
			return text.substring(startIndex, endIndex);
		} catch (Exception e) {
			return null;
		}
	}

	private int parseText0(String text, String flag1, String flag2) {
		String result = splitText0(text, flag1, flag2);
		if (result != null)
			try {
				return Integer.parseInt(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return -1;
	}

	private String splitText(String text, String flag) {
		try {
			return text.substring(0, text.indexOf(flag));
		} catch (Exception e) {
			return null;
		}
	}

	private double parseText(String text, String flag) {
		String result = splitText(text, flag);
		if (result != null)
			try {
				return Double.parseDouble(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return -1;
	}

	public Property(Properties prop) {
		this.id = prop.getProperty("id");
		this.title = prop.getProperty("title");
		this.salePrice = parseText(prop.getProperty("售价"), "万");
		this.downPay = parseText(prop.getProperty("参考首付"), "万");
		this.unitPrice = parseText(prop.getProperty("单价"), "元");
		this.community = splitText(prop.getProperty("所在小区"), " ");
		this.position = prop.getProperty("位置");
		this.roomNum = parseText0(prop.getProperty("房型"), null, "室");
		this.hallNum = parseText0(prop.getProperty("房型"), "室", "厅");
		this.toiletNum = parseText0(prop.getProperty("房型"), "厅", "卫");
		this.acreage = parseText(prop.getProperty("面积"), "平米");
		this.orientation = prop.getProperty("朝向");
		try {
			this.totalFloor = Integer.parseInt(prop.getProperty("楼层").split("/")[1]);
		} catch (Exception e) {
		} // todo hander exception
		try {
			this.floor = Integer.parseInt(prop.getProperty("楼层").split("/")[0]);
		} catch (Exception e) {
		}
		this.decoration = prop.getProperty("装修");
		this.type = prop.getProperty("类型");
	}

	private String title;
	private String id;
	private double salePrice;// 售价(万)
	private double downPay;// 首付(万)
	// private double monthPay;//月供
	private double unitPrice;// 单价(元/平米)
	private String community;// 小区
	private String position;// 位置
	// private String houseType;//房型 如 1室1厅1卫
	private int roomNum;
	private int hallNum;
	private int toiletNum;
	private double acreage;// 面积(平米)
	private String orientation;// 朝向
	private int totalFloor;
	private int floor;// 楼层
	private String decoration;// 装修
	private String type;// 类型

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(double salePrice) {
		this.salePrice = salePrice;
	}

	public double getDownPay() {
		return downPay;
	}

	public void setDownPay(double downPay) {
		this.downPay = downPay;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public int getHallNum() {
		return hallNum;
	}

	public void setHallNum(int hallNum) {
		this.hallNum = hallNum;
	}

	public int getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(int toiletNum) {
		this.toiletNum = toiletNum;
	}

	public double getAcreage() {
		return acreage;
	}

	public void setAcreage(double acreage) {
		this.acreage = acreage;
	}

	public String getOrientation() {
		return orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public int getTotalFloor() {
		return totalFloor;
	}

	public void setTotalFloor(int totalFloor) {
		this.totalFloor = totalFloor;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getDecoration() {
		return decoration;
	}

	public void setDecoration(String decoration) {
		this.decoration = decoration;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
