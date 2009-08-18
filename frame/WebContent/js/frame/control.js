var FrameControlApp= function(){
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
			FrameControlApp.initStore();
			FrameControlApp.initGridPanel();
			FrameControlApp.initLayout();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'frame/control/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'controlId',
		            fields: [
		                'controlId','name' ,'templateId','templateName'
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('controlId', 'desc');
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
	           id: 'controlId', 
	           header: "ID",
	           dataIndex: 'controlId',
	           width: 40
	        },{
	           id: 'name', 
	           header: "名称",
	           dataIndex: 'name',
	           width: 100
	        },{
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
				  	text: '添加控件',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:FrameControlApp.addInfo
				 },'-',{
				  	text: '修改控件',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:FrameControlApp.loadInfo
				 },'-',{
				  	text: '删除控件',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:FrameControlApp.deleteInfo
				 }, '->',{
				  	text: '生成代码',
		            iconCls: 'x-btn-text-icon generate',
		            scope: this,
					handler:FrameControlApp.deleteInfo
				 },'-', {
				  	text: '运行测试',
		            iconCls: 'x-btn-text-icon server',
		            scope: this,
					handler:FrameControlApp.deleteInfo
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
				renderTo:'controlGrid'
		    });
		    grid.on('rowdblclick',FrameControlApp.loadInfo);
		    grid.render();
		},
		
		addInfo:function(){
			FrameControlApp.showInfoDlg();
			form.reset();
			
		},
		initLayout:function(){
			
			var center= new Ext.Panel({
		        collapsible:false,
				layout:'fit',
		        el: 'controlCenter',
				region:'center',
				contentEl:'controlGrid',
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
				Ext.Msg.alert("编辑控件","请先选择一个控件！");
				return;
			}else{
				FrameControlApp.showInfoDlg();
	    		var select=sm.getSelected();
	    		var id=select.get('controlId');
	    		form.getForm().load({url:'frame/control/load.jhtm?id='+id, waitMsg:'正在加载数据...'});
			}
    		
    	}
    	,
		deleteInfo : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除控件","请先选择一个控件！");
				return;
			}else{
				var s=Ext.Msg.confirm("删除控件","确定要删除选中的控件吗？",function(o){
					if(o=='yes'){
						var url='frame/control/deletes.jhtm?'+FrameControlApp.getSelectedIds();
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
					Ext.Msg.alert("删除控件","删除控件成功！");
				}else{
					Ext.Msg.alert("删除控件","删除控件失败！");
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
						ids='ids='+r.get("controlId");
					}else{
						ids+="&ids="+r.get("controlId");
					}
			}
			return ids;
			
		},
		showInfoDlg:function(){
			if(!infoDlg){
								
				var RecordDef = Ext.data.Record.create([    
                    {name: 'templateId'},{name: 'name'}                   
                ]); 
                var store=new Ext.data.Store({    
                    //设定读取的地址 
                    proxy: new Ext.data.HttpProxy({url: 'frame/template/listByType.jhtm?type=control'}),    
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
			        triggerAction: 'all',
			        emptyText:'请选择模板...',
			        selectOnFocus:true,
			        allowBlank:false,
			        hiddenName: 'obj.template.templateId'
			    });
				form = new Ext.form.FormPanel({
			        baseCls: 'x-plain',
			        layout:'form',
			        clientValidation: true,
			        url:'frame/control/save.jhtm',
			        defaultType: 'textfield',
			        defaults: {width: 220},
			        labelAlign: 'left',
			        reader : new Ext.data.JsonReader({
			        	root  : 'data',
			        	successProperty: 'success'
			        }, [
			            {name: 'obj.controlId', mapping:'controlId'}, 
			            {name: 'obj.name', mapping:'name'},
			            {name: 'obj.label', mapping:'label'},
			            {name: 'obj.template.templateId', mapping:'templateId'}
			        ]),
			        items: [{
			        	name:'obj.controlId',
			        	xtype:'hidden',
			        	value:0
			        	
			        },
			        {
	                    fieldLabel: '英文名称',
	                    name: 'obj.name',
	                    allowBlank:false
	                    
	                },{
	                    fieldLabel: '中文名称',
	                    name: 'obj.label',
	                    allowBlank:false
	                },combo]
			    });
				
				form.on({
					actioncomplete: function(form, action){
						FrameMsg.msg("保存控件",action.result.msg);
		               
						saveBtn.enable();
						form.reset();
						FrameControlApp.reload();
		        	},
		        	actionfailed: function(form, action){
		                saveBtn.enable();
		                FrameMsg.msg("保存控件",action.result.msg);
		        	}
				});
				saveBtn=form.addButton({
			        text: '保存',
			        handler: function(){
						if(form.getForm().isValid()){
							form.getForm().submit({url:'frame/control/save.jhtm', waitMsg:'正在保存数据...'});
							saveBtn.disable();
						}else{
							Ext.Msg.alert("保存控件","请把控件信息填写完整！");
						}
			        }
			    });
				
			    infoDlg = new Ext.Window({
			        title: '控件信息',
			        width: 400,
			        height:275,
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
						handler:FrameControlApp.showInfoDlg
					 },'-',{
					  	text: '下一条',
			            iconCls: 'x-btn-text-icon next',
			            scope: this,
						handler:FrameControlApp.loadInfo
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
FrameControlApp.init();