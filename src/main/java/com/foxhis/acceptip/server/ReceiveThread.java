package com.foxhis.acceptip.server;

import com.alibaba.fastjson.JSONObject;
import com.foxhis.acceptip.entity.IP;
import com.foxhis.acceptip.service.IPService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class ReceiveThread extends Thread {

	IPService ipService;

	private  static final long LOST_CONNECTION_TIME = 20*1000;

	Socket socket;

	DataInputStream dataInputStream;

	public ReceiveThread(){

	}
	public ReceiveThread(Socket socket ,DataInputStream dataInputStream){
		this.socket = socket;
		this.ipService = SpringContextUtils.getBean(IPService.class);
		this.dataInputStream = dataInputStream;
	}

	public void run(){
		System.out.println("接收消息线程开启");
		long lastReceiveTime = System.currentTimeMillis();
		while(true){
			if (System.currentTimeMillis()-lastReceiveTime > LOST_CONNECTION_TIME && !socket.isClosed()){
				System.out.println("服务器已超时，即将断开连接");
				try {
					socket.close();
				} catch (IOException e) {
					//e.printStackTrace();
				}
				break;
			}else {
				try{
					String receive = dataInputStream.readUTF();
					if (receive.contains("alive")){
						System.out.println("客户端"+receive+"保持连接");
						lastReceiveTime = System.currentTimeMillis();
					}else if(receive.length()>3){
						JSONObject IPJson = JSONObject.parseObject(receive);
						IP ip = new IP();
						ip.setHotelid(IPJson.get("hotelid").toString());
						ip.setTenantid(IPJson.get("tenantid").toString());
						ip.setIp(IPJson.get("IP").toString());
						ip.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(IPJson.get("Date").toString()));
						Integer i = ipService.getIpRecordCount(ip);
						if (ipService.getIpRecordCount(ip) == 0){
							ipService.addIP(ip);
						}else {
							//获取原来ip
							String oldip = ipService.getIpRecord(ip).getIp();
							if (!oldip.equals(ip.getIp())){
								ipService.addChangeIpRecord(ip.getHotelid(),ip.getTenantid(),oldip,ip.getIp());
							}
							ipService.editIP(ip);
						}
						System.out.println(IPJson.get("tenantid").toString()+"---"+IPJson.get("hotelid").toString()+"---"+IPJson.get("Date").toString()+"---"+IPJson.get("IP").toString());
					}
				}catch (Exception e){
					e.printStackTrace();
					System.out.println("客户端连接失败，重试中");
					try {
						Thread.sleep(2000);
					} catch (Exception e1) {
						//e1.printStackTrace();
					}
				}
			}
		}
	}
}
