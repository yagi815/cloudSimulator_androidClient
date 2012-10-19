$(document).ready(function() { 

	//var int1;
	
	$('#logo2').animate({opacity: 1}, 200);

	theNumberOfCloud=""
	
	CloudList = new Array();
	HostList = new Array();
	
	
	
	
	
	function appendQueueRunning() {
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

		for (i=0; i<CloudList.size(); i++){
			//$("<div CLASS="+CloudList.get(i)+"></div>").appendTo('#secondContent');
			//$("<div CLASS='vsimulator'>").appendTo('#secondContent');
			
			
			var ValueText = new String(CloudList.get(i));
			var firstSpan = new String("<div CLASS='vsimulator'><div CLASS='statusTop'><p CLASS='title'>");
			var secondSpan = new String("</p><a href='#hostStatus' data-transition='slide'><img src='images/btn_config.png' CLASS='statusConfig' CLASS='normalButton'></a><br><p CLASS='subTitle'>");
			var lastSpan = new String(" Status</p></div></div>");
			Result = firstSpan + ValueText + secondSpan + ValueText + lastSpan;
			//alert(ValueText);
			$(Result).appendTo('#secondContent');

		}

		
	}
	
	//<img src="kate.png" style="-webkit-mask-image: url(circle.svg)">
	
	function statusNumber(){
		var Result = new String();
		theNumberOfCloudRunnigArray = new Array();
		theNumberOfCloudIdleArray = new Array();
		theNumberOfCloudUnhealthyArray = new Array();
		theNumberOfCloudAvailableArray = new Array();
		//var firstSpan = new String("<div CLASS='status'></div><div CLASS='host_cover' style='-webkit-mask-image: url(logo.png)'></div>");
		//var secondSpan = new String("<div CLASS='statusNumber unhealthy'>678</div><div CLASS='statusNumber available'>90</div></div><div CLASS='host_cover'></div>");
		//alert(ValueText);
		//Result = firstSpan + secondSpan;
		$("<div CLASS='status'></div><div CLASS='host_cover' ></div>").insertAfter('.statusTop');
		//$("<p> is what I said... </p>").insertAfter(".statusTop");
		//getRunningHostList(CloudList.get(i));
		for (i=0; i<CloudList.size(); i++){
			
			
			HostList[i] = getRunningHostList(CloudList.get(i));
			//alert(HostList[i].size());
		}
		//alert(HostList[0].get(1));
		
		var firstSpan = new String("<div CLASS='host'><div CLASS='hostTop'><div CLASS='hostTitle'><p>");
		
		//alert(ValueText);
		var Result = new String(HostList[0].get(1));
		for (i=0; i<CloudList.size(); i++){
			theNumberOfCloudRunnig = 0;
			theNumberOfCloudIdle = 0;
			theNumberOfCloudUnhealthy = 0;
			theNumberOfCloudAvailable = 0;
			for (j=0; j<HostList[i].size(); j++){
				
				var Result = new String(HostList[i].get(j));
				var stringResult = Result.toString();
				//getCurrentRunningVmCount(Result);
				List=window.NAPIVcluster.getCurrentRunningVmCount(stringResult);
				
				theNumberOfCloudRunnig+=parseInt(List.get(0))
				theNumberOfCloudRunnigArray[i]=theNumberOfCloudRunnig;
				
				theNumberOfCloudIdle+=parseInt(List.get(1))
				theNumberOfCloudIdleArray[i]=theNumberOfCloudIdle;s
				
				theNumberOfCloudUnhealthy+=parseInt(List.get(2))
				theNumberOfCloudUnhealthyArray[i]=theNumberOfCloudUnhealthy;
				
				theNumberOfCloudAvailable+=parseInt(List.get(3))
				theNumberOfCloudAvailableArray[i]=theNumberOfCloudAvailable;
				
				var secondSpan = new String("</p></div></div><div CLASS='hostStatus'><span CLASS='running'>"+List.get(0).toString()+"</span><span CLASS='idle'>"+List.get(1).toString()+"</span><span CLASS='unhealthy'>"+List.get(2).toString()+"</span><span CLASS='available'>"+List.get(3).toString()+"</span></div></div>");
				//alert(theNumberOfCloudRunnigArray);
				$(firstSpan+Result+secondSpan).appendTo($("#secondContent").children().eq(i).children().eq(2));
			}

			$("<div CLASS='statusNumber running'>"+theNumberOfCloudRunnigArray[i]+"</div><div CLASS='statusNumber idle'>"+theNumberOfCloudIdleArray[i]+"</div><div CLASS='statusNumber unhealthy'>"+theNumberOfCloudUnhealthyArray[i]+"</div><div CLASS='statusNumber available'>"+theNumberOfCloudAvailableArray[i]+"</div>").appendTo(($("#secondContent").children().eq(i).children().eq(1)));
		}
		
		//alert(theNumberOfCloudRunnigArray);
	}
	
	function getCurrentRunningVmCount(Result){
		//var Result = new String($item);
		
		var List = new Array();
		List=window.NAPIVcluster.getCurrentRunningVmCount(Result);
		return List;
		alert("test");
	}
	
	
	function getRunningHostList($item){
		var List = new Array();
		List=window.NAPIVcluster.getRunningHostList($item);
		return List;
	}
	
	function start_reload(){
		int1 = setInterval( function() {
			appendQueueRunning();
			appendQueueWaiting();
			appendImageRepository();
			getAccessCloudList();
			statusNumber();
		}, 	
		3000
	);

	};
	
	
	$("[data-role=page]").live("pagebeforeshow",function(event) { 
		if(this.id == "intro") { 


	    }else if(this.id == "status") { 
	    	
	    	

	    }else if(this.id == "hostStatus") { 

    	} 

	});
	
	
	$("[data-role=page]").live("pageshow",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 1}, 200);
			//$("#logo1").animate({width:305px,opacity:1,margin:262px 0px 0px 474px;},1000);
			

	    }else if(this.id == "status") { 
	    	
	    	start_reload();
	    	
			$(".hostTop").after("<div CLASS='hostSign'><img src='images/host_sign_on.gif'></div>");

	    }else if(this.id == "hostStatus") { 
	    	
	    	//alert("세번째 페이지 입니다."); 
    		$(".subHostToptitle").before("<div CLASS='cpuSign'><img src='images/fliker_cpu.gif'></div>"); 
    		$("#masterP").before("<img src='images/master_running.gif' ID='master_running'>"); 
    		//int1=setInterval(function(){lightning_one();}, 300);
    		//lightning_one();
    	} 

	}); 
	
  
	$("[data-role=page]").live("pagebeforehide  ",function(event) { 
		if(this.id == "intro") { 
			$('#logo2').animate({opacity: 0}, 0);


	    }else if(this.id == "status") { 
    		//alert("두번째 페이지 입니다."); 
    		$.mobile.loading( 'show', {
				text: 'foo',
				textVisible: true,
				theme: 'z',
				html: ""
			});
			$(".hostSign").remove();
			$(".vsimulator").remove();
			
	    }else if(this.id == "hostStatus") { 
    		//alert("세번째 페이지 입니다."); 
    		$.mobile.loading( 'show', {
				text: 'foo',
				textVisible: true,
				theme: 'z',
				html: ""
			});
			$(".cpuSign").remove();
			$("#master_running").remove();
			//window.clearInterval(int1);
	    } 

	}); 
}); 




