package chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatClient {
	private static final int SERVER_PORT = 46;
	
	public static void main(String[] args) {
		Scanner scanner = null;
		Socket socket = null;
		List<PrintWriter> listWriters = new ArrayList<PrintWriter>();
		try {
			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결

			socket.connect( new InetSocketAddress("0.0.0.0", SERVER_PORT) );
			log("채팅방에 입장하였습니다.");
			// 4. reader/writer 생성

			PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
			// 5. join 프로토콜
			System.out.print("닉네임을 작성해 주세요 >> ");
			String nickname = scanner.nextLine();
			printwriter.println("join:" + nickname);
			printwriter.flush();

			// 6. ChatClientReceiveThread 시작
			new ChatClientThread(socket, listWriters).start();
			// 7. 키보드 입력 처리
			while (true) {
				String input = scanner.nextLine();

				if ("quit".equals(input) == true) {
					// 8. quit 프로토콜 처리
					break;
				} else {
					// 9. 메시지 처리
					printwriter.println("message:" + input);
				}
			}

		} catch (IOException ex) {
			log("error:" + ex);
		} finally {
			//자원정리
			try {
				if(scanner != null) {
					scanner.close();
				}
				
				if(socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}

	}
	public static void log(String log) {
		System.out.println(log);
	}	

}
