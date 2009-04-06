<ul class="filetree">
	<#if nextFunctions??>
	<#list nextFunctions as fun>
	<#if fun.show>
	<#assign size=fun.children.size()>
	<li><span <#if (size > 0) >class="folder">${fun.functionTitle}
	<#else>class="file">
	<a href="javascript:loadRight('${base+fun.url}','${fun.functionTitle}');" url="${fun.url}" class="childMenu" >
	${fun.functionTitle}
	</a>
	</#if></span>
		<ul>
		<#list fun.children as ch>
			<#if ch.show>
			<li ><span class="file"><a href="javascript:loadRight('${base+ch.url}','${ch.functionTitle}');" url="${ch.url}" class="childMenu"
 >${ch.functionTitle}</a></span></li>
            </#if>
		 </#list>
		</ul>
	</li>
	</#if>
	</#list>
	</#if>

</ul>