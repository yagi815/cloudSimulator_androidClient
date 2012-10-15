package kisti.vSimulator.API;

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
	
	
	
	public tiny_vCluster() {	
		API	 = new API_vcluster();
		jobQueue = new JobQueue(maxQueue);
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
		 * Max Queue 1000 개
		 * 총 가능 vm 수는 12*20 = 240개
		 * MaxHost = 20
		 * Max VM each = 12
		 *  
		 * job 서브미션은 5초에 40~50개 사이 
		 * 
		 * 시뮬레이터에서는 기본 가상머신 몇개 동작중
		 * 상태모니터링 해서 모자르면 vm생성~
		 * 상태모니터링해서 남으면 호스트머신 삭제
		 * Job 은 5~10초정도 수행하고 없어짐 
		 * 
		 * 
		 * 추가로 fail모듈 생성해서 vm 죽이고, 새로운 멋힌에 Job 이동시켜 수행한다. 
		 */
		
		final Random random = new Random();
		final int randomNumber = 0;
		int totalJobs=0;	
		
//		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				while (true) {
//					api_vcluster.showCloud();
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//				
//			}
//		}).start();
//		
		
		reductionResorce(); // 리소스 정리 60초 마다 검사해서 정리 한다.
		
		
		for (int i = 0; i < 3; i++) {
			job_submit(50*i); // 10초에 한번씩
			Log.d("tiny_vCluster", 50*i+"개 job is submitted.");
			try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		}
					
	}
	


	
	public void reductionResorce(){
		//1분마다 검사해서 필요 없는 머신 끈다.
		//vm먼저 끄기,
		//running 중이 vm이 하나도 없는 머신은 호스트 전원끄기
		
		new Thread(new Runnable() {
			
			boolean IS_EXIT=false;
			List runningHost=null;
			
			
			@Override
			public void run() {
				
				while (!IS_EXIT){
					
					try {Thread.sleep(60000);} catch (InterruptedException e) {e.printStackTrace();}
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
	 * Desc : job 을 수행한다.  n.job 이름으로 job을 생성하여 실행한다. 
	 * @Method Name : job_submit
	 * @param num 수행할 job 개수 
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

		//1.  큐에 Job 모두 넣기 
//		for (int i = 0; i < num; i++) {			
//			API.jobSubmit(String.format("job%3d", i));			
//		}
		for (int i = 0; i < num; i++) {
			if (jobQueue.isFull()) {
				Log.d("tiny_vCluster", "큐가 가득 찼습니다. ");
				break;
			}
			jobQueue.enQueue(String.format("%10d.job", cnt++));			
		}		
		
		//2. avail list 대로 찾아서  job 수행하기 모두 수행		
		while (!jobQueue.isEmpty()) {	
//			try {Thread.sleep(200);} catch (InterruptedException e) {e.printStackTrace();}
			
			if (idleVMs > remainJob) {
				API.jobSubmit(jobQueue.deQueue());
				remainJob--;
			}else { 
				// 남은 Job이 생성가능한 vm보다 크면
				
 

					// 가능한 슬롯이 10개보다 많으면 VM생성
					if (availSlots > 10) {
						
						for (int i = 0; i < 10; i++) {
							createVM();
						}
					}else {
						// 10개보다 작으면, host를 하나 더 생성한다.
						turnOnHostMachine();
					}

					// 값 다시 읽기 
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
			Log.d("tiny_vCluster","큐에 데이터 없음");
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
