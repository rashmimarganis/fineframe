function sall(){
	var the = document.getElementById('roleMenuTree').getElementsByTagName ( "input" );
	for( var i = 0 ; i < the.length ; i++ ){
		if( the[ i ].type == "checkbox")
		{
			the[ i ].checked = true ;
		}
	}
}
function stAll( sID ){
	var the = document.getElementById('roleMenuTree').getElementsByTagName ( "input" );
	for( var i = 0 ; i < the.length ; i++ ){
		var ch=the[ i ];
		if( ch.type == "checkbox" &&ch.checked==true){
			ch.checked = false ;
		}
	}
}
function getSelected(){
	var the = Ext.getDom('roleMenuTree').getElementsByTagName ( "input" );
	var ids='';
	var patrn=/^[0-9]+$/; 
	for( var i = 1 ; i < the.length ; i++ ){
		var ch=the[ i ];
		if( ch.type == "checkbox" &&ch.checked==true){
			var s=ch.value;
			if (patrn.exec(s)){
				if(ids==''){
					ids="functionIds="+s;
				}else{
					ids+="&functionIds="+s;
				}
			}
		}
	}
	return ids;
}
var App= function(){
	var tree;
	var viewport;
	var Tree = Ext.tree;
	var selectNode;
	var ope_edit=0;
	var ope_add=1;
	var operation=ope_add;
	var dlg;
	var store;
    var xg = Ext.grid;
	var grid;
	var sm;
	var resourceSm;
	var id;
	var name;
	var title;
	var org_id=0;
	var oldName;
	var form;
	var pageSize=18;
	var pageSize1=20;
	var menuTreeDlg;
	var roleMenuTree;
	var roleTreeLoader;
	var menuTreePanel;
	return {
		init:function(){
			Ext.QuickTips.init();
			App.initForm();
			App.initGridPanel();
			App.initTree();
			App.initLayout();
			
		    Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({remove:true});
		}
		,
		initLayout:function(){
	     	viewport = new Ext.Viewport({
	          layout:'border',
	          items:[
	              {
				  	title:'岗位列表',
					el:'center',
					contentEl:'grid',
					id:'role_grid',
				  	el:'center',
				  	region:'center',
					layout:'fit',
					margins:'0 0 0 0',
					items:[grid]
				  },tree
	           ]
	        });
    	},
		initTree:function(){
		    tree = new Tree.TreePanel({
				id:'tree',
		        el:'west',
				region:'west',
		        autoScroll:true,
		        animate:true,
		        enableDD:true,
				split:true,
				collapsible: false,
				border:true,
				title:'组织结构',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
		        containerScroll: true, 
		        loader: new Tree.TreeLoader({
		            dataUrl:'../org/tree.do'
		        })
		    });
			
			tree.getSelectionModel().on({
		        'selectionchange' : function(sm, node){
					selectNode=node;
					org_id=node.id;
					if(node.id!=0){
						if(!node.expanded){
							node.expand();
						};
					}
					store.proxy=new Ext.data.HttpProxy({
			            url: '../role/page.do?obj.org.id='+node.id
			        });
					store.load({params:{start:0,limit:pageSize}});
		        },
		        scope:this
		    });
		    var root = new Tree.AsyncTreeNode({
		        text: '当前组织',
		        draggable:false,
		        id:'0'
		    });
		    tree.setRootNode(root);
		    tree.render();
		    root.expand();
			root.select();
			selectNode=root;
		}
		,
		
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../role/page.do?obj.org.id='+org_id
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id','name', 'title','orgTitle'
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
			
			sm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'id',
	           header: "ID",
	           dataIndex: 'id',
	           width: 20
	        },{
	           id: 'name', 
	           header: "编号",
	           dataIndex: 'name',
	           width: 100
	        },{
	           header: "名称",
	           dataIndex: 'title',
	           width: 100
	        },{
				header:"所属组织",
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
				margins:'0 0 0 0',
				viewConfig: {
		            forceFit:true
		        },
				renderTo:'grid',
				tbar:[{
				  	text: '新增岗位',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑岗位',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '删除岗位',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteInfo
				 }],
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        })
		    });
			grid.on('rowdblclick',App.edit);
		    grid.render();
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
		,
		initForm:function(){
			id=new Ext.form.Hidden({id:'id',name:'obj.id',value:'0'});
			oldName=new Ext.form.Hidden({id:'oldName',name:'oldName',value:'0'});
			name=new Ext.form.TextField({id:'name',name:'obj.name',value:'',anchor: '100%',fieldLabel:'编号',maxLength:'32',minLength:'2',maxLengthText:'编号长度不能超过32',minLengthText:'编号不能为空！'});
			title=new Ext.form.TextField({id:'title',name:'obj.title',value:'',anchor: '100%',fieldLabel:'名称',maxLength:'50',minLength:'2',maxLengthText:'名称长度不能超过50',minLengthText:'名称长度不能小于2！'});
			orgId=new Ext.form.Hidden({id:'orgId',name:'obj.org.id',value:'0',anchor: '100%'});
			form = new Ext.form.FormPanel({
		        baseCls: 'x-plain',
				id:'form1',
		        labelWidth: 35,
		        url:'../role/save.do',
		        defaultType: 'textfield',
		        items: [id,oldName,name,title,orgId]
		    });
		}
		,
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					title: '岗位信息',
	                width:280,
	                height:140,
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
		},
		add:function(){
			operation=ope_add;
			id.setValue('0');
			oldName.setValue('');
			title.setValue('');
			name.setValue('');
			orgId.setValue(selectNode.id);
			this.showDlg();
		},
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				alert("请先选择一个岗位！");
				return;
			}
			operation=ope_edit;
			Ext.Ajax.request({
				url:'../role/load.do',
				params:'obj.id='+select.get('id'),
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var obj="+rep.responseText);
				id.setValue(obj.id);
				oldName.setValue(obj.name);
				name.setValue(obj.name);
				title.setValue(obj.title);
				orgId.setValue(obj.orgId);
				App.showDlg();
			}
			function failure(rep){
				Ext.Msg.alert("加载失败",rep.responseText);
			}
		},
		saveInfo:function(){
			if(name.getValue()==''){
				Ext.Msg.alert("保存","请填写编号！&nbsp;&nbsp;&nbsp;");
				return;
			}
			if(title.getValue()==''){
				Ext.Msg.alert("保存","请填写标题！");
				return;
			}
	
			Ext.Ajax.request({
				url:'../role/save.do',
				params:'obj.id='+id.getValue()+"&obj.name="+name.getValue()+"&oldName="+oldName.getValue()+"&obj.title="+title.getValue()+"&obj.org.id="+selectNode.id,
				success:success,
				failure:failure
			});
			function success(rep){
				var ss=rep.responseText;
				if(ss=='-1'){
					Ext.Msg.alert("保存岗位","编号已经存在，请重新填写！");
					name.focus();
					return;
				}
				if(operation==ope_edit){
					var select=sm.getSelected();
					select.set('name',name.getValue());
					select.set('title',title.getValue());
					select.commit();
					dlg.hide();
				}else{
					var lastO=store.lastOptions.params;
					var start=lastO.start;
					dlg.hide();
					store.load({params:{start:start, limit:pageSize}});
				}
				Ext.Msg.alert("保存岗位","保存成功！&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			function failure(rep){
				Ext.Msg.alert("保存岗位",rep.reponseText);
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除","请先选择一个岗位！");
				return;
			}
			if(!confirm("确定要删除选中的岗位吗？")){
				return;
			}
			
			var url='../role/delete.do?obj.org.id='+org_id+'&ids='+App.getSelectedIds();
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
					Ext.Msg.alert("删除岗位","删除成功！");
				}else{
					Ext.Msg.alert("删除岗位","删除失败！");
					Ext.Msg.alert("异常信息",rep.responseText);
				}
			}
			function failure(rep){
				Ext.Msg.alert("删除岗位","删除失败！");
				Ext.Msg.alert("删除岗位",rep.responseText);
			}
		}
	};
}();
Ext.onReady(App.init);