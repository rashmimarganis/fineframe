function formatGender(v){
	if(v=='f'){
		return '女';
	}else if(v=='m'){
		return '男';
	}else {
		return '';
	}
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
	var roleStore;
    var xg = Ext.grid;
	var grid;
	var roleGrid;
	var sm;
	var roleSm;
	var userId;
	var roleDlg;
	
	var orgId;
	var orgName;
	var oldName;
	var form;
	var pageSize=18;
	var pageSize1=20;
	var resourceDlg;
	var orgDs;
	var selectNode;
	var orgTree;
	var roleOrgTree;
	var dlgOrg;
	var orgNode;
	var personDlg;
	var personStore;
	var personSm;
	var personGrid;
	
	return {
		init:function(){
			App.initGridPanel();
			App.initTree();
			
			App.initLayout();
		    Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({remove:true});
	        /*document.getElementById('username').onchange = function(){
	        	var username = Ext.getDom('username').value;
	        	Ext.getDom('email').value = username + '@sdaic.gov.cn';
	        }*/
		}
		,
		initLayout:function(){
			var personBtn=new Ext.Button({id:'selectPerson',text:'选择成员',applyTo:'selectPerson'});
			personBtn.on('click',function(){
					App.showPersonDlg();
			});
	     	viewport = new Ext.Viewport({
	          layout:'border',
	          items:[{
				  	title:'用户列表',
					contentEl:'grid',
				  	el:'center',
				  	region:'center',
					layout:'fit',
					margins:'0 0 0 0',
					items:grid
				  },tree]
	        });
			store.load({params:{start:0,limit:pageSize}});
    	},
		initTree:function(){
		    tree = new Tree.TreePanel({
		        el:'west',
				region:'west',
				title:'组织机构',
		        autoScroll:true,
		        animate:true,
				split:true,
		        enableDD:true,
				collapsible: true,
				border:true,
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
		        containerScroll: true, 
		        loader: new Tree.TreeLoader({
		            dataUrl:'../org/tree.do'
		        })
		    });
			tree.on({
		        'beforeclick' : function(node,e){
					selectNode=node;
					if(!node.expanded){
						node.expand();
					};
					store.proxy= new Ext.data.HttpProxy({url: '../user/page.do?orgId='+node.id});
					store.load({params:{start:0,limit:pageSize}});
					
		        },
		        scope:this
		    });
		    
			var root=new Tree.AsyncTreeNode({
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
		initOrgTree:function(){
			if(!orgTree){
			    orgTree = new Tree.TreePanel({
			        el:'orgTree',
			        autoScroll:true,
					region:'west',
			        animate:true,
			        enableDD:true,
					rootVisible:true,
			        containerScroll: true, 
			        loader: new Tree.TreeLoader({
			            dataUrl:'../org/tree.do'
			        })
			    });
				orgTree.on({
			        'beforeclick' : function(node,e){
						if(!node.expanded){
							node.expand();
						};
						personStore.proxy= new Ext.data.HttpProxy({url: '../person/page.do?obj.org.id='+node.id});
						personStore.load({params:{start:0,limit:pageSize}});
						
			        },
			        scope:this
			    });
			    var root = new Tree.AsyncTreeNode({
			        text: '当前组织',
			        draggable:false,
			        id:'0'
			    });
			    orgTree.setRootNode(root);
			    orgTree.render();
				root.expand();
				root.select();
		    }
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../user/page.do'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id','username','email',
						 'lastLoginTime',
						 'lastLoginIp','loginTimes',
						'enabled',
						'nonExpired','nonLocked','credentialsNonExpired','orgTitle',
						'personId',
						'realname'
						
		            ]
		        }),
		        remoteSort: true
		    });
		    store.setDefaultSort('id', 'asc');
			store.on('load',function(s,r,o){
				if(s.getTotalCount()>0){
					//sm.selectFirstRow();
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
				var r=year+'-'+month+'-'+date+' '+hours+':'+minutes+':'+seconds;
				if(r=='1899-11-30 0:0:0'){
					return '';
				}
				return r;
				
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
	           width: 20
	        },{
	           id: 'username', 
	           header: "用户名",
	           dataIndex: 'username',
	           width: 100
	        },{
				header:"姓名",
				dataIndex:'realname',
				width:100,
				renderer:function(v){
					if(v==null){
						return "";
					}else{
						return v;
					}
				}
			},{
	           header: "电子邮件",
	           dataIndex: 'email',
	           width: 130
	        },{
				header:"所属组织",
				dataIndex:'person.org.title',
				width:100
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
	           header: "可用",
	           dataIndex: 'enabled',
	           width: 70,
			   align:'center',
			   renderer:rendererEnabled
	        },{
	           header: "锁定",
	           dataIndex: 'nonLocked',
	           width: 70,
			   align:'center',
			   renderer:rendererNon
	        },{
	           header: "过期",
	           dataIndex: 'nonExpired',
	           width: 70,
			   align:'center',
			   renderer:rendererNon
	        },{
	           header: "可信",
	           dataIndex: 'credentialsNonExpired',
	           width: 70,
			   align:'center',
			   renderer:rendererNon
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
				  	text: '新增用户',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑用户',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.editInfo
				  },'-',{
				  	text: '删除用户',
		            iconCls: 'x-btn-text-icon delete',
		            scope: this,
					handler:App.deleteInfo
				 },'-',{
				 	text: '分配岗位',
		            iconCls: 'x-btn-text-icon resource-config',
		            scope: this,
					handler:App.showRoleDlg
				}],
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize,
		            store: store,
		            displayInfo: true
		        })
		    });
			grid.on('rowdblclick',App.editInfo);
		    grid.render();
			
		},
		initRoleStore:function(){
			var userId=sm.getSelected().get('id');
			
			if(!roleStore){
				roleStore = new Ext.data.Store({
			        proxy: new Ext.data.HttpProxy({
			            url: '../role/findUserRoles.do?userId='+userId+'&orgId=0'
			        }),
			        reader: new Ext.data.JsonReader({
			            root: 'objs',
			            totalProperty: 'totalCount',
			            id: 'id',
			            fields: [
			                'id','name', 'title','right'
			            ]
			        }),
			        remoteSort: true
			    });
			    roleStore.setDefaultSort('id', 'asc');
			}else{
				var orgId=selectNode.id;
				roleStore.proxy.url='../role/findUserRoles.do?userId='+userId;
			}
		},
		initPersonStore:function(){
			personStore = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'person/page.do'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id','realname','gender'
		            ]
		        }),
		        remoteSort: true
		    });
		    personStore.setDefaultSort('id', 'asc');
		},
		initRoleGridPanel:function(){
			App.initRoleStore();
			roleSm = new xg.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([roleSm,{
	           id: 'id',
	           header: "ID",
	           dataIndex: 'id',
	           width: 20,
			   hidden:true
	        },{
	           header: "名称",
	           dataIndex: 'title',
	           width: 150
	        },{
				header:"已授权",
				dataIndex:'right',
				renderer:function(v){
					if(v){
						return '是';
					}
					return '否';
					
				},
				width:50
			},{
				header:"主要职位",
				dataIndex:'right',
				renderer:function(v){
					if(v){
						return '是';
					}
					return '否';
					
				},
				width:50
			},{
				header:"是否领导",
				dataIndex:'right',
				renderer:function(v){
					if(v){
						return '是';
					}
					return '否';
					
				},
				width:50
			}]);
	
		    cm.defaultSortable = true;
			
		    roleGrid = new Ext.grid.GridPanel({
		        store: roleStore,
		        cm: cm,
				sm: roleSm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				margins:'0 0 0 0',
				viewConfig: {
		            forceFit:true
		        },
				renderTo:'roleGrid'
				,
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize1,
		            store: store,
		            displayInfo: true
		        })
		    });
		    roleGrid.render();
		},
		initPersonGridPanel:function(){
			App.initPersonStore();
			personSm = new xg.CheckboxSelectionModel();
			personSm.on("beforerowselect",function(o){
				o.clearSelections();
			});
			var cm = new Ext.grid.ColumnModel([personSm,{
	           id: 'id',
	           header: "ID",
	           dataIndex: 'id',
	           width: 20,
			   hidden:true
	        },{
	           id: 'realname', 
	           header: "姓名",
	           dataIndex: 'realname',
	           width: 100
	        },{
	           header: "性别",
	           dataIndex: 'gender',
	           width: 150
	        }]);
	
		    cm.defaultSortable = true;
			
		    personGrid = new Ext.grid.GridPanel({
				id:'personGrid',
		        store: personStore,
				region:'center',
		        cm: cm,
				sm: personSm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				margins:'0 0 0 0',
				viewConfig: {
		            forceFit:true
		        },
				renderTo:'personGrid'
				,
				 bbar: new Ext.PagingToolbar({
		            pageSize: pageSize1,
		            store: personStore,
		            displayInfo: true
		        })
		    });
			personGrid.on('rowdblclick',App.selectPerson);
		    personGrid.render();
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
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					contentEl:'user-center',
					title: '用户信息',
	                width:400,
	                height:300,
	                closeAction:'hide',
	                plain: true,
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
		},
		add:function(){
			operation=ope_add;
			this.showDlg();
			Ext.getDom("id").value='0';
			Ext.getDom("username").value='';
			Ext.getDom("oldName").value='';
			Ext.getDom("email").value='';
			Ext.getDom("password").value='';
			Ext.getDom("repassword").value='';
			Ext.getDom("enabled").checked=true;
			Ext.getDom("nonLocked").checked=false;
			Ext.getDom("nonExpired").checked=false;
			Ext.getDom("credentialsNonExpired").checked=false;
			Ext.getDom("obj.enabled").value=1;
			Ext.getDom("obj.nonLocked").value=0;
			Ext.getDom("obj.nonExpired").value=0;
			Ext.getDom("obj.credentialsNonExpired").value=0;
			Ext.getDom("personId").value='0';
			Ext.getDom("personRealname").value='';
		},
		editInfo:function(){
			var select=sm.getSelected();
			if(select==null){
				alert("请先选择一个用户！");
				return;
			}
			
			operation=ope_edit;	
			var id=select.get('id');
			Ext.Ajax.request({
				method:'post',
				url:'../user/load.do',
				params:'obj.id='+id,
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var obj="+rep.responseText);
				Ext.getDom("id").value=obj.id;
				Ext.getDom("username").value=obj.username;
				Ext.getDom("oldName").value=obj.username;
				Ext.getDom("email").value=obj.email;
				Ext.getDom("password").value="";
				Ext.getDom("repassword").value="";
				App.showDlg();
				if(obj.enabled){
					Ext.getDom("enabled").checked=true;
					Ext.getDom("obj.enabled").value=true;
				}else{
					Ext.getDom("enabled").checked=false;
					Ext.getDom("obj.enabled").value=false;
					
				}
				if(obj.nonLocked){
					Ext.getDom("nonLocked").checked=false;
					Ext.getDom("obj.nonLocked").value=true;
				}else{
					Ext.getDom("nonLocked").checked=true;
					Ext.getDom("obj.nonLocked").value=false;
				}
				
				if(obj.nonExpired){
					Ext.getDom("nonExpired").checked=false;
					Ext.getDom("obj.nonExpired").value=true;
				}else{
					Ext.getDom("nonExpired").checked=true;
					Ext.getDom("obj.nonExpired").value=false;
					
				}
				if(obj.credentialsNonExpired){
					Ext.getDom("credentialsNonExpired").checked=false;
					Ext.getDom("obj.credentialsNonExpired").value=true;
				}else{
					Ext.getDom("credentialsNonExpired").checked=true;
					Ext.getDom("obj.credentialsNonExpired").value=false;
					
				}
				Ext.getDom("personId").value=obj.personId;
				Ext.getDom("personRealname").value=obj.realname;
				
			}
			function failure(rep){
				Ext.Msg.alert('保存',"保存失败！&nbsp;&nbsp;&nbsp;&nbsp;");
				Ext.Msg.alert('保存失败',rep.responseText);
			}
		},
		saveInfo:function(){
			if(Ext.getDom("username").value==''){
				Ext.Msg.alert('保存',"请用户编号！&nbsp;&nbsp;&nbsp;&nbsp;");
				Ext.getDom("username").focus();
				return;
			}
			
			if(Ext.getDom("id").value==0){
				var passwd=Ext.getDom("password");
				var repasswd=Ext.getDom("repassword");
				if(passwd.value==''){
					Ext.Msg.alert('保存',"请输入密码！");
					passwd.focus();
					return;
				}
				if(repasswd.value!=passwd.value){
					Ext.Msg.alert('保存',"两次输入的密码不一样，请重新输入！");
					passwd.focus();
					return;
				}
			}
			if(Ext.getDom("email").value==''){
				Ext.Msg.alert('保存',"请填写电子邮件！");
				Ext.getDom("email").focus();
				return;
			}
			if(Ext.getDom("personId").value=='0'){
				Ext.Msg.alert('保存',"请选择一个人员！");
				return;
			}

			if(Ext.getDom("enabled").checked){
				Ext.getDom("obj.enabled").value=true;
			}else{
				Ext.getDom("obj.enabled").value=false;
				
			}
			
			if(Ext.getDom("nonLocked").checked){
				Ext.getDom("obj.nonLocked").value=false;
				
			}else{
				Ext.getDom("obj.nonLocked").value=true;
			}
			
			if(Ext.getDom("nonExpired").checked){
				Ext.getDom("obj.nonExpired").value=false;
			}else{
				Ext.getDom("obj.nonExpired").value=true;
				
			}
			
			if(Ext.getDom("credentialsNonExpired").checked){
				Ext.getDom("obj.credentialsNonExpired").value=false;
			}else{
				Ext.getDom("obj.credentialsNonExpired").value=true;
				
			}
			Ext.Ajax.request({
				form:'userForm',
				method:'post',
				url:'../user/save.do',
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var ss="+rep.responseText);
				var result=ss.result;
				if(result==0){
					Ext.Msg.alert('保存',"用户名已经存在，请重新填写！");
					Ext.getDom("username").focus();
					return;
				}
				if(result==-1){
					Ext.Msg.alert('保存',"两次输入密码不一样，请重新输入！");
					Ext.getDom("password").focus();
					return;
				}
			
				dlg.hide();
				var lastO=store.lastOptions.params;
				var start=lastO.start;
				store.load({params:{sort:lastO.sort,dir:lastO.dir,start:start, limit:pageSize}});
				
				Ext.Msg.alert('保存',"保存成功！&nbsp;&nbsp;&nbsp;&nbsp;");
				//App.add();
			}
			function failure(rep){
				Ext.Msg.alert('删除',rep.responseText);
			}
			
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert('删除',"请先选择一个用户！");
				return;
			}
			if(!confirm("确定要删除选中的用户吗？")){
				return;
			}
			Ext.Ajax.request({
				url:'../user/delete.do',
				params:'ids='+App.getSelectedIds()+"&orgId="+selectNode.id,
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
					Ext.Msg.alert('删除',"删除成功！&nbsp;&nbsp;&nbsp;&nbsp;");
				}else{
					Ext.Msg.alert('删除',"删除失败！&nbsp;&nbsp;&nbsp;&nbsp;");
				}
			}
			function failure(rep){
				Ext.Msg.alert('删除',"删除失败！&nbsp;&nbsp;&nbsp;&nbsp;");
				Ext.Msg.alert('删除',rep.responseText);
			}
		},
		getRoleIds:function(ope){
			var ids='';
			var selections=roleSm.getSelections();
			var size=selections.length;
			if(ope=='save'){
				for(var i=0;i<size;i++){
						var r=selections[i];
						if(r.get('right')=='0'){
							if(ids==''){
								ids='roleIds='+r.get("id");
							}else{
								ids+="&roleIds="+r.get("id");
							}
						}
				}
			}else{
				for(var i=0;i<size;i++){
						var r=selections[i];
						if(r.get('right')=='1'){
							if(ids==''){
								ids='roleIds='+r.get("id");
							}else{
								ids+="&roleIds="+r.get("id");
							}
						}
				}
			}
			return ids;
		},
		initRoleOrgTree:function(){
			if(!roleOrgTree){
			roleOrgTree = new Tree.TreePanel({
		        el:'roleOrgTree',
		        autoScroll:true,
		        animate:true,
		        enableDD:true,
				collapsible: true,
				border:true,
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
		        containerScroll: true, 
		        loader: new Tree.TreeLoader({
		            dataUrl:'../org/tree.do'
		        })
				
		    });
			roleOrgTree.on({
		        'beforeclick' : function(node,e){
					if(!node.expanded){
						node.expand();
					};
					var userId=sm.getSelected().get("id");
					roleStore.proxy= new Ext.data.HttpProxy({url: '../role/findUserRoles.do?userId='+userId+'&orgId='+node.id});
					roleStore.load({params:{start:0,limit:pageSize}});
					
		        },
		        scope:this
		    });
		    
			var root=new Tree.AsyncTreeNode({
		        text: '当前组织',
		        draggable:false,
		        id:'0'
		    });
			roleOrgTree.setRootNode(root);
			roleOrgTree.render();
			
			root.expand();
			root.select();
			}
		},
		/*显示组织对应的所有岗位*/
		showRoleDlg:function(){
			if(!roleDlg){
				App.initRoleGridPanel();
				App.initRoleOrgTree();
				var gridPanel=new Ext.Panel({
					el:'roleGridPanel',
					region:'center',
					contentEl:'roleGrid',
					layout:'fit',
					items:roleGrid,
					maxWidth:350,
					minWidth:150,
					split:true,
					collapsible:false
				});
				var treePanel=new Ext.Panel({
					el:'roleOrg',
					contentEl:'roleOrgTree',
					region:'west',
					width:150,
					layout:'fit',
					items:roleOrgTree,
					title:'组织结构',
					split:true,
					collapsible:false
				});
				roleDlg=new Ext.Window({
					el:'roleDlg',
	                layout:'border',
					title: '分配用户岗位',
	                width:550,
	                height:375,
	                closeAction:'hide',
	                plain: true,
					items:[treePanel,gridPanel],
					modal:true,
					bodyStyle:'padding:5px;',
					buttons: [{
	                    text:'分配岗位',
						handler:App.saveRole
	                },{
	                    text: '取消分配',
	                    handler: App.deleteRole
	                },{
	                    text: '关闭窗口',
	                    handler: function(){
	                        roleDlg.hide();
							roleSm.clearSelections();
	                    }
	                }]
				});
			}
			var userId=sm.getSelected().get('id');
			var orgId=roleOrgTree.getSelectionModel().getSelectedNode().id;
			var url='../role/findUserRoles.do?userId='+userId+'&orgId='+orgId;
			roleStore.proxy= new Ext.data.HttpProxy({url: url});
			roleStore.load({params:{start:0,limit:pageSize1}});
			roleDlg.show(this);
			
		},
		showPersonDlg:function(){
			if(!personDlg){
				App.initOrgTree();
				App.initPersonGridPanel();
				personDlg=new Ext.Window({
					el:'personDlg',
	                layout:'border',
					title: '选择关联人员',
	                width:600,
	                height:400,
	                closeAction:'hide',
	                plain: true,
					items:[{
				  	title:'人员列表',
						contentEl:'personGrid',
					  	el:'person',
					  	region:'center',
						layout:'fit',
						margins:'0 0 0 0',
						items:personGrid
					  }	,{
				  	title:'组织结构',
						contentEl:'orgTree',
					  	el:'org',
					  	region:'west',
						width:150,
						split:true,
						layout:'fit',
						margins:'0 0 0 0',
						items:orgTree
					  }],
					modal:true,
					bodyStyle:'padding:5px;',
					buttons: [{
	                    text:'选择',
						handler:App.selectPerson
	                },{
	                    text: '取消',
	                    handler: function(){
	                        personDlg.hide();
							personSm.clearSelections();
	                    }
	                }]
				});
				
			}
			personDlg.show(this);
			personStore.load({params:{start:0,limit:pageSize1}});
		},
		selectPerson:function(){
			var sel=personSm.getSelected();
			if(sel==null){
				Ext.Msg.alert("选择成员","请选择一个成员！");
				return;
			}else{
				Ext.getDom("personId").value=sel.get("id");
				Ext.getDom("personRealname").value=sel.get("realname");
				personDlg.hide();
			}
		}
		,
		saveRole:function(){
			if(roleSm.getSelected==null){
				Ext.Msg.alert("选择岗位","请先选择岗位！");
				return;
			}
			var ids=App.getRoleIds('save');
			if(ids==''){
				Ext.Msg.alert("选择岗位",'您所选的岗位已经分配，请至少选择一个没有分配的岗位！');
				return;
			}
			Ext.Ajax.request({
				url:'../role/saveUsersRoles.do',
				//params:ids+"&userId="+sm.getSelected().get("id"),
				params:ids+"&userIds="+App.getSelectedIds(),
				success:success,
				failure:failure,
				method:'post'
			});
			function success(rep){
				var lastO= roleStore.lastOptions.params;
				var start=lastO.start;
				roleStore.load({params:{start:start, limit:pageSize1}});
				roleSm.clearSelections();
				Ext.Msg.alert("分配岗位","岗位分配成功！");
			
			}
			function failure(rep){
				Ext.Msg.alert("分配岗位","岗位分配失败！");
				Ext.Msg.alert("选择岗位",rep.responseText);
			}
		}
		,
		deleteRole:function(){
			if(roleSm.getSelected==null){
				Ext.Msg.alert("取消岗位","请先选择岗位！");
				return;
			}
			var ids=App.getRoleIds('delete');
			if(ids==''){
				Ext.Msg.alert("取消岗位",'您所选的岗位没有分配给用户，请至少选择一个已经分配给用户的岗位！');
				return;
			}
			Ext.Ajax.request({
				url:'../role/deleteUserRoles.do',
				params:ids+"&userId="+sm.getSelected().get("id"),
				success:success,
				failure:failure,
				method:'post'
			});
			function success(rep){
				var lastO= roleStore.lastOptions.params;
				var start=lastO.start;
				roleStore.load({params:{start:start, limit:pageSize1}});
				roleSm.clearSelections();
				Ext.Msg.alert("取消岗位","取消分配成功！");
				
			}
			function failure(rep){
				Ext.Msg.alert("取消岗位","取消分配失败！");
				Ext.Msg.alert("取消岗位",rep.responseText);
			}
		}
		
		
	};
}();
Ext.onReady(App.init);