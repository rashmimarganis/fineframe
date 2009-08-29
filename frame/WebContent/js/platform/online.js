var App= function(){
	var viewport;
	var dlg;
	var store;
	var logStore;
    var xg = Ext.grid;
	var grid;
	var sm;
	var storeSm;
	var pageSize=18;
	var pageSize1=10;
	var logDlg;
	return {
		init:function(){
			Ext.QuickTips.init();
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			App.initGridPanel();
			App.initLayout();
		    Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({remove:true});
		}
		,
		initLayout:function(){
	     	viewport = new Ext.Viewport({
	          layout:'fit',
	          items:[grid]
	        });
			store.load({params:{start:0,limit:pageSize}});
    	}
		,
		
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../user/online.do'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id','username','email',
						 'lastLoginTime',
						 'lastLoginIp','loginTimes',
						'realname',
						'orgTitle'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('id', 'asc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
				}
			});
		},
		initGridPanel:function(){
			App.initStore();
			function rendererEnabled(v){
				if(v){
					return '<img src="../resources/images/default/menu/checked.gif">';
				}
				return '<img src="../resources/images/default/menu/unchecked.gif">';
			}
			
			function rendererNon(v){
				if(v){
					return '<img src="../resources/images/default/menu/unchecked.gif">';
				}
				return '<img src="../resources/images/default/menu/checked.gif">';
			}
			
			
			function renderOrg(v){
				if(v==='null'){
					return '';
				}else{
					return v;
				}
			}
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'id',
	           header: "ID",
	           dataIndex: 'id',
	           width: 20,
			   hidden:true
	        },{
	           id: 'username', 
	           header: "用户名",
	           dataIndex: 'username',
	           width: 100
	        },{
	           id: 'realname', 
	           header: "姓名",
	           dataIndex: 'realname',
	           width: 100
	        },{
	           header: "电子邮件",
	           dataIndex: 'email',
	           width: 130
	        },{
	           header: "最近登录",
	           dataIndex: 'lastLoginTime',
	           width: 130,
			   renderer:renderDate
	        },{
	           header: "登录IP",
	           dataIndex: 'lastLoginIp',
	           width: 100
	        },{
	           header: "登录次数",
	           dataIndex: 'loginTimes',
	           width: 70,
			   align:'right'
	        },{
				header:"登录组织",
				dataIndex:'orgTitle',
				width:100
			}]);
	
		    cm.defaultSortable = true;
			
		    grid = new Ext.grid.GridPanel({
				id:'grid',
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				viewConfig: {
					enableRowBody:true,
           			showPreview:true,
		            forceFit:true
		        },
				renderTo:'grid',
				tbar:[{
				  	text: '本次登录日志',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.viewLog
				  },'-',{
				  	text: '踢出用户',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.kick
				  }],
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        })
		    });
			grid.on('rowdblclick',function(){App.viewLog();});
		    grid.render();
			
		},
		initLogStore:function(){
			var select=sm.getSelected();
			logStore = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../log/findByUser.do'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [ 
						'ip','time','operation',
						'orgTitle'
		            ]
		        }),
		        remoteSort: true
		    });
		    logStore.setDefaultSort('id', 'asc');
		},
		reloadLogStore:function(){
			var select=sm.getSelected();
			logStore.proxy=new Ext.data.HttpProxy({
		            url: '../log/findByUser.do'
	        });
			var userId=select.get("id");
			var time=renderDate(select.get("lastLoginTime"));
			logStore.load({params:{userId:userId,time:time,start:0,limit:pageSize1}});
		}
		,
		initStoreGridPanel:function(){	
			App.initLogStore();
			function renderDate(v){
				if(v==null){
					return '';
				}
				var month=v.month*1+1;
				var year=v.year*1+1900;
				var date=v.date;
				var hours=v.hours;
				var minutes=v.minutes;
				var seconds=v.seconds;
				
				return year+'-'+month+'-'+date+' '+hours+':'+minutes+':'+seconds;
				
			}
			storeSm = new xg.CheckboxSelectionModel(); 
			var cm = new Ext.grid.ColumnModel([new xg.RowNumberer(),{
	           header: "操作名称",
	           dataIndex: 'operation',
	           width: 150
	        },{
	           header: "操作时间",
	           dataIndex: 'time',
	           width: 150,
			   renderer:renderDate
	        },{
	           header: "操作IP",
	           dataIndex: 'ip',
	           width: 80
	        },{
			   id:'title',
	           header: "登录组织",
	           dataIndex: 'orgTitle',
	           width: 150
	        }]);
	
		    cm.defaultSortable = true;
		    
				 
		    logGrid = new Ext.grid.GridPanel({
		        store: logStore,
		        cm: cm,
				sm: storeSm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        }), 
				viewConfig: {
					enableRowBody:true,
           			showPreview:true,
		            forceFit:true
		        },
				renderTo:'grid'
		    });
		    logGrid.render();
			
		}
		,
		showLogDlg:function(){
			if(!logDlg){
				App.initStoreGridPanel();
				logDlg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					contentEl:'log-grid',
					items:[logGrid],
					title: '用户本次访问日志',
	                width:650,
	                height:350,
	                closeAction:'hide',
	                plain: true,
					modal:true,
					bodyStyle:'padding:5px;',
    				buttonAlign:'right',
	                buttons: [{
	                    text: '关闭',
	                    handler: function(){
	                        logDlg.hide();
	                    }
	                }]
            	});
			}
			logDlg.show(this);
		}
		,
		viewLog:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("查看日志","请先选择一个用户！");
				return;
			}
			App.showLogDlg();
			App.reloadLogStore();
		},
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
					var r=selections[i];
					if(ids.length==0){
						ids="userNames[0]="+r.get("username");
					}else{
						ids+="&userNames["+i+"]="+r.get("username");
					}
			}
			return ids;
		}
		,
		kick:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("踢出用户","请先选择一个用户！");
				return;
			}
			if(!confirm("确定要踢出选中的用户吗？")){
				return;
			}
			var url='../user/kick.do?'+App.getSelectedIds();
			Ext.Ajax.request({
				url:url,
				method:'get',
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				var _success=result.success;
				
				if(_success){
					var totalCount=result.totalCount;
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					var totRecords= new Number(totalCount);
					if(start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
					Ext.Msg.alert("提出用户","踢出用户成功！");
				}else{
					Ext.Msg.alert("提出用户","踢出用户失败！");
				}
			}
			function failure(rep){
				Ext.Msg.alert("提出用户","踢出用户失败！");
				Ext.Msg.alert("提出用户",rep.responseText);
			}
		}
	};
}();
Ext.onReady(App.init);