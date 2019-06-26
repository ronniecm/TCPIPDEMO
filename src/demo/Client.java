package demo;

import java.util.*;
import java.net.*;
import java.io.*;

public class Client {
	private Socket client;
	private Scanner sc;
	private PrintWriter out;
	private BufferedReader br;

	public Client(InetAddress serverAddress, int serverPort) throws IOException {
		client = new Socket(serverAddress, serverPort);
		sc = new Scanner(System.in);
		out = new PrintWriter(client.getOutputStream(), true);
		br = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}

	public void start() throws IOException {
		String input;
		while (true) {
			if (br.ready())
				System.out.println("From other user: " + br.readLine());
			
			input = sc.nextLine();
			if (!input.equals("")) {
				out.println(input);
				out.flush();
			}

		}
	}

	public static void main(String[] args) throws Exception {
		Client client = new Client(InetAddress.getByName(args[0]), Integer.parseInt(args[1]));

		System.out.println("\r\nConnected to Server: " + client.client.getInetAddress());
		client.start();
	}
}
