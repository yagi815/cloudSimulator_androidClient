	theNumberOfCloud=""
	simulatorOnHostList=""
	CloudList = new Array();
	HostList = new Array();
	vmList = new Array();

	theNumberOfCloudRunnigArray = new Array();
	theNumberOfCloudIdleArray = new Array();
	theNumberOfCloudUnhealthyArray = new Array();
	theNumberOfCloudAvailableArray = new Array();
	
	window.NAPIVcluster.simulatorStart();
	
	var RunningTop=0;
	var IdleTop=0;
	var UnhealthyTop=0;
	var AvailableTop=0;
	
	///////////////////////////////////////////////////////////////////////////////////////
	////////////////////////vsimulator를 제외한 변수 및 함수 설정///////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	var FermiHostList=new Array("host01","host02","host03","host04","host05","host06","host07");
	var FermiHostListSize = FermiHostList.length;
	
	var FermiOnHostList=new Array("host01","host02","host03","host04");
	var FermiOnHostListSize = FermiOnHostList.length;
	
	var GCloudHostList=new Array("host01","host02","host03","host04");
	var GCloudHostListSize = GCloudHostList.length;
	
	var GCloudOnHostList=new Array("host01","host02");
	var GCloudOnHostListSize = GCloudOnHostList.length;
	
	var AmazonOnHostList=new Array("host01","host02","host03","host04","host05","host06");
	var AmazonOnHostListSize = AmazonOnHostList.length;
	
	function FermiVmList( host ) {//
		if(host=="host01"){
			var FermiVmRealList = [ 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0  ];
		}else if(host=="host02"){
			var FermiVmRealList = [ 0, 1, 0, 3, 0, 1, 0, 3, 0, 1, 0, 0  ];
		}else if(host=="host03"){
			var FermiVmRealList = [ 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 0, 3  ];
		}else if(host=="host04"){
			var FermiVmRealList = [ 0, 1, 2, 3, 0, 1, 2, 3, 0, 1, 2, 0  ];
		}
		return FermiVmRealList;
	}
	function GCloudVmList( host ) {
		if(host=="host01"){
			var GCloudVmRealList = [ 0, 1, 0, 3, 0, 1, 0, 3, 0, 1, 1, 3 ];
		}else if(host=="host02"){
			var GCloudVmRealList = [ 0, 1, 2, 0, 0, 1, 0, 3, 0, 1, 1, 3 ];
		}
		return GCloudVmRealList;
	}
	function AmazonVmList( host ) {
		if(host=="host01"){
			var AmazonVmRealList = [ 1, 1, 1, 3, 0, 1, 1, 3, 0, 1, 0, 3 ];
		}else if(host=="host02"){
			var AmazonVmRealList = [ 1, 1, 3, 3, 1, 1, 0, 3, 0, 1, 1, 3 ];
		}else if(host=="host03"){
			var AmazonVmRealList = [ 3, 1, 3, 3, 3, 1, 1, 3, 0, 1, 1, 3 ];
		}else if(host=="host04"){
			var AmazonVmRealList = [ 0, 1, 3, 3, 0, 1, 0, 3, 0, 1, 1, 3 ];
		}else if(host=="host05"){
			var AmazonVmRealList = [ 0, 1, 1, 3, 3, 1, 1, 3, 0, 1, 0, 3 ];
		}else if(host=="host06"){
			var AmazonVmRealList = [ 0, 1, 1, 3, 1, 1, 1, 3, 0, 1, 3, 3 ];
		}
		return AmazonVmRealList;
	}
	////////서브 2페이지의 fermi 화면 구성////////////
	function getFermiCloudDisplay(){
		for (j=0; j<FermiHostListSize; j++){
			stringchangeResult = FermiHostList[j];
			var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
			var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
			var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
			$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#FermiSubCover");
			if(j+1==FermiHostListSize){
				$("#FermiSubCover").children().eq(j).css("margin-right" , "0px");
			}
		}
		$("#FermiSubCover").css("width",FermiHostListSize*175-14+"px");
	}
////////서브 2페이지의 GCloud 화면 구성////////////
	function getGCloudDisplay(){
		for (j=0; j<GCloudHostListSize; j++){
			stringchangeResult = GCloudHostList[j];
			var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
			var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
			var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
			$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#GCloudSubCover");
			if(j+1==GCloudHostListSize){
				$("#GCloudSubCover").children().eq(j).css("margin-right" , "0px");
			}
		}
		$("#GCloudSubCover").css("width",GCloudHostListSize*175-14+"px");
	}
////////서브 2페이지의 Amazon 화면 구성////////////
	function getAmazonDisplay(){
		stringchangeResult = AmazonOnHostList[0];
		var firstSpan = new String("<div CLASS='hostSubAmazon'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
		var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
		var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div Class='amazonCover'>");
		var fourthSpan = new String("<div CLASS='cpuAmazonCover'>1111</div>");
		var fifthSpan = new String("</div>");
		var lastSpan = new String("");
		//alert(GCloudHostListSize);
		for (j=0; j<AmazonOnHostListSize; j++){
			lastSpan+=fourthSpan;
		}
		$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan+lastSpan+fifthSpan).appendTo("#AmazonSubCover");
		$("#AmazonSubCover").css("width","1211px");
	}
////////서브 2페이지의 fermi status Number 구성 및 업데이트 ////////////
	function hostFermiStatusNumber(){
		simulatorOnHostList = FermiOnHostList;
		simulatorOnHostListSize = FermiOnHostListSize;
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		k=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[k];
			if(FermiHostList[i]==stringResult){
				FermiVmRunnigNumber=0;
				FermiVmIdleNumber=0;
				FermiVmUnhealthyNumber=0;
				FermiVmAvailbleNumber=0;
				for (j=0; j<12; j++){				
					if(FermiVmList(stringResult)[j]==0){
						changeNumberOfCloudRunnig+=1;
						FermiVmRunnigNumber++
					}else if(FermiVmList(stringResult)[j]==1){
						changeNumberOfCloudIdle+=1;
						FermiVmIdleNumber++
					}else if(FermiVmList(stringResult)[j]==2){
						changeNumberOfCloudUnhealthy+=1;
						FermiVmUnhealthyNumber++
					}else if(FermiVmList(stringResult)[j]==3){
						changeNumberOfCloudAvailable+=1;
						FermiVmAvailbleNumber++
					}
				}
				$("#FermiSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+FermiVmRunnigNumber+"</span><span CLASS='subStatusNumber idle'>"+FermiVmIdleNumber+"</span><span CLASS='subStatusNumber unhealthy'>"+FermiVmUnhealthyNumber+"</span><span CLASS='subStatusNumber available'>"+FermiVmAvailbleNumber+"</span></div></div>");
				if(k<simulatorOnHostListSize-1){
					k++;
				}
			}else{
				$("#FermiSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span></div></div>");
			}
		}
		$("#vsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='vsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////서브 2페이지의 GCloud status Number 구성 및 업데이트 ////////////
	function hostGStatusNumber(){
		simulatorOnHostList = GCloudOnHostList;
		simulatorOnHostListSize = GCloudOnHostListSize;
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		k=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[k];
			if(GCloudHostList[i]==stringResult){
				GCloudVmRunnigNumber=0;
				GCloudVmIdleNumber=0;
				GCloudVmUnhealthyNumber=0;
				GCloudVmAvailbleNumber=0;
				for (j=0; j<12; j++){
					if(GCloudVmList(stringResult)[j]==0){
						changeNumberOfCloudRunnig+=1;
						GCloudVmRunnigNumber++
					}else if(GCloudVmList(stringResult)[j]==1){
						changeNumberOfCloudIdle+=1;
						GCloudVmIdleNumber++
					}else if(GCloudVmList(stringResult)[j]==2){
						changeNumberOfCloudUnhealthy+=1;
						GCloudVmUnhealthyNumber++
					}else if(GCloudVmList(stringResult)[j]==3){
						changeNumberOfCloudAvailable+=1;
						GCloudVmAvailbleNumber++
					}
				}
				$("#GCloudSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+GCloudVmRunnigNumber+"</span><span CLASS='subStatusNumber idle'>"+GCloudVmIdleNumber+"</span><span CLASS='subStatusNumber unhealthy'>"+GCloudVmUnhealthyNumber+"</span><span CLASS='subStatusNumber available'>"+GCloudVmAvailbleNumber+"</span></div></div>");
				if(k<simulatorOnHostListSize-1){
					k++;
				}
			}else{
				$("#GCloudSubCover").children().eq(i).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span><span CLASS='subStatusNumber off'>0</span></div></div>");
			}
		}
		$("#vsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='vsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////서브 2페이지의 Amazon status Number 구성 및 업데이트 ////////////
	function hostAmazonStatusNumber(){
		simulatorOnHostList = AmazonOnHostList;
		simulatorOnHostListSize = AmazonOnHostListSize;
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		for (i=0; i<AmazonOnHostListSize; i++){
			var stringResult = simulatorOnHostList[i];
				AmazonVmRunnigNumber=0;
				AmazonVmIdleNumber=0;
				AmazonVmUnhealthyNumber=0;
				AmazonVmAvailbleNumber=0;
				for (j=0; j<12; j++){
					if(AmazonVmList(stringResult)[j]==0){
						changeNumberOfCloudRunnig+=1;
						AmazonVmRunnigNumber++
					}else if(AmazonVmList(stringResult)[j]==1){
						changeNumberOfCloudIdle+=1;
						AmazonVmIdleNumber++
					}else if(AmazonVmList(stringResult)[j]==2){
						changeNumberOfCloudUnhealthy+=1;
						AmazonVmUnhealthyNumber++
					}else if(AmazonVmList(stringResult)[j]==3){
						changeNumberOfCloudAvailable+=1;
						AmazonVmAvailbleNumber++					
					}	
				}
		}
		$("#AmazonSubCover").children().eq(0).children().eq(1).replaceWith("<div CLASS='subStatus'><span CLASS='subStatusNumber running'>"+changeNumberOfCloudRunnig+"</span><span CLASS='subStatusNumber idle'>"+changeNumberOfCloudIdle+"</span><span CLASS='subStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</span><span CLASS='subStatusNumber available'>"+changeNumberOfCloudAvailable+"</span></div></div>");
		$("#vsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='vsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////서브 2페이지의 fermi 호스트 별 vm staus 구성 ////////////
	function FermihostVmStatus(){		
		var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
		var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
		var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
		var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
		var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
		var secondSpan = new String("</p></div>");		
		simulatorOnHostList = FermiOnHostList;
		simulatorOnHostListSize = FermiOnHostListSize;		
		k=0;
		for (i=0; i<FermiHostListSize; i++){		
			var stringResult = simulatorOnHostList[k];
			if(FermiHostList[i]==stringResult){
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					if(FermiVmList(stringResult)[j]==0){

						lastSpan += firstSpan0+changeNum(j+1)+secondSpan;
					}else if(FermiVmList(stringResult)[j]==1){

						lastSpan += firstSpan1+changeNum(j+1)+secondSpan;
					}else if(FermiVmList(stringResult)[j]==2){

						lastSpan += firstSpan2+changeNum(j+1)+secondSpan;
					}else if(FermiVmList(stringResult)[j]==3){

						lastSpan += firstSpan3+changeNum(j+1)+secondSpan;
					}
					
				}
				$("#FermiSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
				if(k<simulatorOnHostListSize-1){
					k++;
				}
				$("#FermiSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_00.png'></div>");
			}else{
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					lastSpan += firstSpan4+changeNum(j+1)+secondSpan;
				}
				$("#FermiSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
			}			
		}
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////서브 2페이지의 GCloud  호스트 별 vm staus 구성 ////////////	
	function GhostVmStatus(){	
		var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
		var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
		var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
		var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
		var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
		var secondSpan = new String("</p></div>");		
		simulatorOnHostList = GCloudOnHostList;
		simulatorOnHostListSize = GCloudOnHostListSize;		
		k=0;
		for (i=0; i<GCloudHostListSize; i++){		
			var stringResult = GCloudOnHostList[k];
			if(GCloudOnHostList[i]==stringResult){
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					if(GCloudVmList(stringResult)[j]==0){

						lastSpan += firstSpan0+changeNum(j+1)+secondSpan;
					}else if(GCloudVmList(stringResult)[j]==1){

						lastSpan += firstSpan1+changeNum(j+1)+secondSpan;
					}else if(GCloudVmList(stringResult)[j]==2){

						lastSpan += firstSpan2+changeNum(j+1)+secondSpan;
					}else if(GCloudVmList(stringResult)[j]==3){

						lastSpan += firstSpan3+changeNum(j+1)+secondSpan;
					}
					
				}
				$("#GCloudSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
				if(k<simulatorOnHostListSize-1){
					k++;
				}
				$("#GCloudSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_00.png'></div>");
			}else{
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					lastSpan += firstSpan4+changeNum(j+1)+secondSpan;
				}
				$("#GCloudSubCover").children().eq(i).children().eq(2).replaceWith("<div CLASS='cpuCover'>"+lastSpan+"</div>");
			}		
		}
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////서브 2페이지의 Amazon 호스트 별 vm staus 구성 ////////////
	function AmazonhostVmStatus(){		
		var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
		var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
		var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
		var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
		var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
		var secondSpan = new String("</p></div>");	
		simulatorOnHostList = AmazonOnHostList;
		simulatorOnHostListSize = AmazonOnHostListSize;
		for (i=0; i<AmazonOnHostListSize; i++){
			var stringResult = AmazonOnHostList[i];
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					if(AmazonVmList(stringResult)[j]==0){

						lastSpan += firstSpan0+changeNum(j+1)+secondSpan;
					}else if(AmazonVmList(stringResult)[j]==1){

						lastSpan += firstSpan1+changeNum(j+1)+secondSpan;
					}else if(AmazonVmList(stringResult)[j]==2){

						lastSpan += firstSpan2+changeNum(j+1)+secondSpan;
					}else if(AmazonVmList(stringResult)[j]==3){

						lastSpan += firstSpan3+changeNum(j+1)+secondSpan;
					}			
				}
				$("#AmazonSubCover").children().eq(0).children().eq(2).children().eq(i).replaceWith("<div CLASS='cpuAmazonCover'>"+lastSpan+"</div>");
		}
		$("#AmazonSubCover").children().eq(0).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_00.png'></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////
	
	
	
////////1자리를 숫자를 10자리 숫자로 변경하는 함수 ////////////
	function changeNum( vmNumber ) {
  	  var RetrunNumber;
  	  if(vmNumber<10){
  		  RetrunNumber="0"+vmNumber;
  	  }else{
  		  RetrunNumber=vmNumber;
  	  }
  	  return RetrunNumber;
    }
	
///////////////////////////////////////////////////////////////////////////////////
////////////////////////////서브 1페이지 화면 설정 /////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////	
	
////////queue Status Running Jobs 숫자를 갱신하는 함수  ////////////
	function appendQueueRunning() {
		//$.mobile.loading( 'show');
		$("#QueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='QueueRunning'>"+(window.NAPIVcluster.getRunningJobs()+RunningTop)+"</div>");
    }
////////queue Status Wating Jobs 숫자를 갱신하는 함수  ////////////	
	function appendQueueWaiting() {
		$("#QueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='QueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
    }
////////Image Repository를 출력 하는 함수  ////////////
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
	
////////vsimulator의 총 호스트 갯수를 설정  ////////////
	var simulatorHostList=new Array("host01","host02","host03","host04","host05","host06","host07","host08","host09","host10","host11","host12","host13","host14","host15");
	var simulatorHostListSize = simulatorHostList.length;
	
////////Status 숫자 및 vsimulator Status 숫자를 처음 세팅하는 함수  ////////////
	function statusNumber(){
		simulatorOnHostList = getRunningHostList("vsimulator");
		simulatorOnHostListSize = simulatorOnHostList.length-1;
		var firstSpan = new String("<div CLASS='host' id='vsimulatorHost'><div CLASS='simulatorHostTop'><div CLASS='hostTitle'><p>");
			theNumberOfCloudRunnig = 0;
			theNumberOfCloudIdle = 0;
			theNumberOfCloudUnhealthy = 0;
			theNumberOfCloudAvailable = 0;
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
			
			
		/////////fermiCloud Status 숫자 세팅하는 부분 ////////////////////////
		var firstSpan = new String("<div CLASS='host' id='FermiHost'><div CLASS='FermiHostTop'><div CLASS='hostTitle'><p>");
			changeNumberOfFermiRunnig = 0;
			changeNumberOfFermiIdle = 0;
			changeNumberOfFermiUnhealthy = 0;
			changeNumberOfFermiAvailable = 0;
			k=0;
			for (i=0; i<FermiHostListSize; i++){
				var stringResult = FermiOnHostList[k];
				if(FermiHostList[i]==stringResult){
					FermiVmRunnigNumber=0;
					FermiVmIdleNumber=0;
					FermiVmUnhealthyNumber=0;
					FermiVmAvailbleNumber=0;
					for (j=0; j<12; j++){
						if(FermiVmList(stringResult)[j]==0){
							changeNumberOfFermiRunnig+=1;
							FermiVmRunnigNumber++
							RunningTop++
						}else if(FermiVmList(stringResult)[j]==1){
							changeNumberOfFermiIdle+=1;
							FermiVmIdleNumber++
							IdleTop++
						}else if(FermiVmList(stringResult)[j]==2){
							changeNumberOfFermiUnhealthy+=1;
							FermiVmUnhealthyNumber++
							UnhealthyTop++
						}else if(FermiVmList(stringResult)[j]==3){
							changeNumberOfFermiAvailable+=1;
							FermiVmAvailbleNumber++
							AvailableTop++
						}
					}
				    var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='FermiHostStatus'><span CLASS='running'>"+FermiVmRunnigNumber+"</span><span CLASS='idle'>"+FermiVmIdleNumber+"</span><span CLASS='unhealthy'>"+FermiVmUnhealthyNumber+"</span><span CLASS='available'>"+FermiVmAvailbleNumber+"</span></div></div>");
						$(firstSpan+FermiHostList[i]+secondSpan).appendTo("#Fermi_cover");
					if(k<FermiOnHostListSize-1){
						k++;
					}
				}else{
					var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='FermiHostStatus'><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span></div></div>");
						$(firstSpan+FermiHostList[i]+secondSpan).appendTo("#Fermi_cover");
				}
			}
			$("#statusFermiNumber").replaceWith("<div CLASS='status' id='statusFermiNumber'><div CLASS='statusNumber running'>"+changeNumberOfFermiRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfFermiIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfFermiUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfFermiAvailable+"</div></div>");
			/////////Amazon Status 숫자 세팅하는 부분 ////////////////////////
			var firstSpan = new String("<div CLASS='host' id='AmazonHost'><div CLASS='AmazonHostTop'><div CLASS='hostTitle'><p>");
				changeNumberOfAmazonRunnig = 0;
				changeNumberOfAmazonIdle = 0;
				changeNumberOfAmazonUnhealthy = 0;
				changeNumberOfAmazonAvailable = 0;
				l=0;
				for (i=0; i<AmazonOnHostListSize; i++){
					var stringResult = AmazonOnHostList[i];
					for (j=0; j<12; j++){
						if(AmazonVmList(stringResult)[j]==0){
							changeNumberOfAmazonRunnig+=1;
							RunningTop++
						}else if(AmazonVmList(stringResult)[j]==1){
							changeNumberOfAmazonIdle+=1;
							IdleTop++
						}else if(AmazonVmList(stringResult)[j]==2){
							changeNumberOfAmazonUnhealthy+=1;
							UnhealthyTop++
						}else if(AmazonVmList(stringResult)[j]==3){
							changeNumberOfAmazonAvailable+=1;
							AvailableTop++
						}
					}
				}
				var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='FermiHostStatus'><span CLASS='running'>"+changeNumberOfAmazonRunnig+"</span><span CLASS='idle'>"+changeNumberOfAmazonIdle+"</span><span CLASS='unhealthy'>"+changeNumberOfAmazonUnhealthy+"</span><span CLASS='available'>"+changeNumberOfAmazonAvailable+"</span></div></div>");
				$(firstSpan+"host01"+secondSpan).appendTo("#Amazon_cover");
				$("#statusAmazonNumber").replaceWith("<div CLASS='status' id='statusAmazonNumber'><div CLASS='statusNumber running'>"+changeNumberOfAmazonRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfAmazonIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfAmazonUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfAmazonAvailable+"</div></div>");

			/////////GCloud Status 숫자 세팅하는 부분 //////////////
			var firstSpan = new String("<div CLASS='host' id='GHost'><div CLASS='GHostTop'><div CLASS='hostTitle'><p>");
		
				changeNumberOfGRunnig = 0;
				changeNumberOfGIdle = 0;
				changeNumberOfGUnhealthy = 0;
				changeNumberOfGAvailable = 0;
				l=0;
				for (i=0; i<GCloudHostListSize; i++){
					var stringResult = GCloudOnHostList[l];
					if(GCloudHostList[i]==stringResult){
						GVmRunnigNumber=0;
						GVmIdleNumber=0;
						GVmUnhealthyNumber=0;
						GVmAvailbleNumber=0;
						for (j=0; j<12; j++){
							if(GCloudVmList(stringResult)[j]==0){
								changeNumberOfGRunnig+=1;
								GVmRunnigNumber++
								RunningTop++
							}else if(GCloudVmList(stringResult)[j]==1){
								changeNumberOfGIdle+=1;
								GVmIdleNumber++
								IdleTop++
							}else if(GCloudVmList(stringResult)[j]==2){
								changeNumberOfGUnhealthy+=1;
								GVmUnhealthyNumber++
								UnhealthyTop++
							}else if(GCloudVmList(stringResult)[j]==3){
								changeNumberOfGAvailable+=1;
								GVmAvailbleNumber++
								AvailableTop++
							}
						}
					     var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='FermiHostStatus'><span CLASS='running'>"+GVmRunnigNumber+"</span><span CLASS='idle'>"+GVmIdleNumber+"</span><span CLASS='unhealthy'>"+GVmUnhealthyNumber+"</span><span CLASS='available'>"+GVmAvailbleNumber+"</span></div></div>");
							$(firstSpan+GCloudHostList[i]+secondSpan).appendTo("#G_cover");
						if(l<GCloudOnHostListSize-1){
							l++;
						}
					}else{
						var secondSpan = new String("</p></div></div><div CLASS='hostStatus' id='FermiHostStatus'><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span><span CLASS='off'>0</span></div></div>");
							$(firstSpan+GCloudHostList[i]+secondSpan).appendTo("#G_cover");
					}
				}
				$("#statusGNumber").replaceWith("<div CLASS='status' id='statusGNumber'><div CLASS='statusNumber running'>"+changeNumberOfGRunnig+"</div><div CLASS='statusNumber idle'>"+changeNumberOfGIdle+"</div><div CLASS='statusNumber unhealthy'>"+changeNumberOfGUnhealthy+"</div><div CLASS='statusNumber available'>"+changeNumberOfGAvailable+"</div></div>");
	}
	
////////Status 숫자 및 vsimulator Status 숫자를 갱신하는 함수  ////////////
	function changeStatusNumber(){
		simulatorOnHostList = getRunningHostList("vsimulator");
		simulatorOnHostListSize = simulatorOnHostList.length-1;
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		j=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[j];
			if(simulatorHostList[i]==stringResult){
				changeNumberOfCloudRunnig+=parseInt(getCurrentRunningVmList(stringResult));
				changeNumberOfCloudIdle+=parseInt(getCurrentIdleVmList(stringResult));
				changeNumberOfCloudUnhealthy+=parseInt(getCurrentUnhealthyVmList(stringResult));
				changeNumberOfCloudAvailable+=parseInt(getCurrentAvailableVmList(stringResult));
				$("#host_cover").children().eq(i).children().eq(1).replaceWith("<div CLASS='hostStatus' id='vsimulatorHostStatus'><span CLASS='running'>"+getCurrentRunningVmList(stringResult)+"</span><span CLASS='idle'>"+getCurrentIdleVmList(stringResult)+"</span><span CLASS='unhealthy'>"+getCurrentUnhealthyVmList(stringResult)+"</span><span CLASS='available'>"+getCurrentAvailableVmList(stringResult)+"</span></div></div>");
				if(j<simulatorOnHostListSize-1){
					j++;
				}
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
////////각 Cloud별 Host의 power 이미지를 생성하는 함수  ////////////
	function addGif(){
		for (i=0; i<4; i++){
			var hostLength = $("#secondContent").children().eq(i).children().eq(2).children(".host").length;
			if(i==0){
				j=0;
				simulatorOnHostList = getRunningHostList("vsimulator");
				var stringResult = simulatorOnHostList[j];
				
				for (k=0; k<simulatorHostListSize; k++){
					if(simulatorHostList[k]==stringResult){
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
						if(j<simulatorOnHostListSize-1){
							j++;
						}
					}else{
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
					}
				}
			}
			if(i==1){
				j=0;
				var stringResult = FermiOnHostList[j];
				
				for (k=0; k<FermiOnHostListSize; k++){
					if(FermiOnHostListSize[k]==stringResult){
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
						if(j<simulatorOnHostListSize-1){
							j++;
						}
					}else{
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
					}
				}
			}
			if(i==2){
				$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(0).children().eq(0));
			}
			if(i==3){
				j=0;
				var stringResult = GCloudOnHostList[j];
				
				for (k=0; k<GCloudOnHostListSize; k++){
					if(GCloudOnHostListSize[k]==stringResult){
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
						if(j<simulatorOnHostListSize-1){
							j++;
						}
					}else{
						$("<div CLASS='hostSign'><img src='images/host_sign_on_000.png'></div>").appendTo($("#secondContent").children().eq(i).children().eq(2).children().eq(k).children().eq(0));
					}
				}
			}
		}
	}
////////각 Cloud별 Host의 power 이미지를 깜박거리게 하는 함수  ////////////
	function signing(){
		int4 = setInterval( function() {
			$(".hostSign").fadeIn(150).fadeOut(150);
		//show().delay(500).hide();
		},
		300
		);
	}
	
///////////////////////////////////////////////////////////////////////////////////
////////////////////////////서브 2페이지 vsimulator 화면 설정 //////////////////////////
///////////////////////////////////////////////////////////////////////////////////	
	
////////vsimulator의 queue running jobs 수를 업데이트 하는 함수  ////////////
	function subAppendQueueRunning() {
		$("#subQueueRunning").replaceWith("<div CLASS='VclusterStatusNumber running' id='subQueueRunning'>"+window.NAPIVcluster.getRunningJobs()+"</div>");
    }
////////vsimulator의 queue wating jobs수를 업데이트 하는 함수  ////////////	
	function subAppendQueueWaiting() {
		$("#subQueueIdle").replaceWith("<div CLASS='VclusterStatusNumber idle' id='subQueueIdle'>"+window.NAPIVcluster.getWatingJobs()+"</div>");
    }
////////vsimulator의 상단의 staus number를 업데이트 하는 함수  ////////////
	function changeClusterStatusNumber(){
		simulatorOnHostList = getRunningHostList("vsimulator");
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
		$("#vsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='vsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////vsimulator의 host를 디스플레이 하는 함수  ////////////
	function getvsimulatorDisplay(){
		
		for (j=0; j<simulatorHostListSize; j++){
			stringchangeResult = simulatorHostList[j];
			if(j==0){
				
				var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><div CLASS='cpuSign'></div><p CLASS='subHostToptitle'>");
				var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
				var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='masterCover'><div CLASS='master'><p ID='masterP'>Master</p></div></div><div CLASS='cpuCover'></div>");

				
				
				$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");

			}else{
				//alert("test");
				var firstSpan = new String("<div CLASS='hostSub'><div CLASS='subHostTop'><p CLASS='subHostToptitle'>");
				var secondSpan = new String("</p><br><p CLASS='subHostTopsubTitle'>");
				var thirdSpan = new String(" Status</p></div><div CLASS='subStatus'></div><div CLASS='cpuCover'></div>");
				$(firstSpan+stringchangeResult+secondSpan+stringchangeResult+thirdSpan).appendTo("#simulatorSubCover");
			}
			$("#simulatorSubCover").css("width",simulatorHostListSize*175-14+"px");
			if(j+1==simulatorHostListSize){
				$("#simulatorSubCover").children().eq(j).css("margin-right" , "0px");
			}
			
		}
		
	}

	
	
	////////////////vsimulator의 host별 status 숫자를 업데이트 하는 함수 //////////////////////////
	function hostStatusNumber(){
		
		simulatorOnHostList = getRunningHostList("vsimulator");
		simulatorOnHostListSize = simulatorOnHostList.length-1;
		
		changeNumberOfCloudRunnig = 0;
		changeNumberOfCloudIdle = 0;
		changeNumberOfCloudUnhealthy = 0;
		changeNumberOfCloudAvailable = 0;
		j=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[j];
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
		$("#vsimulatorclusterStatus").replaceWith("<div CLASS='VclusterStatus' id='vsimulatorclusterStatus'><div CLASS='VclusterStatusNumber running'>"+changeNumberOfCloudRunnig+"</div><div CLASS='VclusterStatusNumber idle'>"+changeNumberOfCloudIdle+"</div><div CLASS='VclusterStatusNumber unhealthy'>"+changeNumberOfCloudUnhealthy+"</div><div CLASS='VclusterStatusNumber available'>"+changeNumberOfCloudAvailable+"</div></div>");
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
	}
////////////////vsimulator의 host별 vm을 드래그 가능하게 하는 함수 //////////////////////////
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
        		  var srcHostIdx = (dragParentIndex+1);
        		  var srcVmNameIdx = (dragIndex+1);        		  
        		  var desHostIdx = (dropIndex+1);
        		  
        		  List=window.NAPIVcluster.migrationVM(srcHostIdx, srcVmNameIdx, desHostIdx);

        	  }
          });
    } 
	
	///////////////vsimulator의 host별 vm의 상태를 설정 하는 함수 //////////////////
	function hostVmStatus(){
		
		var firstSpan0 = new String("<div CLASS='cpu_running'><p class='cpuP'>vm-");
		var firstSpan1 = new String("<div CLASS='cpu_idle'><p class='cpuP'>vm-");
		var firstSpan2 = new String("<div CLASS='cpu_unhealthy'><p class='cpuP'>vm-");
		var firstSpan3 = new String("<div CLASS='cpu_available'><p class='cpuP'>vm-");
		var firstSpan4 = new String("<div CLASS='cpu_off'><p class='cpuP'>vm-");
		var secondSpan = new String("</p></div>");
		
		simulatorOnHostList = getRunningHostList("vsimulator");
		simulatorOnHostListSize = simulatorOnHostList.length-1;
		
		k=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[k];
			if(simulatorHostList[i]==stringResult){
				resultList=getHostStatus(stringResult);
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
	
///////////////vsimulator의 host별 vm의 상태를 업데이트 하는 함수 //////////////////
	function changeVmStatus(){
		$(".cpuSign").remove();
		simulatorOnHostList = getRunningHostList("vsimulator");
		simulatorOnHostListSize = simulatorOnHostList.length-1;
		
		k=0;
		for (i=0; i<simulatorHostListSize; i++){
			var stringResult = simulatorOnHostList[k];
			if(simulatorHostList[i]==stringResult){
				resultList=getHostStatus(stringResult);
				var lastSpan = new String("");
				for (j=0; j<resultList.length-1; j++){

					if(i==0){
						if(resultList[j]==0){
							$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_running").draggable( 'enable' );
						}else if(resultList[j]==1){
							$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_idle");
						}else if(resultList[j]==2){
							$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_unhealthy");
						}else if(resultList[j]==3){
							$("#simulatorSubCover").children().eq(i).children().eq(3).children().eq(j).removeClass().addClass("cpu_available");
						}
					}else{
						if(resultList[j]==0){
							$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_running").draggable( 'enable' );
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
				$("#simulatorSubCover").children().eq(i).children().eq(0).children().eq(1).before("<div CLASS='cpuSign'><img src='images/btn_power_fliker_00.png'></div>");
			}else{
				var lastSpan = new String("");
				for (j=0; j<12; j++){
					$("#simulatorSubCover").children().eq(i).children().eq(2).children().eq(j).removeClass().addClass("cpu_off").draggable( 'disable' );
				}
			}				
		}
		simulatorOnHostList = null;
		simulatorOnHostListSize = null;
		vmDragStart();
	}
///////////////2페이지의 파워 이미지를 깜박거리게 함수 //////////////////
	function flikering(){
		int3 = setInterval( function() {
			$(".cpuSign").fadeIn(250).fadeOut(250);
		},
		500
		);
	}
/////////////////////////////////////////////////////////////////////////////////////

	
/////////////////////////////////////////////////////////////////////////////////////
/////////////////화면별 함수 및 java 호출 함수 ////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
	
	//////////java내의 simulator의 호스트 리스트를 호출 하는 함수/////////////
	function getHostStatus($item){
		var List = new String();
		List=window.NAPIVcluster.getHostStatus($item);
		resultList = List.split(",");
		return resultList;
		
	}
	//////////java내의 simulator의 러닝호스트 리스트를 호출 하는 함수/////////////
	function getRunningHostList($item){
		var List = new Array();
		List=window.NAPIVcluster.getRunningHostList($item);
		resultList = List.split(",");
		return resultList;
		
	}
	//////////java내의 simulator의 running vm 리스트를 호출 하는 함수/////////////
	function getCurrentRunningVmList($item){
		var List = new Array();
		List=window.NAPIVcluster.getCurrentBusyVmList($item);
		resultList = List.split(",");
		return resultList.length-1;
		
	}
//////////java내의 simulator의 idle vm 리스트를 호출 하는 함수/////////////
	function getCurrentIdleVmList($item){
		var List = new Array();
		List=window.NAPIVcluster.getCurrentIdleVmList($item);
		resultList = List.split(",");
		return resultList.length-1;
		
	}
//////////java내의 simulator의 available vm 리스트를 호출 하는 함수/////////////
	function getCurrentAvailableVmList($item){
		var List = new Array();
		List=window.NAPIVcluster.getCurrentAvailableVmList($item);
		resultList = List.split(",");
		return resultList.length-1;
		
	}
//////////java내의 simulator의 unhealthy vm 리스트를 호출 하는 함수/////////////
	function getCurrentUnhealthyVmList($item){
		var List = new Array();
		List=window.NAPIVcluster.getCurrentUnhealthyVmList($item);
		resultList = List.split(",");
		return resultList.length-1;
		
	}
	
	
	
//////////1페이지내에서 3초마다 화면을 업데이트 하는 함수/////////////
	function start_reload_first(){
		int1 = setInterval( function() {
			changeStatusNumber(); 
			appendQueueRunning(); 
			appendQueueWaiting();
		}, 	
		3000
		);

	};
//////////2페이지내에서 3초마다 화면을 업데이트 하는 함수/////////////
	function start_reload_second(){
		int2 = setInterval( function() {
			changeVmStatus();
			hostStatusNumber();
			subAppendQueueRunning();
			subAppendQueueWaiting(); 
		}, 	
		3000
		);
	}
	
////////////////////////////////////////////////////
//////////화면이 로드 된 후 실행 되는 함수/////////////
////////////////////////////////////////////////////
$(document).ready(function() { 
	
	
	$('#logo2').animate({opacity: 1}, 200);//인트로의 로고를 나타나게 함 
////////////서브 1페이지 기본 세팅 함수들 실행  ////////////////////
	appendQueueRunning();
	appendQueueWaiting();
	appendImageRepository();
	statusNumber();

////////////서브 2페이지 기본 세팅 함수들 실행  ////////////////////
	
	subAppendQueueRunning();
	subAppendQueueWaiting();
	getvsimulatorDisplay();
	hostStatusNumber();
	hostVmStatus();
	
	
	getFermiCloudDisplay();
	getGCloudDisplay();
	getAmazonDisplay();
	
	
	$("[data-role=page]").live("pagebeforeshow",function(event) { 
		if(this.id == "intro") { 


	    }else if(this.id == "status") { 

	    	$('#menuOut').hide();
			$('#menuBack').hide();
			

	    }else if(this.id == "hostStatus") { 

	    	$('#menuSubOut').hide();
			$('#menuSubBack').hide();
			if(pageNumber=="1"){
				changeVmStatus();
	    		hostStatusNumber();
	    		$("#statusTitle").replaceWith("<div CLASS='statusTitle' ID='statusTitle'>vsimulator Cluster Status</div>");
	    	}else if(pageNumber=="2"){
	    		hostFermiStatusNumber();
	    		FermihostVmStatus();
	    		//alert("2");
	    		$("#statusTitle").replaceWith("<div CLASS='statusTitle' ID='statusTitle'>FermiCloud Cluster Status</div>");
	    	}else if(pageNumber=="3"){
	    		hostAmazonStatusNumber();
	    		AmazonhostVmStatus();
	    		$("#statusTitle").replaceWith("<div CLASS='statusTitle' ID='statusTitle'>Amazon Cloud Cluster Status</div>");
	    	}else if(pageNumber=="4"){
	    		//changeGCloudStatusNumber();
	    		hostGStatusNumber();
	    		GhostVmStatus();
	    		$("#statusTitle").replaceWith("<div CLASS='statusTitle' ID='statusTitle'>GCloud Cluster Status</div>");
	    	} 
    	} 

	});
	
	
	$("[data-role=page]").live("pageshow",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 1}, 200);
			

	    }else if(this.id == "status") { 

		    	addGif();
		    	start_reload_first();
		    	
		    	
				changeStatusNumber();
				signing();
	    }else if(this.id == "hostStatus") { 

	    	if(pageNumber=="1"){
	    		
	    		start_reload_second();
	    		$("#masterP").before("<img src='images/master_running.gif' ID='master_running'>"); 
	    	}else if(pageNumber=="2"){
	    	}else if(pageNumber=="3"){
	    	}else if(pageNumber=="4"){
	    	}
	    	flikering();
    	} 
	}); 
	$("[data-role=page]").live("pagebeforehide  ",function(event) { 
		if(this.id == "intro") { 

		}else if(this.id == "status") { 
	    	
			$(".hostSign").remove();
	    	$(".hostSignOff").remove();
			window.clearInterval(int1);
			window.clearInterval(int4);
	    }else if(this.id == "hostStatus") { 
	    	if(pageNumber=="1"){
	    		$("#master_running").remove();
				window.clearInterval(int2);
	    	}else if(pageNumber=="2"){
	    		
	    	}else if(pageNumber=="3"){
	    		
	    	}else if(pageNumber=="4"){
	    		
	    	}
	    	window.clearInterval(int3);
			$(".cpuSign").remove();
			pageNumber=0;
	    } 
	}); 
	
	$("[data-role=page]").live("pagehide  ",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 0}, 0);

		}else if(this.id == "status") { 

	    }else if(this.id == "hostStatus") { 
			$('#amazonSub').hide();
			$('#vsimulatorSub').hide();
			$('#fermiSub').hide();
			$('#gcloudSub').hide();
	    } 
	}); 
}); 




