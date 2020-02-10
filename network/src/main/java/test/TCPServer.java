package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TCPServer {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			//1. 서버소켓 생성
			serverSocket = new ServerSocket();
			
			//1-1 Time-Wait 시간에 소켓에 포트번호 할당을 가능하게 하기 위해서
			serverSocket.setReuseAddress(true);
			
			//2. 바인딩(Socket Address(IP Address + Port) Binding)
			serverSocket.bind(new InetSocketAddress("127.0.0.1", 23));
			
			//3. accept
			Socket socket = serverSocket.accept(); // blocking(스레드 slip상태, 동기샃태)
			InetSocketAddress remoteInetSocketAddress = (InetSocketAddress)socket.getRemoteSocketAddress();
			
			InetAddress remoteInetAddress = remoteInetSocketAddress.getAddress(); // 아이피 주소 받아오기
			String remoteHostAddress = remoteInetAddress.getHostAddress(); // 아이피 주소를 문자로 변환
			int remotePort = remoteInetSocketAddress.getPort();  //호스트 주소(포트 번호)
			
			System.out.println("[server]connected by Client[" + 
								remoteHostAddress + ":" +
								remotePort + "]");
			
			try {
				//4.IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
			
				while(true) {
					//5.데이터 읽기
					Date now = new Date();
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);  // blocking
					
					SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd-hh시 mm분 ss초");
					String day = sdfd.format(now);
					if(readByteCount == -1) {
						//client에서 정상종료
						System.out.println("[server]Closed by Client");
						break;
					}
			
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server]received : " + data + "  " + day);
			
					//6.데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}	
			}catch(SocketException e) {
				System.out.println("[server] sudden closed by Client");
			}catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(socket != null && !socket.isClosed()) {
						socket.close();
					}
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}