package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;

public class RequestHandler extends Thread {
	private static final String DOCUMENT_ROOT = "./webapp";
	private Socket socket;
	
	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );

			// get IOStream
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			OutputStream outputStream = socket.getOutputStream();

			String request = null;
			while(true) {
				String line = br.readLine();
				
				// 브라우저가 연결을 끊음
				if(line == null) {
					break;
				}
				
				// Request(요청)의 헤더만 읽음
				if("".equals(line)) {
					break;
				}
				
				// Header의 첫번째 라인만 읽음
				if(request == null) {
					request = line;
					break;
				}
			}

			consoleLog(request);

			String[] tokens = request.split(" ");
			if("GET".equals(tokens[0])) {
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			} else { // [POST, DELETE, PUT], HEAD, CONNECT
				String Uri = "/error/400.html";
				File file2 = new File(DOCUMENT_ROOT + Uri);
				byte[] body2 = Files.readAllBytes(file2.toPath());
				
				outputStream.write("HTTP/1.1 400 Bad Request\r\n".getBytes());
				outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes());
				outputStream.write("\r\n".getBytes());
				outputStream.write(body2);
			}

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}
				
			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	private void responseStaticResource(
		OutputStream outputStream,
		String uri,
		String protocol) throws IOException {
		
		if("/".equals(uri)) {
			uri = "/index.html";
		}
		
		File file = new File(DOCUMENT_ROOT + uri);
		if(!file.exists()) {
			//응답 예시
			response404Error(outputStream, protocol);
			return;
		}
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());
		
		// 응답
		outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) );
		outputStream.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" ) );
		outputStream.write( "\r\n".getBytes() );
		outputStream.write( body );
	}
	
	private void response404Error(OutputStream outputStream, String protocol) throws IOException {
		
		String Uri = "/error/404.html";
		File file2 = new File(DOCUMENT_ROOT + Uri);
		byte[] body2 = Files.readAllBytes(file2.toPath());
		
		outputStream.write("HTTP/1.1 404 Not Found\r\n".getBytes());
		outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes());
		outputStream.write("\r\n".getBytes());
		outputStream.write(body2);
	}

	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}
}
