package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

//import kisti.vSimulator.Simulator;

import com.darkbrain.testfourth.Simulator;

/**
 * <pre>
 * API
 *   |_ API_for_vcluster.java
 * 
 * </pre>
 * 
 * Desc : Vcluster �� ��� �� vsimulator �����ϴ� API�̴�. <br>
 * ���� Ŭ������ �ùķ����Ϳ��� �ʿ��� ��� �����͸� <br>
 * ��û�ϰ� �޾ƿ´�.
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 8. 10. ���� 2:43:57
 * @Version: 1.0
 * 
 */ 





public class API_vcluster {


	private Simulator simulator = null;
	
//	3-availableVM
//	1-IdleVM, 
//	0-RunningVM, 
//	2-UnHealthyVM, 
	final private int IDLE = 1;
	final private int AVAIL= 3;
	final private int RUNN = 0;
	final private int UNHEALTH = 2;

	public API_vcluster(Simulator simulator) {
		// TODO Auto-generated constructor stub
		this.simulator = simulator;
		
	}

	private  synchronized Object requestToSimulator(String command) {		

		return simulator.execute(command);
	}

	// ******************************************************************
	// 0X: HOSTMACHINE
	// ******************************************************************

	
	public List getRunningHostList() {
		List runningHostList = new ArrayList();
		runningHostList = (List) requestToSimulator("00:-");
		return runningHostList;
	}


	public String turnOnHostMachine(String hostMachine) {
		return (String) requestToSimulator("01:" + hostMachine);
	}

	public String turnOffHostMachine(String hostMachine) {
		return (String) requestToSimulator("02:" + hostMachine);
	}

	public List getAvailableHostList() {
		List availableHostList = new ArrayList();
		availableHostList = (List) requestToSimulator("03:-");
		return availableHostList;
	}

	
	public String getCloudName() {
		return (String) requestToSimulator("04:-");
	}

	public List getHostList() {
		List hostList = new ArrayList();
		hostList = (List) requestToSimulator("05:-");
		return hostList;
	}

	// ******************************************************************
	// 2X: VIRTUAL MACHINE
	// ******************************************************************

	public String createNewVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("20:" + virtualMachine);
	}

	public String removeVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("21:" + virtualMachine);
	}

	public String migrationVirtualMachine(String srcVirtualMachine,
			String desVirtualMachine) {
		return (String) requestToSimulator("22:" + srcVirtualMachine + ","
				+ desVirtualMachine);
	}

	public List getRunningVmList(String hostName) {
		List runningVmList = new ArrayList();
		runningVmList = (List) requestToSimulator("23:" + hostName);
		return runningVmList;
	}

	public List getAvailableVmList(String hostName) {
		List availableVmList = new ArrayList();
		availableVmList = (List) requestToSimulator("24:" + hostName);
		return availableVmList;
	}

	public List getFailVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("25:" + hostName);
		return failVmList;
	}

	public List getBusyVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("26:" + hostName);
		return failVmList;

	}

	public List getIdleVmList(String hostName) {
		List idleVmList = new ArrayList();
		idleVmList = (List) requestToSimulator("27:" + hostName);
		return idleVmList;
	}

	public List getUnhealthyVmList(String hostName) {
		List unHealthyVmList = new ArrayList();
		unHealthyVmList = (List) requestToSimulator("28:" + hostName);
		return unHealthyVmList;
	}

	public List getTotalVmList() {
		List totalVmList = new ArrayList();
		totalVmList = (List) requestToSimulator("29:-");
		return totalVmList;
	}

	public String getRunningJobs(String hostName) {
		return (String) requestToSimulator("30:" + hostName);
	}

	public String getTotalVMs() {
		return (String) requestToSimulator("31:-");
	}

	public String getVmStatus(String virtualMachine) {
		return (String) requestToSimulator("32:" + virtualMachine);
	}

	public String getVMBusy(String virtualMachine) {
		return (String) requestToSimulator("33:" + virtualMachine);
	}
	
	public String getTotalAvailableVMs() {
		return (String) requestToSimulator("35:-" );
	}
	public String getJobName(String virtualMachine) {
		return (String) requestToSimulator("36:"+virtualMachine );
	}
	public String setIdle(String virtualMachine) {
		return (String) requestToSimulator("37:"+virtualMachine );
	}

	public String getHostStatus(String hostName){

//		3-availableVM
//		1-IdleVM, 
//		0-RunningVM, 
//		2-UnHealthyVM, 

		// host01-vm01 .... 
		System.out.println("hostNAme:"+hostName);
		
		String result = (String)requestToSimulator("07:-");		
		Log.d("test", "result:"+result);
		
		int totalVM = Integer.parseInt(result);
		if (hostName.equals("host01")) {
			totalVM -= 2;
		}
		
		String[] hostStatus = new String[totalVM];
		for (int i = 0; i < hostStatus.length; i++) {
			hostStatus[i] = "3";			           
		}
		
		List availableVmList = getAvailableVmList(hostName);				
		for (int i = 0; i < availableVmList.size(); i++) {
			String vm = (String)availableVmList.get(i);
			int index = Integer.parseInt(vm.substring(vm.length()-2, vm.length()));			
			hostStatus[index] = "3";			
		}		
		List busyVmList = getBusyVmList(hostName);
		for (int i = 0; i < busyVmList.size(); i++) {
			String vm = (String)busyVmList.get(i);
			int index = Integer.parseInt(vm.substring(vm.length()-2, vm.length()));			
			hostStatus[index] = "0";			
		}
		List idleVmList = getIdleVmList(hostName);				
		for (int i = 0; i < idleVmList.size(); i++) {
			String vm = (String)idleVmList.get(i);
			int index = Integer.parseInt(vm.substring(vm.length()-2, vm.length()));			
			hostStatus[index] = "1";			
		}		

		String list = "";
		
		for (int i = 0; i < hostStatus.length; i++) {
			list += hostStatus[i]+",";
		}
		
		return list;
	}
	
	// ******************************************************************
	// 4X: VIRTUAL SPEC
	// ******************************************************************

	public String getVMCpuInfo(String virtualMachine) {
		return (String) requestToSimulator("40:" + virtualMachine);
	}

	public String getVMMemInfo(String virtualMachine) {
		return (String) requestToSimulator("41:" + virtualMachine);
	}

	public String getVMDiskInfo(String virtualMachine) {
		return (String) requestToSimulator("42:" + virtualMachine);
	}

	public String getVMOSInfo(String virtualMachine) {
		return (String) requestToSimulator("43:" + virtualMachine);
	}

	public String getVMOSBitInfo(String virtualMachine) {
		return (String) requestToSimulator("44:" + virtualMachine);
	}

	public String getVMKernelInfo(String virtualMachine) {
		return (String) requestToSimulator("45:" + virtualMachine);
	}

	public String getVMUUID(String virtualMachine) {
		return (String) requestToSimulator("46:" + virtualMachine);
	}

	// ******************************************************************
	// MANIPULATION 8X:
	// ******************************************************************
	
	public String jobSubmit(String jobName) {
		return (String) requestToSimulator("80:" + jobName);
	}
	public String jobSubmit(String HostName, String jobName) {
		return (String) requestToSimulator("81:"+HostName+":"+jobName);		
	}

	public List getJobRunningList(String hostName){
		List getJobRunningList = new ArrayList();
		getJobRunningList = (List) requestToSimulator("26:" + hostName);
		return getJobRunningList;
	}
	
	// ******************************************************************
	// FOR TEST
	// ******************************************************************

	public void showHost(String hostName) {
		System.out.println("hostStatus....");
		String dumpCloudStatsus = "";

		List runningVmList = getRunningVmList(hostName);
		List busyVmList = getBusyVmList(hostName);
		List idleVmList = getIdleVmList(hostName);

		dumpCloudStatsus += "\n===================================================================\n";
		dumpCloudStatsus += "\n----POWER : ";
		dumpCloudStatsus += "\n----Running VMs: " + runningVmList.size();
		dumpCloudStatsus += "\n" + runningVmList;
		dumpCloudStatsus += "\n----Busy VMs: " + busyVmList.size();
		dumpCloudStatsus += "\n" + busyVmList;
		dumpCloudStatsus += "\n----idle VMs: " + idleVmList.size();
		dumpCloudStatsus += "\n" + idleVmList;
		dumpCloudStatsus += "\n===================================================================\n";
		System.out.println(dumpCloudStatsus);
	}

	public void showVM(String virtualMachine) {
		String dumpCloudStatus = "";
		dumpCloudStatus += "" + " Status=" + getVmStatus(virtualMachine) + "\n"
				+ " Busy=" + getVMBusy(virtualMachine) + "\n "

				+ "\n-------------\n" + " ID=" + getVMUUID(virtualMachine)
				+ "\n" + " Cpu=" + getVMCpuInfo(virtualMachine) + "\n" + " OS="
				+ getVMOSInfo(virtualMachine) + "\n" + " Disk="
				+ getVMDiskInfo(virtualMachine) + "\n";
		System.out.println(dumpCloudStatus);
	}

	public void showCloud() {
//		System.out.println("dumpCloudStatus....");
		String dumpCloudStatsus = "";

		dumpCloudStatsus += "====================================================================\n"
			+ "running Hosts :" + getRunningHostList()
				+ "\ntotal VMs :"
				+ getTotalVMs()
				+ "\n running Vms :"
				+ getRunningVmList("-").size()
				+ "\ttotal Running Job :"
				+ getRunningJobs("-")
				+ "\ttotal idle VMs :"
				+ getIdleVmList("-").size() + "\n";
		dumpCloudStatsus += "\n===================================================================\n";

		System.out.println(dumpCloudStatsus);
	}
	
	
}