package com.izhi.cms.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.izhi.cms.model.Block;
import com.izhi.cms.service.IBlockService;
import com.izhi.platform.action.BasePageAction;
import com.izhi.platform.util.PageParameter;
@Service
@Scope(value="prototype")
@Namespace("/block")
public class BlockAction extends BasePageAction{

	private static final long serialVersionUID = 8190220809475487574L;
	@Resource(name="blockService")
	private IBlockService blockService;
	private Block obj;
	private List<Integer> ids;
	
	private int id;
	
	
	@Action("list")
	public String list(){
		PageParameter pp=this.getPageParameter();
		int totalCount=(int)blockService.findTotalCount();
		pp.setCurrentPage(p);
		pp.setTotalCount(totalCount);
		pp.setSort("blockId");
		pp.setDir("desc");
		List<Block> l=blockService.findPage(pp);
		this.getRequest().setAttribute("objs", l);
		this.getRequest().setAttribute("page", pp);
		return SUCCESS;
	}
	@Action("add")
	public String add(){
		obj=new Block();
		return SUCCESS;
	}
	@Action("load")
	public String load(){
		obj=blockService.findBlockById(id);
		return SUCCESS;
	}
	
	@Action("delete")
	public String delete(){
		boolean i=blockService.deleteBlock(id);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	@Action("deletes")
	public String deletes(){
		log.debug("Id size:"+ids.size());
		boolean i=blockService.deleteBlocks(ids);
		this.getRequest().setAttribute("success", i);
		return SUCCESS;
	}
	
	@Action("save")
	public String save(){
		
		if(obj.getBlockId()==0){
			int i=blockService.saveBlock(obj);
			this.getRequest().setAttribute("success", i>0);
		}else{
			boolean i=blockService.updateBlock(obj);
			this.getRequest().setAttribute("success", i);
		}
		return SUCCESS;
	}
	
	public IBlockService getBlockService() {
		return blockService;
	}
	public void setBlockService(IBlockService blockService) {
		this.blockService = blockService;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public Block getObj() {
		return obj;
	}
	public void setObj(Block obj) {
		this.obj = obj;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
