package com.foxhis.acceptip.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.deser.Deserializers;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

@ServerEndpoint(value = "/websocket/{tenantid}/{hotelid}")
@Component
public class WebSocketTest {
	private Logger logger = Logger.getLogger(WebSocketTest.class);
	private Session session;

	/*
	 *连接建立后触发
	 */
	@OnOpen
	public void onOpen(@PathParam("tenantid")String tenantid,@PathParam("hotelid")String hotelid, Session session){
		this.session = session;
		logger.info("onOpen"+session.getId());
		WebSocketMapUtil.put(session.getId(),this);
		String remoteAddress = WebSocketMapUtil.getIP(session);
		System.out.println("tenantid:"+tenantid+";hotelid:"+hotelid+";ip:"+remoteAddress);

	}

	/*
	 *连接关闭时触发
	 */
	@OnClose
	public void onClose(){
		//从map中删除
		WebSocketMapUtil.remove(session.getId());
		logger.info("====== onClose:"+session.getId()+" ======");
	}

	/*
	 *客户端发送消息时触发
	 */
	@OnMessage
	public void onMessage(String params,Session session){
		JSONObject ipJson = (JSONObject) JSON.parse(params);
		//获取服务端到客户端的通道
		WebSocketTest webSocketTest = WebSocketMapUtil.get(session.getId());
		logger.info("收到来自"+session.getId()+"的消息"+params);
		String result = "收到来自"+session.getId()+"的消息"+params;
		//返回消息给Web Socket客户端（浏览器）
		try {
			webSocketTest.sendMessage(1,"发送成功",result);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("发送失败");
		}
	}

	/**
	 * 发生错误时触发的方法
	 */
	@OnError
	public void onError(Session session,Throwable error){
		logger.info(session.getId()+"连接发生错误"+error.getMessage());
		error.printStackTrace();
	}

	/*
	 *向客户端发送消息
	 */
	public void sendMessage(int status,String message,Object datas) throws IOException {
		JSONObject result = new JSONObject();
		result.put("status", status);
		result.put("message", message);
		result.put("datas", datas);
		this.session.getBasicRemote().sendText(result.toString());
	}
}
