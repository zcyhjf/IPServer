package com.foxhis.acceptip.dao;

import com.foxhis.acceptip.entity.IP;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
public interface IPMapper {

	IP getIpRecord(IP ip);

	int getIpRecordCount(IP ip);

	void addIP(IP ip);

	void editIP(IP ip);

	void addChangeIpRecord(Map map);
}
