package kisti.vSimulator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.util.Log;

/**
 * <pre>
 * pipeTest
 *   |_ sumulator.java
 * 
 * </pre>
 * 
 * Desc :
 * 
 * @Company : KISTI
 * @Author :grkim
 * @Date :2012. 7. 27. 오후 1:34:31
 * @Version:
 * 
 */
public class Simulator {

	private Object returnObj = null;
	private SimManager simManager = null;
	
	

	public Simulator() {		
		simManager = new SimManager();		
		simManager.initSimulator();
	}

	

	public Object execute(String command){	
		
		returnObj = "";
		
		/*
		 * 명령어 파싱해서 매나저 매소드 호출 한다. 
		 */
		String typeOfCommand = command.substring(0, 2);
		String parameter = command.substring(3, command.length());
		
		switch (Integer.parseInt(typeOfCommand) ) {
		
			// HOST MACHINE
		case 00:
			returnObj  =	simManager.getRunningHostList();
			break;		
		case 01:			
			returnObj  =	simManager.turnOnHostMachine(parameter);
			break;		
		case 02:
			returnObj  =	simManager.turnOffHostMachine(parameter);
			break;
		case 03:
			returnObj  =	simManager.getAvailableHostList();
			break;
		case 04:
			returnObj  =	simManager.getCloudName();
			break;			
		case 05:
			returnObj  =	simManager.getHostList();
			break;		
		case 06:
			returnObj  =	simManager.getMaxHost();
			break;		
		case 07:
			returnObj  =	simManager.getMaxVM();
			break;		
				
			// VIRTUAL MACHINE
		case 20:			
			returnObj  =	simManager.createNewVirtualMachine(parameter);
			break;
		case 21:			
			returnObj  =	simManager.removeVirtualMachine(parameter);
			break;
		case 22:
			String[] tmp = parameter.split("[,]");
			returnObj  =	simManager.migrationVirtualMachine(tmp[0],tmp[1]);
			break;			
		case 23:
			returnObj  =	simManager.getRunningVmList(parameter);			
			break;
		case 24:
			returnObj  =	simManager.getAvaiableVmList(parameter);			
			break;
		case 25:
			returnObj  =	simManager.getFailVmList(parameter);		
			break;
		case 26:
			returnObj  =	simManager.getBusyVmList(parameter);			
			break;
		case 27:
			returnObj  =	simManager.getIdleVmList(parameter);			
			break;
		case 28:
			returnObj  =	simManager.getUnhealthyVmList(parameter);			
			break;
		case 29:
			returnObj  =	simManager.getTotalVmList();			
			break;
		case 30:
			returnObj  =	simManager.getRunningJobs(parameter);			
			break;
		case 31:
			returnObj  =	simManager.getTotalVMs();			
			break;
		case 32:
			returnObj  =	simManager.getVMStatus(parameter);			
			break;
		case 33:
			returnObj  =	simManager.getVMBusy(parameter);			
			break;
		case 34:
			returnObj  =	simManager.getRunningJobList(parameter);			
			break;
		case 35:
			returnObj  =	simManager.getTotalAvailableVMs();			
			break;		
//		case 36:
//			returnObj  =	simManager.getHostStatus(parameter);			
//			break;
			
			// VM SPEC
		case 40:
			returnObj  =	simManager.getVMCpuInfo(parameter);			
			break;
		case 41:
			returnObj  =	simManager.getVMMemInfo(parameter);			
			break;
		case 42:
			returnObj  =	simManager.getVMDiskInfo(parameter);			
			break;
		case 43:
			returnObj  =	simManager.getVMOSInfo(parameter);			
			break;
		case 44:
			returnObj  =	simManager.getVMOSBitInfo(parameter);			
			break;
		case 45:
			returnObj  =	simManager.getVMKernelInfo(parameter);			
			break;
		case 46:
			returnObj  =	simManager.getVMUUID(parameter);			
			break;
					
			// MANIPULATION
		case 80:
			returnObj  =	simManager.jobSubmit(parameter);
			break;		

		default:
			break;
		}
		
		
		
		
		return returnObj;		
	}

	
}