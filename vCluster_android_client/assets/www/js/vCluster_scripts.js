

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
		
		var RunningTop=62;
		var IdleTop=12;
		var UnhealthyTop=0;
		var AvailableTop=11;
		
		
		 
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
			//$.mobile.loading( 'show');
			$("#QueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='QueueRunning'>"+(window.NAPIVcluster.getRunningJobs()+RunningTop)+"</div>");
	    }
		
		function appendQueueWaiting() {
			$("#QueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='QueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
	    }

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
		var simulatorHostListSize = simulatorHostList.length;
		
		function statusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			var firstSpan = new String("<div CLASS='host' id='vsimulatorHost'><div CLASS='simulatorHostTop'><div CLASS='hostTitle'><p>");

				theNumberOfCloudRunnig = 0;
				theNumberOfCloudIdle = 0;
				theNumberOfCloudUnhealthy = 0;
				theNumberOfCloudAvailable = 0;
				//alert(HostList[0]);
				j=0;
				for (i=0; i<simulatorHostListSize; i++){
					
					var stringResult = simulatorOnHostList[j];			
					if(simulatorHostList[i]==stringResult){
						theNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult));
						theNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult));						
						theNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult));						
						theNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult));
						
						var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+getCurrentRunningVmList(stringResult)+"</span><span CLASS='idle'>"+getCurrentIdleVmList(stringResult)+"</span><span CLASS='unhealthy'>"+getCurrentUnhealthyVmList(stringResult)+"</span><span CLASS='available'>"+getCurrentAvailableVmList(stringResult)+"</span></div></div>");
						$(firstSpan+simulatorHostList[i]+secondSpan).appendTo("#host_cover");
						if(j<simulatorOnHostListSize-1){
							j++;
						}
						
					}else{
						var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span></div></div>");
						$(firstSpan+simulatorHostList[i]+secondSpan).appendTo("#host_cover");
					}

				}

				$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+theNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+theNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+theNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+theNumberOfCloudAvailable+"</div></div>");
				$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(theNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(theNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(theNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(theNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");

				simulatorOnHostList = null;
				simulatorOnHostListSize = null;

		}
		
		
		function changeStatusNumber(){
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			j=0;
			for (i=0; i<simulatorHostListSize; i++){
				var stringResult = simulatorOnHostList[j];
				//var stringResult = numberResult.toString();
				
				if(simulatorHostList[i]==stringResult){
					changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult));
					
					changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult));
					
					changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult));
					
					changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult));
					
					
					
					$("#host_cover").children().eq(i).children().eq(1).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+getCurrentRunningVmList(stringResult)+"</span><span CLASS='idle'>"+getCurrentIdleVmList(stringResult)+"</span><span CLASS='unhealthy'>"+getCurrentUnhealthyVmList(stringResult)+"</span><span CLASS='available'>"+getCurrentAvailableVmList(stringResult)+"</span></div></div>");
					
					if(j<simulatorOnHostListSize-1){
						j++;
					}
					//$("#host_cover").children().eq(i).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSign'><img src='images/host_sign_on_0"+changeNum(i)+".gif'></div>");
					$("#host_cover").children().eq(i).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>");
				}else{
					$("#host_cover").children().eq(i).children().eq(1).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span></div></div>");
					$("#host_cover").children().eq(i).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSignOff'><img src='images/host_sign_off.png'></div>");
				}
				
			}

			$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
			
			$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(changeNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(changeNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(changeNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(changeNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");

			
			simulatorOnHostList = null;
			simulatorOnHostListSize = null;
		}
		
		function addGif(){
			
			for (i=0; i<4; i++){
				var hostLength = $("#secondContent").children().eq(i).children().eq(2).children(".host").length;
				for (j=0; j<hostLength; j++){
					//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(j).children().eq(0));
					$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(j).children().eq(0));
				}
			}
			//$("#secondContent").children().eq(i).children().eq(2).children().eq(j).children().eq(0).replaceWith("<div CLASS='hostSign'></div>");
			$("#secondContent").children().eq(1).children().eq(2).children().eq(3).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSignOff'><img src='images/host_sign_off.png'></div>");
			$("#secondContent").children().eq(1).children().eq(2).children().eq(4).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSignOff'><img src='images/host_sign_off.png'></div>");
			$("#secondContent").children().eq(3).children().eq(2).children().eq(2).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSignOff'><img src='images/host_sign_off.png'></div>");
			$("#secondContent").children().eq(3).children().eq(2).children().eq(3).children().eq(0).children().eq(1).replaceWith("<div CLASS='hostSignOff'><img src='images/host_sign_off.png'></div>");
		}
		function signing(){
			int4 = setInterval( function() {
				$(".hostSign").fadeIn(150).fadeOut(150);
			//show().delay(500).hide();
			},
			300
			);
		}
		////////////////////////////����2��° ������ ��� �Ǵ� �Լ� ����///////////////////////////
		
		function subAppendQueueRunning() {
			//$.mobile.loading( 'show');
			$("#subQueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='subQueueRunning'>"+window.NAPIVcluster.getRunningJobs()+"</div>");
	    }
		
		function subAppendQueueWaiting() {
			$("#subQueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='subQueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
	    }
		
		function changeClusterStatusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			for (i=0; i<simulatorOnHostList.size(); i++){
				var numberResult = new String(simulatorOnHostList[i]);
				var stringResult = numberResult.toString();

				changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult));
				
				changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult));
				
				changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult));
				
				changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult));

			}
			$("#VsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='VsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
			simulatorOnHostList = null;
			simulatorOnHostListSize = null;
		}
		
		function getVsimulatorDisplay(){
			for (j=0; j<simulatorHostListSize; j++){
				stringchangeResult = simulatorHostList[j];
				if(j==0){
					var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><div CLASS='cpuSign'></div><p CLASS='subHostToptitle'>");
					var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
					var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='masterCover'><div CLASS='master'><p ID='masterP'>Master</p></div></div><div CLASS='cpuCover'></div>");
					$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
				}else{
					var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
					var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
					var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
					$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
				}
				
			}
			
		}
		function addGif2(){
			
			for (i=0; i<simulatorHostListSize; i++){
					//$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/fliker_cpu_"+changeNum(i)+".gif'></div>"); 
				$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/fliker_cpu_00.png'></div>");
			}
		}
		
		
		////////////////2�������� ����� status�� ������ ���� �ϴ� �Լ� //////////////////////////
		function hostStatusNumber(){
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			changeNumberOfCloudRunnig = 0;
			changeNumberOfCloudIdle = 0;
			changeNumberOfCloudUnhealthy = 0;
			changeNumberOfCloudAvailable = 0;
			j=0;
			for (i=0; i<simulatorHostListSize; i++){
				var stringResult = simulatorOnHostList[j];
				//var stringResult = numberResult.toString();

				if(simulatorHostList[i]==stringResult){
					changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult));
					changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult));
					changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult));
					changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult));
				    $("#simulatorSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+getCurrentRunningVmList(stringResult)+"</span><span CLASS='subStatusNumber idle'>"+getCurrentIdleVmList(stringResult)+"</span><span CLASS='subStatusNumber unhealthy'>"+getCurrentUnhealthyVmList(stringResult)+"</span><span CLASS='subStatusNumber available'>"+getCurrentAvailableVmList(stringResult)+"</span></div></div>");
					if(j<simulatorOnHostListSize-1){
						j++;
					}
				}else{
					$("#simulatorSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span></div></div>");
				}
			}
			$("#VsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='VsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
			simulatorOnHostList = null;
			simulatorOnHostListSize = null;
		}
		
		function vmDragStart(){
	          $( ".cpu_running" ).draggable({ revert: "valid",opacity: 1.0, helper: "clone",stack: ".cpuCover div" });
	          $( ".cpu_idle" ).draggable({ revert: "valid",opacity: 1.0, helper: "clone" ,stack: ".cpuCover div" });
	          $( ".hostSub" ).droppable({
	        	  over: function(ev, ui) {
	        		  $(this).addClass( 'hostSubOver' );
	        		},
	        		out: function(ev, ui) {
	        			  $(this).removeClass( 'hostSubOver' );
	        			},
	        	  drop: function( event, ui ) {
	        		  $(this).removeClass( 'hostSubOver' );
	        		  $dragBox=ui.draggable;
	        		  $dropBox=$(this);
	        		  
	        		  $dragParent=$dragBox.parent().children();
	        		  $dropParent=$dropBox;
	        		  
	        		  var dragIndex = $dragParent.index($dragBox);
	        		  var dropIndex = $dropBox.parent().children().index($dropBox);
						
	        		  var dragParentIndex = $dragBox.parent().parent().parent().children().index($dragBox.parent().parent());
	        		  
	        		  // 추가할 부분
	        		  // =====================================================
	        		  var vmName = "vm"+(dragIndex+1);
	        		  var srcHost = "host-"+(dragParentIndex+1);
	        		  var desHost = "host-"+(dropIndex+1);
	        		  
//	        		  List=window.NAPIVcluster.funcName(vmName, srcHost, desHost);
						
	        		  alert("vm"+(dragIndex+1)+",vm host"+(dragParentIndex+1)+",drop host"+(dropIndex+1));
	        	  }
	          });
        } 
		
		///////////////2�������� ȣ��Ʈ�� vm�� ���¸� ó�� ���� �ϴ� �Լ� //////////////////
		function hostVmStatus(){
			
			var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
			var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
			var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
			var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
			var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
			var secondSpan = new String("</p></div>");
			
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			k=0;
			for (i=0; i<simulatorHostListSize; i++){
				
				var stringResult = simulatorOnHostList[k];
				//var stringResult = numberResult.toString();
				if(simulatorHostList[i]==stringResult){
					//vmList = new Array();
					resultList=getHostStatus(stringResult);
					//resultList = vmList.split(",");
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
			simulatorOnHostList = null;
			simulatorOnHostListSize = null;
			vmDragStart();
		}
		
		///////////////2�������� ȣ��Ʈ�� vm�� ���¸� ���ε� �ϴ� �Լ� //////////////////
		function changeVmStatus(){
			$(".cpuSign").remove();
			simulatorOnHostList = getRunningHostList("vSimulator");
			simulatorOnHostListSize = simulatorOnHostList.length-1;
			
			k=0;
			for (i=0; i<simulatorHostListSize; i++){
				var stringResult = simulatorOnHostList[k];
				//var stringResult = numberResult.toString();
				
				//var numberResult = new String(simulatorOnHostList[k]);
				//var stringResult = numberResult.toString();
				if(simulatorHostList[i]==stringResult){
					resultList=getHostStatus(stringResult);
					var lastSpan = new String("");
					for (j=0; j<resultList.length-1; j++){

						if(i==0){
							if(resultList[j]==0){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_running").draggable( 'enable' );
								//$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).draggable( 'enable' );
							}else if(resultList[j]==1){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_idle").draggable( 'enable' );
								//$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).draggable( 'enable' );
							}else if(resultList[j]==2){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_unhealthy");
							}else if(resultList[j]==3){
								$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_available");
							}
						}else{
							if(resultList[j]==0){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_running").draggable( 'enable' );
								//$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).draggable( 'enable' );
							}else if(resultList[j]==1){
								$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_idle").draggable( 'enable' );
								//$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j);
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
					//$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_"+changeNum(i)+".gif'></div>");
					$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_00.png'></div>");
				}else{
					var lastSpan = new String("");
					for (j=0; j<12; j++){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_off").draggable( 'disable' );
						//$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).draggable( 'disable' );
					}
				}				
			}
			
			simulatorOnHostList = null;
			simulatorOnHostListSize = null;
			vmDragStart();
		}
		
		function flikering(){
			int3 = setInterval( function() {
				$(".cpuSign").fadeIn(250).fadeOut(250);
			//show().delay(500).hide();
			},
			500
			);
		}
		

		//////////ȣ��Ʈ�� vm�� ����/////////////
		function getHostStatus($item){
			var List = new String();
			List=window.NAPIVcluster.getHostStatus($item);
			resultList = List.split(",");
			return resultList;
			
		}
		//////////���� �������� ȣ��Ʈ/////////////
		function getRunningHostList($item){
			var List = new Array();
			List=window.NAPIVcluster.getRunningHostList($item);
			resultList = List.split(",");
			return resultList;
			
		}
		//////////ȣ��Ʈ�� running vm�� ����/////////////
		function getCurrentRunningVmList($item){
			var List = new Array();
			List=window.NAPIVcluster.getCurrentBusyVmList($item);
			resultList = List.split(",");
			return resultList.length-1;
			
		}
		//////////ȣ��Ʈ�� idle vm�� ����/////////////
		function getCurrentIdleVmList($item){
			var List = new Array();
			List=window.NAPIVcluster.getCurrentIdleVmList($item);
			resultList = List.split(",");
			return resultList.length-1;
			
		}
		//////////ȣ��Ʈ�� available vm�� ����/////////////
		function getCurrentAvailableVmList($item){
			var List = new Array();
			List=window.NAPIVcluster.getCurrentAvailableVmList($item);
			resultList = List.split(",");
			return resultList.length-1;
			
		}
		//////////ȣ��Ʈ�� unhealthy vm�� ����/////////////
		function getCurrentUnhealthyVmList($item){
			var List = new Array();
			List=window.NAPIVcluster.getCurrentUnhealthyVmList($item);
			resultList = List.split(",");
			return resultList.length-1;
			
		}
		//////////���� 1�������� ���ε� �Լ� /////////////
		function start_reload_first(){
			int1 = setInterval( function() {
				changeStatusNumber();//���� ȣ��Ʈ�� ���¸� ���ε� �ϴ� �Լ� 
				appendQueueRunning();//����� queue status�� running jobs �� ���ε� �ϴ� �Լ� 
				appendQueueWaiting();//����� queue status�� waiting jobs �� ���ε� �ϴ� �Լ� 
			}, 	
			3000//���ε� ���� ������ ���� 
			);

		};
		//////////���� 2�������� ���ε� �Լ� /////////////
		function start_reload_second(){
			int2 = setInterval( function() {
				changeVmStatus();//���� ȣ��Ʈ��  vm�� ���¸� ���ε� �ϴ� �Լ�
				hostStatusNumber();//����� status�� ������ ���ε� �ϴ� �� 
				subAppendQueueRunning();//����� queue status�� running jobs �� ���ε� �ϴ� �Լ� 
				subAppendQueueWaiting();//����� queue status�� waiting jobs �� ���ε� �ϴ� �Լ� 
			}, 	
			3000//���ε� ���� ������ ���� 
			);

		}
		
		////////////���� 1������ ������ ���� �⺻ �Լ� ȣ�� ////////////////////
		appendQueueRunning();
		appendQueueWaiting();
		appendImageRepository();
		statusNumber();

		////////////���� 2������ ������ ���� �⺻ �Լ� ȣ�� ////////////////////
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
	    	
	    		//alert("1");
		    	addGif();
		    	start_reload_first();
		    	
		    	
				changeStatusNumber();
				signing();
	    	//}else{
	    		
	    	//}
			//$(".hostTop").after("<div CLASS='hostSign'><img src='images/host_sign_on.gif'></div>");
			
			
			
	    }else if(this.id == "hostStatus") { 
	    	if(pageNumber=="1"){
	    		changeVmStatus();
	    		hostStatusNumber();
	    		flikering();
	    		start_reload_second();
	    		$("#masterP").before("<img src='images/master_running.gif' ID='master_running'>"); 
	    	}else if(pageNumber=="2"){
	    		//alert("2");
	    	}else if(pageNumber=="3"){
	    		
	    	}else if(pageNumber=="4"){
	    		
	    	}
			
	    	//addGif2();
	    	 //hostStatusNumber();
	    	 //hostVmStatus();
	    	//alert("����° ������ �Դϴ�."); 
    		//$(".subHostToptitle").before("<div CLASS='cpuSign'><img src='images/fliker_cpu.gif'></div>"); 
    		
    		//int1=setInterval(function(){lightning_one();}, 300);
    		//lightning_one();
    	} 

	}); 
	
  
	$("[data-role=page]").live("pagebeforehide  ",function(event) { 
		if(this.id == "intro") { 



	    }else if(this.id == "status") { 
	    	
			$(".hostSign").remove();
	    	$(".hostSignOff").remove();
			window.clearInterval(int1);
			window.clearInterval(int4);
			//$("#vsimulatorHostStatus").remove();
			//$("#vsimulatorHost").remove();
	    }else if(this.id == "hostStatus") { 
	    	if(pageNumber=="1"){
	    		$("#master_running").remove();
				window.clearInterval(int2);
				window.clearInterval(int3);
	    	}else if(pageNumber=="2"){
	    		
	    	}else if(pageNumber=="3"){
	    		
	    	}else if(pageNumber=="4"){
	    		
	    	}
	    	//cpuCover
			$(".cpuSign").remove();
			pageNumber=0;

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




