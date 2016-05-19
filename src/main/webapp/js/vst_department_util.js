


$("#firstDepartment").change(function(){
       var param=$("#firstDepartment option:selected").val();
       $.ajax({
		   url : "/vst_order/order/ordAuditConfig/findCascadDepartment.do",
		   data : param,
		   type:"POST",
		   dataType:"JSON",
		   success : function(data){
		   
		   		$("#secondDepartment").html("");
		   		  $.each(data[0],function(key,value){

		               //其中key相当于是JAVA中MAP中的KEY，VALUE就是KEY相对应的值
						$("#secondDepartment").append("<option value="+key+">"+value+"</option>");
		               //alert(key+"    "+value);
		        })
		   		
		   }
		});	
});

$("#secondDepartment").change(function(){
       var param=$("#secondDepartment option:selected").val();
       $.ajax({
		   url : "/vst_order/order/ordAuditConfig/findCascadDepartment.do",
		   data : param,
		   type:"POST",
		   dataType:"JSON",
		   success : function(data){
		   
		   		$("#threeDepartment").html("");
		   		  $.each(data[0],function(key,value){

		               //其中key相当于是JAVA中MAP中的KEY，VALUE就是KEY相对应的值
						$("#threeDepartment").append("<option value="+key+">"+value+"</option>");
		               //alert(key+"    "+value);
		        })
		   		
		   }
		});	
});


