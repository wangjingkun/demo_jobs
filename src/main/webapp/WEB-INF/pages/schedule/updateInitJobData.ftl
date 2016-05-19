<form  id="dataForm">

    <table class="p_table form-inline">
    <tbody>

        <tr>
            <input type="hidden" name="jobId" value="${scheduleJobData.jobId}">
            <input type="hidden" name="jobDataId" value="${scheduleJobData.jobDataId}">
            <td class="p_label">参数名称：<span class="notnull">*</span></td>
            <td><input autocomplete="off" type="text" name="dataName"  required=true value="${scheduleJobData.dataName}"></td>
        </tr>

        <tr>
            <td class="p_label">参数值：<span class="notnull">*</span></td>
            <td>
             <input autocomplete="off" type="text" name="dataValue"  required=true value="${scheduleJobData.dataValue}">
             </td>
        </tr>

    </tbody>
    </table>

</form>
