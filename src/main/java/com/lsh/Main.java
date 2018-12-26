package com.lsh;

import com.lsh.control.ServerThread;
import com.lsh.control.SockCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static Socket socket;
    public static void main(String[] args) throws Exception{
        //获取到调度器的实例

//        List<Class> classes =  ClassUtils.getAllClassByInterface(BaseSchedule.class);
//        for(Class baseClass : classes){
//            BaseSchedule baseSchedule = (BaseSchedule)baseClass.newInstance();
//            logger.info("run baseSchedule" + baseSchedule.getClass().getName());
//            baseSchedule.start();
//        }

        //启动Central
//        CentralControllerService centralControllerService = CentralControllerService.getInstance();
//        centralControllerService.getMessage();


        ServerSocket serverSocket = new ServerSocket(9527);
        logger.info("服务器已启动,等待客户连接...");
        SockCheck sockCheck = new SockCheck();
        sockCheck.start();
        logger.info("SOCKET监控已启动,等待检查...");
        while(true){
            socket = serverSocket.accept();
            ServerThread st = new ServerThread(socket);
            st.start();
            logger.info("此用户的IP地址为"+socket.getInetAddress().getHostAddress());
        }

        //logger.info("Main run success");
    }
}
