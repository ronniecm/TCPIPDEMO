package demo;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
	private ServerSocket server;

	public Server(String ipAddress) throws Exception {
		if (ipAddress != null && !ipAddress.isEmpty())
			this.server = new ServerSocket(0, 1, InetAddress.getByName(ipAddress));
		else
			this.server = new ServerSocket(0, 1, InetAddress.getLocalHost());
	}

	private void listen() throws IOException {
		Socket user1 = server.accept();
		System.out.println("User 1 connected from: " + user1.getLocalAddress().toString());
		String dataFromUser1 = null;
		Socket user2 = server.accept();
		System.out.println("User 2 connected from: " + user2.getLocalAddress().toString());
		String dataFromUser2 = null;
		
		BufferedReader fromUser1 = new BufferedReader(new InputStreamReader(user1.getInputStream()));
		BufferedReader fromUser2 = new BufferedReader(new InputStreamReader(user2.getInputStream()));
		PrintWriter toUser1 = new PrintWriter(user1.getOutputStream());
		PrintWriter toUser2 = new PrintWriter(user2.getOutputStream());
		while(true) {
			if(fromUser1.ready())
			{
				toUser2.println(fromUser1.readLine());
				toUser2.flush();
			}
			
			if(fromUser2.ready()) {
				toUser1.println(fromUser2.readLine());
				toUser1.flush();
			}
		}
	}

	public InetAddress getSocketAddress() {
		return this.server.getInetAddress();
	}

	public int getLocalPort() {
		return server.getLocalPort();
	}
	
	public static void main(String[] args) throws Exception {
        Server app = new Server(args[0]);
        System.out.println("\r\nRunning Server: " + 
                "Host=" + app.getSocketAddress().getHostAddress() + 
                " Port=" + app.getLocalPort());
        
        app.listen();
    }
}
