/**
 * Created by wuhao on 2018/6/8.
 */
import com.lsh.model.NsHead;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
public class ServerThread extends Thread {

    private Socket socket = null;
    private InputStream inputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;
    private OutputStream outputStream = null;
    private PrintStream printStream = null;
    private PrintStream ps = null;
    private String a;
    private String b = "地瓜地瓜,我是土豆";

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            inputStream = socket.getInputStream();

            byte[] readHead = new byte[8];
            int result = inputStream.read(readHead);
            System.out.print("---------"+result);
            if(result ==-1){
                return ;
            }
            NsHead head = new NsHead();
            head.unpack(readHead);
            byte[] readBytes = new byte[head.body_len];
            int offset = 0;
            int leftByteNum = head.body_len;
            while (leftByteNum > 0) {
                int len = inputStream.read(readBytes, offset, leftByteNum);
                leftByteNum -= len;
                offset += len;
            }
            System.out.print(new String(readBytes, "UTF-8"));

            byte[] writeBytes = "ok".getBytes();
            NsHead writeHead = new NsHead();

            writeHead.body_len = writeBytes.length + 4;

            outputStream = socket.getOutputStream();

            ps = new PrintStream(outputStream);

            ps.write(writeHead.pack());
            ps.write(writeBytes);

            ps.flush();
            //千万不能忘记关闭输入输出流!!否则不会出结果!!
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printStream = new PrintStream(outputStream);
        try {
            printStream.write(b.getBytes());
            socket.shutdownOutput();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
