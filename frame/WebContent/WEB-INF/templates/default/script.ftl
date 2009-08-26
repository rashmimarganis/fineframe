
	Ext.BLANK_IMAGE_URL = '${base}/js/resources/images/default/s.gif';
	var mainPanel;
	var menuPanel;
	var viewport;
	var mainPanelHeight;
	//Ext.reg('checkbox', Ext.form.Checkbox);
	var contentPanel;
	var oTime;
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
		       /*
		       mainPanel.load({
				    url:'welcome.jsp',
				    callback: function(o){
				    	mainPanel.syncSize();
				    },
				    timeout: 120,
				    scope: this, // optional scope for the callback
		            discardUrl: true,
		            nocache: true,
				    scripts: true
				});
		      */
		       //FrameMsg.init();
		       
		       //FrameMsg.msg("AAAA","AAAAAAAAAAAAAAAAAA");
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
	                	document.title=node.text+' - '+document.title;
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
				return mainPanel.getInnerHeight();
			},
			loadPage:function(url,title){
				/*
				if(contentPanel){
					mainPanel.remove(contentPanel);
				}
				*/
				Ext.getDom("main").innerHtml="";
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
				
				p.setHeight(FineCmsMain.getMainPanelHeight()-1);
				p.setWidth(FineCmsMain.getMainPanelWidth()-1);
				contentPanel=p;
				mainPanel.add(p);
				//mainPanel.syncSize();
				if (oTime){
			        clearTimeout(oTime);
			    }
			    oTime = setTimeout("FineCmsMain.contentPanelResize()", 100);
			},
			contentPanelResize:function(){
				mainPanel.doLayout();
				mainPanel.syncSize();
				//contentPanel.setHeight(FineCmsMain.getMainPanelHeight());
				//contentPanel.setWidth(FineCmsMain.getMainPanelWidth());
			}
			
		};
	}();	
    
    
    FrameMsg = function(){
    	var msgCt;

	    function createBox(t, s){
	        return ['<div class="msg">',
	                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
	                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
	                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
	                '</div>'].join('');
	    }
	    return {
	        msg : function(title, format){
	            if(!msgCt){
	                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
	            }
	            msgCt.alignTo(document, 't-t');
	            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
	            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
	            m.slideIn('t').pause(1).ghost("t", {remove:true});
	        },

	        init : function(){
	            var t = Ext.get('exttheme');
	            if(!t){ // run locally?
	                return;
	            }
	            var theme = Cookies.get('exttheme') || 'aero';
	            if(theme){
	                t.dom.value = theme;
	                Ext.getBody().addClass('x-'+theme);
	            }
	            t.on('change', function(){
	                Cookies.set('exttheme', t.getValue());
	                setTimeout(function(){
	                    window.location.reload();
	                }, 250);
	            });
	
	            var lb = Ext.get('lib-bar');
	            if(lb){
	                lb.show();
	            }
	        }
    };
	}();
	
	
	Ext.onReady(function(){
        FineCmsMain.init();
    });
    
    var Cookies = {};
Cookies.set = function(name, value){
     var argv = arguments;
     var argc = arguments.length;
     var expires = (argc > 2) ? argv[2] : null;
     var path = (argc > 3) ? argv[3] : '/';
     var domain = (argc > 4) ? argv[4] : null;
     var secure = (argc > 5) ? argv[5] : false;
     document.cookie = name + "=" + escape (value) +
       ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
       ((path == null) ? "" : ("; path=" + path)) +
       ((domain == null) ? "" : ("; domain=" + domain)) +
       ((secure == true) ? "; secure" : "");
};

Cookies.get = function(name){
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	var j = 0;
	while(i < clen){
		j = i + alen;
		if (document.cookie.substring(i, j) == arg)
			return Cookies.getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if(i == 0)
			break;
	}
	return null;
};

Cookies.clear = function(name) {
  if(Cookies.get(name)){
    document.cookie = name + "=" + "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
};

Cookies.getCookieVal = function(offset){
   var endstr = document.cookie.indexOf(";", offset);
   if(endstr == -1){
       endstr = document.cookie.length;
   }
   return unescape(document.cookie.substring(offset, endstr));
};

function renderDate(v) {
	if (v == null) {
		return '';
	}
	var month = v.month * 1 + 1;
	var year = v.year * 1 + 1900;
	var date = v.date;
	var hours = v.hours;
	var minutes = v.minutes;
	var seconds = v.seconds;

	return year + '-' + month + '-' + date + ' ' + hours + ':'
			+ minutes + ':' + seconds;

}
