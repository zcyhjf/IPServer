package com.foxhis.acceptip.web;

import com.foxhis.acceptip.entity.IP;
import com.foxhis.acceptip.service.IPService;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileController {

	@Autowired
	IPService ipService;

	@Value("${dirpath}")
	String dirpath;

	@RequestMapping("/getfile")
	//向指定ip发送请求
	public String getfile(String tenantid,String hotelid) {
		IP tmpip = new IP();
		tmpip.setTenantid(tenantid);
		tmpip.setHotelid(hotelid);
		IP ip = ipService.getIpRecord(tmpip);
		String targetIp = ip.getIp();
		String url = "http://"+targetIp+"/file";
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		try{
			httpClient.execute(httpPost);
		}catch (Exception e){
			return "发送失败";
		}
		return "成功";
	}

	@RequestMapping("/upload")
	public void upload(@RequestParam("file")MultipartFile mpfile, String starttime){
		try {
			String fileName = mpfile.getOriginalFilename();
			String filePath = dirpath+ File.separator+fileName;
			File file = new File(filePath);
			FileUtils.copyInputStreamToFile(mpfile.getInputStream(),file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("保存失败");
		}
		Long cost = System.currentTimeMillis()-Long.parseLong(starttime);
		System.out.println("保存成功,耗时"+cost/1000+"s");
	}

}
