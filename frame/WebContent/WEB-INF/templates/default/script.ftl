	Ext.BLANK_IMAGE_URL = '${base}/js/resources/images/default/s.gif';
	var title="FinePlatform开发平台";
	var mainPanel;
	var menuPanel;
	var viewport;
	var mainPanelHeight;
	var contentPanel;
	var oTime;
	
	var editorInstance;  
 	function FCKeditor_OnComplete( instance ) {  
       editorInstance=instance;  
    }; 
	var FineCmsMain=function(){
		return {
			init:function(){
				Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
				Ext.QuickTips.init();
				
				FineCmsMain.initMenu();
				FineCmsMain.initMainPanel();
				var infoPanel=new Ext.Panel({
					region:'south',
                    el: 'south',
                    height: 26,
                    margins:'2 2 2 2',
                    contentEl:'loginInfo'
				});
				viewport = new Ext.Viewport({
		            layout:'border',
		            items:[
		                new Ext.BoxComponent({ // raw
		                    region:'north',
		                    el: 'north',
		                    height:60,
		                    border:'0px',
		                    margins:'0 0 0 0'
		                }),infoPanel,menuPanel,mainPanel
		             ]
		        });
		        
		       viewport.doLayout();
		      
		      mainPanel.load({
				    url:'desktop.jhtm',
				    callback: function(o){
				    	mainPanel.syncSize();
				    },
				    timeout: 120,
				    scope: this, 
		            discardUrl: true,
		            nocache: true,
				    scripts: true
				});
				setTimeout(function(){
			        Ext.get('loading').remove();
			        Ext.get('loading-mask').fadeOut({remove:true});
			    }, 450);
		      
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
		    			FineCmsMain.addChildMenus('${fun.functionId}','${fun.functionName}');
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
	                loader:new Ext.tree.TreeLoader({dataUrl:'${base}/menu.jhtm?pid='+id}),
	                root:root,
	                border:false,
	                margins:'3 3 3 3',
	                rootVisible:false
	            });
	            tree.on('click',function(node){
	                if (node.isLeaf()){
	                	document.title=node.text+' - '+title;
	                	var url='${base}'+node.attributes.url;
	                	FineCmsMain.loadPage(url,node.text);
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
			getMainPanelWidth:function(){
				return mainPanel.getInnerWidth();
			},
			loadUrl:function(url){
				mainPanel.load({
				    url:url,
				    callback: function(o){
				    	mainPanel.syncSize();
				    },
				    text: "正在加载...",
				    timeout: 120,
				    scope: this, 
		            discardUrl: true,
		            nocache: true,
				    scripts: true
				});
			}
			,
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
				    scope: this, // optional scope for the callback
		            discardUrl: true,
		            nocache: true,
				    scripts: true
				});
			},
			addFunctionPanel:function(p){
				contentPanel=p;
				
				p.setHeight(mainPanel.getInnerHeight()-50);
				p.setWidth(mainPanel.getInnerWidth()-50);
				contentPanel=p;
				mainPanel.add(p);
				if (oTime){
			        clearTimeout(oTime);
			    }
			    oTime = setTimeout("FineCmsMain.contentPanelResize()", 2);
			},
			contentPanelResize:function(){
				mainPanel.syncSize();
				mainPanel.doLayout();
			}
			
		};
	}();	
    
	Ext.onReady(function(){
        FineCmsMain.init();
    });
    
function formatDate(v) {
	if (v == null) {
		return '';
	}
	var month = v.month * 1 + 1;
	var year = v.year * 1 + 1900;
	var date = v.date*1;
	
	if(month<10){
		month="0"+month;
	}
	if(date<10){
		date="0"+date;
	}
	return year + '-' + month + '-' + date;

}

function formatGender(v){
	if(v=='m'){
		return '男';
	}else if(v=='f'){
		return '女';
	}else{
		return '';
	}
	
}
