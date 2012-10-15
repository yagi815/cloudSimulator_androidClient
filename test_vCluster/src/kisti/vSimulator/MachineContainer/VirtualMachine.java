package kisti.vSimulator.MachineContainer;

import java.util.UUID;


public class VirtualMachine implements Runnable{

	//1. cloud 정보
	private String cloudName = "simulator"; 
	
	
	//2. Host 정보
	private String hostName = null;
	private String hostStatus = null;
	
	
	//3. vm 정보
	private String vmID = null;
	private String vmName = null; // host1, host2 ....
	private String vmPowerStatus = "off"; // on, off
	private String vmStatus = null; // pending > prolog > running - shudown >
									// epilog > stop
	private String vmBusy = "idle"; // idle, busy
	private String vmHealthy = "healthy"; // healthy, unhealthy
	private String vmAvailable = "avaiable"; // available, unavailable\
	private String vmFail = "ok"; // fail, ok

	private String jobName="";
	
	// 시스템 사양
	private String CPU = "Intel(R) Xeon(R) CPU           E5640  @ 2.67GHz";
	private String MEM = "8Gbyte";
	private String DISK = "100Gbyte";
	private String OS = "Scientific Linux SLF release 5.7 (Lederman)";
	private String OS_BITS = "x86_64";
	private String KERNEL = "2.6.18-308.11.1.el5";
	
	private int jobRunningTime=0;
	
	private final int SECOND = 1000;
	private boolean IS_EXIT = true;
	
	
	public VirtualMachine() {
		// TODO Auto-generated constructor stub
//		Thread t = new Thread(this);
	}
	public VirtualMachine(String virtualMachine) {
		super();		
//		this.vmID = hostName+"-"+vmName+"_"+vmIndex;
		UUID id = UUID.randomUUID();
		this.vmID = id.toString();
//		this.hostName = hostName;
		this.vmName = virtualMachine;
		this.vmPowerStatus = "on";
		this.vmStatus = "pending";
		this.vmBusy = "idle";
		this.vmHealthy = "healthy";
		this.vmAvailable = "unAvailable";
		
		Thread t = new Thread(this);
		t.start();
		System.out.println("vm created by host.......................");
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (IS_EXIT) {
			// power  가 on 하면 vm 상태를 단계적으로 변경 
			if (vmPowerStatus.equals("on") && !vmStatus.equals("running")) {
				try {
					setVmStatus("pending");
					Thread.sleep(5*SECOND	);
					setVmStatus( "prolog");
					Thread.sleep(5*SECOND	);
					setVmStatus("running");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//power 상태를 off하면 vm 상태를 단계적으로 변경 
			if (vmPowerStatus.equals("off") && vmStatus.equals("running")) {
				
				try {
					setVmStatus("shutdown");
					Thread.sleep(5*SECOND);
					setVmStatus("epilog");
					Thread.sleep(5*SECOND);
					setVmStatus("stop");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
			// 1초 마다 상태 확인 
			try {Thread.sleep(1000);} catch (Exception e) {}
		}
		
	}


	public void executeJob(final String jobName, final int jobRunningTime){		
		
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				setVmBusy("busy");
				setJobName(jobName);
				try {Thread.sleep(jobRunningTime);} catch (InterruptedException e) { e.printStackTrace();}
				setVmBusy("idle");
				System.out.println( "job "+jobName+" is done.");				
			}
		}).start();
		
		
	}
		
	//=================    get,set method =========================	
	public synchronized String getCloudName() {
		return cloudName;
	}

	public synchronized String getVmPowerStatus() {
		return vmPowerStatus;
	}

	public synchronized String getHostName() {
		return hostName;
	}

	public synchronized String getHostStatus() {
		return hostStatus;
	}

	public synchronized String getVmName() {
		return vmName;
	}

	public synchronized String getVmStatus() {
		return vmStatus;
	}

//	public synchronized String getVmRunning() {
//		return vmRunning;
//	}

	public synchronized String getJobName() {
		return jobName;
	}
	
	public synchronized String getVmBusy() {
		return vmBusy;
	}

	public synchronized String getVmHealthy() {
		return vmHealthy;
	}

	public synchronized String getVmAvailable() {
		return vmAvailable;
	}

	public synchronized String getVmFail() {
		return vmFail;
	}
	public synchronized void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}

	public synchronized void setVmPower(String vmPower) {
		this.vmPowerStatus = vmPower;
	}
	
	public synchronized void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public synchronized void setHostStatus(String hostStatus) {
		this.hostStatus = hostStatus;
	}

	public synchronized void setVmName(String vmName) {
		this.vmName = vmName;
	}

	public synchronized void setVmStatus(String vmStatus) {
		this.vmStatus = vmStatus;
	}

//	public synchronized void setVmRunning(String vmRunning) {
//		this.vmRunning = vmRunning;
//	}
	public synchronized void setJobName(String jobName){
		this.jobName = jobName;
	}

	public synchronized void setVmBusy(String vmBusy) {
		this.vmBusy = vmBusy;
	}

	public synchronized void setVmHealthy(String vmUnhealthy) {
		this.vmHealthy = vmUnhealthy;
	}

	public synchronized void setVmAvailable(String vmAvailable) {
		this.vmAvailable = vmAvailable;
	}
	public synchronized String getVmID() {
		return vmID ;
	}

	// Machine specification	
	public synchronized String getCPU() {
		return CPU;
	}
	public synchronized String getMEM() {
		return MEM;
	}
	public synchronized String getDISK() {
		return DISK;
	}
	public synchronized String getOS() {
		return OS;
	}
	public synchronized String getOS_BITS() {
		return OS_BITS;
	}
	public synchronized String getKERNEL() {
		return KERNEL;
	}
	public synchronized void setVmID(String vmID ) {
		vmID  = vmID ;
	}
	public synchronized void setIS_EXIT(boolean iS_EXIT) {
		IS_EXIT = iS_EXIT;
	}
	public synchronized void setCPU(String cPU) {
		CPU = cPU;
	}
	public synchronized void setMEM(String mEM) {
		MEM = mEM;
	}
	public synchronized void setDISK(String dISK) {
		DISK = dISK;
	}
	public synchronized void setOS(String oS) {
		OS = oS;
	}
	public synchronized void setOS_BITS(String oS_BITS) {
		OS_BITS = oS_BITS;
	}
	public synchronized void setKERNEL(String kERNEL) {
		KERNEL = kERNEL;
	}
	public static void main(String[] args){
		VirtualMachine v  = new VirtualMachine(); 
	}
	public String getMachineSpec() {
		return "VirtualMachine [CPU=" + CPU + ", MEM=" + MEM + ", DISK=" + DISK
				+ ", OS=" + OS + ", OS_BITS=" + OS_BITS + ", KERNEL=" + KERNEL
				+ "]";
	}
	
	
	
	
}
