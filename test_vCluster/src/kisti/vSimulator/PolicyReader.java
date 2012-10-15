package kisti.vSimulator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.example.test_vcluster.MainActivity;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;





public class PolicyReader {


	private static String cloudName;		
	private static List hostList;
	private static List vmList;
	private static int maxHost;
	private static int maxVM;
		
	
	private static int jobRunningTime;
	private static int hostRunningTime;
	private static int vmRunningTime;
	
	private static int policyReadingInterval;
	
	private static int pendingTime;
	private static int prologTime;
	private static int shutdownTime;
		
	
	
	
	public PolicyReader() {
		// TODO Auto-generated constructor stub
		readPolicy();
	}
	
	


	//	
	static void disp(){
		System.out.println(cloudName);
		System.out.println("host List :");
		for (int i = 0; i < hostList.size(); i++) {
			System.out.println(hostList.get(i));
		}
		System.out.println("vm List :");
		for (int i = 0; i < vmList.size(); i++) {
			System.out.println(vmList.get(i));
		}
		System.out.println("jobRunningTime:"+jobRunningTime);
		System.out.println("hostRunningTime:"+hostRunningTime);
		System.out.println("vmRunningTime:"+vmRunningTime);
		System.out.println("PolicyReadingInterval:"+policyReadingInterval);
		System.out.println("pendingTime:"+pendingTime);
		System.out.println("prologTime:"+prologTime);
		System.out.println("shutdownTime:"+shutdownTime);
		
		
	}
	
	private static String[] splitTorken(String torken){
		String[] torkens = new String[2];
		torkens = torken.split("[=]");
		return torkens;
	}
	private static boolean readPolicy() {

		String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
//		 Toast.makeText(MainActivity.this, imgPath, 0).show();
		 
		FileReader fr;
		
		try {
			fr = new FileReader(filePath+"/policy.conf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("can not read policy file");
			Log.d("PolicyReader", e.toString());
			
			return false;
		}
		
		
		BufferedReader br = new BufferedReader(fr);		
		String line ="";
		String[] torken = new String[2];
		
				
		try{
			while ( (line = br.readLine()) !=null ) {
//				line = new String(data);


				if (line.length() > 0 &&line.substring(0,1).equals("#")) {
					continue;
				}
				torken = splitTorken(line);

				
				
				
				// [CONFIG]
				if(torken[0].equals("CloudName")){
					cloudName = torken[1];
				}
				if(torken[0].equals("maxHost")){
					maxHost = Integer.parseInt( torken[1]);
				}
				if(torken[0].equals("maxVM")){
					maxVM= Integer.parseInt(torken[1]);
				}
				if(torken[0].equals("hostList")){
					hostList = new ArrayList();
					StringTokenizer strTokenizer = new StringTokenizer(torken[1],",");
					while (strTokenizer.hasMoreTokens()) {
						hostList.add(strTokenizer.nextToken());
					}					
				}
				if(torken[0].equals("vmList")){
					vmList = new ArrayList();
					StringTokenizer strTokenizer = new StringTokenizer(torken[1],",");
					while (strTokenizer.hasMoreTokens()) {
						vmList.add(strTokenizer.nextToken());
					}					
				}				
				// [POLICY]
				if(torken[0].equals("PolicyReadingInterval")){
					policyReadingInterval = Integer.parseInt(torken[1]);
				}				
				if(torken[0].equals("PendingTime")){
					pendingTime = Integer.parseInt(torken[1]);
				}
				if(torken[0].equals("PrologTime")){
					prologTime = Integer.parseInt(torken[1]);
				}
				if(torken[0].equals("ShutdownTime")){
					shutdownTime = Integer.parseInt(torken[1]);
				}
				if(torken[0].equals("JobRunningTime")){
					jobRunningTime = Integer.parseInt(torken[1]);
				}
				
				
							
				
				
				

			}
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return true;
	}
	
	
	
	





	/**
	 * Desc :
	 * @Method Name : main
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		splitTest("create_tables");
		
		readPolicy();
		disp();
		
	}




	public static String getCloudName() {
		return cloudName;
	}




	public static List getHostList() {
		return hostList;
	}




	public static List getVmList() {
		return vmList;
	}




	public static int getMaxHost() {
		return maxHost;
	}




	public static int getMaxVM() {
		return maxVM;
	}




	public static int getJobRunningTime() {
		return jobRunningTime;
	}




	public static int getHostRunningTime() {
		return hostRunningTime;
	}




	public static int getVmRunningTime() {
		return vmRunningTime;
	}




	public static int getPolicyReadingInterval() {
		return policyReadingInterval;
	}




	public static int getPendingTime() {
		return pendingTime;
	}




	public static int getPrologTime() {
		return prologTime;
	}




	public static int getShutdownTime() {
		return shutdownTime;
	}




	

	
	
	
	
	

}
