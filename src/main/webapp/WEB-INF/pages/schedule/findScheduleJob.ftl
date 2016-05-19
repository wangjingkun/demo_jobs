<!DOCTYPE html>
<html>
<head>

<#include "/base/head_meta.ftl"/>
</head>
<body>
<div class="iframe_header">
 <i class="icon-home ihome"></i>
    <ul class="iframe_nav">
        <li><a href="#">首页</a> &gt;</li>
        <li><a href="#">任务管理</a> &gt;</li>
        <li class="active">任务列表</li>
    </ul>
</div>

<div class="iframe_search">
    <form method="post" action='/vst_demo/schedule/findScheduleJobList.do' id="searchForm">
    <table class="s_table">
        <tbody>
            <tr>
                <td class="s_label">名称：</td>
                <td class="w18"><input type="text" name="jobName" value="${jobName!''}"></td>
                <td class=" operate mt10"><a class="btn btn_cc1" id="search_button">查询</a></td>
                <td class=" operate mt10"><a class="btn btn_cc1" id="new_button">新增</a></td>
                <input type="hidden" name="page" value="${page}">
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
            <th>任务名称</th>
            <th>任务分组</th>
            <th>任务状态</th>
            <th>任务运行时间表达式</th>
            <th>任务描述</th>
            <th> 调度JOB</th>
            <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <#list pageParam.items as job> 
            <tr>
                <td>${job.jobId!''} </td>
                <td>&nbsp;&nbsp;${job.jobName!''} </td>
                <td>&nbsp;&nbsp;${job.jobGroup!''} </td>
                <td>
                    <#if job.status == 'Y'>
                        <span style="color:green" class="cancelProp">有效</span>
                    <#else>
                        <span style="color:red" class="cancelProp">无效</span>
                    </#if> 
                </td>
                <td>${job.cronExpression!''} </td>
                <td>${job.remark!''} </td>
                <td>${job.runClass!''} </td>
                <td class="oper">
                    <a class="editJob" href="javascript:void(0);" data="${job.jobId!''}" >修改记录</a>
                    <a  href="/vst_demo/schedule/findScheduleJobDataList.do?jobId=${job.jobId!''}">参数值维护</a>
                    <a href="javascript:void(0);" class="delJob" data="${job.jobId!''}" data2="${job.status}">删除记录</a>
                    <a href="javascript:void(0);" class="startJob" data="${job.jobId!''}"  data2="${job.status}"> ${(job.status=='N')?string("启动JOB", "停止JOB")}</a>
                </td>
            </tr>
            </#list>

        </tbody>
    </table>

    <#if pageParam.items?exists>
        <div class="paging" >
           ${pageParam.getPagination()}
        </div> 
    </#if>

</div><!-- div p_box -->

</div><!-- //主要内容显示区域 -->
<#include "/base/foot.ftl"/>
</body>
</html>

<script>
var categoryPropListDialog,categoryPropGroupsDialog,branchListDialog;
$(function(){

$("searchForm input[name='jobName']").focus();
    $("#search_button").bind("click",function(){
        $("#searchForm").submit();
});

//job启动与停止
$("a.startJob").bind("click",function(){
     var url ='';
     var jobId=$(this).attr("data");
     var status=$(this).attr("data2");
     if(status === "N" ){
          url = "/vst_demo/schedule/startJob.do?jobId="+jobId;
     }else{
          url = "/vst_demo/schedule/stopJob.do?jobId="+jobId;
     }

     msg = status === "Y" ? "确认停止JOB？" : "确认启动JOB？";
     $.confirm(msg, function () {
         $.get(url, function(result){
             confirmAndRefresh(result);
         });
     });
     return false;
});

//新增JOB
$("#new_button").bind("click",function(){
   dialog("/vst_demo/schedule/addJobInit.do", "新增JOB", 800,"auto",function(){
        if(!$("#dataForm").validate().form()){
            return false;
        }
        var resultCode; 
        $.ajax({
                url : "/vst_demo/schedule/addScheduleJob.do",
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
     var jobId=$(this).attr("data");
     var status=$(this).attr("data2") == "N" ? "Y": "N";
     var url = "/vst_demo/schedule/deleteScheduleJob.do?jobId="+jobId+"&status="+status;
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
        var jobId=$(this).attr("data");
           dialog("/vst_demo/schedule/updateInitJob.do?jobId="+jobId, "修改JOB", 800,"auto",function(){
        if(!$("#dataForm").validate().form()){
            return false;
        }
        var resultCode; 
        $.ajax({
                url : "/vst_demo/schedule/updateScheduleJob.do",
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

