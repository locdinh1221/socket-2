package ChatClientServer;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ChatServer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Set<DataOutputStream> list;
	private ServerSocket server;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatServer frame = new ChatServer();
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
	 */
	public ChatServer() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 247, 105);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Server is running and waiting for clients...");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 10, 213, 48);
		contentPane.add(lblNewLabel);
		server = new ServerSocket(12345);
		clientHandler();
		list = new HashSet<DataOutputStream>();
	}
	
	public void getClientMessages(Socket client)
	{
		Thread getMessage = new Thread(() -> 
		{
			while (true)
			{
				String str = null;
				try {
					DataInputStream dis = new DataInputStream(client.getInputStream());
						str = dis.readUTF();
						System.out.println("nhan : " + str);
						if(str != "")
							broadCast(str);
							else
								System.out.println("ERROR");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});getMessage.start();
	}
	
	public void broadCast(String str)
	{
		Thread d = new Thread(() -> 
		{
				for(DataOutputStream a : list)
					try {
						a.writeUTF(str);
						Thread.sleep(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		});d.start();
	}
	
	public void clientHandler()
	{
		Thread connectionAccept = new Thread(() -> 
		{
			while (true)
			{
				try {
					Socket newClient = server.accept();
					System.out.println("Client connected: " + newClient);
					DataOutputStream dos = new DataOutputStream(newClient.getOutputStream());
					list.add(dos);
					getClientMessages(newClient);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});connectionAccept.start();
	}
}
