package com.foxhis.acceptip.entity;


import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class IP {
	String hotelid;
	String tenantid;
	Date date;
	String ip;

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getTenantid() {
		return tenantid;
	}

	public void setTenantid(String tenantid) {
		this.tenantid = tenantid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public String toString() {
		return "IP{" +
				"hotelid='" + hotelid + '\'' +
				", tenantid='" + tenantid + '\'' +
				", date=" + date +
				", ip='" + ip + '\'' +
				'}';
	}
}
