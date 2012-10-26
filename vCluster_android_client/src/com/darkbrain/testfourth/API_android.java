package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.DroidGap;

import android.webkit.WebView;

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
	public List getImageRepositoryList(String hostMame) { 
		List list = new ArrayList();
		list.add("CDF_OS_Image");
		list.add("Belle_OS_Image");
		list.add("Ligo_OS_Image");
		return list;
	}		
	public void demoStart(){
		new Thread(new Runnable() {
			
			public void run() {		
				t_vCluster.demoStart();					
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
	public void migrationVM(String srcVM, String desHost){
		API.migrationVirtualMachine(srcVM, desHost);
	}
	public String getHostStatus(String hostName){
		return API.getHostStatus(hostName);
	}

}
