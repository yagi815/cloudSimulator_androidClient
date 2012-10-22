package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

//import kisti.vSimulator.MachineContainer.HostMachine;
import com.darkbrain.testfourth.HostMachine;


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
 * @Date :2012. 9. 11. ���� 5:12:49
 * @Version:
 * 
 */
public class SimManager implements Runnable {

	// host ���� ����Ʈ
	private HostMachine[] mainHostContainer;

	
	// ����Ʈ ���� ���� * ��å ���� ����.		
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

	public void run() {
		// TODO Auto-generated method stub
	}
	

	private void initVariable() {
		// ��å�� �а� ����
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
	 * Desc : ȣ��Ʈ �ӽ��� �̹� �����ϴ��� Ȯ��
	 * 
	 * @Method Name : isContainHost
	 * @param hostMachine
	 *            ȣ��Ʈ�̸� EX) "host01"
	 * @return ������ true , ������ false
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
	 * Desc : ȣ��Ʈ �ӽ��� �ε��� ��ȯ
	 * 
	 * @Method Name : getHostIndex
	 * @param hostMachine
	 *            ȣ��Ʈ �ӽ� �̸� EX) "host01"
	 * @return �ش�ȣ��Ʈ�� �ε��� EX) 2
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
	 * Desc : ��� ȣ��Ʈ�� ����Ʈ
	 * 
	 * @Method Name : getHostList
	 * @return ��� ȣ��Ʈ�� ����Ʈ
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
	 * Desc : �������� ȣ��Ʈ���� ����Ʈ�� ��ȯ�Ѵ�.
	 * 
	 * @Method Name : getPowerOnHostList
	 * @return �������� ȣ��Ʈ���� ����Ʈ ��ȯ <br>
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
	 * Desc : ��밡���� ȣ��Ʈ ����Ʈ
	 * 
	 * @Method Name : getAvailableHostList
	 * @return ��밡���� ȣ��Ʈ ����Ʈ <br>
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
	 * Desc : ȣ��Ʈ �ӽ��� ����� �Ҵ�
	 * 
	 * @Method Name : turnOnHostMachine
	 * @param hostMachine
	 *            ȣ��Ʈ �ӽ� �̸� EX) "host04"
	 * @return �����ϸ� "1", �����ϸ� "-1"
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
	 * Desc : ȣ��Ʈ �ӽ��� ����� ����.
	 * 
	 * @Method Name : turnOffHostMachine
	 * @param hostMachine
	 *            ȣ��Ʈ �ӽ� �̸� EX) "host04"
	 * @return �����ϸ� "1", �����ϸ� "-1"
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
	 * Desc : ����ӽ��� �����ϴ��� Ȯ���Ѵ�.
	 * 
	 * @Method Name : isContainVirtualMachine
	 * @param hostName
	 *            ȣ��Ʈ �̸� <br>
	 *            EX) "host01"
	 * @param vmName
	 *            ����ӽ� �̸� <br>
	 *            EX) "vm01"
	 * 
	 * @return �����ϸ� true, ������ false
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
	 * Desc : ���ο� ����ӽ� �߰� 
	 * <br>�Ķ���Ϳ� ����ӽ��̸��� ������ �ش� �ӽ����� ���ϰ� 
	 * <br>�׷��� ������ ������ ����Ʈ�� ù��°�� ����ӽ��� �����Ѵ�. 
	 * 
	 * @Method Name : addVirtualMachine
	 * @param virtualMachine
	 *            ���� ����ӽ� �̸� <br>
	 *            EX) "host01-vm01"
	 * @return �����ϸ� "1', �����ϸ� "-1"
	 * 
	 */
	public String createNewVirtualMachine(String virtualMachine) {
		HostMachine host;
//		String[] tmp = new String[2];
//		tmp = virtualMachine.split("[-]");
//		String hName = tmp[0];

		
		
		if (virtualMachine.equals("-")) {			
			List alist = getAvaiableVmList("-");
			if (alist == null) {
				return "-1";
			}
//			System.out.println("==="+alist);
			String createVmName = (String) alist.get(0);		
			String[] tmp = new String[2];
			tmp = createVmName.split("[-]");
			host = mainHostContainer[getHostIndex(tmp[0])];
//			System.out.println("name>>>----------"+createVmName);
			Log.d("SimManager", "name>>---"+createVmName);
			host.addVM(createVmName);
			return "1";
			
		}else {			
			if (!isContainVirtualMachine(virtualMachine)) {
				String[] tmp = new String[2];
				tmp = virtualMachine.split("[-]");
				host = mainHostContainer[getHostIndex(tmp[0])];
//				System.out.println("name>>>----------"+virtualMachine);
				Log.d("SimManager", "name>>>---"+virtualMachine);
				host.addVM(virtualMachine);
				return "1";
			}
		}
		return "-1";
	}

	/**
	 * Desc : �����ϰ� �մ� ����ӽ��� �ش� ȣ��Ʈ���� ���� �Ѵ�.
	 * 
	 * @Method Name : removeVirtualMachine
	 * @param virtualMachine
	 *            ������ ����ӽ� �̸� <br>
	 *            EX) "host01-vm01"
	 * @return �����ϸ� "1", �����ϸ� "-1"
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
	 * Desc : �����ϴ� ����ӽ��� ���ο� ����ӽ����� �̵�
	 * 
	 * @Method Name : migrationVirtualMachine
	 * @param srcVirtualMachine
	 *            src ����ӽ��̸� EX) "host01-vm01"
	 * @param desVirtualMachine
	 *            desc ����ӽ��̸� EX) "host02-vm04"
	 * @return �����ϸ� "1", �����ϸ� "-1"
	 * 
	 */
	public String migrationVirtualMachine(String srcVirtualMachine,
			String desVirtualMachine) {
		// job ���� ���¸� �ľ��ؾ� �Ѵ�.
		// job �� ���� �̵�
		if (createNewVirtualMachine(desVirtualMachine) == "1") {
			removeVirtualMachine(srcVirtualMachine);
			return "1";
		}
		return "-1";
	}

	/**
	 * Desc : Ŭ���� ������ �������� ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getRunningVMs
	 * @param srcVirtualMachine
	 *            <br>  ȣ��Ʈ �̸�. EX) "host01"
	 * @return �������� ��� ����ӽ� ����Ʈ ��ȯ <br>
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
	 * Desc : avaiable ������ ��� vm ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getTotalHostList
	 * @return avaiable ������ ��� ����ӽ� ����Ʈ ��ȯ <br>
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
			return host.getAvailableVmList();
		}
	}

	/**
	 * Desc : FAIL ������ ����ӽ��� ����Ʈ�� ��� �´�. *
	 * 
	 * @Method Name : getFailVmList
	 * @return FAIL ������ ����ӽ� ����Ʈ�� ��ȯ<br>
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
	 * Desc : BUSY ������ ����ӽ��� ����Ʈ�� ���´�. *
	 * 
	 * @Method Name : getBusyVmList
	 * @return BUSY ������ ����ӽ� ����Ʈ�� ��ȯ <br>
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
	 * Desc : IDLE ������ ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getIdleVmList
	 * @return IDLE ������ ����ӽ� ����Ʈ�� ��ȯ <br>
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
	 * Desc : unhealthy ������ ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getUnhealthyVmList
	 * @return unhealthy ������ ����ӽ� ����Ʈ�� "," ������ ���ڿ� ���·� ��ȯ <br>
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
	 * Desc : ��밡���� ��� ȣ��Ʈ�� ����Ʈ�� �����´�
	 * 
	 * @Method Name : getTotalVmList
	 * @return ��밡���� ��� ȣ��Ʈ�� ����Ʈ <br>
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
	 * Desc : �ùķ����Ϳ��� �������� ��� Job�� �����´�.
	 * 
	 * @Method Name : getRunningJobs
	 * @return Total Job �� ��ȯ <br>
	 *         EX) "43"
	 */
	public String getRunningJobs(String hostName) {
		List busyVMs;
		busyVMs = getBusyVmList(hostName);
		return busyVMs.size() + "";
	}

	/**
	 * Desc : ȣ��Ʈ���� ����� ����ӽ��� �ִ� ������ �����´�.
	 * 
	 * @Method Name : getTotalVMs
	 * @return ����� ����ս��� �ִ� ������ ��ȯ <br>
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
	 * Desc : ����ӽ��� ���� ���¸� ��ȯ�Ѵ�.
	 * 
	 * @Method Name : virtualMachineStatus
	 * @param virtualMachine
	 *            ����ӽ� �̸� <br>
	 *            EX) "host01-vm004"
	 * @return ������ �ϳ� ��ȯ
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
	 * Desc : ���� Ŭ���� �̸� ��ȯ
	 * 
	 * @Method Name : getCloudName
	 * @return ���� Ŭ���� �̸�
	 * 
	 */
	public String getCloudName() {
		return this.cloudName;
	}

	// ******************************************************************
	// vm specification
	// ******************************************************************

	// /**
	// * Desc : ����ӽ��� ������ �ð��� �����´�.
	// * @Method Name : getVMActiveTime
	// * @param virtualMachine
	// * @return [�ð�:��:�� ] ��Ʈ�� ������� ��ȯ
	// * <br> EX) "00:10:13"
	// *
	// */
	// public String getVMActiveTime(String virtualMachine){
	// String activeTime = "00:10:13";
	// return activeTime;
	// }
	/**
	 * 
	 * Desc : Ư�� ����ӽ��� cpu ������ �����´�.
	 * 
	 * @Method Name : getVMCpuInfo
	 * @param virtualMachine
	 *            cpu ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return cpu ������ ��Ʈ������ ��ȯ <br>
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
	 * Desc : Ư�� ����ӽ��� mem ������ �����´�.
	 * 
	 * @Method Name : getVMMemInfo
	 * @param virtualMachine
	 *            �޸� ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return �޸� ������ ��Ʈ������ ��ȯ <br>
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
	 * Desc : Ư�� ����ӽ��� ��ũ �뷮�� �����´�.
	 * 
	 * @Method Name : getVMDiskInfo
	 * @param virtualMachine
	 *            ��ũ ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return ��ũ �뷮�� ��Ʈ������ ��ȯ <br>
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
	 * Desc : Ư�� ����ӽ��� OS ������ �����´�.
	 * 
	 * @Method Name : getVMOSInfo
	 * @param virtualMachine
	 *            OS ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return OS������ ��Ʈ������ ��ȯ <br>
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
	 * Desc : Ư�� ����ӽ��� OSBIT ������ �����´�.
	 * 
	 * @Method Name : getVMOSBitInfo
	 * @param virtualMachine
	 *            OS BIT ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return OSBIT������ ��Ʈ������ ��ȯ <br>
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
	 * Desc : Ư�� ����ӽ��� Kernel ������ �����´�.
	 * 
	 * @Method Name : getVMKernelInfo
	 * @param virtualMachine
	 *            Ŀ�� ������ ������ ���� �ӽ� �̸��� String ������� �Է�
	 * @return Kernel������ ��Ʈ�� ������� ��ȯ <br>
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
	 * Desc : ��� ����ӽ��� ������ȣ(ID)�� �����´�.
	 * 
	 * @Method Name : getVMUUID
	 * @param virtualMachine
	 *            UUID�� ������ �ӽ��� �̸��� String ������� �Է�
	 * @return VM ���� ��ȣ ��Ʈ�� ������� ��ȯ
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
	 * Desc : job�� �����Ѵ�. 
	 * @Method Name : jobSubmit
	 * @param jobName ������ Job�̸� EX) "job01"
	 * @return ���� "1", ���� "-1"
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
				host.submitJob(jobName, this.jobRunningTime);
				return "1";
			}
		}
		return "-1";		
	}
	
	
}
