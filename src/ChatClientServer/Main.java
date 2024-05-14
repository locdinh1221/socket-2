package ChatClientServer;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {
		ChatServer a = new ChatServer();
		ChatClient b = new ChatClient();
		ChatClient c = new ChatClient();
		b.setVisible(true);
		c.setVisible(true);
		b.setLocationRelativeTo(null);
	}
}
