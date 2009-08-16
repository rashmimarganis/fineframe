var CacheApp= function(){
	var store;
	var grid;
	var sm;
	return {
		init:function(){
			CacheApp.initStore();
			CacheApp.initGridPanel();
		}
		,
		initStore:function(){
			store = new Ext.data.Store({
		        proxy: new Ext.data.HttpProxy({
		            url: 'cache/list.jhtm'
		        }),
		        reader: new Ext.data.JsonReader({
		            root: 'objs',
		            totalProperty: 'totalCount',
		            id: 'name',
		            fields: [
		                'name',  'onDiskHints','accuracyDescription','evictionCount','averageGetTime','size',
						'guid','cacheHints','diskStoreSize','memoryStoreSize','cacheMisses','accuracy'
		            ]
		        }),
		        remoteSort: false
		    });
		   store.setDefaultSort('name', 'desc');
			
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
			sm = new Ext.grid.CheckboxSelectionModel();
			var cm = new Ext.grid.ColumnModel([sm,{
	           id: 'name', 
	           header: "缓存名称",
	           dataIndex: 'name',
	           width: 180,
			   sort:false
	        },{
	           header: "缓存总数",
	           dataIndex: 'size',
	           width: 100,
			   align:'right',
			   sort:false
	        },{
	           header: "缓存命中数",
	           dataIndex: 'cacheHints',
	           width: 100,
			   align:'right',
			   sort:false
	        },{
	           header: "硬盘中缓存数",
	           dataIndex: 'diskStoreSize',
	           width: 150,
			   align:'right',
			   sort:false
	        },{
	           header: "内存缓存数",
	           dataIndex: 'memoryStoreSize',
	           width: 150,
			   align:'right',
			   sort:false
	        },{
	           header: "缓存未中数",
	           dataIndex: 'cacheMisses',
	           width: 150,
			   align:'right',
			   sort:false
	        },{
	           header: "平均获取时间",
	           dataIndex: 'averageGetTime',
	           width: 150,
			   align:'right',
			   sort:false
	        },{
	           header: "精确性",
	           dataIndex: 'accuracy',
	           width: 150,
			   align:'right',
			   sort:false
	        },{
	           header: "精确性描述",
	           dataIndex: 'accuracyDescription',
	           width: 80,
			   align:'right',
			   sort:false
	        }]);
			
		    cm.defaultSortable = true;
		    grid = new Ext.grid.GridPanel({
		    	el:'cacheGrid',
		    	region:'center',
		        store: store,
		        cm: cm,
				sm: sm,
		        trackMouseOver:true,
		        frame:false,
		        autoScroll:true,
		        height:485,
		        loadMask: true,
				tbar:[{
				  	text: '清空缓存',
		            iconCls: 'x-btn-text-icon refresh',
		            scope: this,
					handler:CacheApp.clear
				 },'-',
				 {
				  	text: '全部清空',
		            iconCls: 'x-btn-text-icon refresh',
		            scope: this,
					handler:CacheApp.clearAll
				 }],  
				viewConfig: {
					enableRowBody:true,
           			showPreview:true,
		            forceFit:true
		        }
				
		    });
		    
		    grid.render();
		   
		    mainPanel.add(grid);

			store.load();
			
		},
		clear : function(){
			if(sm.getSelected==null){
				Ext.Msg.alert("清空缓存","请先选择一条记录！");
				return;
			}
			if(!confirm("确定要清空选中的缓存吗？")){
				return;
			}
			var url='cache/clear.jhtm?'+CacheApp.getSelectedIds();
		
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
			function success(rep){
				store.reload();
				Ext.Msg.alert("清空缓存","缓存清空成功！");
			}
			function failure(rep){
				Ext.Msg.alert("清空缓存","缓存清空失败！");
				Ext.Msg.alert("清空缓存","<div style='height:200px;overflow-y:auto;'>"+rep.responseText+"</div>");
			}
		}
		,
		clearAll : function(){
			
			if(!confirm("确定要清空全部缓存吗？")){
				return;
			}
			var url='cache/clearAll.jhtm';
		
			Ext.Ajax.request({
				url:url,
				success:success,
				failure:failure
			});
			function success(rep){
				store.reload();
				Ext.Msg.alert("清空缓存","缓存清空成功！");
			}
			function failure(rep){
				Ext.Msg.alert("清空缓存","缓存清空失败！");
				Ext.Msg.alert("清空缓存","<div style='height:200px;overflow-y:auto;'>"+rep.responseText+"</div>");
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
						ids="names="+r.get("id");
					}else{
						ids+="&names="+r.get("id");
					}
			}
			return ids;
			
		}
	};
}();
CacheApp.init();