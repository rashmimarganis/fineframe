var FrameComponentApp= function(){
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
			FrameComponentApp.initStore();
			FrameComponentApp.initGridPanel();
			FrameComponentApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/component/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'componentId',
		            fields: [
		                'componentId','name','packageName','level' ,'fileType','templateId','templateName'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('componentId', 'desc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					sm.selectFirstRow();
				}
			});
		},
		initGridPanel:function(){	
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'componentId', 
	           header: "ID",
	           dataIndex: 'componentId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "名称",
	           dataIndex: 'name',
	           width: 100
	        },{
		           id: 'packageName', 
		           header: "文件夹",
		           dataIndex: 'packageName',
		           width: 100
		        }
		        ,
	        {
	           id: 'type', 
	           header: "文件类型",
	           dataIndex: 'fileType',
	           width: 100
	        },
		        {
			           id: 'level', 
			           header: "组件类型",
			           dataIndex: 'level',
			           width: 100
			        }
	        ,{
	           header: "对应模板",
	           dataIndex: 'templateName',
	           width: 150,
	           sortable: false
	        },{
		           header: "模板ID",
		           dataIndex: 'templateId',
		           width: 150,
		           sortable: false,
		           hidden:true
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
				  	text: '添加组件',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FrameComponentApp.addInfo
				 },'-',{
				  	text: '修改组件',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:FrameComponentApp.loadInfo
				 },'-',{
				  	text: '删除组件',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:FrameComponentApp.deleteInfo
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
				renderTo:'componentGrid'
		    });
		    grid.on('rowdblclick',FrameComponentApp.loadInfo);
		    grid.render();
		    store.load({params:{start:0, limit:pageSize}});
		},
		
		addInfo:function(){
			
			FrameComponentApp.showInfoDlg();
			form.reset();
			
		},
		initLayout:function(){
			
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'componentCenter',
				contentEl:'componentGrid',
				items:[grid]
		    });
	     	FineCmsMain.addFunctionPanel(center);
			
    	},
    	loadInfo:function(){
    		if(sm.getSelected()==null){
				Ext.Msg.alert("编辑组件","请先选择一个组件！");
				return;
			}else{
				FrameComponentApp.showInfoDlg();
	    		var select=sm.getSelected();
	    		var id=select.get('componentId');
	    		form.getForm().load({url:'frame/component/load.jhtm?id='+id, waitMsg:'正在加载数据...'});
			}
    		
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除组件","请先选择一个组件！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除组件","确定要删除选中的组件吗？",function(o){
					if(o=='yes'){
						var url='frame/component/deletes.jhtm?'+FrameComponentApp.getSelectedIds();
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
					Ext.Msg.alert("删除组件","删除组件成功！");
				}else{
					Ext.Msg.alert("删除组件","删除组件失败！");
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
						ids='ids='+r.get("componentId");
					}else{
						ids+="&ids="+r.get("componentId");
					}
			}
			return ids;
			
		},
		createTemplateCombo:function(){
			var RecordDef = Ext.data.Record.create([    
                {name: 'templateId'},{name: 'name'}                   
            ]); 
            var store=new Ext.data.Store({    
                //设定读取的地址 
                proxy: new Ext.data.HttpProxy({url: 'frame/template/listByType.jhtm?type=component'}),    
                //设定读取的格式    
                reader: new Ext.data.JsonReader({    
                    id:"templateId",totalProperty:'totalCount',root:'objs' 
                }, RecordDef),    
                remoteSort: true,
                autoLoad:true
            }); 
			
		    var combo = new Ext.form.ComboBox({
		        store: store,
		        fieldLabel: '对应模板',
		        displayField:'name',
		        valueField:'templateId',
		        typeAhead: true,
		        mode: 'local',
		        allowBlank:false,
		        triggerAction: 'all',
		        emptyText:'请选择模板...',
		        selectOnFocus:true,
		        allowBlank:false,
		        hiddenName: 'obj.template.templateId'
		    });
		    return combo;
		},
		createTypeCombo:function(){
			var store = new Ext.data.SimpleStore({
		        fields: ['name', 'label', 'tip'],
		        data : Ext.ux.ComponentType 
		    });
		    var combo = new Ext.form.ComboBox({
		        store: store,
		        fieldLabel: '文件类型',
		        displayField:'label',
		        valueField:'name',
		        readOnly:true,
		        typeAhead: true,
		        allowBlank:false,
		        mode: 'local',
		        triggerAction: 'all',
		        emptyText:'请选择类型...',
		        selectOnFocus:true,
		        hiddenName: 'obj.fileType'
		    });
		    return combo;
		}
		,
		createLevel:function(){
			var store = new Ext.data.SimpleStore({
		        fields: ['name', 'label', 'tip'],
		        data : Ext.ux.ComponentLevel 
		    });
		    var combo = new Ext.form.ComboBox({
		        store: store,
		        fieldLabel: '组件类型',
		        displayField:'label',
		        valueField:'name',
		        readOnly:true,
		        typeAhead: true,
		        allowBlank:false,
		        mode: 'local',
		        triggerAction: 'all',
		        emptyText:'请选择组件类型...',
		        selectOnFocus:true,
		        hiddenName: 'obj.level'
		    });
		    return combo;
		}
		,
		showInfoDlg:function(){
			if(!infoDlg){
								
				var templateCombox=FrameComponentApp.createTemplateCombo();
				var typeCombox=FrameComponentApp.createTypeCombo();
				var levelCombox=FrameComponentApp.createLevel();
				form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        layout:'form',
			        clientValidation: true,
			        url:'frame/component/save.jhtm',
			        defaultType: 'textfield',
			        defaults: {width: 220},
			        labelAlign: 'left',
			        reader : new Ext.data.JsonReader({
			        	root  : 'data',
			        	successProperty: 'success'
			        }, [
			            {name: 'obj.componentId', mapping:'componentId'}, 
			            {name: 'obj.name', mapping:'name'},
			            {name: 'obj.fileType', mapping:'fileType'},
			            {name: 'obj.level', mapping:'level'},
			            {name: 'obj.packageName', mapping:'packageName'},
			            {name: 'obj.template.templateId', mapping:'templateId'}
			        ]),
			        items: [{
			        	name:'obj.componentId',
			        	xtype:'hidden',
			        	value:0
			        	
			        },
			        {
	                    fieldLabel: '组件名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },{
	                    fieldLabel: '目录名称',
	                    name: 'obj.packageName',
	                    allowBlank:false
	                },levelCombox,typeCombox,templateCombox]
			    });
				
				form.on({
					actioncomplete: function(form, action){
						FrameMsg.msg("保存组件",action.result.msg);
		               
						saveBtn.enable();
						form.reset();
						FrameComponentApp.reload();
		        	},
		        	actionfailed: function(form, action){
		                saveBtn.enable();
		                FrameMsg.msg("保存组件",action.result.msg);
		        	}
				});
				saveBtn=form.addButton({
			        text: '保存',
			        handler: function(){
						if(form.getForm().isValid()){
							form.getForm().submit({url:'frame/component/save.jhtm', waitMsg:'正在保存数据...'});
							saveBtn.disable();
						}else{
							Ext.Msg.alert("保存组件","请把组件信息填写完整！");
						}
			        }
			    });
				
			    infoDlg = new Ext.Window({
			        title: '组件信息',
			        width: 400,
			        height:255,
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
						handler:FrameComponentApp.showInfoDlg
					 },'-',{
					  	text: '下一条',
			            iconCls: 'x-btn-text-icon next',
			            scope: this,
						handler:FrameComponentApp.loadInfo
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
FrameComponentApp.init();