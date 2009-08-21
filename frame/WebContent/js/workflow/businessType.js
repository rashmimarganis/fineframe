Ext.BLANK_IMAGE_URL = '../js/resources/images/default/s.gif';
var App= function(){
	var viewport;
	var ope_edit=0;
	var ope_add=1;
	var operation=ope_add;
	var dlg;
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var id;
	var name;
	var title;
	var sort;
	var note;
	var oldName;
	var form;
	var pageSize=18;
	var westPanel;
	return {
		init:function(){
			Ext.QuickTips.init();
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
			App.initForm();
			App.initStore();
			App.initGridPanel();
			App.initLayout();
			
		    Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({remove:true});
		}
		,
		initLayout:function(){
	     	viewport = new Ext.Viewport({
	          layout:'fit',
	          items:[
	              grid
	           ]
	        });
			store.load({params:{start:0, limit:pageSize}});
    	},
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../flow/type/page.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id', 'name','sort','note'
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
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'id', 
	           header: "ID",
	           dataIndex: 'id',
	           width: 20
	        },{
	           header: "名称",
	           dataIndex: 'name',
	           width: 80
	        },{
	           header: "排序",
	           dataIndex: 'sort',
	           width: 80
	        },{
	           header: "备注",
	           dataIndex: 'note',
	           width: 150
	        }]);
	
		    cm.defaultSortable = true;
			
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				tbar:[{
				  	text: '新增类型',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑类型',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '删除类型',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteInfo
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
				renderTo:'grid'
		    });
			grid.on('rowdblclick',App.edit);
		    grid.render();
		},
		add:function(){
			operation=ope_add;
			id.setValue('-1');
			oldName.setValue('');
			name.setValue('');
			sort.setValue('0');
			note.setValue('');
			App.showDlg();
		},
		initForm:function(){
			id=new Ext.form.Hidden({id:'id',name:'obj.id',value:'0'});
			oldName=new Ext.form.Hidden({id:'oldName',name:'oldName',value:'0'});
			name=new Ext.form.TextField({id:'name',name:'obj.name',value:'',anchor: '100%',fieldLabel:'名称',maxLength:'32',minLength:'2',maxLengthText:'编号长度不能超过32',minLengthText:'编号不能为空！'});
			sort=new Ext.form.TextField({id:'sort',name:'obj.sort',value:'',anchor: '100%',fieldLabel:'排序',maxLength:'8',minLength:'1',maxLengthText:'编号长度不能超过32',minLengthText:'编号不能为空！'});
			note=new Ext.form.TextField({id:'note',name:'obj.note',value:'',anchor: '100%',fieldLabel:'备注'});
			form = new Ext.form.FormPanel({
		        baseCls: 'x-plain',
		        labelWidth: 35,
		        url:'../flow/type/save.do',
		        defaultType: 'textfield',
		        items: [id,oldName,name,sort,note]
		    });
		}
		,
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					title: '类型信息',
	                width:300,
	                height:160,
	                closeAction:'hide',
	                plain: true,
					items:form,
					modal:true,
					bodyStyle:'padding:5px;',
    				buttonAlign:'right',
	                buttons: [{
	                    text:'保存',
						handler:App.saveInfo
	                },{
	                    text: '取消',
	                    handler: function(){
	                        dlg.hide();
	                    }
	                }]
            	});
	        }
	        dlg.show(this);
			name.focus(false,true);
		}
		,
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				 alert("请先选择一个类型！");
				 return;
			}
			operation=ope_edit;
			Ext.Ajax.request({
				url:'../flow/type/load.do',
				params:'id='+select.get('id'),
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var res="+rep.responseText);
				id.setValue(res.id);
				name.setValue(res.name);
				sort.setValue(res.sort);
				oldName.setValue(res.name);
				note.setValue(res.note);
				App.showDlg();
			}
			function failure(rep){
				alert("加载失败！");
				alert(rep.responseText);
			}
		},
		saveInfo:function(){
			if(name.getValue()==''){
				alert("请填写编号！");
				return;
			}
			
			Ext.Ajax.request({
				url:'../flow/type/save.do',
				params:'obj.typeID='+id.getValue()+"&obj.typeName="+name.getValue()+"&obj.sort="+sort.getValue()+"&obj.note="+note.getValue(),
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var ss="+rep.responseText);
				if(ss.success){
					if(operation==ope_edit){
						var select=sm.getSelected();
						select.set('name',name.getValue());
						select.set('sort',sort.getValue());
						select.set('note',note.getValue());
						dlg.hide();
						select.commit();
					}else{
						var lastO=store.lastOptions.params;
						var start=lastO.start;
						dlg.hide();
						store.load({params:{start:start, limit:pageSize}});
					}
					Ext.Msg.alert("保存类型","保存成功！");
				}else{
					var exist=ss.exist;
					if(exist){
						Ext.Msg.alert("保存类型","编号已经存在，请重新填写！");
						name.focus();
						return;
					}
				}
				
				
				
			}
			function failure(rep){
				
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除类型","请先选择一个类型！");
				return;
			}
			if(!confirm("确定要删除选中的类型吗？")){
				return;
			}
			var url='../flow/type/delete.do?ids='+App.getSelectedIds();
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
					Ext.Msg.alert("删除类型","删除成功！");
				}else{
					Ext.Msg.alert("删除类型","删除失败！");
				}
				
			}
			function failure(rep){
				Ext.Msg.alert("删除类型","删除失败！");
				Ext.Msg.alert("删除类型",rep.responseText);
			}
		},
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
			
		}
	};
}();
Ext.onReady(App.init);