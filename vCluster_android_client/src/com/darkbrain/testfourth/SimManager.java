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
	
	private boolean isContainHost(String hostMachine) {
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostName().equals(hostMachine)) {
				return true;
			}
		}
		return false;
	}

	
	private int getHostIndex(String hostMachine) {
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostName().equals(hostMachine)) {
				return i;
			}
		}
		return -1;
	}
	
	public List getHostList() {
		List hostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			hostList.add(mainHostContainer[i].getHostName());
		}
		return hostList;
	}

	public List getRunningHostList() {
		List runningHostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostPower().equals("on")) {
				runningHostList.add(mainHostContainer[i].getHostName());
			}
		}
		return runningHostList;
	}

	public List getAvailableHostList() {
		List runningHostList = new ArrayList();
		for (int i = 0; i < mainHostContainer.length; i++) {
			if (mainHostContainer[i].getHostPower().equals("off")) {
				runningHostList.add(mainHostContainer[i].getHostName());
			}
		}
		return runningHostList;
	}


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
			
			if (alist.isEmpty()) {
				return "-1";
			}
			
			
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

	public String getRunningJobs(String hostName) {
		List busyVMs;
		busyVMs = getBusyVmList(hostName);
		return busyVMs.size() + "";
	}

	public String getTotalVMs() {
		int maxVMs = 0;
		HostMachine host;
		for (int i = 0; i < mainHostContainer.length; i++) {
			host = mainHostContainer[i];
			maxVMs += host.getTotalVirtualMachine();
		}
		return maxVMs + "";
	}

	
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
	
	public String getJobName(String virtualMachine){
		HostMachine host;
		String[] tmp = new String[2];
		tmp = virtualMachine.split("[-]");
		String hName = tmp[0];
		String vName = tmp[1];
		if (isContainVirtualMachine(vName)) {
			host = mainHostContainer[getHostIndex(hName)];
			return host.getJobName(virtualMachine);
		}
		return null;
	}
	public String setIdle(String virtualMachine){
		HostMachine host;
		String[] tmp = new String[2];
		tmp = virtualMachine.split("[-]");
		String hName = tmp[0];
		String vName = tmp[1];
		if (isContainVirtualMachine(vName)) {
			host = mainHostContainer[getHostIndex(hName)];
			host.setIdle(virtualMachine);
			return "1";
		}
		return null;
	}
	
	
	public String getCloudName() {
		return this.cloudName;
	}

	// ******************************************************************
	// vm specification
	// ******************************************************************

	
	public String getVMCpuInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getCPU(virtualMachine);
		}
		return "null";
	}

	
	public String getVMMemInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getMEM(virtualMachine);
		}
		return "null";
	}

	
	public String getVMDiskInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getDISK(virtualMachine);
		}
		return "null";
	}

	
	public String getVMOSInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getOS(virtualMachine);
		}
		return "null";
	}

	public String getVMOSBitInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getOS_BITS(virtualMachine);
		}
		return "null";
	}

	
	public String getVMKernelInfo(String virtualMachine) {
		HostMachine host;
		String[] tmp = virtualMachine.split("[-]");
		if (isContainVirtualMachine(virtualMachine)) {
			host = mainHostContainer[getHostIndex(tmp[0])];
			return host.getKERNEL(virtualMachine);
		}
		return "null";
	}

	
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

	
	public String jobSubmit(String jobName_hostName){//jobName:hostName
		
		if (jobName_hostName.contains(":")) {//jobName:hostName
			
			String temp[] = new String[2];
			temp = jobName_hostName.split("[:]");
			
			//특정 호스트에 job submit 한다. 
			List list = getIdleVmList(temp[1]);
			
			if (list != null) {
				if (list.isEmpty()) {
					return "-1";
				}
				
				String virtualMachine = (String)list.get(0);
				HostMachine host;
				host = mainHostContainer[getHostIndex(temp[1])];
				host.submitJob(jobName_hostName, this.jobRunningTime);
				return "1";
				
			}
			
			
			
			
		} else { // jobName:null

			List list = getIdleVmList("-");
			if (list != null) {

				if (list.isEmpty()) {
					return "-1";
				}

				String virtualMachine = (String) list.get(0);

				HostMachine host;
				String[] tmp = virtualMachine.split("[-]");
				if (isContainVirtualMachine(virtualMachine)) {
					host = mainHostContainer[getHostIndex(tmp[0])];
					host.submitJob(jobName_hostName, this.jobRunningTime);
					return "1";
				}
			}
		}
		return "-1";		
	}
	
	
}
