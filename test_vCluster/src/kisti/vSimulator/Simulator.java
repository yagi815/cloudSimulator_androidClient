package kisti.vSimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

/**
 * <pre>
 * pipeTest
 *   |_ sumulator.java
 * 
 * </pre>
 * 
 * Desc :
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 7. 27. 오후 1:34:31
 * @Version:
 * 
 */
public class Simulator {

	private static final int PORT = 7878;

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	
	private ExecuteCommand executeCommand = null;
	private static SimManager simManager = null;
	
	

	public Simulator() {
		
		
		ProcessRequestThread_ process = null;
		executeCommand = new ExecuteCommand();
		
		
		try {
			serverSocket = new ServerSocket(PORT);

			while (true) {

//				System.out.println("waiting client...");
				socket = serverSocket.accept();

				process = new ProcessRequestThread_(socket, executeCommand);
				process.start();

			}
			
		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println(e.toString());
			Log.d("Simulator", e.toString());
		}
	}

	public static void main(String[] args) {
		new Simulator();
	}
}


class ProcessRequestThread extends Thread{
	private Socket socket = null;
	private BufferedReader bufferedReader = null;
	private String receiveString = null;
	private PrintWriter printdWriter = null;
	private Object resultObj = null;
	private ObjectOutputStream oos = null;
	private ExecuteCommand executeCommand = null;
	
	
	
	public ProcessRequestThread(Socket s, ExecuteCommand executeCommand) {
		
		
		
		this.socket = s;
		this.executeCommand = executeCommand;
		
		try {
			// in
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));			
			// out
			oos = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public void run(){
		
		
		try {
			receiveString = bufferedReader.readLine();		
//			System.out.println("receiveString > "+receiveString);
			Log.d("Simulator", "receiveString >"+receiveString);
			resultObj = executeCommand.execute(receiveString);
			
            oos.writeObject(resultObj);
            oos.flush();   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}