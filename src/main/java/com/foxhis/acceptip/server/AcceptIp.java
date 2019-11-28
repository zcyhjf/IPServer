package com.foxhis.acceptip.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Component
public class AcceptIp implements ApplicationRunner{

	@Value("${accept.port}")
	String port;

	@Value("${poolsize}")
	String poolsize;

	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		/*ServerSocket serverSocket = new ServerSocket(Integer.parseInt(port));
		System.out.println("正在监听"+port+"端口");
		System.out.println("阻塞监听连接中");
		ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.parseInt(poolsize));
		while(true){
			Socket socket = serverSocket.accept();
			socket.setSoTimeout(10000);
			pool.execute(new ReceiveThread(socket,new DataInputStream(socket.getInputStream())));
			System.out.println("线程池中活跃线程数量"+pool.getActiveCount());
			System.out.println("获得socket连接"+socket.toString());
		}
		//new HeartBeat(socket).start();*/
	}
}
