package socket2;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public Server()
	{
    final int PORT = 12345;
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept(); 
                System.out.println("Client connected: " + clientSocket);
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

                new Thread(() -> {
                    try {
                        for (int i = 1; i <= 1000; i++) {
                            dataOutputStream.write(i);
                            Thread.sleep(1000); 
                        }
                        dataOutputStream.close();
                        clientSocket.close();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}