package com.foxhis.acceptip.server;

import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class WebSocketMapUtil {

	public static ConcurrentMap<String, WebSocketTest> webSocketMap = new ConcurrentHashMap<>();

	public static void put(String key, WebSocketTest webSocketTest){
		webSocketMap.put(key, webSocketTest);
	}

	public static WebSocketTest get(String key){
		return webSocketMap.get(key);
	}

	public static void remove(String key){
		webSocketMap.remove(key);
	}

	public static Collection<WebSocketTest> getValues(){
		return webSocketMap.values();
	}

	public static String getIP(Session session){
		String ip = getRemoteAddress(session).toString().split("/")[1];
		return ip;
	}

	public static InetSocketAddress getRemoteAddress(Session session) {
		if (session == null) {
			return null;
		}
		RemoteEndpoint.Async async = session.getAsyncRemote();
		InetSocketAddress addr = (InetSocketAddress) getFieldInstance(async,"base#socketWrapper#socket#sc#remoteAddress");
		return addr;
	}

	private static Object getFieldInstance(Object obj, String fieldPath) {
		String fields[] = fieldPath.split("#");
		for (String field : fields) {
			obj = getField(obj, obj.getClass(), field);
			if (obj == null) {
				return null;
			}
		}

		return obj;
	}

	private static Object getField(Object obj, Class<?> clazz, String fieldName) {
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				Field field;
				field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field.get(obj);
			} catch (Exception e) {
			}
		}

		return null;
	}

}
