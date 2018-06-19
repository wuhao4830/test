/**
 * Created by wuhao on 2018/6/8.
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static int count;
    private static Socket socket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9527);
        System.out.println("服务器已启动,等待客户连接...");
        while(true){
            socket = serverSocket.accept();
            ServerThread st = new ServerThread(socket);
            st.start();
            System.out.println("这是第"+(count++)+"位用户");
            System.out.println("此用户的IP地址为"+socket.getInetAddress().getHostAddress());
        }
    }
}
