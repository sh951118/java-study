package echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EchoServer {

	private static final int PORT = 26;
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩: Socket Address(IP Address + Port) Binding
			serverSocket.bind(new InetSocketAddress("127.0.0.1", PORT));
			log("Server Starts...[port:" + PORT + "]");
			
			//3. accept
			while(true) {
				Socket socket = serverSocket.accept();
				new EchoServerReceiveThread(socket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(String log) {
		Date now = new Date();
		SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd-hh시 mm분 ss초");
		String day = sdfd.format(now);
		
		System.out.println("[server#" + Thread.currentThread().getId() + "]" + log + "\t" + day);
	}
}
