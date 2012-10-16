package com.example.test_vcluster;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import kisti.vSimulator.API.API_android;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//		StrictMode.setThreadPolicy(policy);
        
        
        Button btn1 = (Button)findViewById(R.id.btn1);
        Button btn2 = (Button)findViewById(R.id.btn2);
        Button btn3 = (Button)findViewById(R.id.btn3);
//        Button btn4 = (Button)findViewById(R.id.btn4);
        final TextView txt1 = (TextView)findViewById(R.id.txt1);
        final API_android api = new API_android();
        
        btn1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("", "cliend");
				txt1.append("simulatorStart");
				api.simulatorStart();
				
//				String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//				 Toast.makeText(MainActivity.this, imgPath, 0).show();
				Toast.makeText(MainActivity.this, "simulaotr Start", 0).show();
			}
		});
        btn2.setOnClickListener(new OnClickListener() {
			
        	
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txt1.append("startDemo");
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						api.demoStart();
						Log.d("main", ""+api.getRunningJobs());
					}
				}).start();
				
				Toast.makeText(MainActivity.this, "demo Start", 0).show();
				
				
				
			}
		});
        
        btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						while (true) {

//							Log.d("disp", "--------------------------------");
//
////							Log.d("disp", ""+api.getRunningJobs());
////							txt1.append(""+api.getRunningJobs());
//							Log.d("disp","\trunnginjobs:" + api.getRunningJobs());
//							Log.d("disp","\tidlevm:"
//									+ api.getCurrentIdleVmList("-").size());
//							Log.d("disp","\trunningHost:"
//									+ api.getRunningHostList("-").size());
//							Log.d("disp","\n\n");
//							Log.d("disp", "--------------------------------");
//							
							
							List list = api.getRunningHostList("-");
							String runningHost="";
							for (int i =0 ; i<list.size(); i++){
								runningHost += (String)list.get(i)+",";
							}
							String dumpCloudStatsus = 
										"====================================================================\n"
										+ "running Hosts :" + runningHost
										+ "\ntotal VMs :"
										+ api.getTotalVMs()
										+ "\n Running Vms :"
										+ api.getCurrentRunningVmList("-").size()
										+ "\n Availablet Vms :"
										+ api.getCurrentAvailableVmList("-").size()
										+ "\ntotal Running Job :"
										+ api.getRunningJobs()
										+ "\ntotal idle VMs :"
										+ api.getCurrentIdleVmList("-").size() + "\n"
										+ "\n===================================================================\n";
								
							Log.d("disp", dumpCloudStatsus);
								
							txt1.append(dumpCloudStatsus);
							
//							
//							try {
//								Thread.sleep(3000);
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						}
//					}
//				}).start();
				Toast.makeText(MainActivity.this, "display start", 0).show();
			}
		});
        
     
    }
    
    public void fileTestATres_raw(){
    	 try {
				FileOutputStream fos = openFileOutput("myText.txt", Context.MODE_WORLD_WRITEABLE);
				    //문자열을 바이트 단위로 변환해서 얻어온다.
				    byte[] strByte="hello World".getBytes();
				    //스트림을 이용해서 파일에 기록한다.
				    
				    fos.write(strByte);
				    //close해주어야 파일이 생성된다.
				    fos.close();
				    Toast.makeText(MainActivity.this, "파일 저장 성공", 0).show();
				    
				    Thread.sleep(3000);
				    
				    FileInputStream fis = openFileInput("myText.txt");
				    //바이트 단위로 읽어오기 위한 배열 준디
				    byte[] buffer = new byte[fis.available()];
				    fis.read(buffer);
				    //byte 배열을 문자열로 변환한다.
				    String readedStr = new String(buffer);
				    //읽어온 문장을 출력하기
//				    result.setText(readedStr);
				    
				    Log.d("Main", readedStr);
				    Toast.makeText(MainActivity.this, fis.toString(), 0).show();
				    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    public void fileRead(){
		
			try {

				InputStream fis = getResources().openRawResource(R.raw.policyfile);
//				InputStream fis = getResources().openRawResource("policyFile");

				byte[] data = new byte[fis.available()];
				String line = "";
				String[] torken = new String[2];

				while (fis.read(data) != -1) {
					
			
//				while ( (line = br.readLine()) !=null ) {
					line = new String(data);
					Log.d("main", line);
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
   }
    
    
   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
