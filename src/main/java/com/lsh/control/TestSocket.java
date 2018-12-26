package com.lsh.control;

import com.lsh.model.NsHead;
import com.lsh.model.OperatorBean;
import com.lsh.model.SolBean;
import com.lsh.utils.JsonUtils;
import com.lsh.utils.NsHeadClient;
import com.lsh.utils.PropsUtils;
import com.lsh.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wuhao on 2018/6/5.
 */
public class TestSocket {
    private static int count;
    private static Socket socket;
    private static final Logger logger = LoggerFactory.getLogger(TestSocket.class);

    //监听一个端口，获取cmd，参数和设备id，根据命令获取方法
    private  static Socket client;


    public static void main(String[] args) throws Exception {
//        ServerSocket serverSocket = new ServerSocket(9527);
//        System.out.println("服务器已启动,等待客户连接...");
//        while(true){
//            socket = serverSocket.accept();
//            ServerThread st = new ServerThread(socket);
//            st.start();
//            System.out.println("这是第"+(count++)+"位用户");
//            System.out.println("此用户的IP地址为"+socket.getInetAddress().getHostAddress());
//        }

        logger.info("init socket");
        try {
            int serverPort = 9527;
            String serverIp = "39.106.103.210";
            client = new Socket(serverIp, serverPort);
            logger.info("connect socket success" +"ip:"+ serverIp +",port:"+ serverPort);
        }catch (Exception e){
            logger.info("connect socket fail");
            logger.error(e.getMessage(),e);
        }
        SolBean solBean = new SolBean();
        solBean.setCmdParams("11131313131");
        solBean.setType(2);
        jsonCall(ResultUtils.SUCCESS(solBean, "1213131", "13131", "123"));

        logger.info(readResponse());
        logger.info(readResponse());
    }

    public static  void jsonCall(String jsonInput) throws Exception{
        logger.info("send jsonInput:"+jsonInput);
        NsHead writeHead = new NsHead();
        OutputStream outputStream = null;
        try {
            byte[] writeBytes = jsonInput.getBytes("UTF-8");
            writeHead.body_len = writeBytes.length + 4;

            outputStream = client.getOutputStream();
            byte [] head = writeHead.pack();
            logger.info("---"+head);
            byte [] byteOutPut = NsHeadClient.byteMerger(head, writeBytes);
            outputStream.write(byteOutPut);
            outputStream.flush();

        }catch (Exception e){
            throw e;
        }
    }


    public static  String readResponse() throws Exception {
        InputStream reader = client.getInputStream();

        byte[] readHead = new byte[8];
        logger.info("begin read");
        int result = reader.read(readHead);
        logger.info("read end:"+result);
        if (result == -1) {
            return null;
        }
        NsHead head = new NsHead();
        head.unpack(readHead);
        logger.info("read head:"+ head);
        byte[] readBytes = new byte[head.body_len];
        int offset = 0;
        int leftByteNum = head.body_len;
        logger.info("read body begin:"+leftByteNum);
        while (leftByteNum > 0) {
            int len = reader.read(readBytes, offset, leftByteNum);
            leftByteNum -= len;
            offset += len;
        }
        logger.info("read body end");
        String response =  new String(readBytes, "UTF-8");
        logger.info("read response:" + response);
        return response;
    }
}
