<!DOCTYPE html>
<html>
<head>

<#include "/base/head_meta.ftl"/>
</head>
<body>
<div class="iframe_header">
 <i class="icon-home ihome"></i>
    <ul class="iframe_nav">
        <li><a href="/vst_demo/schedule/findScheduleJobList.do">首页</a> &gt;</li>
        <li><a href="#">JOB_DATA管理</a> &gt;</li>
        <li class="active">JOB_DATA列表</li>
    </ul>
</div>

<div class="iframe_search">
    <form method="post" action='/vst_demo/schedule/findScheduleJobDataList.do?jobId=${scheduleJob.jobId}' id="searchForm">
    <input type="hidden" name="jobId" value="${scheduleJob.jobId}">
    <table class="s_table">
        <tbody>
            <tr>
                <td class=" operate mt10"><a class="btn btn_cc1" id="new_button">新增</a></td>
            </tr>
        </tbody>
    </table>
    </form>
</div>

<!-- 主要内容显示区域\\ -->
<div class="iframe_content">
    <div class="p_box">
    <table class="p_table table_center">
        <thead>
            <tr>
            <th>编号</th>
            <th>主任务表ID</th>
            <th>参数名称</th>
            <th>参数值</th>    
            <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <#list scheduleJobDataList as dataJob>
            <tr>
            <td>${dataJob.jobDataId!''} </td>
            <td>&nbsp;&nbsp;${dataJob.jobId!''} </td>
            <td>&nbsp;&nbsp;${dataJob.dataName!''} </td>
            <td>${dataJob.dataValue!''} </td>
            <td class="oper">
                <a class="editJob" href="javascript:void(0);" data="${dataJob.jobDataId!''}" >修改记录</a>
                <a href="javascript:void(0);"  class="delJob" data="${dataJob.jobDataId!''}">删除记录</a>
             </td>
            </tr>
            </#list>
        </tbody>
    </table>

    <input type="hidden" id="job_id" value="${scheduleJob.jobId}">

</div><!-- div p_box -->
    
</div><!-- //主要内容显示区域 -->
<#include "/base/foot.ftl"/>
</body>
</html>

<script>

$(function(){
//新增JOB
$("#new_button").bind("click",function(){
    var jobId=$("#job_id").val();
   dialog("/vst_demo/schedule/addJobDataInit.do?jobId="+jobId, "新增JOB", 800,"auto",function(){
        if(!$("#dataForm").validate().form()){
            return false;
        }
        var resultCode;
        $.ajax({
                url : "/vst_demo/schedule/addScheduleJobData.do",
                type : "post",
                async: false,
                data : $(".dialog #dataForm").serialize(),
                dataType:'JSON',
                success : function(result) {
                    resultCode=result.code;
                    confirmAndRefresh(result);
                }
            });
        },"保存");
});

//删除
$("a.delJob").bind("click",function(){
     var jobDataId=$(this).attr("data");
     var status=$(this).attr("data2") == "N" ? "Y": "N";
     var url = "/vst_demo/schedule/deleteScheduleJobData.do?jobDataId="+jobDataId;
     msg = status === "N" ? "确认删除  ？" : "确认取消  ？";
     $.confirm(msg, function () {
         $.get(url, function(result){
             confirmAndRefresh(result);
         });
     });
     return false;
});

//修改记录
$("a.editJob").bind("click",function(){
        var jobDataId=$(this).attr("data");
        dialog("/vst_demo/schedule/updateInitJobData.do?jobDataId="+jobDataId, "修改JOB", 800,"auto",function(){
        if(!$("#dataForm").validate().form()){
            return false;
        }
        var resultCode; 
        $.ajax({
                url : "/vst_demo/schedule/updateScheduleJobData.do",
                type : "post",
                async: false,
                data : $(".dialog #dataForm").serialize(),
                dataType:'JSON',
                success : function(result) {
                    resultCode=result.code;
                    confirmAndRefresh(result);
                }
            });
        },"修改");
});

function confirmAndRefresh(result){
    if (result.code == "success") {
        pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
            $("#searchForm").submit();
        }});
    }else {
        pandora.dialog({wrapClass: "dialog-mini", content:result.message, okValue:"确定",ok:function(){
            //$.alert(result.message);
        }});
    }
}
});

</script>