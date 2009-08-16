<#compress>
	Ext.BLANK_IMAGE_URL = '${base}/js/resources/images/default/s.gif';
	var mainPanel;
	var menuPanel;
	var viewport;
	var mainPanelHeight;
	var FineCmsMain=function(){
		return {
			init:function(){
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
				FineCmsMain.initMenu();
				FineCmsMain.initMainPanel();
				viewport = new Ext.Viewport({
		            layout:'border',
		            items:[
		                new Ext.BoxComponent({ // raw
		                    region:'north',
		                    el: 'north',
		                    height:60,
		                    border:'0px',
		                    margins:'0 0 0 0'
		                }),new Ext.BoxComponent({
		                    region:'south',
		                    el: 'south',
		                    height: 30
		                }),menuPanel,mainPanel
		             ]
		        });
		       viewport.doLayout();
			},
			initMenu:function(){
				menuPanel=new Ext.Panel({
					region:'west',
		            id:'west-panel',
		            title:'导航菜单',
		            split:true,
		            width: 200,
		            minSize: 175,
		            maxSize: 400,
		            collapsible: true,
		            margins:'0 0 0 0',
		            layout:'accordion',
		            collapseMode:'mini',
		            layoutConfig:{
						activeOnTop: false,
		                animate:true
		            }
				});
		        <#if topFunctions??>
		    		<#list topFunctions as fun>
		    			FineCmsMain.addChildMenus('${fun.functionId}','${fun.functionTitle}');
		            </#list>
			 	</#if>
			
			},
			addChildMenus:function(id,title){
				var menu = new Ext.Panel({
	                id:"m_"+id,
	                title:title,
	                iconCls:'nav',
					border:'0px',
					autoScroll:true
        		});
        		var root = new Ext.tree.AsyncTreeNode({
	                id:'m'+id,
	                text:title
	            });
        		 var tree = new Ext.tree.TreePanel({
	                loader:new Ext.tree.TreeLoader({dataUrl:'${base}/leftMenu.jhtm?pid='+id}),
	                root:root,
	                border:false,
	                margins:'3 3 3 3',
	                rootVisible:false
	            });
	            tree.on('click',function(node){
	                if (node.isLeaf()){
	                	var url='${base}'+node.attributes.url;
	                	FineCmsMain.loadPage(url,node.attributes.text);
	                }
	            });
	            root.expand(true);
	            menu.add(tree);
        		menuPanel.add(menu);
			}
			,
			initMainPanel:function(){
				mainPanel=new Ext.Panel({
                    region:'center',
                    title:"我的桌面",
                    el:'main',
                    layout:'fit',
                    frame:false,
                    autoScroll:true
                });
			},
			getMainPanelHeight:function(){
				return mainPanel.getInnerHeight();
			},
			loadPage:function(url,title){
				mainPanel.removeAll();
            	mainPanel.setTitle(title);
                mainPanel.load({
				    url:url,
				    callback: function(o){
				    	mainPanel.syncSize();
				    },
				    text: "正在加载...",
				    timeout: 120,
				    scripts: true
				});
			}
			
		};
	}();	
    Ext.onReady(function(){
        FineCmsMain.init();
    });
    
</#compress>