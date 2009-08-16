var ProjectApp= function(){
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var pageSize=18;
	var infoDlg;
	return {
		init:function(){
			ProjectApp.initStore();
			ProjectApp.initGridPanel();
			ProjectApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/project/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'projectId',
		            fields: [
		                'projectId', 'name','encode','basePath','sourcePath','webPath'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('projectId', 'desc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
				}
			});
		},
		initGridPanel:function(){	
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
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'projectId', 
	           header: "ID",
	           dataIndex: 'projectId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "名称",
	           dataIndex: 'name',
	           width: 100
	        },{
	           header: "编码",
	           dataIndex: 'encode',
	           width: 150
	        },{
	           header: "项目目录",
	           dataIndex: 'basePath',
	           width: 150
	        },{
	           header: "Java目录",
	           dataIndex: 'sourcePath',
	           width: 150
	        },{
		           header: "页面目录",
		           dataIndex: 'webPath',
		           width: 150
		        }]);
	
		    cm.defaultSortable = true;
		    
			var mainHeight=FineCmsMain.getMainPanelHeight()-1;	 
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        height:mainHeight,
		        autoScroll:true,
		        loadMask: true,
				tbar:[{
				  	text: '添加项目',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:ProjectApp.showInfoDlg
				 },'-',{
				  	text: '修改项目',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 },'-',{
				  	text: '删除项目',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 }, '->',{
				  	text: '生成代码',
		            iconCls: 'x-btn-text-icon generate',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 },'-', {
				  	text: '运行测试',
		            iconCls: 'x-btn-text-icon server',
		            scope: this,
					handler:ProjectApp.deleteInfo
				 }],
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
				renderTo:'projectGrid'
		    });
		    grid.render();
		},
		initLayout:function(){
			
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'projectCenter',
				region:'center',
				contentEl:'projectGrid',
				items:[grid]
		    });
	     	mainPanel.add(grid);
	     	
	     	//
			store.load({params:{start:0, limit:pageSize}});
			center.syncSize();
			mainPanel.doLayout();
    	},
		deleteInfo : function(){
			if(sm.getSelected==null){
				alert("请先选择一个项目！");
				return;
			}
			if(!confirm("确定要删除选中的项目吗？")){
				return;
			}
			var url='frame/project/delete.jhtm?ids='+ProjectApp.getSelectedIds();
		
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				var _success=result.success;
				var totalCount=result.totalCount;
				if(_success){
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					var totRecords= new Number(totalCount);
					if(start>0&&start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
					alert("删除成功！");
				}else{
					alert("删除失败！");
				}
				
			}
			function failure(rep){
				alert("删除失败！");
				alert(rep.responseText);
			}
		}
		,
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
					var r=selections[i];
					if(ids.length==0){
						ids=r.get("id");
					}else{
						ids+=","+r.get("id");
					}
			}
			return ids;
			
		},
		showInfoDlg:function(){
			if(!infoDlg){
				var store = new Ext.data.SimpleStore({
			        fields: ['name', 'label', 'tip'],
			        data : Ext.ux.encoding // from states.js
			    });
			    var combo = new Ext.form.ComboBox({
			        store: store,
			        fieldLabel: '文件编码',
			        displayField:'label',
			        valueField:'name',
			        typeAhead: true,
			        mode: 'local',
			        value:'utf-8',
			        triggerAction: 'all',
			        emptyText:'请选择编码...',
			        selectOnFocus:true
			    });
				var form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        layout:'form',
			        url:'frame/project/save.jhtm',
			        defaultType: 'textfield',
			        defaults: {width: 220},
			        labelAlign: 'left',
			        items: [{
	                    fieldLabel: '项目名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },combo,{
	                    fieldLabel: '项目目录',
	                    name: 'obj.packagePath',
	                    allowBlank:false
	                },{
	                    fieldLabel: '源码目录',
	                    name: 'obj.sourcePath',
	                    allowBlank:false
	                },{
	                    fieldLabel: '页面目录',
	                    name: 'obj.webPath',
	                    allowBlank:false
	                }]
			    });

			    infoDlg = new Ext.Window({
			        title: '项目信息',
			        width: 400,
			        height:220,
			        minWidth: 300,
			        minHeight: 200,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        bodyStyle:'padding:5px;',
			        buttonAlign:'center',
			        items: form,

			        buttons: [{
			            text: '保存',
			            handler: function(){
			        		form.getForm().submit({url:'frame/project/save.jhtm', waitMsg:'正在保存数据...'});
			        	}
			        },{
			            text: '取消',
			            handler:function(){
			        		infoDlg.hide();
			        	}
			        }
			        ]
			    });
			    
			}
			infoDlg.show();
			
			
		}
	};
}();
ProjectApp.init();