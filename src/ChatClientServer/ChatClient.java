package ChatClientServer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class ChatClient extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txt,name;
	private JButton send,connect;
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
					ChatClient frame = new ChatClient();
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
	public ChatClient() throws UnknownHostException, IOException {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 538, 332);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt = new JTextField();
		txt.setBounds(10, 251, 425, 34);
		contentPane.add(txt);
		txt.setColumns(10);
		
		send = new JButton("SEND");
		send.setBounds(440, 251, 74, 34);
		contentPane.add(send);
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(279, 10, 102, 27);
		contentPane.add(name);
		
		JLabel lblNewLabel = new JLabel("NAME :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(215, 10, 54, 27);
		contentPane.add(lblNewLabel);
		
		connect = new JButton("CONNECT");
		connect.setBounds(391, 10, 123, 27);
		contentPane.add(connect);
		
		messages = new JTextArea();
		messages.setWrapStyleWord(true);
		messages.setLineWrap(true);
		messages.setEditable(false);
		messages.setBounds(10, 44, 504, 197);
		contentPane.add(messages);
		send.addActionListener(this);
		connect.addActionListener(this);
		
		socket = new Socket("localhost",12345);
		dataInputStream = new DataInputStream(socket.getInputStream());
		dataOutputStream = new DataOutputStream(socket.getOutputStream());
		sc = new Scanner(System.in);
		getServerMessages();
		send.setVisible(false);
		txt.setVisible(false);
		getServerMessages();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("SEND"))
		{
			try 
			{
				String str =name.getText() + ": " + txt.getText();
				dataOutputStream.writeUTF(str);
				dataOutputStream.flush();
				txt.setText("");
			} 
			catch (IOException e1) {
				e1.printStackTrace();
			}
			
		};
		if(e.getActionCommand().equals("CONNECT"))
		{
			name.setEnabled(false);
			this.send.setVisible(true);
			this.txt.setVisible(true);
		}
	}
	
	
	public void getServerMessages() {
	    Thread thread = new Thread(() -> {
	        String str;
			while (true) {
				try {
			        str = dataInputStream.readUTF();
			        System.out.println(str);
			        messages.append(str + "\n");
			        Thread.sleep(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
	    });
	    thread.start();
	}
}
