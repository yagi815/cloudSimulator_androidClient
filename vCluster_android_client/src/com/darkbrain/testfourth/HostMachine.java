package com.darkbrain.testfourth;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class HostMachine {

	// private String cloudName = "vSimulator";
	private String hostName = "";
	private String hostPower = "off";

	private int totalVirtualMachine = 12;
//	private int runningVMs = 0;
//	private int idleVMs = 0;
//	private int busyVMs = 0;
//	private int healthyVMs = 0;
//	private int availableVMs = 0;
//	private int failVMs = 0;

	private Vector virtualMachineList = null;

	public HostMachine() {
		// TODO Auto-generated constructor stub
	}

	public HostMachine(String hostName) {
		this.hostName = hostName;
		this.totalVirtualMachine = 12;
		this.hostPower = "on";
		virtualMachineList = new Vector(12);

	}

	public synchronized void addVM(String virtualMachine) {
		if (this.hostPower == "off") {
			this.hostPower = "on";
		}		
		virtualMachineList.add(new VirtualMachine(virtualMachine));			
	}

	public synchronized void removeVM(String virtualMachine) {

		/*
		 * new Thread( new Runnable() {
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * } } ).start();
		 */
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine) virtualMachineList.get(i);
			if (vm.getVmName().equals(virtualMachine)) {

				// Thread �� ���ƾ� �Ѵ.
				// boolean IS_EXIT=true;
				// while (IS_EXIT) {
				// if (vm.getVmPower().equals("off")) {
				// IS_EXIT = false;
				// }
				// }

				virtualMachineList.remove(i);
//				this.runningVMs--;
			}
		}
	}

	public synchronized void removeAllVM(){
		for (int i = 0; i < virtualMachineList.size(); i++) {			
			virtualMachineList.remove(i);
		}
	}

	private synchronized VirtualMachine getVM(String virtualMachine) {
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine) virtualMachineList.get(i);
			if (vm.getVmName().equals(virtualMachine)) {
				return vm;
			}
		}
		return null;
	}

	private synchronized int getVmIndex(String virtualMachine) {
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine) virtualMachineList.get(i);
			if (vm.getVmName().equals(virtualMachine)) {
				return i;
			}
		}
		return -1;
	}

	public String getHostPower() {
		return this.hostPower;
	}
	public void setHostPower(String power){
		this.hostPower = power;
	}

	public synchronized int getRunningVMs(){
		return virtualMachineList.size();
	}

	public synchronized List getRunningVmList() {
		List runningVMlist = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine) virtualMachineList.get(i);
			if (vm.getVmPowerStatus().equals("on")) {				
				runningVMlist.add( vm.getVmName());						
			}
		}
		return runningVMlist;
	}	

//	public synchronized List getRunningVMsList() {
//		List runningList = new ArrayList();
//		for (int i = 0; i < virtualMachineList.size(); i++) {
//			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
//			if (vm.getVmStatus().equals("running")) {				
//				runningList.add( vm.getVmName());
//			}
//		}
//		return runningList;
//	}	

	public synchronized List getBusyVmList() {
		List busyList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (vm.getVmBusy().equals("busy")) {				
				busyList.add( vm.getVmName());
			}
		}
		return busyList;
	}
	public synchronized List getRunningJobList() {
		List jobList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (vm.getVmBusy().equals("busy")) {				
				jobList.add( "VM: "+vm.getVmName()+" JOB: "+vm.getJobName()+"\n");
			}
		}
		return jobList;
	}

	public synchronized List getIdleVmList() {
		List idleList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (vm.getVmBusy().equals("idle")) {				
				idleList.add( vm.getVmName());
			}
		}
		return idleList;
	}
	
	public synchronized List getHealthyVmList() {
		List healthyList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (vm.getVmHealthy().equals("healthy")) {				
				healthyList.add( vm.getVmName());
			}
		}
		return healthyList;
	}

	public synchronized List getUnHealthyVmList() {
		List unHealthyList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (!vm.getVmHealthy().equals("healthy")) {				
				unHealthyList.add( vm.getVmName());
			}
		}
		return unHealthyList;
	}

	public synchronized List getAvailableVmList() {
		List availableList = new ArrayList();

		String [] list = new String[this.totalVirtualMachine];
		for (int i = 0; i < this.totalVirtualMachine; i++) {
			
			if (this.hostName.equals("host01") && i==10) {// master
				continue;
			}
			if (this.hostName.equals("host01") && i==11) {// master
				continue;
			}
			
			String vName = String.format("vm%02d", i+1);
			String hName= this.hostName;
			String virtualMachine =hName+"-"+vName; 
			list[i] = virtualMachine;
			
			if (!isContainVirtualMachine(list[i])) {				
				availableList.add(list[i]);
			}
		}
		return availableList;
	}
	
	public synchronized List getFailVmList() {
		List failList = new ArrayList();
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			if (vm.getVmBusy().equals("busy")) {				
				failList.add( vm.getVmName());
			}
		}
		return failList;
	}
	
	public synchronized void submitJob(String jobName, int jobRunninTime){
		for (int i = 0; i < virtualMachineList.size(); i++) {
			VirtualMachine vm = (VirtualMachine)virtualMachineList.get(i);
			
			if (!vm.getVmName().equals("host01-master") &&vm.getVmBusy().equals("idle")) {
				vm.executeJob(jobName, jobRunninTime);
				break;
			}
		}
	}

	// ==================== virtual machine =========================	
	public String getVmID(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmID();
		}
		return null;
	}

	public String getVmPowerStatus(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmPowerStatus();
		}
		return null;
	}

	public String getVmName(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return virtualMachine;
		}
		return null;
	}

	public String getVmStatus(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmStatus();
		}
		return null;
	}

	public String getVmBusy(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmBusy();
		}
		return null;
	}

	public String getVmUnhealthy(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmHealthy();
		}
		return null;
	}

	public String getVmAvailable(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getVmAvailable();
		}
		return null;
	}
	public String getJobName(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getJobName();
		}
		return null;
	}
	public String setIdle(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
//			return getVM(virtualMachine).getJobName();
			getVM(virtualMachine).setVmIdle();
//			System.out.println("============================================================");
//			System.out.println("=================================setIdle>>"+getVM(virtualMachine).getVmName());
//			System.out.println("============================================================");
		}
		return null;
	}
	public String setUnHealthy(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			getVM(virtualMachine).setUnHealthy();
		}
		return null;
	}
	

	// Machine specification
	public String getCPU(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getCPU();
		}
		return null;
	}

	public String getMEM(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getMEM();
		}
		return null;
	}

	public String getDISK(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getDISK();
		}
		return null;
	}

	public String getOS(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getOS();
		}
		return null;
	}

	public String getOS_BITS(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getOS_BITS();
		}
		return null;
	}

	public String getKERNEL(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getKERNEL();
		}
		return null;
	}

	public String getMachineSpec(String virtualMachine) {
		if (isContainVirtualMachine(virtualMachine)) {
			return getVM(virtualMachine).getMachineSpec();
		}
		return null;
	}

//	public String getVmStatus(String virtualMachine){
//		if (isContainVirtualMachine(vmName)) {
//			return getVM(vmName).getVmStatus();
//		}
//		return null;
//	}
	public synchronized boolean isContainVirtualMachine(String virtualMachine) {
		for (int i = 0; i < getRunningVmList().size(); i++) {
			VirtualMachine vm = (VirtualMachine) virtualMachineList
					.get(i);
			if (vm.getVmName().equals(virtualMachine)) {
				return true;
			}
		}
		return false;
	}

	// setter - getter
	public synchronized String getHostName() {
		return hostName;
	}

	public synchronized int getTotalVirtualMachine() {
		return totalVirtualMachine;
	}

	public synchronized void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public synchronized void setTotalHostNumber(int mTotalHostNumber) {
		this.totalVirtualMachine = mTotalHostNumber;
	}

	

}
