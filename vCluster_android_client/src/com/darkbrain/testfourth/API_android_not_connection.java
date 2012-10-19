package com.darkbrain.testfourth;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

import org.apache.cordova.DroidGap;

import android.R.string;
import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.darkbrain.testfourth.API_vcluster;
//import javax.swing.text.AbstractDocument.BranchElement;

/**
 * <pre>
 * API.android
 *   |_ API_android.java
 * 
 * </pre>
 * 
 * Desc : 안드로이드 클라이언트에 탑재 할 API 리스트 이다. <br>
 * vSimulator 를 사용하는 경우는 API_vCluster 를 이용한다.
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 8. 24. 오후 4:03:35
 * @Version:
 * 
 */
public class API_android_not_connection {

	private static final String TAG = null;
	private WebView mAppView;
	private DroidGap mGap;
	private API_vcluster API = null;
	private tiny_vCluster t_vCluster = null;

	private String[] sArrays;
	
	public API_android_not_connection(DroidGap gap, WebView view) {
		// TODO Auto-generated constructor stub
		//API = new public API_vcluster(null, null);
		
		mAppView = view;
	    mGap = gap;
	    
		//t_vCluster = new tiny_vCluster();
	}

	// ******************************************************************
	// vClustger API for Androied
	// 00:param1,param2......
	// ******************************************************************

	/**
	 * Desc : Job을 담을 수 있는 max 큐 사이즈 <br>
	 * vCluster 에서 큐 사이즈를 가져온다.
	 * 
	 * @Method Name : getMaxQueue
	 * @return Max 큐 사이즈 반환 <br>
	 *         EX) 100
	 * 
	 */
	public int getMaxQueue() {
		return 1000; // 임시 값.
	}

	/**
	 * Desc : 현재 처리 중인 Job 개수를 반환 <br>
	 * vCluster 에서 값 읽어온다.
	 * 
	 * @Method Name : getRunningJobs
	 * @return 현재 수행하고 있는 Job 수를 반환 <br>
	 *         EX) 80
	 * 
	 */
	public int getRunningJobs() {
		return 80;
	}

	/**
	 * Desc : 큐에 대기하고있는 Job수를 반환 <br>
	 * vCluster 에서 값 읽어온다.
	 * 
	 * @Method Name : getWatingJobs
	 * @return 큐에 대기중인 Job 수 <br>
	 *         EX) 90
	 * 
	 */
	public int getWatingJobs() {
		return 90;
	}

	/**
	 * Desc : 연결 가능한 클라우드 리스트 반환
	 * 
	 * @Method Name : getAvaiableCloudList
	 * @return 연결가능한 클라우드 리스트 반환 <br>
	 *         EX) "vSimulator,fermiCloud,amazon,gCloud" <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
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
	 * Desc : 현재 연결한 클라우드 리스트
	 * 
	 * @Method Name : getAccessCloudList
	 * @return 현재 연결중인 클라우드 사이트들 반환 <br>
	 *         ex) "vSimulator","amazon" <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getAccessCloudList() {
		List list = new ArrayList();
		list.add("vSimulator");
		list.add("fermiCloud");
		list.add("amazon");
		list.add("gCloud");
		return list;
	}

	/**
	 * Desc : 현재 동작중인 host리스트 (전원이 on 상태인..)
	 * 
	 * @Method Name : getRunningHostList
	 * @return 현재 동작중인 host 리스트 반환 <br>
	 *         EX) "host01, host02, host03" <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getRunningHostList(String hostMame) {
		List list = new ArrayList();
		
		list.removeAll(list);
		
		list.add("host01");
		list.add("host02");
		list.add("host03");
		list.add("host04");
		list.add("host05");
		list.add("host06");
		list.add("host07");
		list.add("host08");
		list.add("host09");
		list.add("host10");
		return list;
		
		
	}

	/**
	 * 호스트 별 vm개 추가//////////////////////////////////////
	 */
	public List getCurrentRunningVmCount(String hostName) {
		//String receiveHostName = hostName;
		Log.d(TAG,"======================="+hostName);
		List list = new ArrayList();
		
		list.clear();
		
		if (hostName.equals("host01")) {
			list.add("5");
			list.add("4");
			list.add("2");
			list.add("1");
		} else if (hostName.equals("host02")) {
			list.add("7");
			list.add("3");
			list.add("1");
			list.add("1");
		} else {
			list.add("6");
			list.add("5");
			list.add("2");
			list.add("1");
		}

		return list;
	}
	/**
	 * 호스트별 vm status추가//////////////////////////////////////
	 */
	public List getCurrentVmStatus(String hostName) {
		List list = new ArrayList();
		list.isEmpty();
		list.clear();  
		
		list.removeAll(list);
		
		if (hostName.equals("host01")) {
			list.add("0");
			list.add("1");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");

		} else if (hostName.equals("host02")) {
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			
		} else {
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
		}
		//String[] sArrays = (String[]) list.toArray(new String[list.size()]);
		return list;
	}

	
	public String getChangetVmStatus(String hostName) {
		
		//Log.d(TAG,"=======================");
		//List list = new ArrayList();
		
		List<String> list = new ArrayList<String>();
		if (hostName.equals("host01")) {
			list.add("0");
			list.add("1");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");

		} else if (hostName.equals("host02")) {
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			list.add("3");
			list.add("2");
			list.add("1");
			list.add("0");
			
		} else {
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
			list.add("0");
			list.add("1");
			list.add("2");
			list.add("3");
		}
        
        String[] sArrays = list.toArray(new String[list.size()]);
        //String[] sArrays = {"0","1","2","3","0"};
		//Log.d(TAG,"======================="+sArrays[0]);
        //return new String[]{"bob", "jim"};
        //Log.d(TAG,"=======================" + names[0]);
        //return names;
		//for(String s : sArrays){
         //   return s;
        //}
		//return sArrays;
		return Arrays.toString(sArrays);
	}


	/**
	 * Desc :현재 동작중인 가상머신 리스트
	 * 
	 * @Method Name : getCurrentRunningVms
	 * @return 현재 동작중인 가상머신 리스트 <br>
	 *         EX) "host03-vm00, host04-vm03", <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentRunningVmList(String hostName) {
		List list = new ArrayList();

		if (hostName.equals("host01")) {
			list.add("host01-vm01");
			list.add("host01-vm06");
			list.add("host01-vm08");
		} else if (hostName.equals("host02")) {
			list.add("host02-vm01");
			list.add("host02-vm06");
			list.add("host02-vm08");
		} else {

		}

		return list;
	}

	/**
	 * Desc : 현재 사용가능한 (생성은 되었으나 job은 수행하지 않은..) 가상머신 리스트
	 * 
	 * @Method Name : getCurrentIdleVmList
	 * @return job을 수행가능한 가상머신 리스트 반환 <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentIdleVmList(String hostName) {
		List list = new ArrayList();

		if (hostName.equals("host01")) {
			list.add("host01-vm01");
			list.add("host01-vm06");
			list.add("host01-vm08");
		} else if (hostName.equals("host02")) {
			list.add("host02-vm01");
			list.add("host02-vm06");
			list.add("host02-vm08");
		} 

		return list;
	}

	/**
	 * Desc : 현재 launch 가능한 가상머신 리스트
	 * 
	 * @Method Name : getCurrentAvailableVmList
	 * @return launch 가능한 vm 리스트 <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentAvailableVmList(String hostName) {
		List list = new ArrayList();

		if (hostName.equals("host01")) {
			list.add("host01-vm02");
			list.add("host01-vm03");
			list.add("host01-vm04");
			list.add("host01-vm05");
			list.add("host01-vm06");
			list.add("host01-vm07");
		} else if (hostName.equals("host02")) {
			list.add("host01-vm02");
			list.add("host01-vm03");
			list.add("host01-vm04");
			list.add("host01-vm05");
			list.add("host01-vm06");
			list.add("host01-vm07");
		} 
		
		for (int i = 2; i < 8; i++) {
			for (int j = 1; j < 9; j++) {
				list.add(String.format("host%1d-vm%1d", i,j));
			}
		}

		return list;
	}

	/**
	 * Desc : 현태 동작중이나 사용이 불가한 가상머신 리스트
	 * 
	 * @Method Name : getCurrentUnhealthyVms
	 * @return 사용 불가한 가상머시 개수 리스트 <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentUnhealthyVmList(String hostName) {
		List list = new ArrayList();

		if (hostName.equals("host01")) {
			
			list.add("host01-vm08");
		} else if (hostName.equals("host02")) {
			list.add("host02-vm08");
		} 

		return list;
	}

	/**
	 * Desc : vm생성시 사용할 OS 이미지 리스트 출력
	 * 
	 * @Method Name : getImageRepositoryList
	 * @return 사용 가능한 OS 이미지 리스트 출력 <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getImageRepositoryList(String hostMame) {
		List list = new ArrayList();
		list.add("CDF_OS_Image");
		list.add("Belle_OS_Image");
		list.add("Ligo_OS_Image");
		return list;
	}

	/**
	 * Desc : 데모 동작
	 * 
	 * @Method Name : demoStart
	 * 
	 */
	public void demoStart() {
		t_vCluster.demoStart();
	}

	/**
	 * Desc : 데모 정지
	 * 
	 * @Method Name : demoStop
	 * 
	 */
	public void demoStop() {
		t_vCluster.demoStop();
	}
	
	

}
