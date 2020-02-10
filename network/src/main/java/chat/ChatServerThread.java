package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ChatServerThread extends Thread {

	private String nickname;
	private Socket socket;
	private int count = 0;
	List<PrintWriter> listWriters;

	public ChatServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	// 1. Remote Host Information
	// 2. 스트림 얻기

	@Override
	public void run() {
		try {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter printWriter = new PrintWriter(
					new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);

			// 3. 요청 처리
			while (true) {
				String request = bufferedReader.readLine();
				if (request == null) {
					String data2 = (nickname + "님이 채팅방을 나갔습니다.");
					broadcast(data2);
					break;
				}

				// 4. 프로토콜 분석
				String[] tokens = request.split(":");

				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter, count);
				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);
				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter, count);
				} else {
					ChatServer.log("에러:알수 없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (IOException e) {
			ChatServer.log("클라이언트로 부터 연결 끊김");
		}

	}

	private void doJoin(String nickName, PrintWriter writer, int count) {
		this.nickname = nickName;

		String data = nickName + "님이 참여하였습니다.";
		count++;
		this.count = count;
		broadcast(data);

		/* writer pool에 저장 */
		addWriter(writer);

		// ack
		writer.println("join:ok");
		writer.flush();
		if (this.count == 1) {
			writer.println("채팅방에 아무도 없습니다.");
		}

	}

	private void addWriter(PrintWriter writer) {
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}

	private void broadcast(String data) {

		synchronized (listWriters) {

			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				// printWriter.flush();
			}

		}

	}

	private void doMessage(String message) {

		/* 잘 구현 해 보기 */
		broadcast(nickname + ":" + message);
	}

	private void doQuit(PrintWriter writer, int count) {
		removeWriter(writer);

		String data = nickname + "님이 퇴장 하였습니다.";
		count--;
		this.count = count;
		broadcast(data);
		if (this.count == 1) {
			writer.println("채팅방에 아무도 없습니다.");
		}
	}

	private void removeWriter(PrintWriter writer) {

		/* 잘 구현 해보기 */
		synchronized (listWriters) {
			listWriters.add(writer);
		}
	}
}
