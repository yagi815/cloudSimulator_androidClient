package com.darkbrain.testfourth;


import java.util.List;
import java.util.Random;

import android.util.Log;



public class tiny_vCluster {
	
	
	private static int maxQueue=1000;;
	private static int queue=0;
	private static int running;
	private static int cnt=1;
	
	
	private API_vcluster API;
	private JobQueue jobQueue;
	
	
	
	public tiny_vCluster(API_vcluster API) {	
		this.API	 = API;
		this.jobQueue = new JobQueue(maxQueue);
	}
	private int getMaxQueue(){
		return this.maxQueue;		
	}
	private int getQueue(){
		return this.queue;
	}	
	
	public void demoStop(){
		
	}
	public  void demoStart(){
		/*
		 * Max Queue 1000 ��
		 * �� ���� vm ���� 12*20 = 240��
		 * MaxHost = 20
		 * Max VM each = 12
		 *  
		 * job ����̼��� 5�ʿ� 40~50�� ���� 
		 * 
		 * �ùķ����Ϳ����� �⺻ ����ӽ� � ������
		 * ���¸���͸� �ؼ� ���ڸ��� vm��~
		 * ���¸���͸��ؼ� ������ ȣ��Ʈ�ӽ� ����
		 * Job �� 5~10������ �����ϰ� ����� 
		 * 
		 * 
		 * �߰��� fail��� ���ؼ� vm ���̰�, ���ο� ���� Job �̵����� �����Ѵ�. 
		 */
		
		final Random random = new Random();
		final int nJob = random.nextInt(10);
//		int totalJobs=0;	
		
		
		reductionResorce(); // ���ҽ� ���� 60�� ���� �˻��ؼ� ���� �Ѵ�.
		
		
		for (int i = 0; i < 20; i++) {
			job_submit(10+nJob); // 10�ʿ� �ѹ�
			Log.d("tiny_vCluster", 7+nJob+"�� job is submitted.");
			try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		}
					
	}
	


	
	public void reductionResorce(){
		//1�и��� �˻��ؼ� �ʿ� ��� �ӽ� ����.
		//vm���� ����,
		//running ���� vm�� �ϳ��� ��� �ӽ��� ȣ��Ʈ ������
		
		new Thread(new Runnable() {
			
			boolean IS_EXIT=false;
			List runningHost=null;
			
			
			public void run() {
				final int SEC = 1000;
				
				while (!IS_EXIT){
					
					try {Thread.sleep(10*SEC);} catch (InterruptedException e) {e.printStackTrace();}
//					System.out.println("reductionResource");
					Log.d("tiny_vCluster", "reductionResource");
					runningHost = API.getRunningHostList();
					
					for (int i = 1; i < runningHost.size(); i++) {
						String hostName = (String)runningHost.get(i);
						if(API.getBusyVmList(hostName).size() ==0 ){
							API.turnOffHostMachine(hostName);
							Log.d("tiny_vCluster",hostName+" is off");
						}						
						
					}
				}				
			}
		}).start();
		
	}
	
	
	
	/**
	 * Desc : job �� �����Ѵ�.  n.job �̸����� job�� ���Ͽ� �����Ѵ�. 
	 * @Method Name : job_submit
	 * @param num ������ job ���� 
	 * 
	 */
	public void job_submit(int num){
		
		int runningJobs = getRunninJobs();
		int availSlots = getAvailableSlots();
		int runninVMs = getRunningVms();
		int totalAvailableVMs = getTotalAvailableVMs();
		int idleVMs = getIdleVMs();
		
		
		int remainJob = num;
		int sendCnt = 0;
//		int remainJobs = ;

		//1.  ť�� Job ��� �ֱ� 
//		for (int i = 0; i < num; i++) {			
//			API.jobSubmit(String.format("job%3d", i));			
//		}
		for (int i = 0; i < num; i++) {
			if (jobQueue.isFull()) {
				Log.d("tiny_vCluster", "ť�� ���� á���ϴ�. ");
				break;
			}
			jobQueue.enQueue(String.format("%10d.job", cnt++));			
		}		
		
		//2. avail list ��� ã�Ƽ�  job �����ϱ� ��� ����		
		while (!jobQueue.isEmpty()) {	
//			try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
			
			if (idleVMs > remainJob) {
				API.jobSubmit(jobQueue.deQueue());
				remainJob--;
			}else { 
				// ���� Job�� ����� vm���� ũ��
				
 

					// ������ ������ 10������ ������ VM��
					if (availSlots > 10) {
						
						for (int i = 0; i < 10; i++) {
							createVM();
						}
					}else {
						// 10������ ������, host�� �ϳ� �� ���Ѵ�.
						turnOnHostMachine();
					}

					// �� �ٽ� �б� 
					availSlots = getAvailableSlots();
					idleVMs = getIdleVMs();					
				
			}			
		}		
	}
	private void turnOnHostMachine(){
		if (API.turnOnHostMachine("-").equals("1")) {
			Log.d("tiny_vCluster","host turn on");
		}else {
			Log.d("tiny_vCluster","fail");
		}
	}
	private void createVM(){
		API.createNewVirtualMachine("-");
	}
	private int getTotalAvailableVMs(){
		return Integer.parseInt(API.getTotalAvailableVMs());
	}
	private int getRunninJobs(){
		return API.getBusyVmList("-").size();		
	}	
	private int getAvailVms() {
		return API.getAvailableVmList("-").size();
	}
	private int getAvailableSlots() {
		return API.getAvailableVmList("-").size();
	}
	private int getRunningVms(){
		return API.getRunningVmList("-").size();
	}
	private int getIdleVMs(){
		return API.getIdleVmList("-").size();
	}
	
	
	
	
}

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
			Log.d("tiny_vCluster","ť�� ������ ����");
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
		// �Ա����� �ⱸ�� �������� 1ĭ ���̸� ���� �� ���̴�. 
		if ( (front +1)%size == rear ) {
			return true;
		}else{
			return false;			
		}
	}
	public boolean isEmpty() {
		//�Ա��� �ⱸ�� ������ ����ִ°��̴�. 
		if (front == rear) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
}
