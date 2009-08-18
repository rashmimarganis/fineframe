var FrameModelApp= function(){
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var pageSize=18;
	var infoDlg;
	var saveBtn;
	var form;
	return {
		init:function(){
			FrameModelApp.initStore();
			FrameModelApp.initGridPanel();
			FrameModelApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/template/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'templateId',
		            fields: [
		                'templateId','fileName' ,'name','type'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('templateId', 'desc');
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
	           id: 'templateId', 
	           header: "ID",
	           dataIndex: 'templateId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "名称",
	           dataIndex: 'name',
	           width: 100
	        },{
	           header: "文件名",
	           dataIndex: 'fileName',
	           width: 150
	        },{
	           header: "类型",
	           dataIndex: 'type',
	           width: 150,
	           renderer:function(v){
	        		if(v=='source'){
	        			return '源码模板';
	        		}else if(v=='page'){
	        			return '控件模板';
	        		}else if(v=='control'){
	        			return '控件模板';
	        		}
	        	}
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
				  	text: '添加模板',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FrameModelApp.showInfoDlg
				 },'-',{
				  	text: '修改模板',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:FrameModelApp.loadInfo
				 },'-',{
				  	text: '删除模板',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:FrameModelApp.deleteInfo
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
				renderTo:'templateGrid'
		    });
		    grid.on('rowdblclick',FrameModelApp.loadInfo);
		    grid.render();
		},
		initLayout:function(){
			
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'templateCenter',
				region:'center',
				contentEl:'templateGrid',
				items:[grid]
		    });
	     	mainPanel.add(grid);
	     	
	     	//
			store.load({params:{start:0, limit:pageSize}});
			center.syncSize();
			mainPanel.doLayout();
    	},
    	loadInfo:function(){
    		if(sm.getSelected()==null){
				Ext.Msg.alert("删除模板","请先选择一个模板！");
				return;
			}else{
	    		FrameModelApp.showInfoDlg();
	    		var select=sm.getSelected();
	    		var id=select.get('templateId');
	    		form.getForm().load({url:'frame/template/load.jhtm?id='+id, waitMsg:'正在加载数据...'});
			}
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除模板","请先选择一个模板！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除模板","确定要删除选中的模板吗？",function(o){
					if(o=='yes'){
						var url='frame/template/deletes.jhtm?'+FrameModelApp.getSelectedIds();
						Ext.Ajax.request({
							url:url,
							success:success,
							failure:failure
						});
					}else{
						return;
					}
				});
			}
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
					Ext.Msg.alert("删除模板","删除模板成功！");
				}else{
					Ext.Msg.alert("删除模板","删除模板失败！");
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
						ids='ids='+r.get("templateId");
					}else{
						ids+="&ids="+r.get("templateId");
					}
			}
			return ids;
			
		},
		showInfoDlg:function(){
			if(!infoDlg){
				var store = new Ext.data.SimpleStore({
			        fields: ['name', 'label', 'tip'],
			        data : Ext.ux.templateType 
			    });
			    var combo = new Ext.form.ComboBox({
			        store: store,
			        fieldLabel: '模板类型',
			        displayField:'label',
			        valueField:'name',
			        readOnly:true,
			        typeAhead: true,
			        mode: 'local',
			        value:'source',
			        triggerAction: 'all',
			        emptyText:'请选择类型...',
			        selectOnFocus:true,
			        hiddenName: 'obj.type'
			    });
				form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        layout:'form',
			        clientValidation: true,
			        url:'frame/template/save.jhtm',
			        defaultType: 'textfield',
			        defaults: {width: 220},
			        labelAlign: 'left',
			        reader : new Ext.data.JsonReader({
			        	root  : 'data',
			        	successProperty: 'success'
			        }, [
			            {name: 'obj.templateId', mapping:'templateId'}, // custom mapping
			            {name: 'obj.name', mapping:'name'},
			            {name: 'obj.type', mapping:'type'},
			            {name: 'obj.fileName', mapping:'fileName'},
			            {name: 'obj.content', mapping:'content'}
			        ]),
			        items: [{
			        	name:'obj.templateId',
			        	xtype:'hidden',
			        	value:0
			        	
			        },
			        {
	                    fieldLabel: '模板名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },combo,{
	                    fieldLabel: '文件名称',
	                    name: 'obj.fileName',
	                    allowBlank:false
	                },{
	                	hideLabel:true,
	                	fieldLabel: '模板内容',
	                    name: 'obj.content',
	                    xtype:'textarea',
	                    allowBlank:false,
	                    height:200,
	                    anchor:'98%'
	                }]
			    });
				
				form.on({
					actioncomplete: function(form, action){
						FrameMsg.msg("保存模板",action.result.msg);
		               
						saveBtn.enable();
						form.reset();
						FrameModelApp.reload();
		        	},
		        	actionfailed: function(form, action){
		                saveBtn.enable();
		                FrameMsg.msg("保存模板",action.result.msg);
		        	}
				});
				saveBtn=form.addButton({
			        text: '保存',
			        handler: function(){
						if(form.getForm().isValid()){
							form.getForm().submit({url:'frame/template/save.jhtm', waitMsg:'正在保存数据...'});
							saveBtn.disable();
						}else{
							Ext.Msg.alert("保存模板","请把模板信息填写完整！");
						}
			        }
			    });
				
			    infoDlg = new Ext.Window({
			        title: '模板信息',
			        width: 500,
			        height:400,
			        minWidth: 300,
			        minHeight: 200,
			        layout: 'fit',
			        plain:true,
			        modal:true,
			        bodyStyle:'padding:5px;',
			        buttonAlign:'center',
			        items: form,
			        tbar:[{
					  	text: '上一条',
			            iconCls: 'x-btn-text-icon prev',
			            scope: this,
						handler:FrameModelApp.showInfoDlg
					 },'-',{
					  	text: '下一条',
			            iconCls: 'x-btn-text-icon next',
			            scope: this,
						handler:FrameModelApp.loadInfo
					 }],
			        buttons: [saveBtn,{
			            text: '取消',
			            handler:function(){
			        		infoDlg.hide();
			        	}
			        }
			        ]
			    });
			    
			}
			infoDlg.show();
			form.getForm().findField("obj.name").focus(true);
		},
		reload:function(){
			var lastO= store.lastOptions.params;
			var start=lastO.start;
			store.load({params:{start:start, limit:pageSize}});
		}
	};
}();
FrameModelApp.init();