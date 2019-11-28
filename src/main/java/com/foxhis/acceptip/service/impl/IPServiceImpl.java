package com.foxhis.acceptip.service.impl;

import com.foxhis.acceptip.dao.IPMapper;
import com.foxhis.acceptip.entity.IP;
import com.foxhis.acceptip.service.IPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("IPService")
public class IPServiceImpl implements IPService {

	@Autowired
	IPMapper ipMapper;

	@Override
	public IP getIpRecord(IP ip) {
		return ipMapper.getIpRecord(ip);
	}

	@Override
	public int getIpRecordCount(IP ip) {
		return ipMapper.getIpRecordCount(ip);
	}

	@Override
	public void addIP(IP ip) {
		ipMapper.addIP(ip);
	}

	@Override
	public void editIP(IP ip) {
		ipMapper.editIP(ip);
	}

	@Override
	public void addChangeIpRecord(String hotelid,String tenantid,String oldip, String newip) {
		Map paramMap = new HashMap();
		paramMap.put("hotelid",hotelid);
		paramMap.put("tenantid",tenantid);
		paramMap.put("oldip",oldip);
		paramMap.put("newip",newip);
		paramMap.put("date",new Date());
		ipMapper.addChangeIpRecord(paramMap);
	}
}
