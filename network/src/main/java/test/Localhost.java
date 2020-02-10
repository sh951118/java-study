package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class Localhost {

	public static void main(String[] args) {
		
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String hostname = inetAddress.getHostName();
			String hostAddress = inetAddress.getHostAddress();
			byte[] addresses = inetAddress.getAddress();
			
			for(byte address : addresses) {
														  //int addres = 0x000000ff;
				System.out.println(address & 0x000000ff); //ip주소를 뽑을 때, 음수를 제거하고 뽑기 위한 비트연산
														  //0x000000ff는 int형임으로 바로 println에 집어 넣는다
			}
			System.out.println(hostname);
			System.out.println(hostAddress);
			
		} catch (UnknownHostException e) {
			
			e.printStackTrace();
		}
		
	}

}
