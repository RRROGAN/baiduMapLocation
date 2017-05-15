package org.rogan.map.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * 
 * @author Rogan
 * 2017Äê5ÔÂ5ÈÕ17:05:37
 *
 */
public class Favorite implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String uid;
	private String start_posi;
	private String end_posi;
	private String start_detail_posi;
	private String end_detail_posi;
	private double start_lat;
	private double start_lng;
	private double end_lat;
	private double end_lng;
	private Timestamp create_time;
	private Date modify_time;
	private String label;
	private char isfavorite;
	private char collect_type;
	private String extend_info;
	private String img;
	public Favorite() {
	}
	public Favorite(String uid, String start_posi, String start_detail_posi, double start_lat, double start_lng) {
		this.uid = uid;
		this.start_posi = start_posi;
		this.start_detail_posi = start_detail_posi;
		this.start_lat = start_lat;
		this.start_lng = start_lng;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStart_posi() {
		return start_posi;
	}
	public void setStart_posi(String start_posi) {
		this.start_posi = start_posi;
	}
	public String getEnd_posi() {
		return end_posi;
	}
	public void setEnd_posi(String end_posi) {
		this.end_posi = end_posi;
	}
	public String getStart_detail_posi() {
		return start_detail_posi;
	}
	public void setStart_detail_posi(String start_detail_posi) {
		this.start_detail_posi = start_detail_posi;
	}
	public String getEnd_detail_posi() {
		return end_detail_posi;
	}
	public void setEnd_detail_posi(String end_detail_posi) {
		this.end_detail_posi = end_detail_posi;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	public Date getModify_time() {
		return modify_time;
	}
	public void setModify_time(Date modify_time) {
		this.modify_time = modify_time;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public char getIsfavorite() {
		return isfavorite;
	}
	public void setIsfavorite(char isfavorite) {
		this.isfavorite = isfavorite;
	}
	public char getCollect_type() {
		return collect_type;
	}
	public void setCollect_type(char collect_type) {
		this.collect_type = collect_type;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public double getStart_lat() {
		return start_lat;
	}

	public void setStart_lat(double start_lat) {
		this.start_lat = start_lat;
	}

	public double getStart_lng() {
		return start_lng;
	}

	public void setStart_lng(double start_lng) {
		this.start_lng = start_lng;
	}

	public double getEnd_lat() {
		return end_lat;
	}

	public void setEnd_lat(double end_lat) {
		this.end_lat = end_lat;
	}

	public double getEnd_lng() {
		return end_lng;
	}

	public void setEnd_lng(double end_lng) {
		this.end_lng = end_lng;
	}
	public String getExtend_info() {
		return extend_info;
	}
	public void setExtend_info(String extend_info) {
		this.extend_info = extend_info;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
}
