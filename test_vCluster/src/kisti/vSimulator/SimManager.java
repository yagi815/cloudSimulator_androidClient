package kisti.vSimulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.util.Log;

import kisti.vSimulator.MachineContainer.HostMachine;



/**
 * <pre>
 * SimManager
 *   |_ SimManager.java
 * 
 * </pre>
 * 
 * Desc :
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 9. 11. 오후 5:12:49
 * @Version:
 * 
 */
public class SimManager implements Runnable {

	// host 메인 리스트
	private HostMachine[] mainHostContainer;

	
	// 리스트 변수 지정 * 정책 변수 지정.		
	final private String cloudName = "vSimulator";
	private static List hostList;
	private static List vmList;
	private static int maxHost;
	private static int maxVM;
//	private static int totalAvailableVMs;
		
	
	private static int jobRunningTime;
	private static int hostRunningTime;
	private static int vmRunningTime;
	
	private static int PolicyReadingInterval;
	
	private static int pendingTime;
	private static int prologTime;
	private static int shutdownTime;



	public SimManager() {
		// TODO Auto-generated constructor stub
		
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
	}
	

	private void initVariable() {
		// 정책을 읽고 저장
		PolicyReader policyReader = new PolicyReader();


		hostList = policyReader.getHostList();
		vmList = policyReader.getVmList();		
		maxHost = policyReader.getMaxHost();
		maxVM = policyReader.getMaxVM();		
		jobRunningTime = policyReader.getJobRunningTime();		
		PolicyReadingInterval = policyReader.getPolicyReadingInterval();		
		pendingTime = policyReader.getPendingTime();
		prologTime = policyReader.getPrologTime();
		shutdownTime = policyReader.getShutdownTime();
		


//		System.out.println("Policy Reading...... complete.");
		Log.d("SimManager", "Policy Reading ... complete.");
	}
/*	
	private boolean resizeHostMachine( int length ){
		try {
			int oldSize = this.maxHost;
			
			mainHostContainer = Arrays.copyOf(mainHostContainer, mainHostContainer.length + length);
			this.maxHost = mainHostContainer.length;
			
			for (int i = oldSize; i < this.maxHost; i++) {
				mainHostContainer[i] = new HostMachine(String.format("host%02d",i + 1));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	*/
	public  void initSimulator() {
		initVariable();
		
		
		mainHostContainer = new HostMachine[maxHost];
		for (int i = 0; i < maxHost; i++) {
			mainHostContainer[i] = new HostMachine(String.format("host%02d",i + 1));
			mainHostContainer[i].setHostPower("off");
		}
		// 1. turnon host
//		for (int i = 0; i < hostList.size(); i++) {
//			turnOnHostMachine((String) hostList.get(i));
//		}
		// 2. create vm
		for (int i = 0; i < vmList.size(); i++) {
			createNewVirtualMachine((String)vmList.get(i));
		}
	}
	



	// ******************************************************************
	// 0X: HOST MACHINE
	// ******************************************************************
	/**
	 * Desc : 호스트 머신이 이미 존재하는지 확인
	 * 
	 * @Method Name : isContainHost
	 * @param hostMachine
	 *            호스트이름 EX) "host01"
	 * @return 있으면 true , 없으면 false
	 * 
	 */
	private boolean isContainHost(String hostMachine) {
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostName().equals(hostMachine)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Desc : 호스트 머신의 인덱스 반환
	 * 
	 * @Method Name : getHostIndex
	 * @param hostMachine
	 *            호스트 머신 이름 EX) "host01"
	 * @return 해당호스트의 인덱스 EX) 2
	 * 
	 */
	private int getHostIndex(String hostMachine) {
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostName().equals(hostMachine)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Desc : 모든 호스트의 리스트
	 * 
	 * @Method Name : getHostList
	 * @return 모든 호스트의 리스트
	 * 
	 */
	public List getHostList() {
		List hostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			hostList.add(mainHostContainer[i].getHostName());
		}
		return hostList;
	}

	/**
	 * Desc : 동작중인 호스트들의 리스트를 반환한다.
	 * 
	 * @Method Name : getPowerOnHostList
	 * @return 동작중인 호스트들의 리스트 반환 <br>
	 *         EX) "host01,host02,host02,"
	 * 
	 */
	public List getRunningHostList() {
		List runningHostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostPower().equals("on")) {
				runningHostList.add(mainHostContainer[i].getHostName());
			}
		}
		return runningHostList;
	}

	/**
	 * Desc : 사용가능한 호스트 리스트
	 * 
	 * @Method Name : getAvailableHostList
	 * @return 사용가능한 호스트 리스트 <br>
	 *         EX) "host07, host08..."
	 * 
	 */
	public List getAvailableHostList() {
		List runningHostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostPower().equals("off")) {
				runningHostList.add(mainHostContainer[i].getHostName());
			}
		}
		return runningHostList;
	}


	/**
	 * Desc : 호스트 머신의 전원을 켠다
	 * 
	 * @Method Name : turnOnHostMachine
	 * @param hostMachine
	 *            호스트 머신 이름 EX) "host04"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String turnOnHostMachine(String hostMachine) {
		
		if (hostMachine.equals("-")) {
			for (int i = 0; i < mainHostContainer.length; i++) {
				if (mainHostContainer[i].getHostPower().equals("off")) {
					mainHostContainer[i].setHostPower("on");
					return "1";
				}
			}
		}else{		
			for (int i = 0; i < mainHostContainer.length; i++) {
				if (mainHostContainer[i].getHostName().equals(hostMachine)) {
					mainHostContainer[i].setHostPower("on");
					return "1";
				}
			}
		}
		return "-1";
	}

	/**
	 * Desc : 호스트 머신의 전원을 끝나.
	 * 
	 * @Method Name : turnOffHostMachine
	 * @param hostMachine
	 *            호스트 머신 이름 EX) "host04"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String turnOffHostMachine(String hostMachine) {
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostName().equals(hostMachine)) {
				mainHostContainer[i].setHostPower("off");
				mainHostContainer[i].removeAllVM();
				return "1";
			}
		}
		return "-1";
	}
	
	public String getMaxVM(){
		return this.maxVM+"";
	}
	public String getMaxHost(){
		return this.maxHost+"";
	}


	

	// ******************************************************************
	// 2X: VIRTUAL MACHINE
	// ******************************************************************
	/**
	 * Desc : 가상머신이 존재하는지 확인한다.
	 * 
	 * @Method Name : isContainVirtualMachine
	 * @param hostName
	 *            호스트 이름 <br>
	 *            EX) "host01"
	 * @param vmName
	 *            가상머신 이름 <br>
	 *            EX) "vm01"
	 * 
	 * @return 존재하면 true, 없으면 false
	 * 
	 */
	private boolean isContainVirtualMachine(String virtualMachine) {
		HostMachine host;
		int hostIndex = getHostIndex(virtualMachine.substring(0, 6));

		if (hostIndex != -1) {
			host = mainHostContainer[hostIndex];
			if (host.getVmName(virtualMachine) != null) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Desc : 새로운 가상머신 추가 
	 * <br>파라미터에 가상머신이름이 들어오면 해당 머신으로 생성하고 
	 * <br>그렇지 않으면 가용한 리스트의 첫번째에 가상머신을 ㅎ생성한다. 
	 * 
	 * @Method Name : addVirtualMachine
	 * @param virtualMachine
	 *            생성할 가상머신 이름 <br>
	 *            EX) "host01-vm01"
	 * @return 성공하면 "1', 실패하면 "-1"
	 * 
	 */
	public String createNewVirtualMachine(String virtualMachine) {
		HostMachine host;

		
		
		if (virtualMachine.equals("-")) {			
			List alist = getAvaiableVmList("-");
			if (alist == null) {
				return "-1";
			}

			String createVmName = (String) alist.get(0);		
			String[] tmp = new String[2];
			tmp = createVmName.split("[-]");
			host = mainHostContainer[getHostIndex(tmp[0])];

			Log.d("SimManager", "name>>---"+createVmName);
			host.addVM(createVmName);
			return "1";
			
		}else {			
			if (!isContainVirtualMachine(virtualMachine)) {
				String[] tmp = new String[2];
				tmp = virtualMachine.split("[-]");
				host = mainHostContainer[getHostIndex(tmp[0])];
				Log.d("SimManager", "name>>>---"+virtualMachine);
				host.addVM(virtualMachine);
				return "1";
			}
		}
		return "-1";
	}

	/**
	 * Desc : 동작하고 잇는 가상머신을 해당 호스트에서 삭제 한다.
	 * 
	 * @Method Name : removeVirtualMachine
	 * @param virtualMachine
	 *            삭제할 가상머신 이름 <br>
	 *            EX) "host01-vm01"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String removeVirtualMachine(String virtualMachine) {
		HostMachine host;
		String[] tmp = new String[2];
		tmp = virtualMachine.split("[-]");
		String hName = tmp[0];
//		String vName = tmp[1];
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(hName)];
			host.removeVM(virtualMachine);
			return "1";
		}
		return "-1";
	}

	/**
	 * Desc : 동작하는 가상머신을 새로운 가상머신으로 이동
	 * 
	 * @Method Name : migrationVirtualMachine
	 * @param srcVirtualMachine
	 *            src 가상머신이름 EX) "host01-vm01"
	 * @param desVirtualMachine
	 *            desc 가상머신이름 EX) "host02-vm04"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String migrationVirtualMachine(String srcVirtualMachine,
			String desVirtualMachine) {
		// job 런닝 상태를 파악해야 한다.
		// job 도 같이 이동
		if (createNewVirtualMachine(desVirtualMachine) == "1") {
			removeVirtualMachine(srcVirtualMachine);
			return "1";
		}
		return "-1";
	}

	/**
	 * Desc : 클라우드 내에서 동작중인 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getRunningVMs
	 * @param srcVirtualMachine
	 *            <br>  호스트 이름. EX) "host01"
	 * @return 동작중인 모든 가상머신 리스트 반환 <br>
	 *         EX)"host01-vm01,host02-vm05,host07-vm06"
	 * 
	 */
	public List getRunningVmList(String hostName) {
		
		HostMachine host;		
		
		if (hostName.equals("-")){
			List runnHost = getRunningHostList();
			List runningVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				runningVmList.addAll(host.getRunningVmList());
			}
			return runningVmList;
		}else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getRunningVmList();
		}		
	}
	
	/**
	 * Desc : avaiable 상태의 모든 vm 리스트를 얻어온다.
	 * 
	 * @Method Name : getTotalHostList
	 * @return avaiable 상태의 모든 가상머신 리스트 반환 <br>
	 *         EX) "host01-vm02,host02-vm08"
	 * 
	 */
	public List getAvaiableVmList(String hostName) {
		HostMachine host;
		
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List availableVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				availableVmList.addAll(host.getAvailableVmList());
			}
			return availableVmList;			
		}else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getRunningVmList();
		}
	}

	/**
	 * Desc : FAIL 상태의 가상머신의 리스트를 얻어 온다. *
	 * 
	 * @Method Name : getFailVmList
	 * @return FAIL 상태의 가상머신 리스트를 반환<br>
	 *         EX) "host07-vm06"
	 * 
	 */
	public List getFailVmList(String hostName) {
		HostMachine host;
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List failVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				failVmList.addAll(host.getFailVmList());
			}
			return failVmList;			
		}else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getFailVmList();			
		} 		
	}

	/**
	 * Desc : BUSY 상태의 가상머신의 리스트를 얻어온다. *
	 * 
	 * @Method Name : getBusyVmList
	 * @return BUSY 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm06,hot08-vm01"
	 * 
	 */
	public List getBusyVmList(String hostName) {
		HostMachine host;
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List busyVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				busyVmList.addAll(host.getBusyVmList());
			}
			return busyVmList;			
		} else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getBusyVmList();
		}
	}
	public List getRunningJobList(String hostName) {
		HostMachine host;
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List busyVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				busyVmList.addAll(host.getRunningJobList());
			}
			return busyVmList;			
		} else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getRunningJobList();
		}
	}

	/**
	 * Desc : IDLE 상태의 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getIdleVmList
	 * @return IDLE 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm05,..."
	 * 
	 */
	public List getIdleVmList(String hostName) {
		HostMachine host;
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List idleVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
			 	host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				idleVmList.addAll(host.getIdleVmList());
			}
			return idleVmList;			
		} else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getIdleVmList();
		}
	}

	/**
	 * Desc : unhealthy 상태의 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getUnhealthyVmList
	 * @return unhealthy 상태의 가상머신 리스트를 "," 구분의 문자열 형태로 반환 <br>
	 *         EX) "host07-vm01"
	 * 
	 */
	public List getUnhealthyVmList(String hostName) {
		HostMachine host;
		if (hostName.equals("-")) {
			List runnHost = getRunningHostList();
			List unHealthyVmList = new ArrayList();
			for (int i = 0; i < runnHost.size(); i++) {
				host = mainHostContainer[getHostIndex((String) runnHost.get(i))];
				unHealthyVmList.addAll(host.getUnHealthyVmList());
			}
			return unHealthyVmList;			
		} else {
			host = mainHostContainer[getHostIndex(hostName)];
			return host.getUnHealthyVmList();
		}
	}

	/**
	 * Desc : 사용가능한 모든 호스트의 리스트를 가져온다
	 * 
	 * @Method Name : getTotalVmList
	 * @return 사용가능한 모든 호스트의 리스트 <br>
	 *         "host01-vm01,host02-vm02..."
	 * 
	 */
	public List getTotalVmList() {
		List totalVmList = new ArrayList();
		HostMachine host; 
		for (int i = 0; i < mainHostContainer.length; i++) {
			host = mainHostContainer[i];
			for (int j = 0; j < host.getTotalVirtualMachine(); j++) {
				totalVmList.add(host.getHostName() + "-"
						+ String.format("host%02d", i));
			}
		}
		return totalVmList;
	}

	/**
	 * Desc : 시뮬레이터에서 동작중인 모든 Job을 가져온다.
	 * 
	 * @Method Name : getRunningJobs
	 * @return Total Job 수 반환 <br>
	 *         EX) "43"
	 */
	public String getRunningJobs(String hostName) {
		List busyVMs;
		busyVMs = getBusyVmList(hostName);
		return busyVMs.size() + "";
	}

	/**
	 * Desc : 호스트에서 생성가능한 가상머신의 최대 개수를 가져온다.
	 * 
	 * @Method Name : getTotalVMs
	 * @return 생성가능한 가사먼신의 최대 개수를 반환 <br>
	 *         EX) "192"
	 * 
	 */
	public String getTotalVMs() {
		int maxVMs = 0;
		HostMachine host;
		for (int i = 0; i < mainHostContainer.length; i++) {
			host = mainHostContainer[i];
			maxVMs += host.getTotalVirtualMachine();
		}
		return maxVMs + "";
	}

	/**
	 * Desc : 가상머신의 동작 상태를 반환한다.
	 * 
	 * @Method Name : virtualMachineStatus
	 * @param virtualMachine
	 *            가상머신 이름 <br>
	 *            EX) "host01-vm004"
	 * @return 다음중 하나 반환
	 *         "pending","prolog","running","shudown","eliplog","stop","null"
	 * 
	 */
	public String getVMStatus(String virtualMachine) {
		HostMachine host;
		String[] tmp = new String[2];
		tmp = virtualMachine.split("[-]");
		String hName = tmp[0];
//		String vName = tmp[1];
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(hName)];
			return host.getVmStatus(virtualMachine);
		}
		return null;
	}
	public String getVMBusy(String virtualMachine) {
		HostMachine host;
		String[] tmp = new String[2];
		tmp = virtualMachine.split("[-]");
		String hName = tmp[0];
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(hName)];
			return host.getVmBusy(virtualMachine);
		}
		return null;
	}

	public String getTotalAvailableVMs(){
		int totalAvailableVMs=0;
		HostMachine host;
		for (int i = 0; i < mainHostContainer.length; i++) {
			host = mainHostContainer[i];
			totalAvailableVMs += host.getTotalVirtualMachine();
		}
		return totalAvailableVMs+"";
	}
	
	/**
	 * Desc : 현재 클라우드 이름 반환
	 * 
	 * @Method Name : getCloudName
	 * @return 현제 클라우드 이름
	 * 
	 */
	public String getCloudName() {
		return this.cloudName;
	}

	// ******************************************************************
	// vm specification
	// ******************************************************************

	// /**
	// * Desc : 가상머신이 런닝한 시간을 가져온다.
	// * @Method Name : getVMActiveTime
	// * @param virtualMachine
	// * @return [시간:분:초 ] 스트링 형식으로 반환
	// * <br> EX) "00:10:13"
	// *
	// */
	// public String getVMActiveTime(String virtualMachine){
	// String activeTime = "00:10:13";
	// return activeTime;
	// }
	/**
	 * 
	 * Desc : 특정 가상머신의 cpu 스펙을 가져온다.
	 * 
	 * @Method Name : getVMCpuInfo
	 * @param virtualMachine
	 *            cpu 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return cpu 스펙을 스트링으로 반환 <br>
	 *         EX) "Intel(R) Xeon(R) CPU           E5640  @ 2.67GHz"
	 * 
	 */
	public String getVMCpuInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getCPU(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 특정 가상머신의 mem 정보를 가져온다.
	 * 
	 * @Method Name : getVMMemInfo
	 * @param virtualMachine
	 *            메모리 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return 메모리 정보를 스트링으로 반환 <br>
	 *         EX) "8Gbyte"
	 * 
	 */
	public String getVMMemInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getMEM(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 특정 가상머신의 디스크 용량을 가져온다.
	 * 
	 * @Method Name : getVMDiskInfo
	 * @param virtualMachine
	 *            디스크 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return 디스크 용량을 스트링으로 반환 <br>
	 *         EX) "100Gbyte"
	 * 
	 */
	public String getVMDiskInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getDISK(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 특정 가상머신의 OS 정보를 가져온다.
	 * 
	 * @Method Name : getVMOSInfo
	 * @param virtualMachine
	 *            OS 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return OS정보를 스트링으로 반환 <br>
	 *         EX) "Scientific Linux SLF release 5.7 (Lederman)"
	 * 
	 */
	public String getVMOSInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getOS(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 특정 가상머신의 OSBIT 정보를 가져온다.
	 * 
	 * @Method Name : getVMOSBitInfo
	 * @param virtualMachine
	 *            OS BIT 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return OSBIT정보를 스트링으로 반환 <br>
	 *         EX) "Scientific Linux SLF release 5.7 (Lederman)"
	 * 
	 */
	public String getVMOSBitInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getOS_BITS(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 특정 가상머신의 Kernel 정보를 가져온다.
	 * 
	 * @Method Name : getVMKernelInfo
	 * @param virtualMachine
	 *            커널 정보를 가져올 가상 머신 이름을 String 형식으로 입력
	 * @return Kernel정보를 스트링 형식으로 반환 <br>
	 *         EX) "2.6.18-308.11.1.el5"
	 * 
	 */
	public String getVMKernelInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getKERNEL(virtualMachine);
		}
		return "null";
	}

	/**
	 * Desc : 생성된 가상머신의 고유번호(ID)를 가져온다.
	 * 
	 * @Method Name : getVMUUID
	 * @param virtualMachine
	 *            UUID를 가져올 머신을 이름을 String 형식으로 입력
	 * @return VM 고유 번호 스트링 형식으로 반환
	 * 
	 */
	public String getVMUUID(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getVmID(virtualMachine);
		}
		return "null";
	}

	
	
	
	
	
	
	
	// ******************************************************************
	// 8X: MANIPULATION
	// ******************************************************************

	/**
	 * Desc : job을 수행한다. 
	 * @Method Name : jobSubmit
	 * @param jobName 실행할 Job이름 EX) "job01"
	 * @return 성공 "1", 실패 "-1"
	 * 
	 */
	public String jobSubmit(String jobName){
		List list = getIdleVmList("-");
		if (list != null) {

			String virtualMachine = (String) list.get(0);

			HostMachine host;
			String[] tmp = virtualMachine.split("[-]");
			if (isContainVirtualMachine(virtualMachine)) {
				host = mainHostContainer[getHostIndex(tmp[0])];
				int runningTime = new Random().nextInt(40);    // 10~30 sec 
				host.submitJob(jobName, runningTime);				
				return "1";
			}
		}
		return "-1";		
	}
	
	
}
