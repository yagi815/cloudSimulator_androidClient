package kisti.vSimulator.API;

import java.util.ArrayList;
import java.util.List;

import kisti.vSimulator.Simulator;




/**
 * <pre>
 * API.android
 *   |_ API_android.java
 * 
 * </pre>
 * 
 * Desc :  안드로이드 클라이언트에 탑재 할 API 리스트 이다. <br> 
 * vSimulator 를 사용하는  경우는 API_vCluster 를 이용한다.
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 8. 24. 오후 4:03:35
 * @Version: 
 * 
 */
public class API_android {

	
	private API_vcluster API = null;
	private tiny_vCluster  t_vCluster = null;
	

	public API_android() {
		// TODO Auto-generated constructor stub
		API = new API_vcluster();
		t_vCluster = new tiny_vCluster();
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
	 * @return Max 큐 사이즈 반환 <br> EX) 100
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
	 * @return 현재 수행하고 있는 Job 수를 반환 <br>EX) 80
	 * 
	 */
	public int getRunningJobs() {
		return 80;
	}

	/**
	 * Desc : 큐에 대기하고있는 Job수를 반환 <br>
	 * vCluster 에서 값 읽어온다. 
	 * @Method Name : getWatingJobs
	 * @return 큐에 대기중인 Job 수  <br> EX) 90
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
		return list;
	}
	/**
	 * Desc : 현재 동작중인 host리스트 (전원이 on 상태인..)
	 * 
	 * @Method Name : getRunningHostList
	 * @return 현재 동작중인 host 리스트 반환 <br>
	 *         EX)
	 *         "vSimulator-host01, vSimulaotr-host02, fermiCloud-host01, fermiCloud-host02" <br>
	 * 	 	String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getRunningHostList(String hostMame) {
		return API.getRunningHostList();
	}
	/**
	 * Desc :현재 동작중인 가상머신 리스트 
	 * 
	 * @Method Name : getCurrentRunningVms
	 * @return 현재 동작중인 가상머신 리스트  <br>
	 * EX) "host03-vm00, host04-vm03", <br>
	 *  String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentRunningVmList(String hostMame) {
		return API.getRunningVmList(hostMame);
	}
	/**
	 * Desc : 현재 사용가능한 (생성은 되었으나 job은 수행하지 않은..) 가상머신 리스트
	 * 
	 * @Method Name : getCurrentIdleVmList
	 * @return job을 수행가능한 가상머신 리스트 반환
	 * <br>         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentIdleVmList(String hostMame) {
		return API.getIdleVmList(hostMame);
	}
	/**
	 * Desc : 현재 launch 가능한 가상머신 리스트 
	 * 
	 * @Method Name : getCurrentAvailableVmList
	 * @return launch 가능한 vm 리스트
	 * <br>         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentAvailableVmList(String hostMame) {
		return API.getAvailableVmList(hostMame);
	}
	/**
	 * Desc : 현태 동작중이나 사용이 불가한 가상머신 리스트
	 * 
	 * @Method Name : getCurrentUnhealthyVms
	 * @return 사용 불가한 가상머시 개수 리스트
	 * <br>         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getCurrentUnhealthyVmList(String hostMame) {
		return API.getUnhealthyVmList(hostMame);
	}
	/**
	 * Desc : vm생성시 사용할 OS 이미지 리스트 출력
	 * 
	 * @Method Name : getImageRepositoryList
	 * @return 사용 가능한 OS 이미지 리스트 출력
	 * <br>         String으로 캐스팅해서 사용 (String)list.get(i)
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
	 * @Method Name : demoStart
	 * 
	 */
	public void demoStart(){
		t_vCluster.demoStart();
	}
	/**
	 * Desc : 데모 정지
	 * @Method Name : demoStop
	 * 
	 */
	public void demoStop(){
		t_vCluster.demoStop();
	}
	public void simulatorStart(){
		new Simulator();
	}


}
