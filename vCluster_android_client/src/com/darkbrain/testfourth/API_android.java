package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;

import org.apache.cordova.DroidGap;

import android.webkit.WebView;
//import com.example.test_vcluster.MainActivity;
//import kisti.vSimulator.Simulator;
//
//
/**
 * <pre>
 * API.android
 *   |_ API_android.java
 * 
 * </pre>
 * 
 * Desc :  �ȵ���̵� Ŭ���̾�Ʈ�� ž�� �� API ����Ʈ �̴�. <br> 
 * vSimulator �� ����ϴ�  ���� API_vCluster �� �̿��Ѵ�.
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 8. 24. ���� 4:03:35
 * @Version: 
 * 
 */
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
	
	/**
	 * Desc : Job�� ���� �� �ִ� max ť ������ <br>
	 * vCluster ���� ť ����� �����´�. 
	 * 
	 * @Method Name : getMaxQueue
	 * @return Max ť ������ ��ȯ <br> EX) 100
	 * 
	 */
	public int getMaxQueue() {		
		return 1000; // �ӽ� ��.
	}
	/**
	 * Desc : ���� ó�� ���� Job ������ ��ȯ <br>
	 * vCluster ���� �� �о�´�.
	 * 
	 * @Method Name : getRunningJobs
	 * @return ���� �����ϰ� �ִ� Job ���� ��ȯ <br>EX) 80
	 * 
	 */
	public int getRunningJobs() {
//		return 80;
		int runningJobs = API.getBusyVmList("-").size();
		return runningJobs;
	}

	/**
	 * Desc : ť�� ����ϰ��ִ� Job���� ��ȯ <br>
	 * vCluster ���� �� �о�´�. 
	 * @Method Name : getWatingJobs
	 * @return ť�� ������� Job ��  <br> EX) 90
	 * 
	 */
	public int getWatingJobs() {
		return 90;
	}

	/**
	 * Desc : ���� ������ Ŭ���� ����Ʈ ��ȯ
	 * 
	 * @Method Name : getAvaiableCloudList
	 * @return ���ᰡ���� Ŭ���� ����Ʈ ��ȯ <br>
	 *         EX) "vSimulator,fermiCloud,amazon,gCloud" <br>
	 *         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getAvaiableCloudList() {
		List list = new ArrayList();
		list.add("vSimulator");
		list.add("fermiCloud");
		list.add("amazon");
		list.add("gCloud");
		return list;
	}
	/**
	 * Desc : ���� ������ Ŭ���� ����Ʈ
	 * 
	 * @Method Name : getAccessCloudList
	 * @return ���� �������� Ŭ���� ����Ʈ�� ��ȯ <br>
	 *         ex) "vSimulator","amazon" <br>
	 *         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getAccessCloudList() {
		List list = new ArrayList();
		list.add("vSimulator");
		list.add("fermiCloud");
		return list;
	}
	/**
	 * Desc : ���� �������� host����Ʈ (����� on ������..)
	 * 
	 * @Method Name : getRunningHostList
	 * @return ���� �������� host ����Ʈ ��ȯ <br>
	 *         EX)
	 *         "vSimulator-host01, vSimulaotr-host02, fermiCloud-host01, fermiCloud-host02" <br>
	 * 	 	String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getRunningHostList(String hostMame) {
		return API.getRunningHostList();
	}
	public List getCurrentBusyVmList(String hostMame) {
		return API.getBusyVmList(hostMame);
	}
	/**
	 * Desc :���� �������� ����ӽ� ����Ʈ 
	 * 
	 * @Method Name : getCurrentRunningVms
	 * @return ���� �������� ����ӽ� ����Ʈ  <br>
	 * EX) "host03-vm00, host04-vm03", <br>
	 *  String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getCurrentRunningVmList(String hostMame) {
		return API.getRunningVmList(hostMame);
	}
	/**
	 * Desc : ���� ��밡���� (���� �Ǿ����� job�� �������� ����..) ����ӽ� ����Ʈ
	 * 
	 * @Method Name : getCurrentIdleVmList
	 * @return job�� ���డ���� ����ӽ� ����Ʈ ��ȯ
	 * <br>         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getCurrentIdleVmList(String hostMame) {
		return API.getIdleVmList(hostMame);
	}
	/**
	 * Desc : ���� launch ������ ����ӽ� ����Ʈ 
	 * 
	 * @Method Name : getCurrentAvailableVmList
	 * @return launch ������ vm ����Ʈ
	 * <br>         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getCurrentAvailableVmList(String hostMame) {
		return API.getAvailableVmList(hostMame);
	}
	/**
	 * Desc : ���� �������̳� ����� �Ұ��� ����ӽ� ����Ʈ
	 * 
	 * @Method Name : getCurrentUnhealthyVms
	 * @return ��� �Ұ��� ����ӽ� ���� ����Ʈ
	 * <br>         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getCurrentUnhealthyVmList(String hostMame) {
		return API.getUnhealthyVmList(hostMame);
	}
	/**
	 * Desc : vm��� ����� OS �̹��� ����Ʈ ���
	 * 
	 * @Method Name : getImageRepositoryList
	 * @return ��� ������ OS �̹��� ����Ʈ ���
	 * <br>         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getImageRepositoryList(String hostMame) { 
		List list = new ArrayList();
		list.add("CDF_OS_Image");
		list.add("Belle_OS_Image");
		list.add("Ligo_OS_Image");
		return list;
	}	
	/**
	 * Desc : ���� ����
	 * @Method Name : demoStart
	 * 
	 */
	public void demoStart(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {		
				t_vCluster.demoStart();
					
			}
			
		}).start();
			
		
	}
	/**
	 * Desc : ���� ����
	 * @Method Name : demoStop
	 * 
	 */
	public void demoStop(){
	
			
			t_vCluster.demoStop();
		
	}
	public void simulatorStart(){
		simulator = new Simulator();
	}
	public String getTotalVMs(){
		return API.getTotalVMs();
	}
	/**
	 * Desc : 1.1. 0-RunningVM, 1-IdleVM, 2-UnHealthyVM, 3-availableVM

	 * @Method Name : getHostStatus
	 * @param hostName
	 * @return
	 * 
	 */
	public String getHostStatus(String hostName){	
		
//		Log.d("test", hostName);
		return API.getHostStatus(hostName);
	}
//	public boolean isRunSumlator(){
//		if (simulator != null) {
//			return true;
//		}else{
//			return false;
//		}
//			
//	}


}
