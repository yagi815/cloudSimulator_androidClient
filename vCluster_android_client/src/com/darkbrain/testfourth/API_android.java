package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.DroidGap;

import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class API_android {

	
	private API_vcluster API = null;
	private tiny_vCluster  t_vCluster = null;
	private Simulator simulator = null;
	
	private WebView mAppView;
	private DroidGap mGap;
	
	public API_android(DroidGap gap, WebView view) {
		// TODO Auto-generated constructor stub
		
		mAppView = view;
	    mGap = gap;
	    
		simulatorStart();
		API = new API_vcluster(simulator);
		t_vCluster = new tiny_vCluster(API);
	}

	// ******************************************************************
	// vClustger API for Androied
	// 00:param1,param2......
	// ******************************************************************
	
	public int getMaxQueue() {		
		return 1000; 
	}	
	public int getRunningJobs() {
		int runningJobs = API.getBusyVmList("-").size();
		return runningJobs;
	}
	public int getWatingJobs() {
		return t_vCluster.getWatingQueue();
	}
	public List getAvaiableCloudList() {
		List list = new ArrayList();
		list.add("vSimulator");
		list.add("fermiCloud");
		list.add("amazon");
		list.add("gCloud");
		return list;
	}
	
	public List getAccessCloudList() {
		List list = new ArrayList();
		list.add("vSimulator");
		list.add("fermiCloud");
		return list;
	}
	/*
	public List getRunningHostList(String hostMame) {
		return API.getRunningHostList();
	}
	public List getCurrentBusyVmList(String hostMame) {
		return API.getBusyVmList(hostMame);
	}	
	public List getCurrentRunningVmList(String hostMame) {
		return API.getRunningVmList(hostMame);
	}	
	public List getCurrentIdleVmList(String hostMame) {
		return API.getIdleVmList(hostMame);
	}	
	public List getCurrentAvailableVmList(String hostMame) {
		return API.getAvailableVmList(hostMame);
	}	
	public List getCurrentUnhealthyVmList(String hostMame) {
		return API.getUnhealthyVmList(hostMame);
	}	
	*/
	public String getRunningHostList(String hostMame) {
        return API.newgetRunningHostList();
	}
	public String getCurrentBusyVmList(String hostMame) {
		return API.newgetBusyVmList(hostMame);
	}	
	public String getCurrentRunningVmList(String hostMame) {
		return API.newgetRunningVmList(hostMame);
	}	
	public String getCurrentIdleVmList(String hostMame) {
		return API.newgetIdleVmList(hostMame);
	}	
	public String getCurrentAvailableVmList(String hostMame) {
		return API.newgetAvailableVmList(hostMame);
	}	
	public  String getCurrentUnhealthyVmList(String hostMame) {
		return API.newgetUnhealthyVmList(hostMame);
	}
	public List getImageRepositoryList(String hostMame) { 
		List list = new ArrayList();
		list.add("CDF_OS_Image");
		list.add("Belle_OS_Image");
		list.add("Ligo_OS_Image");
		return list;
	}		
	public void demoStart1(){
		new Thread(new Runnable() {
			
			public void run() {		
				t_vCluster.demoStart1();					
			}			
		}).start();			
	}	
	public void demoStart2(){
		new Thread(new Runnable() {
			
			public void run() {		
				t_vCluster.demoStart2();					
			}			
		}).start();			
	}	
	public void demoStop(){				
			t_vCluster.demoStop();		
	}
	public void simulatorStart(){
		simulator = new Simulator();
	}
	public String getTotalVMs(){
		return API.getTotalVMs();
	}
	public void migrationVM(int srcHostIdx, int srcVmNameIdx, int desHostIdx){
		String srcHostName =  String.format("host%02d",srcHostIdx);
		String srcVmName=  String.format("vm%02d",srcVmNameIdx);
		String desHostName =  String.format("host%02d",desHostIdx);
		
		
		t_vCluster.migrationVM(srcHostName, srcVmName, desHostName);
//		Log.d("aaa","srcHost:"+srcHostName+",srcVmName:"+srcVmName+",desHost:"+desHostName);
		mAppView.loadUrl("javascript:start_reload_second()"); 
	}
	public String getHostStatus(String hostName){
		return API.getHostStatus(hostName);
	}
	public void alertTestJava(){	
		//return "test";
		mAppView.loadUrl("javascript:alertTest()"); 
	}
	public void alertJava(){	
		//return "test";
		//mAppView.loadUrl("javascript:alertTest()"); 
		//Toast.makeText(API_android.this,String.valueOf("test"), Toast.LENGTH_SHORT).show();
		Toast.makeText(mGap,"Demo has been started!", Toast.LENGTH_SHORT).show();  
		//Toast.makeText(ButtonEdit.this,str,Toast.LENGTH_SHORT).show();
	}

}
