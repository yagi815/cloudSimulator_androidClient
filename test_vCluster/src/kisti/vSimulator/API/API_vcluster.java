package kisti.vSimulator.API;

import java.util.ArrayList;
import java.util.List;

import kisti.vSimulator.Simulator;



/**
 * <pre>
 * API
 *   |_ API_for_vcluster.java
 * 
 * </pre>
 * 
 * Desc : Vcluster 가 사용 할 vsimulator 접속하는 API이다. <br>
 * 가상 클라우드인 시뮬레이터에서 필요한 모든 데이터를 <br>
 * 요청하고 받아온다.
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 8. 10. 오후 2:43:57
 * @Version: 1.0
 * 
 */





public class API_vcluster {


	private Simulator simulator = null;

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
	 * Desc : 동작하고 있는 호스트 리스트를 가져온다
	 * 
	 * @Method Name : getRunningHostList
	 * @return 동작하는 호스트 리스트 반환 br <h2>EX) "host01, host02... " <br>
	 *         String으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getRunningHostList() {
		List runningHostList = new ArrayList();
		runningHostList = (List) requestToSimulator("00:-");
		return runningHostList;
	}

	/**
	 * Desc : 호스트 전원을 켠다
	 * 
	 * @Method Name : turnOnHostMachine
	 * @param hostMachine
	 *            호스트머신 이름 EX) "host04"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String turnOnHostMachine(String hostMachine) {
		return (String) requestToSimulator("01:" + hostMachine);
	}

	/**
	 * Desc : 호스트 전원을 끈다.
	 * 
	 * @Method Name : turnOffHostMachine
	 * @param hostMachine
	 *            호스트머신 이름 EX) "host04"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String turnOffHostMachine(String hostMachine) {
		return (String) requestToSimulator("02:" + hostMachine);
	}

	/**
	 * Desc : 사용가능한 호스트 리스트
	 * 
	 * @Method Name : getAvailableHostList
	 * @return 사용가능한 호스트 리스트 반환 <br>
	 *         EX) "host07, host08... " <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 * 
	 */
	public List getAvailableHostList() {
		List availableHostList = new ArrayList();
		availableHostList = (List) requestToSimulator("03:-");
		return availableHostList;
	}

	/**
	 * Desc : 현재 접속한 클라우드 이름을 가져온다.
	 * 
	 * @Method Name : getCloudName
	 * @return 현재 클라우드 이름 반환 <br>
	 *         EX) "CloudSinulator"
	 * 
	 */
	public String getCloudName() {
		return (String) requestToSimulator("04:-");
	}

	/**
	 * Desc : 모든 호스트의 리스트
	 * 
	 * @Method Name : getHostList
	 * @return 모든 호스트의 리스트 <br>
	 *         EX) "host01, host02" <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
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
	 * Desc : 새로운 가상머신을 생성한다.
	 *

	 * @Method Name : createNewVirtualMachine
	 * @param virtualMachine
	 *            생성할 가상머신이름 EX) "host03-vm03" <br>
	 *            parameter가 "-" 이면, 앞에서 순차적으로 생성됨
	 * 
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String createNewVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("20:" + virtualMachine);
	}

	/**
	 * Desc : 삭제할 가상머신을 생성한다.
	 * 
	 * @Method Name : removeVirtualMachine
	 * @param virtualMachine
	 *            삭제할 가상머신이름 EX) "host03-vm03"
	 * @return 성공하면 "1", 실패하면 "-1"
	 * 
	 */
	public String removeVirtualMachine(String virtualMachine) {
		return (String) requestToSimulator("21:" + virtualMachine);
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
		return (String) requestToSimulator("22:" + srcVirtualMachine + ","
				+ desVirtualMachine);
	}

	/**
	 * Desc : 클라우드 내에서 동작중인 가상머신의 리스트를 얻어온다. 호스트-vm정보 의 포멧으로 형식 *
	 * 
	 * @Method Name : getRunningVmList
	 * @return 동작중인 가상머신 리스트를 반환 <br>
	 *         EX)"host01-vm01,host02-vm05,host07-vm06" <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 * 
	 */
	public List getRunningVmList(String hostName) {
		List runningVmList = new ArrayList();
		runningVmList = (List) requestToSimulator("23:" + hostName);
		return runningVmList;
	}

	/**
	 * Desc : Available 상태의 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getAvailableVmList
	 * @return Available 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm10,host07-vm11,host07-vm12" * <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getAvailableVmList(String hostName) {
		List availableVmList = new ArrayList();
		availableVmList = (List) requestToSimulator("24:" + hostName);
		return availableVmList;
	}

	/**
	 * Desc : FAIL 상태의 가상머신의 리스트를 얻어 온다. *
	 * 
	 * @Method Name : getFailVmList
	 * @return FAIL 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm06" * <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getFailVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("25:" + hostName);
		return failVmList;
	}

	/**
	 * Desc : BUSY 상태의 가상머신의 리스트를 얻어온다. *
	 * 
	 * @Method Name : getBusyVmList
	 * @return BUSY 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm06" <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getBusyVmList(String hostName) {
		List failVmList = new ArrayList();
		failVmList = (List) requestToSimulator("26:" + hostName);
		return failVmList;

	}

	/**
	 * Desc : IDLE 상태의 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getIdleVmList
	 * @return IDLE 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm05" <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getIdleVmList(String hostName) {
		List idleVmList = new ArrayList();
		idleVmList = (List) requestToSimulator("27:" + hostName);
		return idleVmList;
	}

	/**
	 * Desc : unhealthy 상태의 가상머신의 리스트를 얻어온다.
	 * 
	 * @Method Name : getUnhealthyVmList
	 * @return unhealthy 상태의 가상머신 리스트를 반환 <br>
	 *         EX) "host07-vm01" <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getUnhealthyVmList(String hostName) {
		List unHealthyVmList = new ArrayList();
		unHealthyVmList = (List) requestToSimulator("28:" + hostName);
		return unHealthyVmList;
	}

	/**
	 * Desc : 클라우드 내의 가용한 모든 호스트의 리스트를 얻어온다. *
	 * 
	 * @Method Name : getTotalVmList
	 * @return 가용한 모든 호스트 리스트 들을 반환 <br>
	 *         EX) "host01-vm01,host02-vm02..." <br>
	 *         스트링으로 캐스팅해서 사용 (String)list.get(i)
	 */
	public List getTotalVmList() {
		List totalVmList = new ArrayList();
		totalVmList = (List) requestToSimulator("29:-");
		return totalVmList;
	}

	/**
	 * Desc : Job을 실행중인(busy) 머신 수를 반환
	 * 
	 * @Method Name : getRunningJobs
	 * @return Job을 처리중인 머신수 EX) 80
	 * 
	 */
	public String getRunningJobs(String hostName) {
		return (String) requestToSimulator("30:" + hostName);
	}

	/**
	 * Desc : 최대로 생성가능한 가상머신의 개수를 반환
	 * 
	 * @Method Name : getTotalVMs
	 * @return 최대 생성가능한 가상머신 수 EX) 196
	 * 
	 */
	public String getTotalVMs() {
		return (String) requestToSimulator("31:-");
	}

	/**
	 * Desc : 가상머신의 상태를 반환
	 * 
	 * @Method Name : getVmStatus
	 * @param virtualMachine
	 *            가성머신 이름 EX) "host01-vm02"
	 * @return 다음중 하나 반환
	 *         "pending","prolog","running","shudown","eliplog","stop","null"
	 */
	public String getVmStatus(String virtualMachine) {
		return (String) requestToSimulator("32:" + virtualMachine);
	}

	/**
	 * Desc : Job이 수행중인지 확인
	 * 
	 * @Method Name : getVMBusy
	 * @param virtualMachine
	 *            가상머신 이름 EX) "host01-vm03"
	 * @return 상태반환 "busy" or "idle"
	 * 
	 */
	public String getVMBusy(String virtualMachine) {
		return (String) requestToSimulator("33:" + virtualMachine);
	}
	/**
	 * Desc : 총 생성가능한 vm 개수 리턴 한다. 
	 * @Method Name : getTotalAvailableVMs
	 * @return
	 * 
	 */
	public String getTotalAvailableVMs() {
		return (String) requestToSimulator("35:-" );
	}

	// ******************************************************************
	// 4X: VIRTUAL SPEC
	// ******************************************************************

	/**
	 * Desc : 가상머신이 런닝한 시간을 가져온다.
	 * 
	 * @Method Name : getVMActiveTime
	 * @param virtualMachine
	 * @return [시간:분:초 ] 스트링 형식으로 반환 <br>
	 *         EX) "00:10:13"
	 * 
	 */
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
		return (String) requestToSimulator("40:" + virtualMachine);
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
		return (String) requestToSimulator("41:" + virtualMachine);
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
		return (String) requestToSimulator("42:" + virtualMachine);
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
		return (String) requestToSimulator("43:" + virtualMachine);
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
		return (String) requestToSimulator("44:" + virtualMachine);
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
		return (String) requestToSimulator("45:" + virtualMachine);
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
		return (String) requestToSimulator("46:" + virtualMachine);
	}

	// ******************************************************************
	// MANIPULATION 8X:
	// ******************************************************************
	/**
	 * Desc : Job 수행
	 * 
	 * @Method Name : jobSubmit
	 * @param jobName
	 *            수행 Job 이름
	 * @return 성공하면 "1", 실패 "-1"
	 * 
	 */
	public String jobSubmit(String jobName) {
		return (String) requestToSimulator("80:" + jobName);
	}

	/**
	 * Desc : 수행중인 Job 리스트를 가져온다. 
	 * @Method Name : getJobRunningList
	 * @param hostName 호스트 이름 EX) "host02"
	 * @return 동작중인 job list <br> EX) "VM:host02-vm00 JOB:123.job"
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
	 * Desc : 호스트의 상태를 보여준다. 
	 * @Method Name : showHost
	 * @param hostName 보여줄 호스트 이름 EX)"host02"
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
	 * Desc : 가상머신의 상태를 보여준다. 
	 * @Method Name : showVM
	 * @param virtualMachine 보여줄 가상머신이름 EX) "host01-vm00"
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
	 * Desc : 클라우드 상태를 보여준다. (vSimulator)
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
	
	
	
	
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------
	//									tiny_vCluster  for jobQueue
	//-------------------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------	
/*	
	private static int maxQueue=1000;;
	private static int queue=0;
	private static int running;
	private static int cnt=0;
//	private JobQueue jobQueue = new JobQueue(maxQueue);
	
	private void _job_submit(int num){		
		int runningJobs = _getRunninJobs();
		int availSlots = _getAvailableSlots();
		int requestJobs = num;
//		int remainJobs = ;

		//1.  큐에 Job 모두 넣기 
		for (int i = 0; i < num; i++) {
			if (!jobQueue.isFull()) {
				System.out.println("큐가 가득 찼습니다. "+i+"개 job이 큐에 들어갔습니다. ");
				break;
			}
			jobQueue.enQueue(String.format("%d.job", cnt++));			
		}
		
		//2. avail list 대로 찾아서  job 수행하기 모두 수행		
		while (!jobQueue.isEmpty()) {
						
			if (_getAvailableSlots() > 0) {
				jobSubmit(jobQueue.deQueue());				
			}else { //가능한 슬롯이 없으면 vm 을 생성 한다. 
				if (_getRunningVms() < _getTotalAvailableVMs()) {
					//vm 하나씩 생성 
					_createVM();
				}
			}
			// 1초마다 Job 하나씩 던진다. (API통신과 과부하를 막기위해 1초 딜레이 )
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}			
		}				
	}
	
	private int _getMaxQueue(){
		return this.maxQueue;		
	}
	private int _getQueue(){
		return this.queue;
	}	
	
	private void _createVM(){
		createNewVirtualMachine("-");
	}
	private int _getTotalAvailableVMs(){
		return Integer.parseInt(getTotalAvailableVMs());
	}
	private int _getRunninJobs(){
		return getBusyVmList("-").size();		
	}	
	private int _getAvailVms() {
		return getAvailableVmList("-").size();
	}
	private int _getAvailableSlots() {
		return getAvailableVmList("-").size();
	}
	private int _getRunningVms(){
		return getRunningVmList("-").size();
	}
	
*/
	
}
// -------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------
/*
class JobQueue {
	
	private int size=0;
	private int front;
	private int rear;		
	private String queue[] = null;
	private int cnt=0;
	
	
	
	public JobQueue() {
		// TODO Auto-generated constructor stub		
	}
	public JobQueue(int size){
		this.size = size;	
		queue = new String[size];
		
		this.front = 0;
		this.rear = 0;
	}		
	public boolean enQueue(String jobName) {
		boolean is;
		if (!isFull()) {
			queue[front] = jobName;
			front = (front+1)%size;
			return true;
		}else{
			return false;
		}
	}		
	public String deQueue() {
		String 	str = peek();
		
		if (str != null) {
			rear = (rear +1)%size;		
		}
		return str;		
	}		
	public String peek() {
		String str=null;
		if (!isEmpty()) {
			str = queue[rear];
		}else {
			System.out.println("큐에 데이터 없음");
			str = null;
		}
		return str;
	}
	
	
		
	
	
	
	private boolean incrementSize(int size) {
		int tempSize = this.size+size;
		
		String[] tempQueue = new String[tempSize];
		System.arraycopy(queue, 0, tempQueue, 0, this.size);
		
		this.queue = tempQueue;
		this.size = tempSize;
		
		return true;	
	}
	public boolean isFull() {
		// 입구에서 출구가 앞쪽으로 1칸 차이면 가득 찬 것이다. 
		if ( (front +1)%size == rear ) {
			return true;
		}else{
			return false;			
		}
	}
	public boolean isEmpty() {
		//입구와 출구가 같으면 비어있는것이다. 
		if (front == rear) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
*/
// -------------------------------------------------------------------------------------
// -------------------------------------------------------------------------------------