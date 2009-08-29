var OrgFormPanel = function() {
	this.idField = {
		xtype : 'hidden',
		fieldLabel : "id",
		name : "obj.orgId",
		value:'0'
	};
	this.nameField = {
		xtype : 'textfield',
		fieldLabel : "英文名称",
		allowBlank : false,
		name : "obj.orgName"
	};
	this.titleField = {
		xtype : 'textfield',
		fieldLabel : "中文名称",
		allowBlank : false,
		name : "obj.title"
	};
	this.sortField = {
		xtype : 'textfield',
		fieldLabel : "排序",
		allowBlank : false,
		name : "obj.sort"
	};

	var typeStore=new Ext.data.SimpleStore({
        fields: ['name', 'title','tip'],
        data : Ext.ux.OrgType 
    });
    this.typeField = new Ext.form.ComboBox({
        store:typeStore,
        displayField:'title',
        valueField:'name',
        fieldLabel: '组织类型',
        readOnly:true,
        typeAhead: true,
        allowBlank:false,
        mode: 'local',
        triggerAction: 'all',
        emptyText:'请选择类型...',
        selectOnFocus:true,
        hiddenName: 'obj.type'
    });
  
	
	OrgFormPanel.superclass.constructor.call(this, {
		bodyStyle : 'padding:2px 2px 0',
		frame : true,
		reader : new Ext.data.JsonReader( 
		{
			root : 'data',
			successProperty : 'success',
			totalProperty : 'totalCount',
			id : 'orgId'
		}, [
		    {name:'obj.orgId', mapping:'orgId'},
		    {name:'obj.orgName',mapping:'orgName'}, 
		    {name:'obj.title',mapping:'title'},
		    {name:'obj.type',mapping:'type'},
		    {name:'obj.sort',mapping:'sort'}
		    ]
		),
		items : [this.idField,this.nameField,this.titleField,this.typeField,this.sortField]
	});
};
Ext.extend(OrgFormPanel, Ext.form.FormPanel, {
	loadData : function(id) {
		var url = 'org/load.jhtm?id=' + id;
		this.getForm().load( {
			url : url,
			waitMsg : '正在加载数据....',
			success:function(form,action){
				alert(action.response.responseText);
			},
			failure : function(form, action) {
				var json = action.response.responseText;
				var o = eval("(" + json + ")");
				Ext.MessageBox.show( {
					title : '出现错误',
					msg : o.message,
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
});
var OrgApp= function(){
	var westPanel;
	var centerPanel;
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var formPanel=new OrgFormPanel();
	return {
		init:function(){
		    OrgApp.initTree();
			OrgApp.initLayout();
			
		},
		initLayout:function(){
		    centerPanel=new Ext.Panel(
	     	{
                  region:'center',
				  split:true,
				  layout:'fit',
				  collapsible:false,
                  title:'组织信息',
                  el:'orgFormPanel',
                  border:true,
                  autoScroll:true,
                  items:[formPanel],
				  tbar:[{
				  	text: '增加同级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:OrgApp.addTj
				  },'-',{
				  	text: '增加下级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:OrgApp.addXj
				  },'-',{
				  	text: '保存组织',
		            iconCls: 'x-btn-text-icon save',
		            scope: this,
					handler:OrgApp.saveInfo
				  },{
				  	text: '删除',
		            iconCls: 'delete',
					scope:this,
					handler: function(){
						OrgApp.deleteInfo();
					}
				  },'-',{
				  	text: '刷新',
		            iconCls: 'refresh',
		            scope: this,
					handler:function(){
						selectNode.reload();
					}
				  }]
             	}
		     );
		     var treePanel=new Ext.Panel({
		    	 layout:'fit',
		    	 el:'orgTreePanel',
		    	 region:'west',
		    	 width:200,
		    	 split:true,
		    	 items:[westPanel]
		     });
		     var panel=new Ext.Panel({
		    	 layout:'border',
		    	 items:[
			              treePanel,centerPanel
				           ]
		     });
		     FineCmsMain.addFunctionPanel(panel);
    	},
		initTree:function(){
		    westPanel = new Ext.tree.TreePanel({
				el:'orgTree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				split:true,
				border:true,
				title:'组织结构树',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
				containerScroll: true, 
				margins: '0 0 0 0',
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'org/tree.jhtm'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'beforeselect' : function(sm, node){
					selectNode=node;
					if(node.id!='0'){
						if(!node.expanded){
							node.expand();
						};
						formPanel.loadData(node.id);
					}
		        
		        },
				'selectionchange' : function(sm, node){
		        	/*
		        	if(node.id!=0){
		        		formPanel.loadData(node.id);
		        	}
		        	*/
		        },
		        scope:this
		    });
		    var root = new Ext.tree.AsyncTreeNode({
		        text: '当前组织',
		        draggable:false,
		        id:'0'
		    });
		    westPanel.setRootNode(root);
			
			westPanel.render();
			root.select();
			root.expand();
			selectNode=root;
			
		},
		addTj:function(){
			var depth=selectNode.getDepth();
			if(selectNode.id==0){
				Ext.Msg.alert("提示","对不起，您没有权限创建本级组织的同级组织！");
				return;
			}
			
			level=level_tj;
			OrgApp.newObj(level);
		}
		,
		addXj:function(){
			var depth=selectNode.getDepth();
			if(depth==4){
				Ext.Msg.alert("提示","已经到最后一级！");
				return;
			}
			if(Ext.getDom("id").value==""){
				selectNode.select();
			}
			level=level_xj;
			OrgApp.newObj(level);
		},
		newObj:function(l){
			var depth=selectNode.getDepth();
			
			if(l==level_xj){
				Ext.get("type").dom.value=1;
				Ext.get("parentName").dom.value=selectNode.text;
				Ext.get("parentId").dom.value=selectNode.id;
			}else{
				Ext.get("type").dom.value=1;
				Ext.get("parentName").dom.value=selectNode.parentNode.text;
				Ext.get("parentId").dom.value=selectNode.parentNode.id;
			}
			Ext.get("id").dom.value=0;
			Ext.get("name").dom.value="";
			Ext.get("title").dom.value="";
			Ext.get("oldName").dom.value="";
			Ext.get("sort").dom.value="0";
			Ext.get("fullTitle").dom.value="";
		}
		,
		loadInfo:function(node){
			Ext.Ajax.request({
				url:'../org/load.do',
				params:'obj.id='+node.id,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var info="+rep.responseText);
				Ext.get("id").dom.value=node.id;
				Ext.get("name").dom.value=info.name;
				Ext.get("title").dom.value=info.title;
				Ext.get("type").dom.value=info.type;
				Ext.get("oldName").dom.value=info.name;
				Ext.get("sort").dom.value=info.sort;
				Ext.get("fullTitle").dom.value=info.fullTitle;
				if(info.parent!=null && node.getDepth() > 1){
					Ext.get("parentName").dom.value=info.parent.title;
					Ext.get("parentId").dom.value=info.parent.id;
				}else{
					Ext.get("parentName").dom.value="";
					Ext.get("parentId").dom.value="0";
				}
			}
			function failure(rep){
				Ext.Msg.alert("加载出现错误",rep.responseText);
			}
		},
		saveInfo:function(){
			var name=Ext.get("name").dom.value;
			var oldName=Ext.get("oldName").dom.value;
			var title=Ext.get("title").dom.value;
			if(name==""){
				Ext.Msg.alert("保存验证","请输入编号！");
				Ext.get("name").focus();
				return;
			}
			if(title==""){
				Ext.Msg.alert("保存验证","请输入名称！");
				Ext.get("title").focus();
				return;
			}
			var url='../org/save.do';
			Ext.Ajax.request({
				form:'form1',
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				var exist=result.exist;
				var obj=result.obj;
			
				if(exist==true){
					Ext.Msg.alert("保存失败","编号已经存在，请重新输入！");
					Ext.get("name").focus();
					return;
				}
				var id=obj.id;
				var text=obj.title;
				var node = new Ext.tree.TreeNode({id:id,text:text,cls:'folder',leaf:false});
				var id_=Ext.get("id").dom.value;
				if(id_=='0'){
					if(level==level_tj){
						selectNode.parentNode.appendChild(node);
					}else{
						selectNode.appendChild(node);
					}
					selectNode=node;
					selectNode.select();
				}else{
					selectNode.setText(text);
				}
				Ext.Msg.alert("保存组织","保存成功！");
			}
			function failure(rep){
				Ext.Msg.alert("保存失败",rep.responseText);
			}
		},
		deleteInfo:function(){
			if(selectNode.firstChild!=null){
				alert("本组织含有下级组织，请先删除下级组织！");
				return;
			}
			if(!confirm("确定要删除该组织吗？")){
				return;
			}
			var url='../org/delete.do';
			Ext.Ajax.request({
				url:url,
				params:'obj.id='+selectNode.id,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var result="+rep.responseText);
				if(result.success){
					deleteNode=selectNode;
					selectNode=selectNode.parentNode;
					deleteNode.remove();
					if(selectNode.firstChild!=null){
						selectNode=selectNode.firstChild;	
					}
					selectNode.select();
				}
				Ext.Msg.alert("删除组织","删除成功！");
			}
			function failure(rep){
				Ext.Msg.alert("删除组织",rep.repsonseText);
			}
		}
	};
}();
OrgApp.init();