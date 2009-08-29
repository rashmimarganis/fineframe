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
	return year+'-'+month+'-'+date;
	
}
function renderBirth(v){
	var b=renderDate(v);
	if(b=='1899-11-30'){
		return "";
	}
	if (b.length > 10) {
		return b.substring(0, 10);
	}else{
		return b;
	}
}



function formatGender(v){
	if(v=='m'){
		return '男';
	}else if(v=='f'){
		return '女';
	}else{
		return '';
	}
	
}

function formatMarriage(v){
	if(v==0){
		return '未婚';
	}else if(v==1){
		return '已婚';
	}else if(v==2){
		return '保密';
	}else{
		return '';
	}
}
var App= function(){
	var viewport;
	var westPanel;
	var ope_edit=0;
	var ope_add=1;
	var operation=ope_add;
	var dlg;
	var store;
    var xg = Ext.grid;
	var orgDlg,orgTree;
	var regionDlg,regionTree;
	var grid;
	var sm;
	var birthday;
	var pageSize=18;
	var orgTree;
	var currentOrgId=0;
	return {
		init:function(){
			App.initStore();
			
			App.initLayout();
		    Ext.get('loading').remove();
	        Ext.get('loading-mask').fadeOut({remove:true});
			birthday=new Ext.form.DateField({
				width:140,
				name:'obj.birthday',
				format: "Y-m-d",
				allowBlank:true,
				applyTo:'birthday'
				
			});
			var selectRegionBtn=new Ext.Button( {
					renderTo :'selectRegionBtn',
					text: "...",
					handler: function(){
						App.showRegionDlg();
					}
			});
			var selectOrgBtn=new Ext.Button( {
					renderTo :'selectOrgBtn',
					text: "...",
					handler: function(){
						App.showOrgDlg();
					}
			});
			/*document.getElementById('realname').onchange = function() {
				// auto insert firstname and last name
				var realname = Ext.getDom("realname").value;
				Ext.getDom('lastname').value = realname.substring(0,1);
				if(realname.length > 3 )
				Ext.getDom('firstname').value = realname.substring(1);
				else 
				Ext.getDom('firstname').value = realname.substring(1);
			}*/
		}
		,
		initTree:function(){
		    westPanel = new Ext.tree.TreePanel({
		        el:'west',
				region:'west',
				contentEl:'tree',
		        autoScroll:true,
		        animate:true,
		        enableDD:false,
				collapsible:true,
				split:true,
				border:true,
				title:'组织结构',
				width:200,
				minSize: 180,
				maxSize: 250,
				rootVisible:true,
				containerScroll: true, 
				margins: '0 0 0 0',
		        loader: new Ext.tree.TreeLoader({
		            dataUrl:'../org/tree.do'
		        })
		    });
			westPanel.getSelectionModel().on({
		        'selectionchange' : function(sm, node){
					if(node.id!='0'){
						if(!node.expanded){
							node.expand()
						};
					}
					currentOrgId=node.id;
					Ext.getDom("org.id").value=node.id;
					Ext.getDom("org.name").value=node.text;
					store.proxy= new Ext.data.HttpProxy({
		            	url: '../person/pageBySort.do?obj.org.id='+currentOrgId
		        	});
					store.load({params:{start:0, limit:pageSize}});
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
		initLayout:function(){
			App.initTree();
			App.initGridPanel();
	     	viewport = new Ext.Viewport({
	          layout:'border',
	          items:[{
				  	title:'人员列表',
					el:'center',
					contentEl:'grid',
				  	el:'center',
				  	region:'center',
					layout:'fit',
					margins:'0 0 0 0',
					items:grid
				  },westPanel]
	        });
			store.load({params:{start:0, limit:pageSize}});
    	},
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: '../person/pageBySort.do?obj.org.id='+currentOrgId
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'id',
		            fields: [
		                'id', 'realname', 'firstName', 'lastName', 'gender','marriage','industry','vocation',
						/* {name: 'regionName' , mapping: 'region.name'},*/
						 'birthday','cardId','qq','msn','homepage',
						'sort'
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
	           width: 20,
			   hidden:true
	        },{
	           id: 'realname', 
	           header: "姓名",
	           dataIndex: 'realname',
	           width: 100
	        },{
	           id: 'lastName', 
	           header: "姓",
	           dataIndex: 'lastName',
	           width: 60
	        },{
	           id: 'firstName', 
	           header: "名",
	           dataIndex: 'firstName',
	           width: 60
	        },{
	           id: 'sort', 
	           header: "序号",
	           dataIndex: 'sort',
	           width: 50
	        },{
	           id: 'gender', 
	           header: "性别",
	           dataIndex: 'gender',
	           width: 50
	        },{
	           header: "婚姻状况",
	           dataIndex: 'marriage',
	           width: 80,
			   renderer:formatMarriage
	        },{
	           header: "所在行业",
	           dataIndex: 'industry',
	           width: 80
	        },{
	           header: "职业/职务",
	           dataIndex: 'vocation',
	           width: 80
	        }/*,
			{
	           header: "所在城市",
	           dataIndex: 'regionName',
	           width: 80
	        }*/
			,{
				header: "生日",
	           dataIndex: 'birthday',
	           width: 80,
			   renderer:renderBirth
			},{
				header: "身份证号",
	           dataIndex: 'cardId',
	           width: 80
			},{
				header: "QQ",
	           dataIndex: 'qq',
	           width: 80
			},{
				header: "MSN",
	           dataIndex: 'msn',
	           width: 80
			},{
				header: "个人主页",
	           dataIndex: 'homepage',
	           width: 80
			}]);
	
		    cm.defaultSortable = true;
			
		    grid = new Ext.grid.GridPanel({
		        store: store,
		        cm: cm,
				region:'center',
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        loadMask: true,
				tbar:[{
				  	text: '新增人员',
		            iconCls: 'x-btn-text-icon add',
		            scope: this,
					handler:App.add
				  },'-',{
				  	text: '编辑人员',
		            iconCls: 'x-btn-text-icon edit',
		            scope: this,
					handler:App.edit
				  },'-',{
				  	text: '删除人员',
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
			App.showDlg();
			Ext.getDom('id').value='0';
			Ext.getDom('realname').value='';
			Ext.getDom('firstname').value="";
			Ext.getDom('lastname').value="";
			Ext.getDom('gender').value='m';
			Ext.getDom('birthday').value='';
			Ext.getDom('marriage').value='1';
			Ext.getDom('qualifications').value='3';
			Ext.getDom('vocation').value='';
			Ext.getDom('industry').value='';
			Ext.getDom('postcode').value='';
			Ext.getDom('address').value='';
			Ext.getDom('telephone').value='';
			Ext.getDom('mobilephone').value='';
			Ext.getDom('email').value='';
			Ext.getDom('cardId').value='';
			Ext.getDom('qq').value='';
			Ext.getDom('msn').value='';
			Ext.getDom('sort').value='0';
		}
		,
		showDlg:function(){
			if(!dlg){
				dlg = new Ext.Window({
	                el:'dlg',
	                layout:'fit',
					title: '成员信息',
	                width:600,
	                height:390,
	                closeAction:'hide',
	                plain: true,
					contentEl:'person-center',
					modal:true,
					bodyStyle:'padding:2px;',
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
			Ext.get("realname").focus(false,true);
		}
		,
		edit:function(){
			var select=sm.getSelected();
			if(select==null){
				 Ext.Msg.alert("修改信息","请先选择一个成员！");
				 return;
			}
			operation=ope_edit;
			Ext.Ajax.request({
				url:'../person/load.do',
				params:'obj.id='+select.get('id'),
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var obj="+rep.responseText);
				App.showDlg();
				Ext.getDom('id').value=obj.id;
				Ext.getDom('realname').value=obj.realname;
				Ext.getDom('org.id').value=obj.org.id;
				Ext.getDom('org.name').value=obj.org.title;
				Ext.getDom('region.title').value=obj.region.name;
				Ext.getDom('region.id').value=obj.region.id;
				Ext.getDom('gender').value=obj.gender;
				Ext.getDom('birthday').value=renderBirth(obj.birthday);
				Ext.getDom('marriage').value=obj.marriage;
				Ext.getDom('qualifications').value=obj.qualifications;
				Ext.getDom('vocation').value=obj.vocation;
				Ext.getDom('industry').value=obj.industry;
				Ext.getDom('postcode').value=obj.postcode;
				Ext.getDom('address').value=obj.address;
				Ext.getDom('telephone').value=obj.telephone;
				Ext.getDom('mobilephone').value=obj.mobilephone;
				Ext.getDom('email').value=obj.email;
				Ext.getDom('cardId').value=obj.cardId;
				Ext.getDom('qq').value=obj.qq;
				Ext.getDom('msn').value=obj.msn;
				Ext.getDom('sort').value=obj.sort;
				Ext.getDom('firstname').value = obj.firstName;
				Ext.getDom('lastname').value = obj.lastName;
			}
			function failure(rep){
				Ext.Msg.alert("加载","加载失败！     ");
				Ext.Msg.alert("加载",rep.responseText);
			}
		},
		saveInfo:function(){
			var name=Ext.get("realname").getValue();
			if(name==''){
				Ext.Msg.alert("保存信息","请填写姓名！");
				Ext.get("realname").focus(false,true);
				return;
			}
			var firstname = Ext.getDom('firstname').value;
			if( firstname == ''){
				Ext.Msg.alert("保存信息","请填写姓(Last Name)！");
				Ext.get('firstname').focus(false, true);
				return ;
			}
			var lastname = Ext.getDom('lastname').value;
			if( lastname == ""){
				Ext.Msg.alert("保存信息","请填写名(First Name)！");
				Ext.get('lastname').focus(false, true);
				return ;
			}
			var region=Ext.get("region.id").getValue();
			if(region==''){
				Ext.Msg.alert("保存信息","请选择区域！");
				Ext.get("region.id").focus(false,true);
				return;
			}
			
			var btd=Ext.getDom("birthday");
			if(btd.value==''){
				btd.value="1899-11-30";
			}
			
			var postcode=Ext.getDom("postcode").value;
			if(postcode!=''){
				var patrn=/^[a-zA-Z0-9]{3,12}$/; 
				if(!/^[0-9]+$/.test(postcode)){
					Ext.Msg.alert("保存信息","邮政编码是数字，请重新输入！");
					Ext.get("postCode").focus();
					return;
				}else if(postcode.length!=6){
					Ext.Msg.alert("保存信息","请输入6位邮政编码！");
					Ext.get("postCode").focus();
					return;
				}
			}
			var telephone =Ext.getDom("telephone").value;
			if(telephone!=''){
				var patrn=/^[+]{0,1}(\d){3,4}[-]{1}((\d){7,8})+$/; 
				if (!patrn.exec(telephone)){ 
					Ext.Msg.alert("保存信息","电话号码格式不正确！");
					Ext.get("telephone").focus();
					return;
				} 
				
			}
			var mobilephone=Ext.getDom("mobilephone").value;
			if(mobilephone!=''){
				var patrn=/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/; 
				if (!patrn.exec(mobilephone)){
					 Ext.Msg.alert("保存信息","手机号码格式输入不正确，请重新输入！");
					 Ext.get("mobilephone").focus();
					 return;
				} 
			}
			var emailValue=Ext.getDom("email").value;
			if(emailValue!=""){
				if(!/(\S)+[@]{1}(\S)+[.]{1}(\w)+/.test(emailValue)) {
					 Ext.Msg.alert("保存信息","邮件格式不正确，请重新输入！");
					Ext.get("email").focus();
					return;
				}
			}
			
			Ext.Ajax.request({
				form:'personForm',
				url:'../person/save.do',
				success:success,
				failure:failure
			});
			function success(rep){
				eval("var ss="+rep.responseText);
				var exist=ss.exist;
				if(exist){
					Ext.Msg.alert("保存信息","编号已经存在，请重新填写！");
					Ext.get("name").focus();
					return;
				}
				
				var lastO=store.lastOptions.params;
				var start=lastO.start;
				dlg.hide();
				store.load({params:{start:start, limit:pageSize}});
				
				Ext.Msg.alert("保存信息    ","保存成功！&nbsp;&nbsp;&nbsp;&nbsp;");
				//App.add();
			}
			function failure(rep){
				
			}
		},
		deleteInfo:function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("删除成员","请先选择一个成员！  ");
				return;
			}
			if(!confirm("确定要删除选中的成员吗？")){
				return;
			}
			var node=westPanel.getSelectionModel().getSelectedNode();
			var url='../person/delete.do?obj.org.id='+node.id+"&"+App.getSelectedIds();
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
					var msg="删除成功！";
					if(result.isUsed){
						msg+="但是有的人员信息在使用中，没法删除！";
					}
					var lastO= store.lastOptions.params;
					var start=lastO.start;
					var totRecords= new Number(totalCount);
					if(start>=totRecords){
						store.load({params:{start:start-pageSize, limit:pageSize}});
					}else{
						store.load({params:{start:start, limit:pageSize}});
					}
					Ext.Msg.alert("删除","删除成功！   ");
				}else{
					Ext.Msg.alert("删除","删除失败！   ");
				}
			}
			function failure(rep){
				Ext.Msg.alert("删除","删除失败！  ");
				Ext.Msg.alert("删除",rep.responseText);
			}
		},
		getSelectedIds:function(){
			var ids="";
			var selections=sm.getSelections();
			var size=selections.length;
			for(var i=0;i<size;i++){
					var r=selections[i];
					if(ids.length==0){
						ids="ids="+r.get("id");
					}else{
						ids+="&ids="+r.get("id");
					}
			}
			return ids;
		},
		initOrgTree:function(){
			if (!orgTree) {
				orgTree = new Ext.tree.TreePanel({
					el: 'orgTree',
					autoScroll: true,
					animate: true,
					enableDD: false,
					split: true,
					border: true,
					width: 200,
					minSize: 180,
					maxSize: 250,
					rootVisible: true,
					containerScroll: true,
					margins: '0 0 0 0',
					loader: new Ext.tree.TreeLoader({
						dataUrl: '../org/tree.do'
					})
					
				});
			}
			orgTree.getSelectionModel().on({
		        'selectionchange' : function(sm, node){
					if(node.id!='0'){
						if(!node.expanded){
							node.expand()
						};
					}
		        },
		        scope:this
		    });
			var root = new Ext.tree.AsyncTreeNode({
		        text: '当前组织',
		        draggable:false,
		        id:'0'
		    });
		    orgTree.setRootNode(root);
		
			orgTree.render();
			root.select();
			root.expand();
		}
		,
		showOrgDlg:function(){
			if(!orgDlg){
				App.initOrgTree();
				orgDlg = new Ext.Window({
	                el:'orgDlg',
	                layout:'fit',
					contentEl:'orgTree',
	                width:300,
					title:'选择组织',
	                height:250,
					items:[orgTree],
	                closeAction:'hide',
	                plain: true,
					modal:true,
	                buttons: [{
		                    text:'确定',
		                    disabled:false,
							handler:function(){
								App.selectOrg();
							}
		                },{
	                    	text:'取消',
	                    	disabled:false,
							handler:function(){
								orgDlg.hide();
							}
		                }]
	            	}
				);
			}
			orgDlg.show(this);
		},
		selectOrg:function(){
			var node=orgTree.getSelectionModel().getSelectedNode();
			Ext.getDom("org.id").value=node.id;
			Ext.getDom("org.name").value=node.text;
			orgDlg.hide();
		}
		,
		initRegionTree:function(){
			if (!regionTree) {
				regionTree = new Ext.tree.TreePanel({
					el: 'regionTree',
					autoScroll: true,
					animate: true,
					enableDD: false,
					split: true,
					border: true,
					width: 200,
					minSize: 180,
					maxSize: 250,
					rootVisible: false,
					containerScroll: true,
					margins: '0 0 0 0',
					loader: new Ext.tree.TreeLoader({
						dataUrl: '../region/tree.do'
					})
					
				});
			}
			var root = new Ext.tree.AsyncTreeNode({
		        text: '所有区域',
		        draggable:false,
		        id:'0'
		    });
			regionTree.getSelectionModel().on({
		        'selectionchange' : function(sm, node){
					if(node.id!='0'){
						if(!node.expanded){
							node.expand()
						};
					}
		        },
		        scope:this
		    });
		    regionTree.setRootNode(root);
			regionTree.render();
			root.select();
			root.expand();
		}
		,
		showRegionDlg:function(){
			if(!regionDlg){
				App.initRegionTree();
				regionDlg = new Ext.Window({
	                el:'regionDlg',
	                layout:'fit',
					title:'选择区域',
					contentEl:'regionTree',
	                width:300,
	                height:250,
					items:[regionTree],
	                closeAction:'hide',
	                plain: true,
					modal:true,
	                buttons: [{
		                    text:'确定',
		                    disabled:false,
							handler:function(){
								App.selectRegion();
							}
		                },{
	                    	text:'取消',
	                    	disabled:false,
							handler:function(){
								regionDlg.hide();
							}
		                }]
	            	}
				);
			}
			regionDlg.show(this);
		},
		selectRegion:function(){
			var node=regionTree.getSelectionModel().getSelectedNode();
			Ext.getDom("region.id").value=node.id;
			Ext.getDom("region.title").value=node.text;
			regionDlg.hide();
			
		}
	};
}();
Ext.onReady(App.init);