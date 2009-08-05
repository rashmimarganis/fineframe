<table class="table_info">
<caption>服务器信息</caption>
<tr>
<td>服务器启动时间：</td><td>${serverInfo.startDate?string("yyyy-MM-dd HH:mm:ss")}</td>
</tr>
<tr>
<td>Java运行环境版本：</td><td>${serverInfo.javaRuntimeVersion}</td>
</tr>
<tr>
<td>
Java虚拟机版本：</td><td>${serverInfo.javaVmVersion}
</td>
</tr>
<tr>
<td>
JVM Vendor：</td><td>${serverInfo.javaVmVendor}
</td>
</tr>
<tr>
<td>
操作系统名称：</td><td>${serverInfo.osName}
</td>
</tr>
<tr>
<td>
操作系统版本：</td><td>${serverInfo.osVersion}
</td>
</tr>
<tr>
<td>
服务器版本：</td><td>${serverInfo.serverVersion}(发布时间:${serverInfo.serverBuild})
</td>
</tr>

<tr>
<td>
空闲内存：</td><td>${serverInfo.freeMemory}
</td>
</tr>
<tr>
<td>
全部内存：</td><td>${serverInfo.totalMemory}
</td>
</tr>
<tr>
<td>
最大内存：</td><td>${serverInfo.maxMemory}
</td>
</tr>
<tr><td colspan="2" class="align_c"><input class="button_style" type="button" value="&nbsp;刷新状态&nbsp;" onclick="javascript:loadPage('${base}/system/status.jhtm');"></td></tr>
</table>
