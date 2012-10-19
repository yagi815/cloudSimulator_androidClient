$(document).ready(function() { 

	//var int1;
	var myScroll;
	function loaded() {
		myScroll = new iScroll('host_cover');
	}
	document.addEventListener('DOMContentLoaded', loaded, false);

	
	
	$('#logo2').animate({opacity: 1}, 200);

	theNumberOfCloud=""
	simulatorHostList=""
	CloudList = new Array();
	HostList = new Array();
	vmList = new Array();
	
	//var numberResult = new String();
	theNumberOfCloudRunnigArray = new Array();
	theNumberOfCloudIdleArray = new Array();
	theNumberOfCloudUnhealthyArray = new Array();
	theNumberOfCloudAvailableArray = new Array();
	
	
	
	var RunningTop=20;
	var IdleTop=20;
	var UnhealthyTop=20;
	var AvailableTop=20;
	
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
	

	function getAccessCloudList(){
		
		CloudList=window.NAPIVcluster.getAccessCloudList();
		//alert(List);
		theNumberOfCloud=CloudList.size();
		//getRunningHostList(CloudList.get(0));
		
		//alert(theNumberOfCloud);
		var Result = new String();

			
			//var ValueText = new String(CloudList.get(0));
			//var firstSpan = new String("<div CLASS='vsimulator'><div CLASS='statusTop'><p CLASS='title'>");
			//var secondSpan = new String("</p><a href='#hostStatus' data-transition='slide'><img src='images/btn_config.png' CLASS='statusConfig' CLASS='normalButton'></a><br><p CLASS='subTitle'>");
			//var lastSpan = new String(" Status</p></div></div>");
			Result =("<div CLASS='vsimulator'><div CLASS='statusTop' id='statusTop'><p CLASS='title'>vsimulator</p><a href='#hostStatus' data-transition='slide'><img src='images/btn_config.png' CLASS='statusConfig' CLASS='normalButton'></a><br><p CLASS='subTitle'>vsimulator Status</p></div><div CLASS='status' id='status'></div><div CLASS='host_cover'></div></div>");
			//alert(ValueText);
			//$(Result).insertBefore('#FermiCloud');



		
	}
	
	//<img src="kate.png" style="-webkit-mask-image: url(circle.svg)">
	simulatorHostList = {"host01","host02","host03","host04","host05","host06","host07","host08","host09","host10","host11","host12","host13","host14","host15"};
	simulatorHostListSize = simulatorHostList.size();
	
	simulatorOnHostList = getRunningHostList("vSimulator");
	simulatorOnHostListSize = simulatorOnHostList.size();
	
	alert(simulatorHostList);
	
	
	function statusNumber(){
		var Result = new String();
		
		var firstSpan = new String("<div CLASS='host' id='vsimulatorHost'><div CLASS='simulatorHostTop'><div CLASS='hostTitle'><p>");

			theNumberOfCloudRunnig = 0;
			theNumberOfCloudIdle = 0;
			theNumberOfCloudUnhealthy = 0;
			theNumberOfCloudAvailable = 0;
			//alert(HostList[0]);
			j=0;
			for (i=0; i<simulatorHostList.size(); i++){
				
				var numberResult = new String(simulatorOnHostList.get(j));
				var stringResult = numberResult.toString();
				
				var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>0</span><span CLASS='idle'>0</span><span CLASS='unhealthy'>0</span><span CLASS='available'>0</span></div></div>");
				$(firstSpan+stringResult+secondSpan).appendTo("#host_cover");
				}

			}

			$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+theNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+theNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+theNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+theNumberOfCloudAvailable+"</div></div>");
			$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(theNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(theNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(theNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(theNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");



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

	
	var vsimulatorHostList = new Array();
	vsimulatorHostList = window.NAPIVcluster.getRunningHostList("simulator");
	vsimulatorHostListSize = simulatorHostList.size();
	var fakeList = new Array();
	
	function getFakeList(){
		//alert(vsimulatorHostList);
		for (j=0; j<vsimulatorHostListSize; j++){
			fakeList[j] = vsimulatorHostList.get(j).toString();
		}
	}
	
	getFakeList();
	
	function changeStatusNumber(){
		
		
		
		
		//alert(vsimulatorHostList);
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		changeResult = "";
		stringchangeResult = "";

		for (j=0; j<vsimulatorHostListSize; j++){
			stringchangeResult = vsimulatorHostList.get(j).toString();

			
			List=window.NAPIVcluster.getCurrentRunningVmCount(stringchangeResult);
			
			changeNumberOfCloudRunnig+=parseInt(List.get(0));
			
			changeNumberOfCloudIdle+=parseInt(List.get(1));
			
			changeNumberOfCloudUnhealthy+=parseInt(List.get(2));

			changeNumberOfCloudAvailable+=parseInt(List.get(3));
			
			
			$("#host_cover").children().eq(j).children().eq(2).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+List.get(0).toString()+"</span><span CLASS='idle'>"+List.get(1).toString()+"</span><span CLASS='unhealthy'>"+List.get(2).toString()+"</span><span CLASS='available'>"+List.get(3).toString()+"</span></div></div>");
			stringchangeResult = "";
		}
		
		List=null;
		$("#statusCloudNumber").replaceWith("<div CLASS='status' id='statusCloudNumber'><div CLASS='statusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		
		$("#VclusterStatus").replaceWith("<div CLASS='VclusterStatus'  id='VclusterStatus'><div CLASS='VclusterStatusNumber running'>"+(parseInt(changeNumberOfCloudRunnig)+parseInt(RunningTop))+"</div><div CLASS='VclusterStatusNumber idle'>"+(parseInt(changeNumberOfCloudIdle)+parseInt(IdleTop))+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+(parseInt(changeNumberOfCloudUnhealthy)+parseInt(UnhealthyTop))+"</div><div CLASS='VclusterStatusNumber available'>"+(parseInt(changeNumberOfCloudAvailable)+parseInt(AvailableTop))+"</div></div>");
		//vsimulatorHostList = null;
		//vsimulatorHostListSize = null;
		
		changeNumberOfCloudRunnig = null;
		changeNumberOfCloudIdle = null;
		changeNumberOfCloudUnhealthy = null;
		changeNumberOfCloudAvailable = null;
		//stringchangeResult = null;
		//vsimulatorHostList.removeAll(vsimulatorHostList);
		//alert(vsimulatorHostList);
		//stringchangeResult = null;
	}
	////////////////////////////3번째 페이지 시작///////////////////////////
	function changeClusterStatusNumber(){
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		changeResult = "";
		stringchangeResult = "";
		//alert(vsimulatorHostList);
		for (j=0; j<vsimulatorHostListSize; j++){
			stringchangeResult = vsimulatorHostList.get(j).toString();

			
			List=window.NAPIVcluster.getCurrentRunningVmCount(stringchangeResult);
			
			changeNumberOfCloudRunnig+=parseInt(List.get(0));
			
			changeNumberOfCloudIdle+=parseInt(List.get(1));
			
			changeNumberOfCloudUnhealthy+=parseInt(List.get(2));

			changeNumberOfCloudAvailable+=parseInt(List.get(3));
			
			
			stringchangeResult = "";
		}
		
		//$("#VsimulatorclusterStatus").replaceWith("<div CLASS="VclusterStatus" id="VsimulatorclusterStatus"><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		$("#VsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='VsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
	}
	
	function getVsimulatorDisplay(){
		//alert(vsimulatorHostListSize);
		for (j=0; j<vsimulatorHostListSize; j++){
			stringchangeResult = vsimulatorHostList.get(j).toString();
			if(j==0){
				var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><img src='images/btn_host_power.png' CLASS='subHost_power'><p CLASS='subHostToptitle'>");
				var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
				//var thirdSpan = new String(" Status</p></div>");
				var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='masterCover'><div CLASS='master'><p ID='masterP'>Master</p></div></div><div CLASS='cpuCover'></div>");
				$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
			}else{
				var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><img src='images/btn_host_power.png' CLASS='subHost_power'><p CLASS='subHostToptitle'>");
				var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
				//var thirdSpan = new String(" Status</p></div>");
				var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
				$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
			}
			
		}
		
	}
	function addGif2(){
		
		for (j=0; j<vsimulatorHostListSize; j++){
				//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children().eq(1));
				//$("<div CLASS='cpuSign'><img src='images/fliker_cpu.gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children(".subHost_power"));
				//node.getElementsByTagName("tagname");
				//$("<div CLASS='hostSign'><img src='images/host_sign_on_"+i+changeNum(j)+".gif'></div>").appendTo($("#simulatorSubCover").children().eq(j).children().eq(0).children().eq(1));
				$("#simulatorSubCover").children().eq(j).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/fliker_cpu_"+changeNum(j)+".gif'></div>"); 
		}
	}
	function hostStatusNumber(){
		for (j=0; j<vsimulatorHostListSize; j++){

			stringchangeResult = vsimulatorHostList.get(j).toString();

			
			List=window.NAPIVcluster.getCurrentRunningVmCount(stringchangeResult);
			
			$("#simulatorSubCover").children().eq(j).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+List.get(0).toString()+"</span><span CLASS='subStatusNumber idle'>"+List.get(1).toString()+"</span><span CLASS='subStatusNumber unhealthy'>"+List.get(2).toString()+"</span><span CLASS='subStatusNumber available'>"+List.get(3).toString()+"</span></div></div>");
			stringchangeResult = ""; 
		}
		
	}
	
	function hostVmStatus(){
		
		var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
		var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
		var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
		var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
		var secondSpan = new String("</p></div>");
		for (i=0; i<vsimulatorHostListSize; i++){
			
			vmList[i] = getCurrentVmStatus(vsimulatorHostList.get(i).toString());
			
			var lastSpan = new String("");
			
			
			for (j=0; j<vmList[i].size(); j++){
				//for (j=0; j<5; j++){
				//Result = 1+parseInt(j)
				//alert(vmList[i].get(j));
				if(vmList[i].get(j).toString()==0){

					lastSpan += firstSpan0+changeNum(j+1)+secondSpan;
				}else if(vmList[i].get(j).toString()==1){

					lastSpan += firstSpan1+changeNum(j+1)+secondSpan;
				}else if(vmList[i].get(j).toString()==2){

					lastSpan += firstSpan2+changeNum(j+1)+secondSpan;
				}else if(vmList[i].get(j).toString()==3){

					lastSpan += firstSpan3+changeNum(j+1)+secondSpan;
				}
			}
			if(i==0){
				$("#simulatorSubCover").children().eq(i).children().eq(3).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
			}else{
				$("#simulatorSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
			}
		}
	}
	
	function  changeVmStatus(){
		

		
		for (i=0; i<vsimulatorHostListSize; i++){
			//window.NAPIVcluster.getChangetVmStatus(fakeList[i]);
			vmList = new Array();
			//vmList=getCurrentVmStatus(fakeList[i]);
			vmList=window.NAPIVcluster.getChangetVmStatus(fakeList[i]);
			vmList=vmList.replace("[","");
			vmList=vmList.replace("]","");
			resultList = vmList.split(",")
			//alert(resultList[0]);
			for (j=0; j<resultList.length; j++){
				resultVm = resultList[j];
				if(i==0){
					if(resultVm==0){
						$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_running");
					}else if(resultVm==1){
						$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_idle");
					}else if(resultVm==2){
						$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_unhealthy");
					}else if(resultVm==3){
						$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_available");
					}
				}else{
					if(resultVm==0){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_running");
					}else if(resultVm==1){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_idle");
					}else if(resultVm==2){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_unhealthy");
					}else if(resultVm==3){
						$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_available");
					}
				}
				
			}
			
			
		}
		//alert($vmList);
		
		
		//alert($vmList);
		//delete ($vmList);
		//$vmList.remove();;
		//fakeList = new Array();
		//alert($vmList);
	}
	
	
	
	
	function getCurrentRunningVmCount(Result){
		//var Result = new String($item);
		
		var List = new Array();
		List=window.NAPIVcluster.getCurrentRunningVmCount(Result);
		return List;
		//alert("test");
	}
	function getChangetVmStatus(Result){
		//var Result = new String($item);
		
		var List = new Array();
		List=window.NAPIVcluster.getChangetVmStatus(Result);
		return List;
		alert("test");
	}
	
	
	
	function getCurrentVmStatus(Result){
		//var Result = new String($item);
		
		var List = new Array();
		List=window.NAPIVcluster.getCurrentVmStatus(Result);
		//List=[1,1,1,1,1,1,1,1,1,1,1];
		return List;
		alert("test");
	}
	
	function getRunningHostList($item){
		try { 
			
			var List = new Array();
			
			//List.removeAll(List);
			
			List=window.NAPIVcluster.getRunningHostList($item);
			return List;
		} 
		finally { 
			List = null; 
		} 
		
	}
	
	function start_reload_first(){
		int1 = setInterval( function() {
			changeStatusNumber();
		}, 	
		5000
		);

	};
	function start_reload_second(){
		int2 = setInterval( function() {
			changeVmStatus();
			//hostStatusNumber();
			//changeClusterStatusNumber();
		}, 	
		5000
		);

	}
	
	appendQueueRunning();
	appendQueueWaiting();
	appendImageRepository();
	statusNumber();
	
	changeClusterStatusNumber();
	getVsimulatorDisplay();
	hostStatusNumber();
	addGif2();
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
			//changeStatusNumber();
			//$(".hostTop").after("<div CLASS='hostSign'><img src='images/host_sign_on.gif'></div>");
			
			
			
	    }else if(this.id == "hostStatus") { 
	    	start_reload_second();
	    	addGif2();
	    	 //hostStatusNumber();
	    	 //hostVmStatus();
	    	//alert("세번째 페이지 입니다."); 
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




