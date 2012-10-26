$(document).ready(function() { 
	$('#logo2').animate({opacity: 1}, 200);//�ΰ� ���İ� ��

		theNumberOfCloud=""
		simulatorOnHostList=""
		CloudList = new Array();
		HostList = new Array();
		vmList = new Array();
		
		//var numberResult = new String();
		theNumberOfCloudRunnigArray = new Array();
		theNumberOfCloudIdleArray = new Array();
		theNumberOfCloudUnhealthyArray = new Array();
		theNumberOfCloudAvailableArray = new Array();
		
		window.NAPIVcluster.simulatorStart();
		
		var RunningTop=0;
		var IdleTop=0;
		var UnhealthyTop=0;
		var AvailableTop=0;
		
		function changeNum( vmNumber ) {
	  	  var RetrunNumber;
	  	  
	  	  if(vmNumber<10){
	  		  RetrunNumber="0"+vmNumber;
	  	  }else{
	  		  RetrunNumber=vmNumber;
	  	  }
	  	  
	  	  //alert(RetrunNumber);
	  	  return RetrunNumber;
	    }
	
		function appendQueueRunning() {
			$.mobile.loading( 'show');
			$("#QueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='QueueRunning'>"+window.NAPIVcluster.getRunningJobs()+"</div>");
	    }
		
		function appendQueueWaiting() {
			$("#QueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='QueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
	    }
		
		//$Result=""
		function appendImageRepository() {
			var List = new Array();
			List=window.NAPIVcluster.getImageRepositoryList("test");

			var Result = new String();
			var Result1 = new String();

			$lastResult = "";
			for (i=0; i<List.size(); i++){
				var ValueText = new String(List.get(i));
				var firstSpan = new String("<span>");
				var lastSpan = new String("</span>");

				
				Result = firstSpan + ValueText + lastSpan;
				
				Result1 += Result;

			}
			$("#ImageRepositoryText").replaceWith("<div CLASS='ImageText' ID='ImageRepositoryText'>"+Result1+"</div>");
			
	    }
		

		var simulatorHostList=new Array("host01","host02","host03","host04","host05","host06","host07","host08","host09","host10","host11","host12","host13","host14","host15");
		simulatorHostListSize = simulatorHostList.length;
		

		
		//alert(simulatorOnHostListSize);
		
		function statusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			var Result = new String();
			
			var firstSpan = new String("<div CLASS='host' id='vsimulatorHost'><div CLASS='simulatorHostTop'><div CLASS='hostTitle'><p>");

				theNumberOfCloudRunnig = 0;
				theNumberOfCloudIdle = 0;
				theNumberOfCloudUnhealthy = 0;
				theNumberOfCloudAvailable = 0;
				//alert(HostList[0]);
				j=0;
				for (i=0; i<simulatorHostListSize; i++){
					
					var numberResult = new String(simulatorOnHostList.get(j));
					var stringResult = numberResult.toString();
					//theNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult).size());
					
					if(simulatorHostList[i]==stringResult){
						theNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult).size());
						
						theNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult).size());
						
						theNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult).size());
						
						theNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult).size());
						
						var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+getCurrentRunningVmList(stringResult).size()+"</span><span CLASS='idle'>"+getCurrentIdleVmList(stringResult).size()+"</span><span CLASS='unhealthy'>"+getCurrentUnhealthyVmList(stringResult).size()+"</span><span CLASS='available'>"+getCurrentAvailableVmList(stringResult).size()+"</span></div></div>");
						$(firstSpan+simulatorHostList[i]+secondSpan).appendTo("#host_cover");
						if(j<simulatorOnHostListSize-1){
							j++;
						}
						
					}else{
						var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>0</span><span CLASS='idle'>0</span><span CLASS='unhealthy'>0</span><span CLASS='available'>0</span></div></div>");
						$(firstSpan+simulatorHostList[i]+secondSpan).appendTo("#host_cover");
					}

				}

				$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+theNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+theNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+theNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+theNumberOfCloudAvailable+"</div></div>");
				$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(theNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(theNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(theNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(theNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");



		}
		
		
		function changeStatusNumber(){
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			changeResult = "";
			stringchangeResult = "";
			j=0;
			for (i=0; i<simulatorHostListSize; i++){
				//$("<div CLASS='hostSign'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(i).children().eq(0));
				var numberResult = new String(simulatorOnHostList.get(j));
				var stringResult = numberResult.toString();
				
				if(simulatorHostList[i]==stringResult){
					changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult).size());
					
					changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult).size());
					
					changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult).size());
					
					changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult).size());
					
					
					
					$("#host_cover").children().eq(i).children().eq(1).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+getCurrentRunningVmList(stringResult).size()+"</span><span CLASS='idle'>"+getCurrentIdleVmList(stringResult).size()+"</span><span CLASS='unhealthy'>"+getCurrentUnhealthyVmList(stringResult).size()+"</span><span CLASS='available'>"+getCurrentAvailableVmList(stringResult).size()+"</span></div></div>");
					
					if(j<simulatorOnHostListSize-1){
						j++;
					}
					//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(i)+".gif'></div>").replaceWith($("#secondContent").children().eq(0).children().eq(2).children().eq(i).children().eq(0));
					$("#host_cover").children().eq(i).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSign'><img src='images/host_sign_on_0"+changeNum(i)+".gif'></div>");
				}else{
					$("#host_cover").children().eq(i).children().eq(1).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>0</span><span CLASS='idle'>0</span><span CLASS='unhealthy'>0</span><span CLASS='available'>0</span></div></div>");
					$("#host_cover").children().eq(i).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSign'></div>");
				}
				
			}

			$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
			
			$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(changeNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(changeNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(changeNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(changeNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");

		}
		
		function addGif(){
			
			for (i=0; i<4; i++){
				var hostLength = $("#secondContent").children().eq(i).children().eq(2).children(".host").length;
				for (j=0; j<hostLength; j++){
					$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(j).children().eq(0));
					
					//$(".host_cover").children().eq(j).children().eq(0).after("<div CLASS='hostSign'><img src='images/host_sign_on_"+changeNum(j)+".gif'></div>");
				}
			}
		}
		
////////////////////////////3��° ������ ����///////////////////////////
		
		function subAppendQueueRunning() {
			//$.mobile.loading( 'show');
			$("#subQueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='subQueueRunning'>"+window.NAPIVcluster.getRunningJobs()+"</div>");
	    }
		
		function subAppendQueueWaiting() {
			$("#subQueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='subQueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
	    }
		
		function changeClusterStatusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			changeResult = "";
			stringchangeResult = "";
			//alert(vsimulatorHostList);
			for (i=0; i<simulatorOnHostList.size(); i++){
				var numberResult = new String(simulatorOnHostList.get(i));
				var stringResult = numberResult.toString();

				changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult).size());
				
				changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult).size());
				
				changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult).size());
				
				changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult).size());

			}
			
			//$("#VsimulatorclusterStatus").replaceWith("<div CLASS="VclusterStatus" id="VsimulatorclusterStatus"><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
			$("#VsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='VsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		}
		
		function getVsimulatorDisplay(){
			//alert(simulatorHostListSize);
			for (j=0; j<simulatorHostListSize; j++){
				stringchangeResult = simulatorHostList[j];
				if(j==0){
					var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><div CLASS='cpuSign'></div><p CLASS='subHostToptitle'>");
					var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
					//var thirdSpan = new String(" Status</p></div>");
					var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='masterCover'><div CLASS='master'><p ID='masterP'>Master</p></div></div><div CLASS='cpuCover'></div>");
					$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
				}else{
					var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
					var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
					//var thirdSpan = new String(" Status</p></div>");
					var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
					$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
				}
				
			}
			
		}
		function addGif2(){
			
			for (i=0; i<simulatorHostListSize; i++){
					//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children().eq(1));
					//$("<div CLASS='cpuSign'><img src='images/fliker_cpu.gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children(".subHost_power"));
					//node.getElementsByTagName("tagname");
					//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children().eq(1));
					$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/fliker_cpu_"+changeNum(i)+".gif'></div>"); 
			}
		}
		
		function hostStatusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			changeResult = "";
			stringchangeResult = "";
			
			j=0;
			for (i=0; i<simulatorHostListSize; i++){
				var numberResult = new String(simulatorOnHostList.get(j));
				var stringResult = numberResult.toString();

				if(simulatorHostList[i]==stringResult){
					changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult).size());
					
					changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult).size());
					
					changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult).size());
					
					changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult).size());
				

				
					$("#simulatorSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+getCurrentRunningVmList(stringResult).size()+"</span><span CLASS='subStatusNumber idle'>"+getCurrentIdleVmList(stringResult).size()+"</span><span CLASS='subStatusNumber unhealthy'>"+getCurrentUnhealthyVmList(stringResult).size()+"</span><span CLASS='subStatusNumber available'>"+getCurrentAvailableVmList(stringResult).size()+"</span></div></div>");
					if(j<simulatorOnHostListSize-1){
						j++;
					}
				}else{
					$("#simulatorSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>0</span><span CLASS='subStatusNumber idle'>0</span><span CLASS='subStatusNumber unhealthy'>0</span><span CLASS='subStatusNumber available'>0</span></div></div>");
				}
			}
			$("#VsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='VsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		}
		
		function hostVmStatus(){
			
			var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
			var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
			var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
			var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
			var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
			var secondSpan = new String("</p></div>");
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			k=0;
			for (i=0; i<simulatorHostListSize; i++){
				
				var numberResult = new String(simulatorOnHostList.get(k));
				var stringResult = numberResult.toString();
				if(simulatorHostList[i]==stringResult){
					vmList = new Array();
					vmList=getHostStatus(stringResult);
					resultList = vmList.split(",");
					var lastSpan = new String("");
					
					for (j=0; j<resultList.length-1; j++){
						if(resultList[j]==0){

							lastSpan += firstSpan0+changeNum(j+1)+secondSpan;
						}else if(resultList[j]==1){

							lastSpan += firstSpan1+changeNum(j+1)+secondSpan;
						}else if(resultList[j]==2){

							lastSpan += firstSpan2+changeNum(j+1)+secondSpan;
						}else if(resultList[j]==3){

							lastSpan += firstSpan3+changeNum(j+1)+secondSpan;
						}
						
					}
					if(i==0){
						$("#simulatorSubCover").children().eq(i).children().eq(3).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
					}else{
						$("#simulatorSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
					}
					
					if(k<simulatorOnHostListSize-1){
						k++;
					}
				}else{
					var lastSpan = new String("");
					for (j=0; j<12; j++){
						lastSpan += firstSpan4+changeNum(j+1)+secondSpan;
					}
					$("#simulatorSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
					
				}
				
			}
		}
		
		
		function changeVmStatus(){
			$(".cpuSign").remove();
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.size();
			
			k=0;
			for (i=0; i<simulatorHostListSize; i++){
				var numberResult = new String(simulatorOnHostList.get(k));
				var stringResult = numberResult.toString();
				if(simulatorHostList[i]==stringResult){
					vmList = new Array();
					vmList=getHostStatus(stringResult);
					resultList = vmList.split(",");
					var lastSpan = new String("");
					//alert(resultList.length);
					for (j=0; j<resultList.length-1; j++){

						if(i==0){
							if(resultList[j]==0){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_running");
							}else if(resultList[j]==1){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_idle");
							}else if(resultList[j]==2){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_unhealthy");
							}else if(resultList[j]==3){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_available");
							}
						}else{
							if(resultList[j]==0){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_running");
							}else if(resultList[j]==1){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_idle");
							}else if(resultList[j]==2){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_unhealthy");
							}else if(resultList[j]==3){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_available");
							}
						}
						
					}
					if(k<simulatorOnHostListSize-1){
						k++;
					}
					//$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(0).replaceWith("<img src='images/btn_host_power.png' CLASS='subHost_power'>");
					$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_"+changeNum(i)+".gif'></div>"); 
				}else{
					//$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(0).replaceWith("<img src='images/btn_host_power_off.png' CLASS='subHost_power'>");
					var lastSpan = new String("");
					for (j=0; j<12; j++){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_off");
					}
				}				
			}
		}


		
		function getHostStatus($item){
			var List = new String();

			List=window.NAPIVcluster.getHostStatus($item);

			return List;
			
		}
		
		function getRunningHostList($item){
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getRunningHostList($item);
			//alert(List);
			return List;
			
		}
		
		function getCurrentRunningVmList($item){
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getCurrentBusyVmList($item);
			//alert(List);
			return List;
			
		}
		
		function getCurrentIdleVmList($item){
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getCurrentIdleVmList($item);
			//alert(List);
			return List;
			
		}
		
		function getCurrentAvailableVmList($item){
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getCurrentAvailableVmList($item);
			//alert(List);
			return List;
			
		}
		
		function getCurrentUnhealthyVmList($item){
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getCurrentUnhealthyVmList($item);
			//alert(List);
			return List;
			
		}
		
		function start_reload_first(){
			int1 = setInterval( function() {
				changeStatusNumber();
			}, 	
			5000
			);

		};
	/*
	 * scene refresh time
	 * 
	 *   
	 *   
	 *   화면 리프레시 시간 
	 *   
	 *   
	 */	
		function start_reload_second(){
			int2 = setInterval( function() {
				changeVmStatus();
				hostStatusNumber();
				subAppendQueueRunning();
				subAppendQueueWaiting();
				//changeClusterStatusNumber();
			}, 	
			3000
			);

		}
		
		appendQueueRunning();
		appendQueueWaiting();
		appendImageRepository();
		statusNumber();
	 
		
		//changeClusterStatusNumber();
		subAppendQueueRunning();
		subAppendQueueWaiting();
		getVsimulatorDisplay();
		hostStatusNumber();
		hostVmStatus();
	$("[data-role=page]").live("pagebeforeshow",function(event) { 
		if(this.id == "intro") { 


	    }else if(this.id == "status") { 

	    	$('#menuOut').hide();
			$('#menuBack').hide();

	    }else if(this.id == "hostStatus") { 

	    	$('#menuSubOut').hide();
			$('#menuSubBack').hide();
    	} 

	});
	
	
	$("[data-role=page]").live("pageshow",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 1}, 200);
			//$("#logo1").animate({width:305px,opacity:1,margin:262px 0px 0px 474px;},1000);
			

	    }else if(this.id == "status") { 
	    	
	    	
	    	start_reload_first();
	    	addGif();
			changeStatusNumber();
			//$(".hostTop").after("<div CLASS='hostSign'><img src='images/host_sign_on.gif'></div>");
			
			
			
	    }else if(this.id == "hostStatus") { 
	    	changeVmStatus();
			hostStatusNumber();
	    	start_reload_second();
	    	
	    	//addGif2();
	    	 //hostStatusNumber();
	    	 //hostVmStatus();
	    	//alert("����° ������ �Դϴ�."); 
    		//$(".subHostToptitle").before("<div CLASS='cpuSign'><img src='images/fliker_cpu.gif'></div>"); 
    		$("#masterP").before("<img src='images/master_running.gif' ID='master_running'>"); 
    		//int1=setInterval(function(){lightning_one();}, 300);
    		//lightning_one();
    	} 

	}); 
	
  
	$("[data-role=page]").live("pagebeforehide  ",function(event) { 
		if(this.id == "intro") { 



	    }else if(this.id == "status") { 

			$(".hostSign").remove();
			window.clearInterval(int1);

			//$("#vsimulatorHostStatus").remove();
			//$("#vsimulatorHost").remove();
	    }else if(this.id == "hostStatus") { 
	    	//cpuCover
			$(".cpuSign").remove();
			$("#master_running").remove();
			window.clearInterval(int2);
			

			//window.clearInterval(int1);
	    } 

	}); 
	
	$("[data-role=page]").live("pagehide  ",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 0}, 0);


	    }else if(this.id == "status") { 

	    }else if(this.id == "hostStatus") { 

	    	//cpuCover
			$('#amazonSub').hide();
			$('#vsimulatorSub').hide();
			$('#fermiSub').hide();
			$('#gcloudSub').hide();
			//window.clearInterval(int1);
	    } 

	}); 
}); 




