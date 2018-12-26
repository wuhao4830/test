package com.lsh.model;

import com.alibaba.fastjson.JSON;
import com.lsh.utils.PropsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by wuhao on 2018/5/31.
 */
public class NsHead {
    public int id=Integer.valueOf(PropsUtils.get("socket.head.id"));
    public int body_len=0;

    private static final Logger logger = LoggerFactory.getLogger(NsHead.class);


    public NsHead(){

    }

    public byte[] pack(){
        byte packByte[] = new byte[8];
        byte idLeng[] = intToByteArray(id);
        byte bodyLeng [] = intToByteArray(body_len);
        System.arraycopy(bodyLeng, 0, packByte, 0, bodyLeng.length);
        System.arraycopy(idLeng, 0, packByte, bodyLeng.length, idLeng.length);
        return packByte;
    }

    public void unpack(byte[]bytes) throws Exception {
        byte[] idByte = new byte[4];
        byte[] lengthByte = new byte[4];
        System.arraycopy(bytes, 4, idByte, 0, 4);
        System.arraycopy(bytes, 0, lengthByte, 0, 4);
        body_len = byteArrayToInt(lengthByte)-4;
        id=byteArrayToInt(idByte);
    }

    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[] {
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    @Override
    public String toString() {
        return "NsHead{" +
                "id=" + id +
                ", body_len=" + body_len +
                '}';
    }

    public static void main(String args[]) {
        byte a[] = new byte[8];
        a[0]=0;
        a[1]=0;
        a[2]=1;
        a[3]=0;
        a[4]=0;
        a[5]=0;
        a[6]=0;
        a[7]=0;
        byte[] idByte = new byte[4];
        byte[] lengthByte = new byte[4];
        System.arraycopy(a,4,idByte,0,4);
        System.arraycopy(a, 0, lengthByte, 0, 4);
        System.out.print(JSON.toJSONString(idByte) + "++++" + byteArrayToInt(idByte) + "-----" + byteArrayToInt(lengthByte)+"++++" + JSON.toJSONString(lengthByte));

        System.out.print(byteArrayToInt(a));
    }

}