package com.lsh.control;

import com.alibaba.fastjson.JSON;
import com.lsh.data.SocketData;
import com.lsh.model.NsHead;
import com.lsh.model.RelationSocket;
import com.lsh.model.SolBean;
import com.lsh.model.SolSocket;
import com.lsh.utils.IdGenerator;
import com.lsh.utils.NsHeadClient;
import com.lsh.utils.PropsUtils;
import com.lsh.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by wuhao on 2018/6/8.
 */
public class ServerThread extends Thread {

    private Socket socket = null;

    private static final Logger logger = LoggerFactory.getLogger(ServerThread.class);

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
            while (true) {
                try {
                    InputStream inputStream = socket.getInputStream();
                    byte[] readHead = new byte[8];
                    int result = inputStream.read(readHead);
                    if (result == -1) {
                        continue;
                    }
                    logger.info("reading");
                    NsHead head = new NsHead();
                    head.unpack(readHead);
                    if (!Integer.valueOf(PropsUtils.get("socket.head.id")).equals(head.id)) {
                        break;
                    }
                    byte[] readBytes = new byte[head.body_len];
                    int offset = 0;
                    int leftByteNum = head.body_len;
                    while (leftByteNum > 0) {
                        int len = socket.getInputStream().read(readBytes, offset, leftByteNum);
                        leftByteNum -= len;
                        offset += len;
                    }
                    String response = new String(readBytes, "UTF-8");

                    logger.info("response value:" + response);
                    SolSocket solSocket = JSON.parseObject(response, SolSocket.class);
                    if (solSocket.getStatus() == 1) {
                        SolBean body = solSocket.getBody();
                        int type = body.getType();
                        if (type == 1) {
                            //是用户注册
                            RelationSocket relationSocket = new RelationSocket(solSocket.getRelationId(), socket);
                            SocketData.saveSocket(relationSocket, solSocket.getUid());
                            jsonCall(ResultUtils.SUCCESS(new SolBean(), solSocket.getRelationId(), IdGenerator.getInstance().genCode("")));
                            continue;
                        }

                        //是下发到客户端的数据,根据uid获取到socket连接
                        RelationSocket relationSocket = SocketData.getSocket(solSocket.getUid());
                        if (relationSocket != null) {
                            String relationId = relationSocket.getRelationId();
                            if (relationId.equals(solSocket.getRelationId())) {
                                SolBean solBean = new SolBean();
                                solBean.setType(3);
                                solBean.setCmdParams(body.getCmdParams());
                                jsonCall(ResultUtils.SUCCESS(solBean, solSocket.getUid(), IdGenerator.getInstance().genCode("")), relationSocket);
                                logger.info("send end");
                            }
                        }
                        inputStream.close();
                        socket.close();

                    }
                    break;
                }catch (SocketException exception){
                    logger.error(exception.getMessage(),exception);
                    break;
                } catch (Exception e){
                    logger.error(e.getMessage(),e);

                }
            }
    }


    public void jsonCall(String jsonInput) throws Exception{
        logger.info("send jsonInput:" + jsonInput);
        NsHead writeHead = new NsHead();
        OutputStream outputStream = null;
        try {
            byte[] writeBytes = jsonInput.getBytes("UTF-8");
            writeHead.body_len = writeBytes.length + 4;

            outputStream = socket.getOutputStream();
            byte [] head = writeHead.pack();
            byte [] byteOutPut = NsHeadClient.byteMerger(head, writeBytes);
            outputStream.write(byteOutPut);
            outputStream.flush();

        }catch (Exception e){
            throw e;
        }
    }

    public void jsonCall(String jsonInput,RelationSocket relationSocket) throws Exception{
        logger.info("send jsonInput:" + jsonInput);
        NsHead writeHead = new NsHead();
        OutputStream outputStream = null;
        try {
            byte[] writeBytes = jsonInput.getBytes("UTF-8");
            writeHead.body_len = writeBytes.length + 4;
            Socket client = relationSocket.getSocket();
            outputStream = client.getOutputStream();
            byte [] head = writeHead.pack();
            byte [] byteOutPut = NsHeadClient.byteMerger(head, writeBytes);
            outputStream.write(byteOutPut);
            outputStream.flush();

        }catch (Exception e){
            throw e;
        }
    }


}
