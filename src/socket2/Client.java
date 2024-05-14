package socket2;

import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class Client extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea messages;
	
	Socket socket;
	DataInputStream dataInputStream ;
	DataOutputStream dataOutputStream;
	Scanner sc;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public Client() throws UnknownHostException, IOException {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 254);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		messages = new JTextArea();
		messages.setLineWrap(true);
		messages.setWrapStyleWord(true);
		messages.setBounds(10, 10, 416, 199);
		contentPane.add(messages);
		
		
		Thread run = new Thread(() -> run());
		run.start();
	}


	@Override
	public void run() {
		final String SERVER_ADDRESS = "localhost";
        final int PORT = 12345; 
        try
        {
        	socket = new Socket(SERVER_ADDRESS, PORT);
            dataInputStream = new DataInputStream(socket.getInputStream());

            while (true) 
            {
               int i = dataInputStream.read();
               messages.append(i + ", ");
            }
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

}
