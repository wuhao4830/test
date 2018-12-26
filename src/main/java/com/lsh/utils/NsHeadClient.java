package com.lsh.utils;

import com.lsh.model.NsHead;
import com.lsh.model.SocketBean;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

/**
 * Created by wuhao on 2018/5/31.
 */

public class NsHeadClient {
    private static final Logger logger = LoggerFactory.getLogger(NsHeadClient.class);

    //监听一个端口，获取cmd，参数和设备id，根据命令获取方法
    private  Socket client;

    private NsHeadClient () {

    }

    //单粒模式内部类
    private static class  NsHeadClientSingletonFactory {
        private static NsHeadClient instance = new NsHeadClient();
    }


    public static NsHeadClient getInstance() {
        return NsHeadClientSingletonFactory.instance;
    }

    public void initClient() {
        logger.info("init socket");
        try {
            int serverPort = Integer.valueOf(PropsUtils.get("server.service.port"));
            String serverIp =PropsUtils.get("server.service.ip");
            client = new Socket(serverIp, serverPort);
            logger.info("connect socket success" +"ip:"+ serverIp +",port:"+ serverPort);
        }catch (Exception e){
            logger.info("connect socket fail");
            logger.error(e.getMessage(),e);
        }
    }

    public void checkSocketConn() {
        try {
            client.sendUrgentData(0xFF);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            initClient();
        }
        if(client==null || client.isClosed() || !client.isConnected()){
            //client连接失败或者断开了
            initClient();
        }
    }
    {
       initClient();
    }

    public synchronized void jsonCall(String jsonInput) throws Exception{
        logger.info("send jsonInput:"+jsonInput);
        NsHead writeHead = new NsHead();
        OutputStream outputStream = null;
        try {
            byte[] writeBytes = jsonInput.getBytes("UTF-8");
            writeHead.body_len = writeBytes.length + 4;

            outputStream = client.getOutputStream();
            byte [] head = writeHead.pack();
            byte [] byteOutPut = byteMerger(head,writeBytes);
            outputStream.write(byteOutPut);
            outputStream.flush();

        }catch (Exception e){
            throw e;
        }
    }

    private String readResponse() throws Exception {
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
    public SocketBean getSocketBean() throws Exception{
        try {
            String response  = readResponse();
            if(response==null){
                return null;
            }
            logger.info("response value:" + response);
            JSONObject jsonObject = JSON.parseObject(response);
            SocketBean socketBean = new SocketBean();
            socketBean.setHead((Map)jsonObject.get("head"));
            socketBean.setBody((Map) jsonObject.get("body"));
            return socketBean;

        }catch (Exception e){
            logger.error(e.getMessage(), e);
           throw e;
        }
    }

    public static byte[] byteMerger(byte[] bt1, byte[] bt2){
        byte[] bt3 = new byte[bt1.length+bt2.length];
        System.arraycopy(bt1, 0, bt3, 0, bt1.length);
        System.arraycopy(bt2, 0, bt3, bt1.length, bt2.length);
        return bt3;
    }
}
