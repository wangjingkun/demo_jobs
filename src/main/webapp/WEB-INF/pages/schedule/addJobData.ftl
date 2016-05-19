<form  id="dataForm">

    <table class="p_table form-inline">
    <tbody>

        <tr>
            <input type="hidden" name="jobId" value="${scheduleJob.jobId}">
            <td class="p_label">参数名称：<span class="notnull">*</span></td>
            <td><input autocomplete="off" type="text" name="dataName"  required=true value=""></td>
        </tr>

        <tr>
            <td class="p_label">参数值：<span class="notnull">*</span></td>
            <td>
             <input autocomplete="off" type="text" name="dataValue"  required=true value="">
            </td>
        </tr>

    </tbody>
    </table>
</form>
