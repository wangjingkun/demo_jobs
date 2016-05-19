<form  id="dataForm">

    <table class="p_table form-inline">
    <tbody>
        <tr>
            <td class="p_label">任务名称：<span class="notnull">*</span></td>
            <td><input autocomplete="off" type="text" name="jobName" required=true value=""></td>
        </tr>

        <tr>
            <td class="p_label">任务组：</td>
            <td>
             <input autocomplete="off" type="text" name="jobGroup"  required=true value="">
             </td>
        </tr>

        <tr>
            <td class="p_label">任务状态：</td>
            <td>
                <select name="status"> 
                    <option value="Y">启用</option>
                    <option value="N">无效</option>
                </select>
            </td>
        </tr>

        <tr>
            <td class="p_label">任务运行时间表达式：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="off" type="text" name="cronExpression" required=true value="">
            </td>
        </tr>

         <tr>
            <td class="p_label"> 任务描述：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="off" type="text" name="remark" required=true value="">
            </td>
        </tr>

        <tr>
            <td class="p_label">调度JOB：<span class="notnull">*</span></td>
            <td>
                <input autocomplete="on" type="text" name="runClass" required=true value="">
            </td>
        </tr>

    </tbody>
    </table>
</form>
