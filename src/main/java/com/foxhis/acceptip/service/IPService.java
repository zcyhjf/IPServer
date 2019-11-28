package com.foxhis.acceptip.service;

import com.foxhis.acceptip.entity.IP;
import org.springframework.stereotype.Service;

public interface IPService {

	IP getIpRecord(IP ip);

	int getIpRecordCount(IP ip);

	void addIP(IP ip);

	void editIP(IP ip);

	void addChangeIpRecord(String hotelid,String tenantid,String oldip,String newip);


}
