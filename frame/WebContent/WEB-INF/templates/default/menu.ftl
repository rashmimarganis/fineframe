Ext.BLANK_IMAGE_URL = '${base}/js/resources/images/default/s.gif';	
    Ext.onReady(function(){
        Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
        
        var menuPanel=new Ext.Panel({
			region:'west',
            id:'west-panel',
            title:'导航菜单',
            split:true,
            width: 200,
            minSize: 175,
            maxSize: 400,
            collapsible: true,
            margins:'0 0 0 5',
            layout:'accordion',
            collapseMode:'mini',
            
            layoutConfig:{
				activeOnTop: false,
                animate:true
            }
		});
        <#if topFunctions??>
    		<#list topFunctions as fun>
    			var menu_${fun.functionId} = new Ext.Panel({
	                id:"menu_${fun.functionId}",
	                title:"${fun.functionTitle}",
	                iconCls:'nav',
					border:'0px'
        		});
        		menuPanel.add(menu_${fun.functionId});
            </#list>
	 	</#if>  
		
		
       var viewport = new Ext.Viewport({
            layout:'border',
            items:[
                new Ext.BoxComponent({ // raw
                    region:'north',
                    el: 'north',
                    height:32
                }),new Ext.BoxComponent({
                    region:'south',
                    el: 'south',
                    height: 30
                }),menuPanel,
                new Ext.TabPanel({
                    region:'center',
                    deferredRender:false,
                    activeTab:0,
                    items:[{
                        contentEl:'center1',
                        title: 'Close Me',
                        closable:true,
                        autoScroll:true
                    },{
                        contentEl:'center2',
                        title: 'Center Panel',
                        autoScroll:true
                    }]
                })
             ]
        });
      
       viewport.doLayout();
    });