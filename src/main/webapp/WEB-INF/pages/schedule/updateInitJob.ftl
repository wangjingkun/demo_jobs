<form  id="dataForm">

    <table class="p_table form-inline">
    <tbody>

        <tr>
            <input type="hidden" name="jobId" value="${scheduleJob.jobId!''}">
            <td class="p_label">任务名称：<span class="notnull">*</span></td>
            <td><input autocomplete="off" type="text" name="jobName" required=true value="${scheduleJob.jobName!''}"></td>
        </tr>

        <tr>
            <td class="p_label">任务组：</td>
            <td>
             <input autocomplete="off" type="text" name="jobGroup" required=true value="${scheduleJob.jobGroup!''}">
             </td>
        </tr>

        <tr>
            <td class="p_label">任务状态：</td>
            <td>
                <#if scheduleJob.status == 'Y'>
                    <select name="status"> 
                        <option value="Y" selected="selected">启用</option>
                        <option value="N">无效</option>
                    </select>
                <#else>
                    <select name="status"> 
                        <option value="Y">启用</option>
                        <option value="N" selected="selected">无效</option>
                    </select>
                </#if> 
            </td>
        </tr>

        <tr>
            <td class="p_label">任务运行时间表达式：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="off" type="text" name="cronExpression" required=true value="${scheduleJob.cronExpression!''}">
            </td>
        </tr>

         <tr>
            <td class="p_label"> 任务描述：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="off" type="text" name="remark" required=true value="${scheduleJob.remark!''}">
            </td>
        </tr>

        <tr>
            <td class="p_label">调度JOB：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="on" type="text" name="runClass" required=true value="${scheduleJob.runClass!''}">
            </td>
        </tr>

    </tbody>
    </table>
</form>
