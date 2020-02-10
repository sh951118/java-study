package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatClientThread extends Thread {

	private Socket socket;
	List<PrintWriter> listWriters;

	public ChatClientThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		try {
			BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (true) {

				String data = bufferedreader.readLine();
				if (data == null) {
					ChatServer.log("closed by server");
					break;
				}
				ChatClient.log(data);
			}
		} catch(SocketException e) {
			ChatClient.log("클라이언트가 끝났습니다.");
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && !socket.isClosed()) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
