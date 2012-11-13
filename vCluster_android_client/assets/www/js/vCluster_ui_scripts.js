

$(function() {
	
	
	
	
	$('#demoButton').click(function(){
			navigator.notification.vibrate(300);
			window.NAPIVcluster.alertJava();
		setTimeout(function() {
			window.NAPIVcluster.demoStart2();}
		,1000
		);
	});
	
	$('#resetButton').click(function(){
			navigator.notification.vibrate(300);
			window.NAPIVcluster.alertJava();
		setTimeout(function() {
			window.NAPIVcluster.demoStart1();}
		,1000
		);
	});
	
	

	$('#demoStopButton').click(function(){
		$('#menuBack').fadeOut("fast");
		$('#menuOut').fadeOut("fast");
		window.NAPIVcluster.demoStop();
	});

	$('#govsimulatorButton').click(function() {
		pageNumber=1;
		$("#vsimulatorSub").show();
		
	});

	$('#goAmazonButton').click(function() {
		pageNumber=3
		$('#amazonSub').show();
		;
	});
	
	$('#goFermiButton').click(function() {
		pageNumber=2;
		$('#fermiSub').show();
		
	});
	
	$('#goGcloudButton').click(function() {
		pageNumber=4;
		$('#gcloudSub').show();
	});
	
	

	
	
	$('#menuButton').click(function() {
		//alert("test");
		navigator.notification.vibrate(300);
		$('#menuBack').fadeIn("fast");
		$("#menuOut").fadeIn("fast");
	});
	
	
	$('#menuBack').click(function() {
		$('#menuBack').fadeOut("fast");
		$('#menuOut').fadeOut("fast");
	});
	
	$('#menuPop p a').click(function() {

	});


	


          /*
          $( ".cpu_available" ).droppable({
            activeClass: ".cpu_available_ready",
            drop: function( event, ui ) {
            	
            	$dragBox=ui.draggable;
            	$dropBox=$(this);
            	
            	$dragParent=$dragBox.parent().children();
            	$dropParent=$dropBox.parent().children();
            	
            	var dragIndex = $dragParent.index($dragBox);
				var dropIndex = $dropParent.index($dropBox);
				
				var dragParentIndex = $dragBox.parent().parent().parent().children().index($dragBox.parent().parent());
				var dropParentIndex = $dropBox.parent().parent().parent().children().index($dropBox.parent().parent());

            	//alert(dragParentIndex);
            	//alert(dropParentindex);
            	//var dragTitle=$dragBox.children().siblings("p").attr("title");
            	//var dropTitle=$dropBox.children().siblings("p").attr("title");
            	
            	if(dragParentIndex!=dropParentIndex){
            		var answer=confirm("change Virtual-Machine?")
            		if (answer){
            			if(dropIndex==0){
            				
        	            	ui.draggable.insertBefore($dropParent.eq(dropIndex+1));
            			}else{
            				
        	            	ui.draggable.insertAfter($dropParent.eq(dropIndex-1));	
            			}
						if(dragIndex==0){
            				
							$( this ).insertBefore($dragParent.eq(dragIndex+1));
            			}else{
            				
            				$( this ).insertAfter($dragParent.eq(dragIndex-1));	
            			}
						
						
						$( this ).find( "p" ).html("vm-"+changeNum(dragIndex+1));
						ui.draggable.find( "p" ).html("vm-"+changeNum(dropIndex+1));

						//changeNum(dragIndex);
						
						
						//"vm-"+changeNum(dragIndex+1)
						
						//var pppp=$( this).children().siblings("p").attr("title");
						//alert(pppp);
					}
            	}
            	
            	
            }
          
         
          
        });
          */
	
	$('#demoSubButton').click(function(){
		//$('#menuSubBack').fadeOut("fast");
		//$('#menuSubOut').fadeOut("fast");
		//start_reload_second();
		navigator.notification.vibrate(300);
		window.NAPIVcluster.alertJava();
		setTimeout(function() {
			window.NAPIVcluster.demoStart2();}
		,1000
		);
		
	});
	
	
	$('#resetSubButton').click(function(){
		//$('#menuSubBack').fadeOut("fast");
		//$('#menuSubOut').fadeOut("fast");
		//start_reload_second();
		navigator.notification.vibrate(300);
		window.NAPIVcluster.alertJava();
		setTimeout(function() {
			window.NAPIVcluster.demoStart1();}
		,1000
		);
		
	});
	
	$('#subDemoStopButton').click(function(){
		$('#menuSubBack').fadeOut("fast");
		$('#menuSubOut').fadeOut("fast");
		window.NAPIVcluster.demoStop();
	});
	
	
	
	$('#menuSubButton').click(function() {
		//alert("test");
		navigator.notification.vibrate(300);
		$('#menuSubBack').fadeIn("fast");
		$('#menuSubOut').fadeIn("fast");
	});
	
	
	$('#menuSubBack').click(function() {
		$('#menuSubBack').fadeOut("fast");
		$('#menuSubOut').fadeOut("fast");
	});
	
	$('#menuPop p a').click(function() {
		$('#menuSubBack').fadeOut("fast");
		$('#menuSubOut').fadeOut("fast");
	});


	
	
	

	$('.cpu').click(function() {
		//$('#popBack').css("display","block");
		//$('#infoPop').css("display","block");
		$('#popBack').fadeIn("fast");
		$('#infoPop').slideDown("fast");

	});

	$('.subHost_power').click(function() {
		var hostName = $( this).attr("name");
		var turnOffHost = confirm("turn off this "+hostName+"?");
		if (turnOffHost){
			window.APIVcluster.subHost_power(hostName);
		}
		
	});
	
	
	
	$('.cpu_running').click(function() {
		
		var cpuText=$(this).children(":first").attr("title");
		var cpuTime="00:10:13";
		var cpuInfo="Intel(R) Xeon(R) CPU  E5640  @ 2.67GHz";
		var cpuRam="8Gbyte";
		
		var vmIndex = $(this).parent().children().index($(this));
		
		///alert(pppp);
		$('#popBack').fadeIn("fast");
		$('#infoPop').fadeIn("fast");
		
		$('.title').css("color","#84c219");
		
		$('#subText').replaceWith("<div class='subText' ID='subText'>Running<br>"+cpuTime+"<br>"+cpuInfo+"<br>"+cpuRam+"<br></div>");
		$('#popCpuImage').replaceWith("<img src='images/cpu_running.png' CLASS='pop_running' ID='popCpuImage'>");
		$('#popCpu').replaceWith("<p class='cpuP' ID='popCpu'>vm-"+changeNum(vmIndex+1)+"</p>");

	});
	
	$('.cpu_available').click(function() {
	
		var cpuText=$(this).children(":first").attr("title");
		var cpuTime="00:10:13";
		var cpuInfo="Intel(R) Xeon(R) CPU  E5640  @ 2.67GHz";
		var cpuRam="8Gbyte";
		
		var vmIndex = $(this).parent().children().index($(this));
		
		///alert(pppp);
		$('#popBack').fadeIn("fast");
		$('#infoPop').fadeIn("fast");

		$('.title').css("color","#FFFFFF");
		
		$('#subText').replaceWith("<div class='subText' ID='subText'>Available<br>"+cpuTime+"<br>"+cpuInfo+"<br>"+cpuRam+"<br></div>");
		$('#popCpuImage').replaceWith("<img src='images/cpu_available.png' CLASS='pop_running' ID='popCpuImage'>");
		$('#popCpu').replaceWith("<p class='cpuP' ID='popCpu'>vm-"+changeNum(vmIndex+1)+"</p>");

	});
	
	$('.cpu_unhealthy').click(function() {
		var cpuText=$(this).children(":first").attr("title");
		var cpuTime="00:10:13";
		var cpuInfo="Intel(R) Xeon(R) CPU  E5640  @ 2.67GHz";
		var cpuRam="8Gbyte";
		
		var vmIndex = $(this).parent().children().index($(this));

		///alert(pppp);
		$('#popBack').fadeIn("fast");
		$('#infoPop').fadeIn("fast");
		
		$('.title').css("color","#d31c49");
		
		$('#subText').replaceWith("<div class='subText' ID='subText'>Unhealthy<br>"+cpuTime+"<br>"+cpuInfo+"<br>"+cpuRam+"<br></div>");
		$('#popCpuImage').replaceWith("<img src='images/cpu_unhealthy.png' CLASS='pop_running' ID='popCpuImage'>");
		$('#popCpu').replaceWith("<p class='cpuP' ID='popCpu'>vm-"+changeNum(vmIndex+1)+"</p>");

	});
	
	$('.cpu_idle').click(function() {
		var cpuText=$(this).children(":first").attr("title");
		var cpuTime="00:10:13";
		var cpuInfo="Intel(R) Xeon(R) CPU  E5640  @ 2.67GHz";
		var cpuRam="8Gbyte";
		
		var vmIndex = $(this).parent().children().index($(this));
		
		///alert(pppp);
		$('#popBack').fadeIn("fast");
		$('#infoPop').fadeIn("fast");
		
		$('.title').css("color","#eb8200");
		
		$('#subText').replaceWith("<div class='subText' ID='subText'>Idle<br>"+cpuTime+"<br>"+cpuInfo+"<br>"+cpuRam+"<br></div>");
		$('#popCpuImage').replaceWith("<img src='images/cpu_idle.png' CLASS='pop_running' ID='popCpuImage'>");
		$('#popCpu').replaceWith("<p class='cpuP' ID='popCpu'>vm-"+changeNum(vmIndex+1)+"</p>");

	});
	
	$('#popBack').click(function() {
		$('#popBack').fadeOut("fast");
		$('#infoPop').fadeOut("fast");
	});
	
	$('.popClose').click(function() {
		$('#popBack').fadeOut("fast");
		$('#infoPop').fadeOut("fast");
	});
});