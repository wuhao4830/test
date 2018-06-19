
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by wuhao on 2018/6/8.
 */
public class Cilent {
    private static String info = " 土豆土豆,我是地瓜";
    private static String localhost;
    private static byte[] b;
    private static String a;

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socket = new Socket(localhost, 9527);
        OutputStream outputStream = socket.getOutputStream();
        PrintStream ps = new PrintStream(outputStream);
        b = info.getBytes();
        ps.write(b);
        ps.flush();
        //千万不能忘记关闭输入输出流!!否则不会出结果!!
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        while((a = bf.readLine()) != null){
            System.out.println("服务器说:"+a);
        }
        socket.shutdownInput();

        bf.close();
        inputStream.close();
        ps.close();
        outputStream.close();
        socket.close();
    }
}
