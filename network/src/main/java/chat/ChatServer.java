package chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatServer {
	private static final int PORT = 29;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		 List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//2. 바인딩: Socket Address(IP Address + Port) Binding
			serverSocket.bind(new InetSocketAddress("0.0.0.0", PORT));
			log("서버 시작....[port:" + PORT + "]");
			
			//3. accept
			while(true) {
				Socket socket = serverSocket.accept();
				new ChatServerThread(socket, listWriters).start();
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
		
		System.out.println(log + "\t" + day);
	}

}
