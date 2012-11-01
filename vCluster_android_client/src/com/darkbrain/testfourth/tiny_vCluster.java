package com.darkbrain.testfourth;

import java.util.List;
import java.util.Random;

import android.util.Log;

public class tiny_vCluster {

	private static int maxQueue = 1000;;
	private static int queue = 0;
	private static int runningTime=0;
	private static int cnt = 1;

	private API_vcluster API;
	private JobQueue jobQueue;

	public tiny_vCluster(API_vcluster API) {
		this.API = API;
		this.jobQueue = new JobQueue(maxQueue);
	}

	private int getMaxQueue() {
		return this.maxQueue;
	}

	public synchronized int getWatingQueue() {
		return this.queue;
	}

	private synchronized void setQueue(int queue) {
		this.queue = queue;
	}

	private synchronized int getQueue() {
		return this.queue;
	}

	public void demoStop() {
		// not yet impleted.
		// 
	}

	public void demoStart() {
//		demoScenario01();		
		demoScenario02();		
	}
	private void demoScenario01(){
		/*
		 * 10~20개 job을 5회에 걸쳐서 submit  
		 * 
		 */
		
		final Random random = new Random();
		final int nJob = random.nextInt(10);
		final int runningTime = 50+random.nextInt(10);
				
		this.runningTime = runningTime;

		reductionResorce(); 

		for (int i = 0; i < 5; i++) {
			job_submit(10 + nJob, this.runningTime);			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void demoScenario02(){
		/*
		 * host01 에 10개 60초 동안 
		 * host02에 10개 60초 동안
		 * host03 에 3개 60초 동안
		 * 
		 *  host02에 unHealthy 발생 
		 *  손으로 이동 양쪽으로 host02자동으로 꺼짐 
		 */
		
		job_submit(25, 60);
		API.turnOnHostMachine("host04");
		API.createNewVirtualMachine("-");
		API.createNewVirtualMachine("-");
		API.createNewVirtualMachine("-");
		API.createNewVirtualMachine("-");
		API.createNewVirtualMachine("-");
		API.createNewVirtualMachine("-");
		API.jobSubmit(60, "50.job"+":"+"host04");
		API.jobSubmit(60, "51.job"+":"+"host04");
//		API.jobSubmit(60, "52.job"+":"+"host04");
		try {Thread.sleep(10000);} catch (InterruptedException e) {e.printStackTrace();}
		API.setUnHealthy("host04-vm04");
		reductionResorce();
	}
	
	
	public void reductionResorce() {

		new Thread(new Runnable() {

			boolean IS_EXIT = false;
			List runningHost = null;

			public void run() {
				final int SEC = 1000;

				while (!IS_EXIT) {

					try {
						Thread.sleep(15 * SEC);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// System.out.println("reductionResource");
					// Log.d("tiny_vCluster", "reductionResource");
					runningHost = API.getRunningHostList();

					for (int i = 1; i < runningHost.size(); i++) {
						String hostName = (String) runningHost.get(i);
						if (API.getBusyVmList(hostName).size() == 0) {
							API.turnOffHostMachine(hostName);
							Log.d("tiny_vCluster", hostName + " is off");
						}

					}
				}
			}
		}).start();

	}
	/**
	 * Desc :
	 * 
	 * @Method Name  : migrationVM
	 * @param srcVmName
	 * @param desHost
	 */
	public void migrationVM(String srcHost, String srcVmName, String desHost) {
		List idleList = API.getIdleVmList(desHost);
		List availList = API.getAvailableVmList(desHost);
		String jobName = API.getJobName(srcHost+"-"+srcVmName);
		String desVM = "";
		String srcJobName = "";
		
		if (!idleList.isEmpty()) {	//비어 있지 않으면 처리 
			desVM = (String)idleList.get(0);
			Log.d("aaa","idleList is not empty");
		}else if(!availList.isEmpty()){ //비어 있지 않으면 처리 
			desVM = (String)availList.get(0);
			Log.d("aaa","availlist is not empty");
		}else{
			//nothing to do 
			Log.d("aaa","nothing to do ");
		}
	
		// resubmit job
		String result = (String)API.jobSubmit(60, jobName+":"+desHost);
		Log.d("aaa", "resubmit Job:"+desHost+"-"+jobName);
		if (  result.equals("1")){
		// setBusy -> idle
			API.setIdle(srcHost+"-"+srcVmName);
		// refresh screen 
		}

		
		
	}
	public void troubleMaker(int numberOfTrouble, String hostName, String vmName) {

	}
	public void job_submit(int num, int runningTime) {

		int runningJobs = getRunninJobs();
		int availSlots = getAvailableSlots();
		int runninVMs = getRunningVms();
		int totalAvailableVMs = getTotalAvailableVMs();
		int idleVMs = getIdleVMs();

		int remainJob = num;
		int sendCnt = 0;

		for (int i = 0; i < num; i++) {
			if (jobQueue.isFull()) {
				Log.d("tiny_vCluster", "job submitted. ");
				break;
			}
			jobQueue.enQueue(String.format("%10d.job", cnt++));
			setQueue(getQueue() + 1);
		}

		while (!jobQueue.isEmpty()) {

			if (idleVMs > remainJob) {
//				API.jobSubmit(jobQueue.deQueue()+":"+runningTime);
				API.jobSubmit(runningTime, jobQueue.deQueue());
				setQueue(getQueue() - 1);

				remainJob--;
			} else {

				if (availSlots > 10) {

					for (int i = 0; i < 10; i++) {
						createVM();
					}
				} else {

					turnOnHostMachine();
				}

				availSlots = getAvailableSlots();
				idleVMs = getIdleVMs();

			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void turnOnHostMachine() {
		if (API.turnOnHostMachine("-").equals("1")) {
			Log.d("tiny_vCluster", "host turn on");
		} else {
			Log.d("tiny_vCluster", "fail");
		}
	}

	private void createVM() {
		API.createNewVirtualMachine("-");
	}

	private int getTotalAvailableVMs() {
		return Integer.parseInt(API.getTotalAvailableVMs());
	}

	private int getRunninJobs() {
		return API.getBusyVmList("-").size();
	}

	private int getAvailVms() {
		return API.getAvailableVmList("-").size();
	}

	private int getAvailableSlots() {
		return API.getAvailableVmList("-").size();
	}

	private int getRunningVms() {
		return API.getRunningVmList("-").size();
	}

	private int getIdleVMs() {
		return API.getIdleVmList("-").size();
	}

}

class JobQueue {
	private int size = 0;
	private int front;
	private int rear;

	private String queue[] = null;
	private int cnt = 0;

	public JobQueue() {
		// TODO Auto-generated constructor stub
	}

	public JobQueue(int size) {
		this.size = size;
		queue = new String[size];

		this.front = 0;
		this.rear = 0;
	}

	public boolean enQueue(String jobName) {
		boolean is;
		if (!isFull()) {
			queue[front] = jobName;
			front = (front + 1) % size;
			return true;
		} else {
			return false;
		}
	}

	public String deQueue() {
		String str = peek();

		if (str != null) {
			rear = (rear + 1) % size;
		}
		return str;
	}

	public String peek() {
		String str = null;
		if (!isEmpty()) {
			str = queue[rear];
		} else {
			Log.d("tiny_vCluster", "ť�� ������ ����");
			str = null;
		}
		return str;
	}

	private boolean incrementSize(int size) {
		int tempSize = this.size + size;

		String[] tempQueue = new String[tempSize];
		System.arraycopy(queue, 0, tempQueue, 0, this.size);

		this.queue = tempQueue;
		this.size = tempSize;

		return true;
	}

	public boolean isFull() {
	
		if ((front + 1) % size == rear) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isEmpty() {
	
		if (front == rear) {
			return true;
		} else {
			return false;
		}
	}

}
