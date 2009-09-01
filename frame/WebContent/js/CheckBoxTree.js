Ext.tree.CheckNodeUI = function() {
	Ext.tree.CheckboxNode.superclass.constructor.apply(this, arguments);
};
Ext.tree.CheckNodeUI = Ext.extend(Ext.tree.TreeNodeUI, {
    renderElements : function(n, a, targetNode, bulkRender){
		 this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : '';
		
        var t = n.getOwnerTree();
     
        var bw = t.borderWidth;
		this.indentMarkup = "";
		if(n.parentNode){
			this.indentMarkup = n.parentNode.ui.getChildIndent();
		}
        var buf = [
             '<li class="x-tree-node">',
			 	'<div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf ', a.cls,'">',
	                '<div class="x-tree-col">',
	                    '<span class="x-tree-node-indent">',this.indentMarkup,"</span>",
	                    '<img src="', this.emptyIcon, '" class="x-tree-ec-icon">',
	                    '<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',
						(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on">',
	                    '<input style="margin-left:2px" name="nodeCheck" value="',n.id,'" type="checkbox" ', (a.checked ? "checked>" : '>'),
						'<a hidefocus="on" class="x-tree-node-anchor" href="',a.href ? a.href : "#",'" tabIndex="1" ',
	                    a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '>',
	                    '<span unselectable="on">',
						 n.text,'</span></a>',
	                "</div>",
				'<div class="x-clear"></div></div>',
            	'<ul class="x-tree-node-ct" style="display:none;"></ul>',
            "</li>"
				];
       
   

        if(bulkRender !== true && n.nextSibling && n.nextSibling.ui.getEl()){
            this.wrap = Ext.DomHelper.insertHtml("beforeBegin",
                                n.nextSibling.ui.getEl(), buf.join(""));
        }else{
            this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf.join(""));
        }

        this.elNode = this.wrap.childNodes[0];
        this.ctNode = this.wrap.childNodes[1];
		
        var cs = this.elNode.firstChild.childNodes;
        this.indentNode = cs[0];
        this.ecNode = cs[1];
        this.iconNode = cs[2];
		this.checkbox = cs[3];
        this.anchor = cs[4];
        this.textNode = cs[4].firstChild;
		Ext.fly(this.checkbox).on('change', this.check.createDelegate(this, [null]));
		n.on('dblclick', function(e) {
			if( this.isLeaf() ) {
				this.getUI().toggleCheck();
			}
		});
		
		if(!this.node.expanded){
			this.updateExpandIcon();
		}
		
		
	},

	checked : function() {
		return this.checkbox.checked;
	},
	
	check : function(state, descend) {
		var _node = this.node;
		var _tree = _node.getOwnerTree();
		var parentNode = _node.parentNode;
		
		if( typeof state == 'undefined' || state === null ) {
			state = this.checkbox.checked;
			descend = true;
		} else {
			this.checkbox.checked = state;
		}
		//_tree.checked[_node.id] = state;

		// do we have parents?
		if( parentNode !== null && state ) {
			parentNode.getUI().check(state);
		}
		/*
		if( descend && !_node.isLeaf() ) {
			var cs = _node.childNodes;
			for(var i = 0; i < cs.length; i++) {
			  cs[i].getUI().check(state, true);
			}
		}
		*/
	},
	
	toggleCheck : function() {
		this.check(!this.checkbox.checked, true);
	}
});


