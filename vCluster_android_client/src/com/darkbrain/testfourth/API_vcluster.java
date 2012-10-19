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

	/**
	 * Desc : �����ϰ� �ִ� ȣ��Ʈ ����Ʈ�� �����´�
	 * 
	 * @Method Name : getRunningHostList
	 * @return �����ϴ� ȣ��Ʈ ����Ʈ ��ȯ br <h2>EX) "host01, host02... " <br>
	 *         String���� ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getRunningHostList() {
		List runningHostList = new ArrayList();
		runningHostList = (List) requestToSimulator("00:-");
		return runningHostList;
	}

	/**
	 * Desc : ȣ��Ʈ ������ �Ҵ�
	 * 
	 * @Method Name : turnOnHostMachine
	 * @param hostMachine
	 *            ȣ��Ʈ�ӽ� �̸� EX) "host04"
	 * @return �����ϸ� "1", �����ϸ� "-1"
	 * 
	 */
	public String turnOnHostMachine(String hostMachine) {
		return (String) requestToSimulator("01:" + hostMachine);
	}

	/**
	 * Desc : ȣ��Ʈ ������ ����.
	 * 
	 * @Method Name : turnOffHostMachine
	 * @param hostMachine
	 *            ȣ��Ʈ�ӽ� �̸� EX) "host04"
	 * @return �����ϸ� "1", �����ϸ� "-1"
	 * 
	 */
	public String turnOffHostMachine(String hostMachine) {
		return (String) requestToSimulator("02:" + hostMachine);
	}

	/**
	 * Desc : ��밡���� ȣ��Ʈ ����Ʈ
	 * 
	 * @Method Name : getAvailableHostList
	 * @return ��밡���� ȣ��Ʈ ����Ʈ ��ȯ <br>
	 *         EX) "host07, host08... " <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 * 
	 */
	public List getAvailableHostList() {
		List availableHostList = new ArrayList();
		availableHostList = (List) requestToSimulator("03:-");
		return availableHostList;
	}

	/**
	 * Desc : ���� ������ Ŭ���� �̸��� �����´�.
	 * 
	 * @Method Name : getCloudName
	 * @return ���� Ŭ���� �̸� ��ȯ <br>
	 *         EX) "CloudSinulator"
	 * 
	 */
	public String getCloudName() {
		return (String) requestToSimulator("04:-");
	}

	/**
	 * Desc : ��� ȣ��Ʈ�� ����Ʈ
	 * 
	 * @Method Name : getHostList
	 * @return ��� ȣ��Ʈ�� ����Ʈ <br>
	 *         EX) "host01, host02" <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getHostList() {
		List hostList = new ArrayList();
		hostList = (List) requestToSimulator("05:-");
		return hostList;
	}

	// ******************************************************************
	// 2X: VIRTUAL MACHINE
	// ******************************************************************

	/**
	 * Desc : ���ο� ����ӽ��� �����Ѵ�.
	 *

	 * @Method Name : createNewVirtualMachine
	 * @param virtualMachine
	 *            ������ ����ӽ��̸� EX) "host03-vm03" <br>
	 *            parameter�� "-" �̸�, �տ��� ���������� ������
	 * 
	 * @return �����ϸ� "1", �����ϸ� "-1"
	 * 
	 */
	public String createNewVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("20:" + virtualMachine);
	}

	/**
	 * Desc : ������ ����ӽ��� �����Ѵ�.
	 * 
	 * @Method Name : removeVirtualMachine
	 * @param virtualMachine
	 *            ������ ����ӽ��̸� EX) "host03-vm03"
	 * @return �����ϸ� "1", �����ϸ� "-1"
	 * 
	 */
	public String removeVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("21:" + virtualMachine);
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
		return (String) requestToSimulator("22:" + srcVirtualMachine + ","
				+ desVirtualMachine);
	}

	/**
	 * Desc : Ŭ���� ������ �������� ����ӽ��� ����Ʈ�� ���´�. ȣ��Ʈ-vm���� �� �������� ���� *
	 * 
	 * @Method Name : getRunningVmList
	 * @return �������� ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX)"host01-vm01,host02-vm05,host07-vm06" <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 * 
	 */
	public List getRunningVmList(String hostName) {
		List runningVmList = new ArrayList();
		runningVmList = (List) requestToSimulator("23:" + hostName);
		return runningVmList;
	}

	/**
	 * Desc : Available ������ ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getAvailableVmList
	 * @return Available ������ ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX) "host07-vm10,host07-vm11,host07-vm12" * <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getAvailableVmList(String hostName) {
		List availableVmList = new ArrayList();
		availableVmList = (List) requestToSimulator("24:" + hostName);
		return availableVmList;
	}

	/**
	 * Desc : FAIL ������ ����ӽ��� ����Ʈ�� ��� �´�. *
	 * 
	 * @Method Name : getFailVmList
	 * @return FAIL ������ ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX) "host07-vm06" * <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getFailVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("25:" + hostName);
		return failVmList;
	}

	/**
	 * Desc : BUSY ������ ����ӽ��� ����Ʈ�� ���´�. *
	 * 
	 * @Method Name : getBusyVmList
	 * @return BUSY ������ ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX) "host07-vm06" <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getBusyVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("26:" + hostName);
		return failVmList;

	}

	/**
	 * Desc : IDLE ������ ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getIdleVmList
	 * @return IDLE ������ ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX) "host07-vm05" <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getIdleVmList(String hostName) {
		List idleVmList = new ArrayList();
		idleVmList = (List) requestToSimulator("27:" + hostName);
		return idleVmList;
	}

	/**
	 * Desc : unhealthy ������ ����ӽ��� ����Ʈ�� ���´�.
	 * 
	 * @Method Name : getUnhealthyVmList
	 * @return unhealthy ������ ����ӽ� ����Ʈ�� ��ȯ <br>
	 *         EX) "host07-vm01" <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getUnhealthyVmList(String hostName) {
		List unHealthyVmList = new ArrayList();
		unHealthyVmList = (List) requestToSimulator("28:" + hostName);
		return unHealthyVmList;
	}

	/**
	 * Desc : Ŭ���� ���� ������ ��� ȣ��Ʈ�� ����Ʈ�� ���´�. *
	 * 
	 * @Method Name : getTotalVmList
	 * @return ������ ��� ȣ��Ʈ ����Ʈ ���� ��ȯ <br>
	 *         EX) "host01-vm01,host02-vm02..." <br>
	 *         ��Ʈ������ ĳ�����ؼ� ��� (String)list.get(i)
	 */
	public List getTotalVmList() {
		List totalVmList = new ArrayList();
		totalVmList = (List) requestToSimulator("29:-");
		return totalVmList;
	}

	/**
	 * Desc : Job�� ��������(busy) �ӽ� ���� ��ȯ
	 * 
	 * @Method Name : getRunningJobs
	 * @return Job�� ó������ �ӽż� EX) 80
	 * 
	 */
	public String getRunningJobs(String hostName) {
		return (String) requestToSimulator("30:" + hostName);
	}

	/**
	 * Desc : �ִ�� ���������� ����ӽ��� ������ ��ȯ
	 * 
	 * @Method Name : getTotalVMs
	 * @return �ִ� ���������� ����ӽ� �� EX) 196
	 * 
	 */
	public String getTotalVMs() {
		return (String) requestToSimulator("31:-");
	}

	/**
	 * Desc : ����ӽ��� ���¸� ��ȯ
	 * 
	 * @Method Name : getVmStatus
	 * @param virtualMachine
	 *            �����ӽ� �̸� EX) "host01-vm02"
	 * @return ������ �ϳ� ��ȯ
	 *         "pending","prolog","running","shudown","eliplog","stop","null"
	 */
	public String getVmStatus(String virtualMachine) {
		return (String) requestToSimulator("32:" + virtualMachine);
	}

	/**
	 * Desc : Job�� ���������� Ȯ��
	 * 
	 * @Method Name : getVMBusy
	 * @param virtualMachine
	 *            ����ӽ� �̸� EX) "host01-vm03"
	 * @return ���¹�ȯ "busy" or "idle"
	 * 
	 */
	public String getVMBusy(String virtualMachine) {
		return (String) requestToSimulator("33:" + virtualMachine);
	}
	/**
	 * Desc : �� ���������� vm ���� ���� �Ѵ�. 
	 * @Method Name : getTotalAvailableVMs
	 * @return
	 * 
	 */
	public String getTotalAvailableVMs() {
		return (String) requestToSimulator("35:-" );
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

	/**
	 * Desc : ����ӽ��� ������ �ð��� �����´�.
	 * 
	 * @Method Name : getVMActiveTime
	 * @param virtualMachine
	 * @return [�ð�:��:�� ] ��Ʈ�� �������� ��ȯ <br>
	 *         EX) "00:10:13"
	 * 
	 */
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
	 *            cpu ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return cpu ������ ��Ʈ������ ��ȯ <br>
	 *         EX) "Intel(R) Xeon(R) CPU           E5640  @ 2.67GHz"
	 * 
	 */
	public String getVMCpuInfo(String virtualMachine) {
		return (String) requestToSimulator("40:" + virtualMachine);
	}

	/**
	 * Desc : Ư�� ����ӽ��� mem ������ �����´�.
	 * 
	 * @Method Name : getVMMemInfo
	 * @param virtualMachine
	 *            �޸� ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return �޸� ������ ��Ʈ������ ��ȯ <br>
	 *         EX) "8Gbyte"
	 * 
	 */
	public String getVMMemInfo(String virtualMachine) {
		return (String) requestToSimulator("41:" + virtualMachine);
	}

	/**
	 * Desc : Ư�� ����ӽ��� ��ũ �뷮�� �����´�.
	 * 
	 * @Method Name : getVMDiskInfo
	 * @param virtualMachine
	 *            ��ũ ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return ��ũ �뷮�� ��Ʈ������ ��ȯ <br>
	 *         EX) "100Gbyte"
	 * 
	 */
	public String getVMDiskInfo(String virtualMachine) {
		return (String) requestToSimulator("42:" + virtualMachine);
	}

	/**
	 * Desc : Ư�� ����ӽ��� OS ������ �����´�.
	 * 
	 * @Method Name : getVMOSInfo
	 * @param virtualMachine
	 *            OS ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return OS������ ��Ʈ������ ��ȯ <br>
	 *         EX) "Scientific Linux SLF release 5.7 (Lederman)"
	 * 
	 */
	public String getVMOSInfo(String virtualMachine) {
		return (String) requestToSimulator("43:" + virtualMachine);
	}

	/**
	 * Desc : Ư�� ����ӽ��� OSBIT ������ �����´�.
	 * 
	 * @Method Name : getVMOSBitInfo
	 * @param virtualMachine
	 *            OS BIT ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return OSBIT������ ��Ʈ������ ��ȯ <br>
	 *         EX) "Scientific Linux SLF release 5.7 (Lederman)"
	 * 
	 */
	public String getVMOSBitInfo(String virtualMachine) {
		return (String) requestToSimulator("44:" + virtualMachine);
	}

	/**
	 * Desc : Ư�� ����ӽ��� Kernel ������ �����´�.
	 * 
	 * @Method Name : getVMKernelInfo
	 * @param virtualMachine
	 *            Ŀ�� ������ ������ ���� �ӽ� �̸��� String �������� �Է�
	 * @return Kernel������ ��Ʈ�� �������� ��ȯ <br>
	 *         EX) "2.6.18-308.11.1.el5"
	 * 
	 */
	public String getVMKernelInfo(String virtualMachine) {
		return (String) requestToSimulator("45:" + virtualMachine);
	}

	/**
	 * Desc : ������ ����ӽ��� ������ȣ(ID)�� �����´�.
	 * 
	 * @Method Name : getVMUUID
	 * @param virtualMachine
	 *            UUID�� ������ �ӽ��� �̸��� String �������� �Է�
	 * @return VM ���� ��ȣ ��Ʈ�� �������� ��ȯ
	 * 
	 */
	public String getVMUUID(String virtualMachine) {
		return (String) requestToSimulator("46:" + virtualMachine);
	}

	// ******************************************************************
	// MANIPULATION 8X:
	// ******************************************************************
	/**
	 * Desc : Job ����
	 * 
	 * @Method Name : jobSubmit
	 * @param jobName
	 *            ���� Job �̸�
	 * @return �����ϸ� "1", ���� "-1"
	 * 
	 */
	public String jobSubmit(String jobName) {
		return (String) requestToSimulator("80:" + jobName);
	}

	/**
	 * Desc : �������� Job ����Ʈ�� �����´�. 
	 * @Method Name : getJobRunningList
	 * @param hostName ȣ��Ʈ �̸� EX) "host02"
	 * @return �������� job list <br> EX) "VM:host02-vm00 JOB:123.job"
	 * 
	 */
	public List getJobRunningList(String hostName){
		List getJobRunningList = new ArrayList();
		getJobRunningList = (List) requestToSimulator("26:" + hostName);
		return getJobRunningList;
	}
	
	// ******************************************************************
	// FOR TEST
	// ******************************************************************
	/**
	 * Desc : ȣ��Ʈ�� ���¸� �����ش�. 
	 * @Method Name : showHost
	 * @param hostName ������ ȣ��Ʈ �̸� EX)"host02"
	 * 
	 */
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

	/**
	 * Desc : ����ӽ��� ���¸� �����ش�. 
	 * @Method Name : showVM
	 * @param virtualMachine ������ ����ӽ��̸� EX) "host01-vm00"
	 * 
	 */
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

	/**
	 * Desc : Ŭ���� ���¸� �����ش�. (vSimulator)
	 * @Method Name : showCloud
	 * 
	 */
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