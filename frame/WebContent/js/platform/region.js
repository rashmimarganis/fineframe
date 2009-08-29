var App= function(){
	var westPanel;
	var centerPanel;
	var viewport;
	var Tree = Ext.tree;
	var level=1;
	var level_tj=1;
	var level_xj=2;
	var selectNode;
	var edit=1;
	var noteEdit=0;
	var nameEdit=0;
	return {
		init:function(){
			//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		    App.initTree();
			App.initLayout();
			setTimeout(function(){
		        Ext.get('loading').remove();
		        Ext.get('loading-mask').fadeOut({remove:true});
				Ext.get("type").show();
		    }, 350);
			
		},
		initLayout:function(){
			
		     centerPanel=new Ext.Panel(
	     	 	{
                  region:'center',
				  split:true,
				  collapsible:true,
                  title:'位置信息',
                  contentEl:'center',
                  border:true,
                  autoScroll:true,
				  tbar:[{
				  	text: '增加同级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler: function(){
						App.addTj();
					}
				  },{
				  	text: '增加下级',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler: function(){
						App.addXj();
					}
				  },{
				  	text: '删除',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler: function(){
						App.deleteInfo();
					}
				  },{
				  	text: '保存',
		            iconCls: 'x-btn-text-icon save',
		            scope: this,
					handler: function(){
						App.saveInfo();
					}
				  }]
             	}
		     );
	     	viewport = new Ext.Viewport({
	          layout:'border',
	          items:[
	              westPanel,centerPanel
	           ]
	        });
    	},
		initTree:function(){
		    westPanel = new Tree.TreePanel({
		        el:'west',
				region:'west',
		        autoScroll:true,
		        animate:true,
		        enableDD:true,
				split:true,
				border:true,
				title:'区域管理',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:false,
		        containerScroll: true, 
		        loader: new Tree.TreeLoader({
		            dataUrl:'../region/tree.do'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'beforeselect' : function(sm, node){
					selectNode=node;
					if(node.id!='0'){
						if(!node.expanded){
							node.expand()
						};
		            	App.loadInfo( node);
					}
		        },
		        'selectionchange' : function(sm, node){
		            //selectNode=node;
		        },
		        scope:this
		    });
		    var root = new Tree.AsyncTreeNode({
		        text: '全部区域',
		        draggable:false,
		        id:'0'
		    });
		    westPanel.setRootNode(root);
		    westPanel.render();
			
			var expand=function(n){
				if(n.firstChild!=null){
					n.firstChild.select();
				}
			};
			root.on('expand',expand);
		    root.expand();
			root.select();
			selectNode=root;
		},
		addTj:function(){
			var depth=selectNode.getDepth();
			if(depth==0){
				alert("请选择一个地理位置！");
				return;
			}
			
			level=level_tj;
			App.newObj(level);
		}
		,
		addXj:function(){
			var depth=selectNode.getDepth();
			if(depth==4){
				alert("已经到最后一级！");
				return;
			}
			if(Ext.getDom("id").value==""){
				selectNode.select();
			}
			level=level_xj;
			App.newObj(level);
		},
		newObj:function(l){
			var depth=selectNode.getDepth();
			if(l==level_xj){
				Ext.getDom("type").value=depth*1-1;
				Ext.getDom("parentName").value=selectNode.text;
				Ext.getDom("parentId").value=selectNode.id;
			}else{
				Ext.getDom("type").value=depth*1-2;
				Ext.getDom("parentName").value=selectNode.parentNode.text;
				Ext.getDom("parentId").value=selectNode.parentNode.id;
			}
			Ext.getDom("id").value=0;
			Ext.getDom("name").value="";
			Ext.getDom("code").value="";
			
		}
		,
		loadInfo:function(node){
			Ext.Ajax.request({
				url:'../region/load.do',
				params:'obj.id='+node.id,
				success:success,
				failure:failure
			});
			function success(rep){
				//alert(rep);
				eval("var info="+rep.responseText);
				Ext.getDom("id").value=node.id;
				Ext.getDom("code").value=info.code;
				Ext.getDom("name").value=info.name;
				Ext.getDom("type").value=info.type;
				Ext.getDom("oldCode").value=info.code;
				if(info.parent!=null && node.getDepth() > 2){
					Ext.getDom("parentName").value=info.parent.name;
					Ext.getDom("parentId").value=info.parent.id;
				}else{
					Ext.getDom("parentName").value="";
					Ext.getDom("parentId").value="0";
				}
			}
			function failure(rep){
				Ext.Msg.alert("出现错误",rep.responseText);
			}
		},
		saveInfo:function(){
			
			var name=Ext.getDom("code").value;
			var oldName=Ext.getDom("oldCode").value;
			var title=Ext.getDom("name").value;
			if(name==""){
				Ext.Msg.alert('新增区域',"请输入编号！");
				Ext.getDom("name").focus();
				return;
			}
			if(title==""){
				Ext.Msg.alert('新增区域',"请输入名称！");
				Ext.getDom("name").focus();
				return;
			}
			Ext.getDom("type").disabled=false;
			var url='../region/save.do';
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
					Ext.Msg.alert("新增区域","编号已经存在，请重新输入！");
					Ext.getDom("code").focus();
					return;
				}
				var id=obj.id;
				var text=obj.name;
				var node = new Ext.tree.TreeNode({id:id,text:text,cls:'folder',leaf:false});
				var id_=Ext.getDom("id").value;
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
				Ext.getDom("type").disabled=true;
			}
			function failure(rep){
				
			}
		},
		deleteInfo:function(){
			if(selectNode.firstChild!=null){
				Ext.Msg.alert('删除区域',"该区域含有下级区域，请先删除下级区域！");
				return;
			}
			Ext.Msg.confirm("删除区域","确定要删除该区域吗？",function(o){
				if(o=='yes'){
					Ext.Ajax.request({
						url:'../region/delete.do',
						params:'obj.id='+selectNode.id,
						success:success,
						failure:failure
					});
				}else{
					return;
				}
			});
			
			//var url='region/delete.do?id='+selectNode.id;
			//YAHOO.util.Connect.asyncRequest('GET',url,{success:success,failure:failure});
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
				Ext.Msg.alert("删除区域","删除成功！");
			}
			function failure(rep){
				Ext.Msg.alert("删除区域",rep.responseText);
			}
		}
	};
}();
Ext.onReady(App.init);