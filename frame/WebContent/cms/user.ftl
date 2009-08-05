<#list data as u>
	${u_index}.${u.username}_${u.realname?default("")}(${u.email})<br>
</#list>
