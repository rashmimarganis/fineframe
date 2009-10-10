CmsCategoryTree=function(){
	var _tree=this;
	
	CmsCategoryTree.superclass.constructor.call(this, {
		el:'cmsCategoryTree',
        autoScroll:true,
        animate:true,
        enableDD:false,
		split:true,
		width:200,
		height:200,
		region:'west',
		minSize: 180,
		maxSize: 250,
		rootVisible:false,
		containerScroll: true, 
		margins: '0 0 0 0',
        loader: new Ext.tree.TreeLoader({
            dataUrl:'cms/category/all.jhtm'
        })
    });
	 
};

Ext.extend(CmsCategoryTree, Ext.tree.TreePanel, {
	loadInfo:function(){
	
	}
});


function init(){
    var tree=new CmsCategoryTree();
    var root = new Ext.tree.AsyncTreeNode({
        text: '所有栏目',
        draggable:false,
        id:'0'
    });
    tree.setRootNode(root);
	
	tree.render();
	root.select();
	root.expand();
	var panel=Ext.Panel({
		layout:'border',
		el:'cmsCategory',
		contentEl:'cmsCategoryTree',
		height:100,
		width:200,
		items:[tree]
	});
	FineCmsMain.addFunctionPanel(panel);
}

init();