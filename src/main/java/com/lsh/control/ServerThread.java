package com.lsh.control;

import com.lsh.enums.SocketType;
import com.lsh.model.NsHead;
import com.lsh.utils.JsonUtils;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Created by wuhao on 2018/6/8.
 */
public class ServerThread extends Thread {

    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private PrintStream ps = null;;
    private String a;
    private String b = "地瓜地瓜,我是土豆";

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            while (true) {
                Thread.sleep(3500);
                inputStream = socket.getInputStream();
                byte[] readHead = new byte[8];
                int result = inputStream.read(readHead);
                System.out.print("----" + result);
                if (result == -1) {
                    return;
                }
                NsHead head = new NsHead();
                head.unpack(readHead);
                byte[] readBytes = new byte[head.body_len];
                int offset = 0;
                int leftByteNum = head.body_len;
                while (leftByteNum > 0) {
                    int len = socket.getInputStream().read(readBytes, offset, leftByteNum);
                    leftByteNum -= len;
                    offset += len;
                }
                System.out.print(new String(readBytes, "UTF-8"));

                byte[] writeBytes ="{\"head\":{\"status\":0,\"message\":\"\",\"type\":1,\"timestamp\":1529060266,\"messageId\":\"9e41ddb529433c795548d3bb39bcb404\",\"gatewayId\":\"10\"},\"body\":{\"cmd\":\"openDoor\",\"cmdParams\":\"\",\"macId\":\"6413335619551891456\"}}".getBytes();
                NsHead writeHead = new NsHead();

                writeHead.body_len = writeBytes.length + 4;

                outputStream = socket.getOutputStream();


                outputStream.write(writeHead.pack());
                outputStream.write(writeBytes);

                outputStream.flush();


            }

        }catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }


}
